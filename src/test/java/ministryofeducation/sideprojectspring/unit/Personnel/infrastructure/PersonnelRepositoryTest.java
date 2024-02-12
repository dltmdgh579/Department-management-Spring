package ministryofeducation.sideprojectspring.unit.Personnel.infrastructure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ministryofeducation.sideprojectspring.config.TestQueryDslConfig;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.department.infrastructure.SmallGroupRepository;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.Gender;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.AttendanceRepository;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelFilterCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static ministryofeducation.sideprojectspring.personnel.domain.Attendance.*;
import static ministryofeducation.sideprojectspring.personnel.domain.Gender.*;
import static ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck.*;
import static ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType.JOSHUA;
import static ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType.KINDERGARTEN;
import static ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestQueryDslConfig.class)
class PersonnelRepositoryTest {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SmallGroupRepository smallGroupRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @DisplayName("개인정보 전체 리스트를 불러온다. (정렬 기본조건은 이름 내림차순이다)")
    @Test
    void findAll() {
        // given
        Department departmentJoshua = Department.builder()
            .name("JOSHUA")
            .build();
        Department departmentKindergarten = Department.builder()
            .name("KINDERGARTEN")
            .build();
        departmentRepository.saveAll(List.of(departmentJoshua, departmentKindergarten));

        Personnel personnel1 = Personnel.builder()
            .name("test1")
            .department(departmentJoshua)
            .build();
        Personnel personnel2 = Personnel.builder()
            .name("test2")
            .department(departmentKindergarten)
            .build();
        personnelRepository.saveAll(List.of(personnel1, personnel2));

        PersonnelFilterCondRequest filterRequest = PersonnelFilterCondRequest.builder()
            .departmentTypeList(new ArrayList<>())
            .build();

        // when
        List<PersonnelListResponse> personnelList = personnelRepository.findAllByCondition(filterRequest, null);

        // then
        assertThat(personnelList).hasSize(2)
            .extracting("name")
            .containsExactly(
                "test2", "test1"
            );
    }

    @DisplayName("소속 부서를 조건으로 인원을 조회한다.")
    @Test
    public void findAllInDepartmentType() {
        //given
        Department departmentJoshua = Department.builder()
            .name("JOSHUA")
            .build();
        Department departmentKindergarten = Department.builder()
            .name("KINDERGARTEN")
            .build();
        departmentRepository.saveAll(List.of(departmentJoshua, departmentKindergarten));

        Personnel personnel1 = Personnel.builder()
            .name("test1")
            .departmentType(JOSHUA)
            .build();
        Personnel personnel2 = Personnel.builder()
            .name("test2")
            .departmentType(KINDERGARTEN)
            .build();
        personnelRepository.saveAll(List.of(personnel1, personnel2));

        PersonnelFilterCondRequest filterRequest = PersonnelFilterCondRequest.builder()
            .departmentTypeList(List.of(JOSHUA))
            .build();

        // when
        List<PersonnelListResponse> personnelListResponse = personnelRepository.findAllByCondition(filterRequest, null);

        //then
        assertThat(personnelListResponse).hasSize(1)
            .extracting("name")
            .containsExactly("test1");
    }

    @DisplayName("성별을 조건으로 인원을 조회한다.")
    @Test
    public void findAllInGender() {
        //given
        Personnel personnel1 = Personnel.builder()
            .name("test1")
            .gender(M)
            .build();
        Personnel personnel2 = Personnel.builder()
            .name("test2")
            .gender(W)
            .build();
        personnelRepository.saveAll(List.of(personnel1, personnel2));

        PersonnelFilterCondRequest filterRequest = PersonnelFilterCondRequest.builder()
            .departmentTypeList(new ArrayList<>())
            .gender(M)
            .build();

        // when
        List<PersonnelListResponse> personnelListResponse = personnelRepository.findAllByCondition(filterRequest, null);

        //then
        assertThat(personnelListResponse).hasSize(1)
            .extracting("name")
            .containsExactly("test1");
    }

    @DisplayName("나이를 기준으로 인원을 내림차순 정렬한다.")
    @Test
    public void findAllOrderByAgeDesc() {
        //given
        Personnel personnel1 = Personnel.builder()
            .name("test1")
            .dateOfBirth(LocalDate.of(2000, 1, 1))
            .build();
        Personnel personnel2 = Personnel.builder()
            .name("test2")
            .dateOfBirth(LocalDate.of(2001, 1, 2))
            .build();
        Personnel personnel3 = Personnel.builder()
            .name("test3")
            .dateOfBirth(LocalDate.of(2001, 1, 1))
            .build();
        personnelRepository.saveAll(List.of(personnel1, personnel2, personnel3));

        PersonnelFilterCondRequest filterRequest = PersonnelFilterCondRequest.builder()
            .departmentTypeList(new ArrayList<>())
            .build();

        // when
        List<PersonnelListResponse> personnelListResponse = personnelRepository.findAllByCondition(filterRequest, AGE);

        //then
        assertThat(personnelListResponse).hasSize(3)
            .extracting("name")
            .containsExactly("test2", "test3", "test1");
    }

    @DisplayName("이름을 기준으로 인원을 내림차순 정렬한다.")
    @Test
    public void findAllOrderByNameDesc() {
        //given
        Personnel personnel1 = Personnel.builder()
            .name("test2")
            .build();
        Personnel personnel2 = Personnel.builder()
            .name("test1")
            .build();
        Personnel personnel3 = Personnel.builder()
            .name("test3")
            .build();
        personnelRepository.saveAll(List.of(personnel1, personnel2, personnel3));

        PersonnelFilterCondRequest filterRequest = PersonnelFilterCondRequest.builder()
            .departmentTypeList(new ArrayList<>())
            .build();

        // when
        List<PersonnelListResponse> personnelListResponse = personnelRepository.findAllByCondition(filterRequest, NAME);

        //then
        assertThat(personnelListResponse).hasSize(3)
            .extracting("name")
            .containsExactly("test3", "test2", "test1");
    }

    @DisplayName("특정 부서 내 특정 그룹에 속한 인원정보를 조회한다.")
    @Test
    void findByDepartmentIdAndSmallGroupId() {
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

    @DisplayName("그룹 내 결석인원을 조회한다.")
    @Test
    void findByAbsentPersonnel() {
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

    @DisplayName("부서 내 모든 인원을 조회한다.")
    @Test
    void findByDepartmentId() {
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