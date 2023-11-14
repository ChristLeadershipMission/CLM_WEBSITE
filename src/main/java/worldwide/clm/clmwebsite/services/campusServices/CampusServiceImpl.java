package worldwide.clm.clmwebsite.services.campusServices;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.data.repositories.CampusRepository;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.request.CampusUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static worldwide.clm.clmwebsite.common.Message.*;

@Service
@RequiredArgsConstructor
public class CampusServiceImpl implements CampusService {

    private final CampusRepository campusRepository;
    private final ModelMapper modelMapper;
    private final MinisterService ministerService;

    @Override
    public CampusDetailsResponse createCampus(CampusCreationRequest campusCreationRequest) throws CampusAlreadyExistsException, UserNotFoundException {
        ministerService.findById(campusCreationRequest.getMinisterInChargeId());
        if (campusRepository.findCampusByName(campusCreationRequest.getName()).isPresent())
            throw new CampusAlreadyExistsException(
                    String.format(CAMPUS_WITH_NAME_ALREADY_EXISTS, campusCreationRequest.getName())
            );
        Campus campus = modelMapper.map(campusCreationRequest, Campus.class);
        campus.setId(null);
        return getCampusResponse(campusRepository.save(campus));
    }

    @Override
    public CampusDetailsResponse updateCampusDetails(Long id, CampusUpdateRequest campusCreationRequest) throws UserNotFoundException, CampusNotFoundException {
        Campus foundCampus = campusRepository.findById(id).orElseThrow(
                () -> new CampusNotFoundException(String.format(CAMPUS_WITH_ID_NOT_FOUND, id))
        );
        if (campusCreationRequest.getEmail() != null && campusCreationRequest.getEmail() != "") {
            foundCampus.setEmail(foundCampus.getEmail());
        }
        if (campusCreationRequest.getName() != null && campusCreationRequest.getName() != "") {
            foundCampus.setName(campusCreationRequest.getName());
        }
        if (campusCreationRequest.getMinisterInChargeId() != null) {
            ministerService.findById(campusCreationRequest.getMinisterInChargeId());
            foundCampus.setMinisterInChargeId(campusCreationRequest.getMinisterInChargeId());
        }
        if (campusCreationRequest.getLogo() != null && campusCreationRequest.getLogo() != "") {
            foundCampus.setLogo(campusCreationRequest.getLogo());
        }
        return getCampusResponse(campusRepository.save(foundCampus));
    }

    private CampusDetailsResponse getCampusResponse(Campus updatedCampus) throws UserNotFoundException {
        return CampusDetailsResponse.builder()
                .id(updatedCampus.getId())
                .logo(updatedCampus.getLogo())
                .email(updatedCampus.getEmail())
                .name(updatedCampus.getName())
                .ministerInCharge(ministerService.findById(updatedCampus.getMinisterInChargeId()))
                .address(updatedCampus.getAddress())
                .build();
    }

    @Override
    public CampusDetailsResponse findCampusById(Long id) throws CampusNotFoundException, UserNotFoundException {
        Campus foundCampus = campusRepository.findById(id).orElseThrow(
                () -> new CampusNotFoundException(String.format(CAMPUS_WITH_ID_NOT_FOUND, id))
        );
        return getCampusResponse(foundCampus);
    }

    @Override
    public CampusDetailsResponse findCampusByName(String name) throws CampusNotFoundException, UserNotFoundException {
        return getCampusResponse(
                campusRepository.findCampusByName(name).orElseThrow(
                        () -> new CampusNotFoundException(String.format(CAMPUS_WITH_NAME_NOT_FOUND, name))
                )
        );
    }

    @Override
    public List<CampusDetailsResponse> findAllCampuses() throws UserNotFoundException {
        List<CampusDetailsResponse> campusDetailsResponses = new ArrayList<>();
        for (var each : campusRepository.findAll()) {
            campusDetailsResponses.add(getCampusResponse(each));
        }
        return campusDetailsResponses;
    }

    @Override
    public void removeCampus(Long id) throws CampusNotFoundException {
        Campus campus = campusRepository.findCampusById(id);
        if (campus == null) {
            throw new CampusNotFoundException(String.format(CAMPUS_WITH_ID_NOT_FOUND, id));
        }
        campusRepository.delete(campus);
    }
}
