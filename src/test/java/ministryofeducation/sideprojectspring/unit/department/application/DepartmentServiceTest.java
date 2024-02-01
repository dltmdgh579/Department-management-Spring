package ministryofeducation.sideprojectspring.unit.department.application;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck.*;
import static ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType.*;
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
import ministryofeducation.sideprojectspring.department.presentation.dto.request.DepartmentAttendanceMemberListRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.DepartmentAttendanceMemberListRequest.AttendanceMemberInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAbsentListRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAbsentListRequest.AbsenteeInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAddMemberListRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAddMemberListRequest.AddMemberInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAddRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupModifyRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentAttendanceMemberListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentMemberListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentNameResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAbsentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAbsentListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAddMemberListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAddResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupModifyResponse;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
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
    void 부서_내_그룹을_추가한다() {
        //given
        Department department = Department.createDepartment(1l, "departmentName", 20);
        GroupAddRequest groupAddRequest = GroupAddRequest.builder()
            .name("newGroup")
            .build();
        SmallGroup smallGroup = SmallGroup.createSmallGroup(1l, "newGroup", null, department);

        given(departmentRepository.findById(anyLong())).willReturn(Optional.of(department));
        given(smallGroupRepository.save(any(SmallGroup.class))).willReturn(smallGroup);

        //when
        GroupAddResponse groupAddResponse = departmentService.addGroup(department.getId(), groupAddRequest);

        //then
        assertThat(groupAddResponse.getName()).isEqualTo("newGroup");
    }

    @Test
    void 부서_내_그룹_이름을_수정한다() {
        //given
        Department department = Department.createDepartment(1l, "departmentName", 20);
        SmallGroup smallGroup = SmallGroup.createSmallGroup(1l, "groupName", "leader", department);
        GroupModifyRequest groupModifyRequest = GroupModifyRequest.builder()
            .name("modifyGroupName")
            .build();

        given(departmentRepository.findById(anyLong())).willReturn(Optional.of(department));
        given(smallGroupRepository.findById(anyLong())).willReturn(Optional.of(smallGroup));

        //when
        GroupModifyResponse groupModifyResponse = departmentService.modifyGroup(department.getId(), smallGroup.getId(),
            groupModifyRequest);

        //then
        assertThat(groupModifyResponse.getName()).isEqualTo("modifyGroupName");
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

        given(attendanceRepository.countByAttendanceDateAndAttendanceCheckAndDepartmentId(any(LocalDate.class), any(
            AttendanceCheck.class), anyLong()))
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
        LocalDate absentDate = LocalDate.of(2023, 12, 26);
        Department department = Department.createDepartment(1l, "department", 20);
        SmallGroup smallGroup = SmallGroup.createSmallGroup(1l, "smallGroup", "leader", department);
        Personnel personnel1 = testPersonnel(1l, "test1", department, smallGroup);
        Personnel personnel2 = testPersonnel(2l, "test2", department, smallGroup);

        given(personnelRepository.findByAbsentPersonnel(anyLong(), anyLong(), any(LocalDate.class)))
            .willReturn(List.of(personnel1, personnel2));

        //when
        List<GroupAbsentInfoResponse> groupAbsentInfoResponse = departmentService.getGroupAbsentInfo(department.getId(),
            smallGroup.getId(), absentDate);

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
        Attendance attendance1 = Attendance.createAttendance(1l, LocalDate.now(), ABSENT, department,
            personnel1);
        Attendance attendance2 = Attendance.createAttendance(2l, LocalDate.now(), ABSENT, department,
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
        given(attendanceRepository.save(any(Attendance.class))).willReturn(attendance1, attendance2);

        //when
        List<GroupAbsentListResponse> groupAbsentListResponseDto = departmentService.checkGroupAbsentInfo(
            department.getId(), smallGroup.getId(), requestDto);

        //then
        assertThat(groupAbsentListResponseDto).hasSize(2)
            .extracting("id")
            .containsExactlyInAnyOrder(1l, 2l);

    }

    @Test
    void 그룹_내_인원을_추가한다() {
        //given
        Department department = Department.createDepartment(1l, "department", 20);
        SmallGroup smallGroup = SmallGroup.createSmallGroup(1l, "smallGroup", "leader", department);
        Personnel personnel1 = testPersonnel(1l, "test1", department, smallGroup);
        Personnel personnel2 = testPersonnel(2l, "test2", department, smallGroup);

        AddMemberInfo addMemberInfo1 = AddMemberInfo.builder()
            .id(personnel1.getId())
            .name(personnel1.getName())
            .addDate(LocalDate.now())
            .build();
        AddMemberInfo addMemberInfo2 = AddMemberInfo.builder()
            .id(personnel2.getId())
            .name(personnel2.getName())
            .addDate(LocalDate.now())
            .build();

        GroupAddMemberListRequest request = GroupAddMemberListRequest.builder()
            .addMemberList(List.of(addMemberInfo1, addMemberInfo2))
            .build();

        given(departmentRepository.findById(anyLong())).willReturn(Optional.of(department));
        given(smallGroupRepository.findById(anyLong())).willReturn(Optional.of(smallGroup));
        given(personnelRepository.findById(anyLong())).willReturn(Optional.of(personnel1), Optional.of(personnel2));

        //when
        List<GroupAddMemberListResponse> groupAddMemberListResponse = departmentService.addGroupMember(
            department.getId(), smallGroup.getId(), request);

        //then
        assertThat(groupAddMemberListResponse).hasSize(2)
            .extracting("id", "name")
            .containsExactlyInAnyOrder(
                tuple(1l, "test1"),
                tuple(2l, "test2")
            );

    }

    @Test
    void 부서_내_모든_인원을_조회한다() {
        //given
        LocalDate today = LocalDate.of(2023, 12, 27);

        Department department = Department.createDepartment(1l, "department", 20);
        Personnel personnel1 = testPersonnel(1l, "test1", department, null);
        Personnel personnel2 = testPersonnel(2l, "test2", department, null);
        Personnel personnel3 = testPersonnel(3l, "test3", department, null);
        Attendance attendance1 = Attendance.createAttendance(1l, today, ABSENT, department, personnel1);
        Attendance attendance2 = Attendance.createAttendance(2l, today, ATTENDANCE, department, personnel2);
        Attendance attendance3 = Attendance.createAttendance(3l, today.minusWeeks(1), ATTENDANCE, department, personnel3);

        personnel1.addAttendance(attendance1);
        personnel2.addAttendance(attendance2);
        personnel3.addAttendance(attendance3);

        given(personnelRepository.findByDepartmentId(anyLong()))
            .willReturn(List.of(personnel1, personnel2, personnel3));

        //when
        List<DepartmentMemberListResponse> departmentMemberList = departmentService.getDepartmentMemberList(
            department.getId(), today);

        //then
        assertThat(departmentMemberList).hasSize(3)
            .extracting("name", "attendanceCheck")
            .containsExactlyInAnyOrder(
                tuple("test1", ABSENT),
                tuple("test2", ATTENDANCE),
                tuple("test3", null)
            );
    }

    @Test
    void 부서_내_모든_인원을_대상으로_출석체크를_한다() {
        //given
        LocalDate today = LocalDate.of(2024, 1, 31);

        Department department = Department.createDepartment(1l, "department", 20);
        Personnel personnel1 = testPersonnel(1l, "test1", department, null);
        Personnel personnel2 = testPersonnel(2l, "test2", department, null);

        Attendance attendance1 = Attendance.createAttendance(1l, LocalDate.now(), ATTENDANCE, department,
                personnel1);
        Attendance attendance2 = Attendance.createAttendance(2l, LocalDate.now(), ATTENDANCE, department,
                personnel2);

        AttendanceMemberInfo requestMemberInfo1 = AttendanceMemberInfo.builder()
            .id(1l)
            .name("test1")
            .attendanceDate(today)
            .build();
        AttendanceMemberInfo requestMemberInfo2 = AttendanceMemberInfo.builder()
            .id(2l)
            .name("test2")
            .attendanceDate(today)
            .build();

        DepartmentAttendanceMemberListRequest request = DepartmentAttendanceMemberListRequest.builder()
            .attendanceMemberList(List.of(requestMemberInfo1, requestMemberInfo2))
            .build();

        given(departmentRepository.findById(anyLong())).willReturn(Optional.of(department));
        given(personnelRepository.findById(anyLong()))
            .willReturn(Optional.of(personnel1), Optional.of(personnel2));
        given(attendanceRepository.save(any(Attendance.class))).willReturn(attendance1, attendance2);


        //when
        List<DepartmentAttendanceMemberListResponse> response = departmentService.attendanceDepartmentMember(
            department.getId(), request);

        //then
        assertThat(response).hasSize(2);
        assertThat(personnel1.getAttendanceList().get(0).getAttendanceCheck()).isEqualTo(ATTENDANCE);
        assertThat(personnel2.getAttendanceList().get(0).getAttendanceCheck()).isEqualTo(ATTENDANCE);

    }

}