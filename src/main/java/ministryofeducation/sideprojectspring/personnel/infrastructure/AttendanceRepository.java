package ministryofeducation.sideprojectspring.personnel.infrastructure;

import java.time.LocalDate;
import java.util.Optional;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Long countByAttendanceDateAndDepartmentId(LocalDate attendanceDate, Long departmentId);
    Optional<Attendance> findTop1ByPersonnelIdOrderByAttendanceDateDesc(Long personnelId);
}
