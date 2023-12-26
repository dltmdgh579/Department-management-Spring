package ministryofeducation.sideprojectspring.unit.Personnel.infrastructure;

import java.time.LocalDate;
import java.util.List;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.department.infrastructure.SmallGroupRepository;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import ministryofeducation.sideprojectspring.personnel.infrastructure.AttendanceRepository;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static ministryofeducation.sideprojectspring.personnel.domain.Attendance.*;
import static ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PersonnelRepositoryTest {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SmallGroupRepository smallGroupRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    void 개인정보_전체_리스트를_불러온다(){
        // given
        Personnel personnel1 = testPersonnel(1l, "test1", "test1@email.com");
        Personnel personnel2 = testPersonnel(2l, "test2", "test2@email.com");
        Personnel personnel3 = testPersonnel(3l, "test3", "test3@email.com");
        personnelRepository.saveAll(List.of(personnel1, personnel2, personnel3));

        // when
        List<Personnel> personnelList = personnelRepository.findAll();

        // then
        assertThat(personnelList).hasSize(3)
            .extracting("name", "email")
            .containsExactlyInAnyOrder(
                tuple("test1", "test1@email.com"),
                tuple("test2", "test2@email.com"),
                tuple("test3", "test3@email.com")
            );
    }

    @Test
    void 특정_부서_내_특정_그룹에_속한_인원정보를_조회한다() {
        //given
        Department department = Department.createDepartment(1l, "department", 20);
        departmentRepository.save(department);

        SmallGroup smallGroup = SmallGroup.createSmallGroup(1l, "smallGroup", "leader", department);
        smallGroupRepository.save(smallGroup);

        Personnel personnel1 = testPersonnel(1l, "test1", department, smallGroup);
        Personnel personnel2 = testPersonnel(2l, "test2", department, smallGroup);
        Personnel personnel3 = testPersonnel(3l, "test3", department, smallGroup);
        personnelRepository.saveAll(List.of(personnel1, personnel2, personnel3));

        //when
        List<Personnel> groupInfoResponse = personnelRepository.findByDepartmentIdAndSmallGroupId(
            department.getId(), smallGroup.getId());

        //then
        assertThat(groupInfoResponse).hasSize(3)
            .extracting("id", "name")
            .containsExactlyInAnyOrder(
                tuple(1l, "test1"),
                tuple(2l, "test2"),
                tuple(3l, "test3")
            );
    }

    @Test
    void 그룹_내_결석인원을_조회한다() {
        //given
        LocalDate absentDate = LocalDate.of(2023, 12, 26);

        Department department = Department.createDepartment(1l, "department", 20);
        departmentRepository.save(department);

        SmallGroup smallGroup = SmallGroup.createSmallGroup(1l, "smallGroup", "leader", department);
        smallGroupRepository.save(smallGroup);

        Personnel personnel1 = testPersonnel(1l, "test1", department, smallGroup);
        Personnel personnel2 = testPersonnel(2l, "test2", department, smallGroup);
        Personnel personnel3 = testPersonnel(3l, "test3", department, smallGroup);
        personnelRepository.saveAll(List.of(personnel1, personnel2, personnel3));

        Attendance attendance1 = createAttendance(1l, absentDate, ABSENT, department, personnel1);
        Attendance attendance2 = createAttendance(2l, absentDate, ABSENT, department, personnel3);
        attendanceRepository.saveAll(List.of(attendance1, attendance2));

        //when
        List<Personnel> groupAbsentInfoResponse = personnelRepository.findByAbsentPersonnel(department.getId(), smallGroup.getId(), absentDate);

        //then
        assertThat(groupAbsentInfoResponse).hasSize(2)
            .extracting("id", "name")
            .containsExactlyInAnyOrder(
                tuple(1l, "test1"),
                tuple(3l, "test3")
            );
    }
}