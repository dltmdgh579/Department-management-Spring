package ministryofeducation.sideprojectspring.unit.Personnel.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import ministryofeducation.sideprojectspring.config.TestQueryDslConfig;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.department.infrastructure.SmallGroupRepository;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.AttendanceRepository;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static ministryofeducation.sideprojectspring.personnel.domain.Attendance.*;
import static ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck.*;
import static ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType.JOSHUA;
import static ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType.KINDERGARTEN;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestQueryDslConfig.class)
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
    void 개인정보_전체_리스트를_불러온다() {
        // given
        Personnel personnel1 = testPersonnel(1l, "test1", "test1@email.com");
        Personnel personnel2 = testPersonnel(2l, "test2", "test2@email.com");
        personnelRepository.saveAll(List.of(personnel1, personnel2));

        PersonnelCondRequest condition = PersonnelCondRequest.builder().build();

        // when
        List<PersonnelListResponse> personnelList = personnelRepository.findAllByCondition(condition);

        // then
        assertThat(personnelList).hasSize(2)
            .extracting("name")
            .containsExactly(
                "test1", "test2"
            );
    }

    @Test
    public void 소속_부서를_조건으로_인원을_조회한다() {
        //given
        Personnel personnel1 = Personnel.builder()
            .name("test1")
            .departmentType(JOSHUA)
            .build();
        Personnel personnel2 = Personnel.builder()
            .name("test2")
            .departmentType(KINDERGARTEN)
            .build();
        personnelRepository.saveAll(List.of(personnel1, personnel2));

        PersonnelCondRequest condition = PersonnelCondRequest.builder()
            .departmentType1(JOSHUA)
            .build();

        //when
        List<PersonnelListResponse> personnelListResponse = personnelRepository.findAllByCondition(condition);

        //then
        assertThat(personnelListResponse).hasSize(1)
            .extracting("name")
            .containsExactly("test1");
    }

    @Test
    public void 소속_부서를_조건으로_여러_부서의_인원을_조회한다() {
        //given
        Personnel personnel1 = Personnel.builder()
            .name("test1")
            .departmentType(JOSHUA)
            .build();
        Personnel personnel2 = Personnel.builder()
            .name("test2")
            .departmentType(KINDERGARTEN)
            .build();
        personnelRepository.saveAll(List.of(personnel1, personnel2));

        PersonnelCondRequest condition = PersonnelCondRequest.builder()
            .departmentType1(JOSHUA)
            .departmentType2(KINDERGARTEN)
            .build();

        //when
        List<PersonnelListResponse> personnelListResponse = personnelRepository.findAllByCondition(condition);

        //then
        assertThat(personnelListResponse).hasSize(2)
            .extracting("name")
            .containsExactly("test1", "test2");
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
            .extracting("name")
            .containsExactly("test1", "test2", "test3");
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
        List<Personnel> groupAbsentInfoResponse = personnelRepository.findByAbsentPersonnel(department.getId(),
            smallGroup.getId(), absentDate);

        //then
        assertThat(groupAbsentInfoResponse).hasSize(2)
            .extracting("name")
            .containsExactly("test1", "test3");
    }

    @Test
    void 부서_내_모든_인원을_조회한다() {
        //given
        Department department = Department.createDepartment(1l, "departmentName", 20);
        departmentRepository.save(department);

        Personnel personnel1 = testPersonnel(1l, "test1", department, null);
        Personnel personnel2 = testPersonnel(2l, "test2", department, null);
        Personnel personnel3 = testPersonnel(3l, "test3", department, null);
        personnelRepository.saveAll(List.of(personnel1, personnel2, personnel3));

        //when
        List<Personnel> personnelList = personnelRepository.findByDepartmentId(department.getId());

        //then
        assertThat(personnelList).hasSize(3)
            .extracting("name")
            .containsExactly("test1", "test2", "test3");
    }
}