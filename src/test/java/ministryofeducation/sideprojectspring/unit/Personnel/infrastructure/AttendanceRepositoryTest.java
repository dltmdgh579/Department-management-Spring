package ministryofeducation.sideprojectspring.unit.Personnel.infrastructure;

import static ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import ministryofeducation.sideprojectspring.config.TestQueryDslConfig;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import ministryofeducation.sideprojectspring.personnel.infrastructure.AttendanceRepository;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestQueryDslConfig.class)
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
            .name("test")
            .build();
        personnelRepository.save(personnel);

        Department department = Department.builder()
            .name("testDepartment")
            .enrollment(10)
            .build();
        departmentRepository.save(department);

        Attendance attendance = buildAttendance(recentDate, personnel, department, ABSENT);
        attendanceRepository.save(attendance);

        //when
        Attendance recentAttendance = attendanceRepository.findTop1ByPersonnelIdOrderByAttendanceDateDesc(
            personnel.getId()).get();

        //then
        assertThat(recentAttendance.getAttendanceDate()).isEqualTo(recentDate);
        assertThat(recentAttendance.getAttendanceCheck()).isEqualTo(ABSENT);
    }

    @DisplayName("해당 날짜의 부서 출석 인원을 조회한다.")
    @Test
    void countByAttendanceDateAndDepartmentId(){
        //given
        LocalDate testDate = LocalDate.of(2024, 1, 30);

        Personnel personnel1 = Personnel.builder()
            .name("test1")
            .build();
        Personnel personnel2 = Personnel.builder()
            .name("test2")
            .build();
        Personnel personnel3 = Personnel.builder()
            .name("test3")
            .build();
        personnelRepository.saveAll(List.of(personnel1, personnel2, personnel3));

        Department department = Department.builder()
            .name("testDepartment")
            .enrollment(10)
            .build();
        departmentRepository.save(department);

        Attendance attendance1 = buildAttendance(testDate, personnel1, department, ATTENDANCE);
        Attendance attendance2 = buildAttendance(testDate, personnel2, department, ATTENDANCE);
        Attendance attendance3 = buildAttendance(testDate, personnel3, department, ABSENT);

        attendanceRepository.saveAll(List.of(attendance1, attendance2, attendance3));

        //when
        Long attendanceCount = attendanceRepository.countByAttendanceDateAndAttendanceCheckAndDepartmentId(testDate, ATTENDANCE,
            department.getId());

        //then
        assertThat(attendanceCount).isEqualTo(2l);
    }

    private Attendance buildAttendance(LocalDate recentDate, Personnel personnel, Department department,
        AttendanceCheck attendanceCheck) {
        return Attendance.builder()
            .attendanceDate(recentDate)
            .attendanceCheck(attendanceCheck)
            .department(department)
            .personnel(personnel)
            .build();
    }

}