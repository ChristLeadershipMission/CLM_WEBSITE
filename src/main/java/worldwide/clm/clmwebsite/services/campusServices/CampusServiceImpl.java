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
import worldwide.clm.clmwebsite.exception.BusinessLogicException;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CampusServiceImpl implements CampusService{

    private final CampusRepository campusRepository;
    @Override
    public void createCampus(CampusCreationRequest campusCreationRequest) throws BusinessLogicException {
        if (campusRepository.findCampusByName(campusCreationRequest.getName()).isPresent()) throw new CampusAlreadyExistsException(
                String.format("%s already exists", campusCreationRequest.getName())
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
    public void removeCampus(Long id) throws BusinessLogicException {
        Campus campus = campusRepository.findCampusById(id);
        if(campus == null){
            throw new BusinessLogicException("Campus with ID " + id + " not found");
        }
        campusRepository.delete(campus);
    }
}
