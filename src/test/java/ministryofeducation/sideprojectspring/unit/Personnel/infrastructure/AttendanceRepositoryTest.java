package ministryofeducation.sideprojectspring.unit.Personnel.infrastructure;

import static ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
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

        Attendance attendance = Attendance.createAttendance(1l, recentDate, ABSENT, department, personnel);
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
            .id(1l)
            .name("test1")
            .build();
        Personnel personnel2 = Personnel.builder()
            .id(2l)
            .name("test2")
            .build();
        Personnel personnel3 = Personnel.builder()
            .id(3l)
            .name("test3")
            .build();
        personnelRepository.saveAll(List.of(personnel1, personnel2, personnel3));

        Department department = Department.builder()
            .name("testDepartment")
            .enrollment(10)
            .build();
        departmentRepository.save(department);

        Attendance attendance1 = Attendance.createAttendance(1l, testDate, ATTENDANCE, department, personnel1);
        Attendance attendance2 = Attendance.createAttendance(2l, testDate, ATTENDANCE, department, personnel2);
        Attendance attendance3 = Attendance.createAttendance(3l, testDate, ABSENT, department, personnel3);
        attendanceRepository.saveAll(List.of(attendance1, attendance2, attendance3));

        //when
        Long attendanceCount = attendanceRepository.countByAttendanceDateAndAttendanceCheckAndDepartmentId(testDate, ATTENDANCE,
            department.getId());

        //then
        assertThat(attendanceCount).isEqualTo(2l);
    }

}