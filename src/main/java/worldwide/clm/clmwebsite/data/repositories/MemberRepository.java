package worldwide.clm.clmwebsite.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import worldwide.clm.clmwebsite.data.models.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findMemberByEmail(String email);
}
