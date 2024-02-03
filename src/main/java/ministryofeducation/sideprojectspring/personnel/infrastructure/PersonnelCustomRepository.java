package ministryofeducation.sideprojectspring.personnel.infrastructure;

import java.util.List;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;

public interface PersonnelCustomRepository {

    List<PersonnelListResponse> findAllByCondition(PersonnelCondRequest condition);

}
