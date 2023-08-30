package worldwide.clm.clmwebsite.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import worldwide.clm.clmwebsite.data.models.Member;
//@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findMemberByEmail(String email);
}
