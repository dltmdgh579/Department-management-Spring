package ministryofeducation.sideprojectspring.department.infrastructure;

import java.util.Optional;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
}
