package ministryofeducation.sideprojectspring.personnel.infrastructure;

import java.util.List;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnelListRepository extends JpaRepository<Personnel, Long> {
    List<Personnel> findAll();
}
