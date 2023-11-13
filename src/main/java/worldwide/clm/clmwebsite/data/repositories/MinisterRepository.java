package worldwide.clm.clmwebsite.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.data.models.Minister;

import java.util.Optional;

public interface MinisterRepository extends JpaRepository<Minister, Long> {
	Optional<Minister> findByEmailAddress(String email);
}
