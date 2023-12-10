package ministryofeducation.sideprojectspring.department.infrastructure;

import java.util.List;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmallGroupRepository extends JpaRepository<SmallGroup, Long> {
    List<SmallGroup> findByDepartmentId(Long departmentId);
}
