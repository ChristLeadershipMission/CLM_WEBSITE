package worldwide.clm.clmwebsite.data.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import worldwide.clm.clmwebsite.data.models.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByEventName(String eventName);
    List<Event> findAllByCampusId(Long campusId);
    List<Event> searchAllByEventNameContainingIgnoreCase(String name);

}
