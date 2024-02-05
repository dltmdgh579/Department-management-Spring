package ministryofeducation.sideprojectspring.personnel.presentation.dto.request;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonnelCondRequest {

    private DepartmentType departmentType1;
    private DepartmentType departmentType2;
    private DepartmentType departmentType3;
    private DepartmentType departmentType4;

    @Builder
    public PersonnelCondRequest(DepartmentType departmentType1, DepartmentType departmentType2,
        DepartmentType departmentType3, DepartmentType departmentType4) {
        this.departmentType1 = departmentType1;
        this.departmentType2 = departmentType2;
        this.departmentType3 = departmentType3;
        this.departmentType4 = departmentType4;
    }
}
