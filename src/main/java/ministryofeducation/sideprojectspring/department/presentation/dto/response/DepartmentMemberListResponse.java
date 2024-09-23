package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.domain.Gender;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceStatus;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentMemberListResponse {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private Gender gender;
    private String profileImage;
    private DepartmentType departmentType;
    private AttendanceStatus attendanceStatus;

    @Builder
    public DepartmentMemberListResponse(Long id, String name, LocalDate dateOfBirth, String phone, String address,
            Gender gender, String profileImage, DepartmentType departmentType, AttendanceStatus attendanceStatus) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.profileImage = profileImage;
        this.departmentType = departmentType;
        this.attendanceStatus = attendanceStatus;
    }
}
