package ministryofeducation.sideprojectspring.auth.infrastructure;

import java.util.Optional;
import ministryofeducation.sideprojectspring.auth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

}
