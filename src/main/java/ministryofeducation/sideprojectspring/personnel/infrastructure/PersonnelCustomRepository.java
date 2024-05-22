package ministryofeducation.sideprojectspring.personnel.infrastructure;

import java.time.LocalDate;
import java.util.List;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentMemberListResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelFilterCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;

public interface PersonnelCustomRepository {

    List<PersonnelListResponse> findAllByCondition(PersonnelFilterCondRequest filterCond, PersonnelOrderCondRequest orderCond);
    List<PersonnelListResponse> findPersonnelByName(PersonnelFilterCondRequest filterCond, PersonnelOrderCondRequest orderCond, String[] searchWordRange);
    List<DepartmentMemberListResponse> findPersonnelListAttendanceByDate(PersonnelFilterCondRequest filterCond, PersonnelOrderCondRequest orderCond, Long departmentId, LocalDate date);
}
