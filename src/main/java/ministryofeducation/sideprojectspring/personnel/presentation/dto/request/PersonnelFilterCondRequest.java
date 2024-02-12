package ministryofeducation.sideprojectspring.personnel.presentation.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.domain.Gender;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonnelFilterCondRequest {

    private List<DepartmentType> departmentTypeList = new ArrayList<>();
    private Gender gender;

    @Builder
    public PersonnelFilterCondRequest(List<DepartmentType> departmentTypeList, Gender gender) {
        this.departmentTypeList = departmentTypeList;
        this.gender = gender;
    }
}
