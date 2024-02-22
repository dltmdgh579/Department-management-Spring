package ministryofeducation.sideprojectspring.personnel.infrastructure;

import java.util.List;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelFilterCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;

public interface PersonnelCustomRepository {

    List<PersonnelListResponse> findAllByCondition(PersonnelFilterCondRequest filterCond, PersonnelOrderCondRequest orderCond);
    List<PersonnelListResponse> findPersonnelByName(PersonnelFilterCondRequest filterCond, PersonnelOrderCondRequest orderCond, String[] searchWordRange);

}
