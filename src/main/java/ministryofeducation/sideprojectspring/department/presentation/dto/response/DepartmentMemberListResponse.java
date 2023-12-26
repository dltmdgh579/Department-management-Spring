package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentMemberListResponse {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private String profileImage;
    private DepartmentType departmentType;
    private AttendanceCheck attendanceCheck;

    @Builder
    public DepartmentMemberListResponse(Long id, String name, LocalDate dateOfBirth, String phone,
        String address, String profileImage, DepartmentType departmentType, AttendanceCheck attendanceCheck) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.profileImage = profileImage;
        this.departmentType = departmentType;
        this.attendanceCheck = attendanceCheck;
    }

    public static DepartmentMemberListResponse of(Personnel personnel, LocalDate today) {
        AttendanceCheck todayAttendance = personnel.todayAttendance(today);

        return DepartmentMemberListResponse.builder()
            .id(personnel.getId())
            .name(personnel.getName())
            .dateOfBirth(personnel.getDateOfBirth())
            .phone(personnel.getPhone())
            .address(personnel.getAddress())
            .profileImage(personnel.getProfileImage())
            .departmentType(personnel.getDepartmentType())
            .attendanceCheck(todayAttendance)
            .build();
    }
}
