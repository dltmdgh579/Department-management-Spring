package ministryofeducation.sideprojectspring.personnel.infrastructure;

import java.time.LocalDate;
import java.util.List;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    List<Personnel> findAll();

    List<Personnel> findByDepartmentIdAndSmallGroupId(Long departmentId, Long smallGroupId);

    @Query(value = "select p from Personnel p join fetch p.attendanceList a "
        + "where p.department.id = :departmentId and p.smallGroup.id = :smallGroupId and a.attendanceDate = :absentDate and a.attendanceCheck = 'ABSENT'")
    List<Personnel> findByAbsentPersonnel(
        @Param(value = "departmentId") Long departmentId,
        @Param(value = "smallGroupId") Long smallGroupId,
        @Param(value = "absentDate") LocalDate absentDate);

    List<Personnel> findByDepartmentId(Long departmentId);
}
