package worldwide.clm.clmwebsite.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import worldwide.clm.clmwebsite.data.models.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
