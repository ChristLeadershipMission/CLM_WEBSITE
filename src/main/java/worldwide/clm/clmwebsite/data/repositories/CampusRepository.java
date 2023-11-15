package worldwide.clm.clmwebsite.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import worldwide.clm.clmwebsite.data.models.Campus;

import java.util.List;
import java.util.Optional;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findCampusByName(String name);
    Campus findCampusById(Long id);
    List<Campus> searchAllByNameContainingIgnoreCase(String name);
}
