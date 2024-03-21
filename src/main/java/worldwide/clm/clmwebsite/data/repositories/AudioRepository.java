package worldwide.clm.clmwebsite.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import worldwide.clm.clmwebsite.data.models.Audio;

public interface AudioRepository extends JpaRepository<Audio, Long> {
}
