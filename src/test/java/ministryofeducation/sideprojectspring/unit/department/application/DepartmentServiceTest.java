package ministryofeducation.sideprojectspring.unit.department.application;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import ministryofeducation.sideprojectspring.department.application.DepartmentService;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.department.infrastructure.SmallGroupRepository;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAbsentListRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAbsentListRequest.AbsenteeInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentNameResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAbsentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAbsentListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupInfoResponse;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import ministryofeducation.sideprojectspring.personnel.infrastructure.AttendanceRepository;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private SmallGroupRepository smallGroupRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private PersonnelRepository personnelRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void 홈_화면의_부서_전체_이름을_조회한다() {
        //given
        Department department1 = Department.builder()
            .id(1l)
            .name("department1")
            .enrollment(10)
            .build();
        Department department2 = Department.builder()
            .id(2l)
            .name("department2")
            .enrollment(20)
            .build();

        given(departmentRepository.findAll()).willReturn(List.of(department1, department2));

        //when
        List<DepartmentNameResponse> allDepartmentResponse = departmentService.getAllDepartment();

        //then
        assertThat(allDepartmentResponse).hasSize(2)
            .extracting("id", "name")
            .containsExactlyInAnyOrder(
                tuple(1l, "department1"),
                tuple(2l, "department2")
            );
    }

    @Test
    void 부서_정보를_조회한다() {
        //given
        Department department = Department.createDepartment(1l, "department1", 20);
        SmallGroup smallGroup1 = SmallGroup.builder()
            .id(1l)
            .name("groupName1")
            .leader("leader1")
            .department(department)
            .build();
        SmallGroup smallGroup2 = SmallGroup.builder()
            .id(2l)
            .name("groupName2")
            .leader("leader2")
            .department(department)
            .build();

        given(attendanceRepository.countByAttendanceDateAndDepartmentId(any(LocalDate.class), anyLong()))
            .willReturn(10l);
        given(departmentRepository.findById(anyLong()))
            .willReturn(Optional.of(department));
        given(smallGroupRepository.findByDepartmentId(anyLong()))
            .willReturn(List.of(smallGroup1, smallGroup2));

        //when
        DepartmentInfoResponse departmentInfoResponse = departmentService.getDepartmentInfo(department.getId());

        //then
        assertThat(departmentInfoResponse.getAttendance()).isEqualTo(10);
        assertThat(departmentInfoResponse.getEnrollment()).isEqualTo(20);
        assertThat(departmentInfoResponse.getSmallGroupInfoList()).hasSize(2)
            .extracting("name", "leader")
            .containsExactlyInAnyOrder(
                tuple("groupName1", "leader1"),
                tuple("groupName2", "leader2")
            );
    }

    @Test
    void 부서_내_그룹_정보를_조회한다() {
        //given
        Department department = Department.createDepartment(1l, "department", 20);
        SmallGroup smallGroup = SmallGroup.createSmallGroup(1l, "smallGroup", "leader", department);
        Personnel personnel1 = testPersonnel(1l, "test1", department, smallGroup);
        Personnel personnel2 = testPersonnel(2l, "test2", department, smallGroup);

        given(personnelRepository.findByDepartmentIdAndSmallGroupId(anyLong(), anyLong()))
            .willReturn(List.of(personnel1, personnel2));

        //when
        List<GroupInfoResponse> groupInfoResponse = departmentService.getGroupInfo(department.getId(),
            smallGroup.getId());

        //then
        assertThat(groupInfoResponse).hasSize(2)
            .extracting("id", "name")
            .containsExactlyInAnyOrder(
                tuple(1l, "test1"),
                tuple(2l, "test2")
            );

    }

    @Test
    void 그룹_내_결석인원을_조회한다() {
        //given
        Department department = Department.createDepartment(1l, "department", 20);
        SmallGroup smallGroup = SmallGroup.createSmallGroup(1l, "smallGroup", "leader", department);
        Personnel personnel1 = testPersonnel(1l, "test1", department, smallGroup);
        Personnel personnel2 = testPersonnel(2l, "test2", department, smallGroup);

        given(personnelRepository.findByAbsentPersonnel(anyLong(), anyLong(), any(AttendanceCheck.class)))
            .willReturn(List.of(personnel1, personnel2));

        //when
        List<GroupAbsentInfoResponse> groupAbsentInfoResponse = departmentService.getGroupAbsentInfo(department.getId(),
            smallGroup.getId());

        //then
        assertThat(groupAbsentInfoResponse).hasSize(2)
            .extracting("id", "name")
            .containsExactlyInAnyOrder(
                tuple(1l, "test1"),
                tuple(2l, "test2")
            );
    }

    @Test
    void 그룹_내_결석인원을_저장한다() {
        //given
        Department department = Department.createDepartment(1l, "department", 20);
        SmallGroup smallGroup = SmallGroup.createSmallGroup(1l, "smallGroup", "leader", department);
        Personnel personnel1 = testPersonnel(1l, "test1", department, smallGroup);
        Personnel personnel2 = testPersonnel(2l, "test2", department, smallGroup);
        Attendance attendance1 = Attendance.createAttendance(1l, LocalDate.now(), AttendanceCheck.ABSENT, department,
            personnel1);
        Attendance attendance2 = Attendance.createAttendance(2l, LocalDate.now(), AttendanceCheck.ABSENT, department,
            personnel2);


        AbsenteeInfo absenteeInfo1 = AbsenteeInfo.builder()
            .id(1l)
            .name("test1")
            .absentDate(LocalDate.now())
            .build();
        AbsenteeInfo absenteeInfo2 = AbsenteeInfo.builder()
            .id(2l)
            .name("test2")
            .absentDate(LocalDate.now())
            .build();
        GroupAbsentListRequest requestDto = GroupAbsentListRequest.builder()
            .absenteeList(List.of(absenteeInfo1, absenteeInfo2))
            .build();

        given(departmentRepository.findById(anyLong())).willReturn(Optional.of(department));
        given(personnelRepository.findById(anyLong())).willReturn(Optional.of(personnel1), Optional.of(personnel2));
        given(attendanceRepository.saveAll(anyCollection())).willReturn(List.of(attendance1, attendance2));

        //when
        List<GroupAbsentListResponse> groupAbsentListResponseDto = departmentService.checkGroupAbsentInfo(
            department.getId(), smallGroup.getId(), requestDto);

        //then
        assertThat(groupAbsentListResponseDto).hasSize(2)
            .extracting("id")
            .containsExactlyInAnyOrder(1l, 2l);

    }

}