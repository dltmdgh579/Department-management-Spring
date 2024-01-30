package ministryofeducation.sideprojectspring.unit.Personnel.infrastructure;

import static ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import ministryofeducation.sideprojectspring.personnel.infrastructure.AttendanceRepository;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @DisplayName("가장 최근 출석 기록을 1개 불러온다.")
    @Test
    void findTop1ByPersonnelIdOrderByAttendanceDateDesc(){
        //given
        LocalDate recentDate = LocalDate.of(2024, 1, 30);

        Personnel personnel = Personnel.builder()
            .id(1l)
            .name("test")
            .build();
        personnelRepository.save(personnel);

        Department department = Department.builder()
            .name("testDepartment")
            .enrollment(10)
            .build();
        departmentRepository.save(department);

        Attendance attendance = Attendance.builder()
            .attendanceDate(recentDate)
            .attendanceCheck(ABSENT)
            .department(department)
            .personnel(personnel)
            .build();
        attendanceRepository.save(attendance);

        //when
        Attendance recentAttendance = attendanceRepository.findTop1ByPersonnelIdOrderByAttendanceDateDesc(
            personnel.getId()).get();

        //then
        assertThat(recentAttendance.getAttendanceDate()).isEqualTo(recentDate);
        assertThat(recentAttendance.getAttendanceCheck()).isEqualTo(ABSENT);
    }

}