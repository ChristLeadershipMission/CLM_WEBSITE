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
import worldwide.clm.clmwebsite.services.eventServices.EventService;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;

import java.util.ArrayList;
import java.util.List;

import static worldwide.clm.clmwebsite.common.Message.*;
import static worldwide.clm.clmwebsite.utils.AppUtils.DEFAULT_COORDINATING_MINISTER_ID;

@Service
@RequiredArgsConstructor
public class CampusServiceImpl implements CampusService {

    private final CampusRepository campusRepository;
    private final ModelMapper modelMapper;

    @Override
    public CampusDetailsResponse createCampus(CampusCreationRequest campusCreationRequest, MinisterService ministerService) throws CampusAlreadyExistsException, UserNotFoundException {
        ministerService.findById(campusCreationRequest.getMinisterInChargeId());
        if (campusRepository.findCampusByName(campusCreationRequest.getName()).isPresent())
            throw new CampusAlreadyExistsException(
                    String.format(CAMPUS_WITH_NAME_ALREADY_EXISTS, campusCreationRequest.getName())
            );
        Campus campus = modelMapper.map(campusCreationRequest, Campus.class);
        campus.setId(null);
        return getCampusResponse(campusRepository.save(campus), ministerService);
    }

    @Override
    public CampusDetailsResponse updateCampusDetails(Long id, CampusUpdateRequest campusCreationRequest, MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException {
        Campus foundCampus = campusRepository.findById(id).orElseThrow(
                () -> new CampusNotFoundException(String.format(CAMPUS_WITH_ID_NOT_FOUND, id))
        );
        if (campusCreationRequest.getEmail() != null && campusCreationRequest.getEmail() != "") {
            foundCampus.setEmail(campusCreationRequest.getEmail());
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
        return getCampusResponse(campusRepository.save(foundCampus), ministerService);
    }

    @Override
    public Long getCount() {
        return campusRepository.count();
    }

    @Override
    public List<CampusDetailsResponse> searchByName(String name, MinisterService ministerService) throws UserNotFoundException {
        return getCampusDetailsResponses(campusRepository.searchAllByNameContainingIgnoreCase(name), ministerService);
    }

    private CampusDetailsResponse getCampusResponse(Campus updatedCampus, MinisterService ministerService) throws UserNotFoundException {
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
    public CampusDetailsResponse findCampusById(Long id, MinisterService ministerService) throws CampusNotFoundException, UserNotFoundException {
        Campus foundCampus = campusRepository.findById(id).orElseThrow(
                () -> new CampusNotFoundException(String.format(CAMPUS_WITH_ID_NOT_FOUND, id))
        );
        return getCampusResponse(foundCampus, ministerService);
    }

    @Override
    public CampusDetailsResponse findCampusByName(String name, MinisterService ministerService) throws CampusNotFoundException, UserNotFoundException {
        return getCampusResponse(
                campusRepository.findCampusByName(name).orElseThrow(
                        () -> new CampusNotFoundException(String.format(CAMPUS_WITH_NAME_NOT_FOUND, name))
                ),
                ministerService);
    }

    @Override
    public List<CampusDetailsResponse> findAllCampuses(MinisterService ministerService) throws UserNotFoundException {
        return getCampusDetailsResponses(campusRepository.findAll(), ministerService);
    }

    private List<CampusDetailsResponse> getCampusDetailsResponses(List<Campus> campuses, MinisterService ministerService) throws UserNotFoundException {
        List<CampusDetailsResponse> campusDetailsResponses = new ArrayList<>();
        for (var each : campuses) {
            campusDetailsResponses.add(getCampusResponse(each, ministerService));
        }
        return campusDetailsResponses;
    }

    @Override
    public void removeCampus(Long id, EventService eventService) throws CampusNotFoundException {
        eventService.resetToDefaultCampusEventsWithId(id);
        var campus = campusRepository.findById(id);
        if (campus.isEmpty()) {
            throw new CampusNotFoundException(String.format(CAMPUS_WITH_ID_NOT_FOUND, id));
        }
        campusRepository.delete(campus.get());
    }

    @Override
    public void resetToDefaultMinisterCampusesWithId(Long ministerId) {
        for (Campus campus : campusRepository.findAllByMinisterInChargeId(ministerId)) {
            campus.setMinisterInChargeId(Long.valueOf(DEFAULT_COORDINATING_MINISTER_ID));
            campusRepository.save(campus);
        }
    }
}
