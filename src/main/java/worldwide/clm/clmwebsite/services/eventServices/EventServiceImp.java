package worldwide.clm.clmwebsite.services.eventServices;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Event;
import worldwide.clm.clmwebsite.data.repositories.EventRepository;
import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.EventResponse;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.EventNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.campusServices.CampusService;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static worldwide.clm.clmwebsite.common.Message.*;
import static worldwide.clm.clmwebsite.utils.AppUtils.DEFAULT_CENTER_CAMPUS_ID;

@Service
@AllArgsConstructor
public class EventServiceImp implements EventService {

    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private final CampusService campusService;

    @Override
    public ApiResponse createEvent(EventCreationRequest eventCreationRequest, MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException {
        campusService.findCampusById(eventCreationRequest.getCampusId(), ministerService);
        Event event = modelMapper.map(eventCreationRequest, Event.class);
        event.setId(null);
        eventRepository.save(event);
        return ResponseUtils.created(EVENT_CREATED_SUCCESSFULLY);
    }

    @Override
    public ApiResponse deleteEventByEventId(Long id) {
        var event = eventRepository.findById(id);
        if (event.isEmpty()) return ResponseUtils.noEventFound(NO_EVENT_FOUND);
        eventRepository.delete(event.get());
        return ResponseUtils.eventDeleted(EVENT_DELETED_SUCCESSFULLY);
    }

    @Override
    public ApiResponse updateEventInfo(EventUpdateRequest eventUpdateRequest, MinisterService ministerService) throws EventNotFoundException, UserNotFoundException, CampusNotFoundException {
        findById(eventUpdateRequest.getId(), ministerService);
        updateEvent(eventUpdateRequest);
        return ResponseUtils.updated(EVENT_UPDATED_SUCCESSFULLY);
    }

    @Override
    public List<EventResponse> findAll(MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException {
        return getEventResponses(eventRepository.findAll(), ministerService);
    }

    private List<EventResponse> getEventResponses(List<Event> foundEvents, MinisterService ministerService) throws CampusNotFoundException, UserNotFoundException {
        System.out.println(foundEvents);
        List<EventResponse> events = new ArrayList<>();
        for (Event each : foundEvents) {
            EventResponse eventResponse = modelMapper.map(each, EventResponse.class);
            eventResponse.setCampus(campusService.findCampusById(each.getCampusId(), ministerService));
            events.add(eventResponse);
        }
        events.sort(Comparator.comparing(EventResponse::getStartDate));
        return events;
    }

    @Override
    public List<EventResponse> findByCampusId(Long campusId, MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException {
        return getEventResponses(eventRepository.findAllByCampusId(campusId), ministerService);
    }

    @Override
    public Long getCount() {
        return eventRepository.count();
    }

    @Override
    public List<EventResponse> searchByName(String name, MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException {
        return getEventResponses(eventRepository.searchAllByEventNameContainingIgnoreCase(name), ministerService);
    }

    private Event updateEvent(EventUpdateRequest eventUpdateRequest) {
        Event event = eventRepository.findById(eventUpdateRequest.getId()).get();
        if (eventUpdateRequest.getEventName() != null) {
            event.setEventName(eventUpdateRequest.getEventName());
        }
        if (eventUpdateRequest.getStartDate() != null) {
            event.setStartDate(eventUpdateRequest.getStartDate());
        }
        if (eventUpdateRequest.getEndDate() != null) {
            event.setEndDate(eventUpdateRequest.getEndDate());
        }
        if (eventUpdateRequest.getEventImageUrl() != null) {
            event.setEventImageUrl(eventUpdateRequest.getEventImageUrl());
        }
        if (eventUpdateRequest.getEventVideoUrl() != null) {
            event.setEventVideoUrl(eventUpdateRequest.getEventVideoUrl());
        }
        return eventRepository.save(event);
    }

    public EventResponse findById(Long id, MinisterService ministerService) throws EventNotFoundException, UserNotFoundException, CampusNotFoundException {
        Event foundEvent = eventRepository.findById(id).orElseThrow(
                () -> new EventNotFoundException(NO_EVENT_FOUND)
        );
        EventResponse eventResponse = modelMapper.map(foundEvent, EventResponse.class);
        eventResponse.setCampus(campusService.findCampusById(foundEvent.getCampusId(), ministerService));
        return eventResponse;
    }

    @Override
    public void resetToDefaultCampusEventsWithId(Long campusId) {
        for (Event event :eventRepository.findAllByCampusId(campusId)) {
            event.setCampusId(Long.valueOf(DEFAULT_CENTER_CAMPUS_ID));
            eventRepository.save(event);
        }
    }
}
