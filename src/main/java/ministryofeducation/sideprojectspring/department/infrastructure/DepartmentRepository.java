package ministryofeducation.sideprojectspring.department.infrastructure;

import ministryofeducation.sideprojectspring.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
