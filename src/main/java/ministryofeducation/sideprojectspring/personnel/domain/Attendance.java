package ministryofeducation.sideprojectspring.personnel.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.common.BaseEntity;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;

@NoArgsConstructor(access = PROTECTED)
@Entity
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate attendanceDate;
    @Enumerated(EnumType.STRING)
    private AttendanceCheck attendanceCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_id")
    private Personnel personnel;

    @Builder
    private Attendance(Long id, LocalDate attendanceDate, AttendanceCheck attendanceCheck, Department department,
        Personnel personnel) {
        this.id = id;
        this.attendanceDate = attendanceDate;
        this.attendanceCheck = attendanceCheck;
        this.department = department;
        this.personnel = personnel;
    }

    public static Attendance createAttendance(Long id, LocalDate attendanceDate, AttendanceCheck attendanceCheck, Department department,
        Personnel personnel) {
        return Attendance.builder()
            .id(id)
            .attendanceDate(attendanceDate)
            .attendanceCheck(attendanceCheck)
            .department(department)
            .personnel(personnel)
            .build();
    }
}
