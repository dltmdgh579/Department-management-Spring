package ministryofeducation.sideprojectspring.personnel_list.infrastructure;

import java.util.List;
import java.util.Optional;
import ministryofeducation.sideprojectspring.personnel_list.domain.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnelListRepository extends JpaRepository<Personnel, Long> {
    List<Personnel> findAll();
}
