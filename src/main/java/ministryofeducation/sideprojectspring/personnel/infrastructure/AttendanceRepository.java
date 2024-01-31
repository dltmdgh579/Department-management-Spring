package ministryofeducation.sideprojectspring.personnel.infrastructure;

import java.time.LocalDate;
import java.util.Optional;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Long countByAttendanceDateAndAttendanceCheckAndDepartmentId(LocalDate attendanceDate, AttendanceCheck attendanceCheck, Long departmentId);
    Optional<Attendance> findTop1ByPersonnelIdOrderByAttendanceDateDesc(Long personnelId);
}
