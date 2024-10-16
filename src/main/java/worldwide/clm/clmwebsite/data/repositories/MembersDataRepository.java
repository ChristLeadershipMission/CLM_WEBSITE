package worldwide.clm.clmwebsite.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.data.models.MembersData;

import java.util.Optional;

public interface MembersDataRepository extends JpaRepository<MembersData, Long> {
	Optional<MembersData> findByEmailAddress(String email);
	Optional<MembersData> findByPhoneNumber(String phoneNumber);
}
