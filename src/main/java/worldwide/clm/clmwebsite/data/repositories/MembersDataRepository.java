package worldwide.clm.clmwebsite.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.data.models.MembersData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MembersDataRepository extends JpaRepository<MembersData, Long> {
	Optional<MembersData> findByEmailAddress(String email);
	Optional<MembersData> findByPhoneNumber(String phoneNumber);

	@Query("SELECT m FROM members m WHERE MONTH(m.dob) = :month ORDER BY m.dob asc")
	List<MembersData> findAllByMonthOfBirth(int month);

	@Query("SELECT m FROM members m WHERE MONTH(m.dob) = :month and m.isNotificationSent = :notification ORDER BY m.dob asc")
	List<MembersData> findAllByMonthOfBirthAndNotificationSent(int month, boolean notification);
}
