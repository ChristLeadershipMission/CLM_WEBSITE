package worldwide.clm.clmwebsite.services.campusServices;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.data.repositories.CampusRepository;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;

import java.util.List;
import java.util.Optional;

import static worldwide.clm.clmwebsite.common.Message.CAMPUS_WITH_ID_ALREADY_EXISTS;
import static worldwide.clm.clmwebsite.common.Message.CAMPUS_WITH_NAME_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class CampusServiceImpl implements CampusService{

    private final CampusRepository campusRepository;
    @Override
    public void createCampus(CampusCreationRequest campusCreationRequest) throws CampusAlreadyExistsException {
        if (campusRepository.findCampusByName(campusCreationRequest.getName()).isPresent()) throw new CampusAlreadyExistsException(
                String.format(CAMPUS_WITH_NAME_ALREADY_EXISTS, campusCreationRequest.getName())
        );
        Campus campus = Campus.builder()
                .name(campusCreationRequest.getName())
                .ministerInCharge(campusCreationRequest.getMinisterInCharge())
                .email(campusCreationRequest.getEmail())
                .address(campusCreationRequest.getAddress())
                .build();
        campusRepository.save(campus);
    }

    @Override
    public CampusDetailsResponse updateCampusDetails(Long id, JsonPatch updatePayLoad) {
        ObjectMapper mapper = new ObjectMapper();
        Campus foundCampus = campusRepository.getReferenceById(id);
        JsonNode node = mapper.convertValue(foundCampus, JsonNode.class);
        try{
            JsonNode updatedNode = updatePayLoad.apply(node);
            Campus updatedCampus = mapper.convertValue(updatedNode, Campus.class);
            updatedCampus = campusRepository.save(updatedCampus);
            return getCampusResponse(updatedCampus);
        } catch (JsonPatchException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    private CampusDetailsResponse getCampusResponse(Campus updatedCampus) {
        return CampusDetailsResponse.builder()
                .name(updatedCampus.getName())
                .ministerInCharge(updatedCampus.getMinisterInCharge())
                .address(updatedCampus.getAddress())
                .build();
    }

    @Override
    public Optional<Campus> findCampusById(Long id) {
        return campusRepository.findById(id);
    }

    @Override
    public Optional<Campus> findCampusByName(String name){
        return campusRepository.findCampusByName(name);
    }

    @Override
    public List<Campus> findAllCampuses() {
        return campusRepository.findAll();
    }

    @Override
    public void removeCampus(Long id) throws CampusNotFoundException {
        Campus campus = campusRepository.findCampusById(id);
        if(campus == null){
            throw new CampusNotFoundException(String.format(CAMPUS_WITH_ID_ALREADY_EXISTS, id));
        }
        campusRepository.delete(campus);
    }
}
