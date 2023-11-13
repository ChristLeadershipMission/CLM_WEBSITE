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
import worldwide.clm.clmwebsite.exception.EventNotFoundException;
import worldwide.clm.clmwebsite.services.campusServices.CampusService;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.util.ArrayList;
import java.util.List;

import static worldwide.clm.clmwebsite.common.Message.*;

@Service
@AllArgsConstructor
public class EventServiceImp implements EventService {

    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private final CampusService campusService;

    @Override
    public ApiResponse createEvent(EventCreationRequest eventCreationRequest) {
        Event event = modelMapper.map(eventCreationRequest, Event.class);
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
    public ApiResponse updateEventInfo(EventUpdateRequest eventUpdateRequest) throws EventNotFoundException {
        findById(eventUpdateRequest.getId());
        Event event = updateEvent(eventUpdateRequest);
        return ResponseUtils.updated(EVENT_UPDATED_SUCCESSFULLY);
    }

    @Override
    public List<EventResponse> findAll() {
        List<EventResponse> events = new ArrayList<>();
        for (Event each : eventRepository.findAll()) {
            events.add(modelMapper.map(each, EventResponse.class));
        }
        return events;
    }

    @Override
    public List<EventResponse> findByCampusId(Long campusId) {
        List<EventResponse> events = new ArrayList<>();
        eventRepository.findByCampusId(campusId).map(each -> events.add(modelMapper.map(each, EventResponse.class)));
        return events;
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

    public EventResponse findById(Long id) throws EventNotFoundException {
        Event foundEvent = eventRepository.findById(id).orElseThrow(
                () -> new EventNotFoundException(NO_EVENT_FOUND)
        );
        EventResponse eventResponse = modelMapper.map(foundEvent, EventResponse.class);
        eventResponse.setCampus(campusService.findCampusById(foundEvent.getCampusId()).get());
        return eventResponse;
    }
}
