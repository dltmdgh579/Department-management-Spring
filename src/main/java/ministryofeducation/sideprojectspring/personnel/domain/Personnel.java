package ministryofeducation.sideprojectspring.personnel.domain;

import static lombok.AccessLevel.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.common.BaseEntity;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Personnel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    private String phone;

    private String landline;

    private String email;

    private String workSpace;

    private String address;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private DepartmentType departmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "small_group_id")
    private SmallGroup smallGroup;

    @OneToMany(mappedBy = "personnel")
    private List<Attendance> attendanceList = new ArrayList<>();

    @Builder
    private Personnel(Long id, String name, Gender gender, LocalDate dateOfBirth, String phone, String landline, String email,
        String workSpace, String address, String profileImage, DepartmentType departmentType, Department department,
        SmallGroup smallGroup) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.landline = landline;
        this.email = email;
        this.workSpace = workSpace;
        this.address = address;
        this.profileImage = profileImage;
        this.departmentType = departmentType;
        this.department = department;
        this.smallGroup = smallGroup;
    }

    public static Personnel createPersonnel(Long id, String name, LocalDate dateOfBirth, String phone, String landline,
        String email, String workSpace, String address, String profileImage, DepartmentType departmentType, Department department,
        SmallGroup smallGroup) {
        return Personnel.builder()
            .id(id)
            .name(name)
            .dateOfBirth(dateOfBirth)
            .phone(phone)
            .landline(landline)
            .email(email)
            .workSpace(workSpace)
            .address(address)
            .profileImage(profileImage)
            .departmentType(departmentType)
            .department(department)
            .smallGroup(smallGroup)
            .build();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeSmallGroup(SmallGroup smallGroup) {
        this.smallGroup = smallGroup;
    }

    public void changeProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void addAttendance(Attendance attendance) {
        this.getAttendanceList().add(attendance);
        attendance.addPersonnel(this);
    }

    public AttendanceCheck todayAttendance(LocalDate today) {
        if(this.attendanceList.isEmpty()) return null;
        Attendance attendance = this.attendanceList.get(attendanceList.size() - 1);
        if (attendance.getAttendanceDate() == today) {
            return attendance.getAttendanceCheck();
        }
        return null;
    }
}
