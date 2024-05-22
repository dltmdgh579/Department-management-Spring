package ministryofeducation.sideprojectspring.personnel.infrastructure;

import java.time.LocalDate;
import java.util.Optional;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Long countByAttendanceDateAndAttendanceStatusAndDepartmentId(LocalDate attendanceDate, AttendanceStatus attendanceStatus, Long departmentId);
    Optional<Attendance> findTop1ByPersonnelIdOrderByAttendanceDateDesc(Long personnelId);
}
