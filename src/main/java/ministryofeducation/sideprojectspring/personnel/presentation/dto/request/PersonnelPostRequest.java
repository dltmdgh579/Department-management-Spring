package ministryofeducation.sideprojectspring.personnel.presentation.dto.request;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonnelPostRequest {

    private String name;
    private DepartmentType departmentType;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    private String workSpace;
    private String address;
    private String family;

    @Builder
    public PersonnelPostRequest(String name, DepartmentType departmentType, LocalDate dateOfBirth, String phone, String email,
        String workSpace, String address, String family) {
        this.name = name;
        this.departmentType = departmentType;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.workSpace = workSpace;
        this.address = address;
        this.family = family;
    }
}
