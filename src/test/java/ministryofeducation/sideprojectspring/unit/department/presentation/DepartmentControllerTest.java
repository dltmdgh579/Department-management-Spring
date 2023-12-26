package ministryofeducation.sideprojectspring.unit.department.presentation;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.testPersonnel;
import static ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck.*;
import static ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAbsentListRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAbsentListRequest.AbsenteeInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAddMemberListRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAddRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupModifyRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse.SmallGroupInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentMemberListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentNameResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAbsentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAbsentListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAddMemberListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAddResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupModifyResponse;
import ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import ministryofeducation.sideprojectspring.unit.ControllerTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DepartmentControllerTest extends ControllerTest {

    @Test
    void 홈_화면의_부서_리스트를_조회한다() throws Exception {
        //given
        DepartmentNameResponse department1 = DepartmentNameResponse.builder()
            .id(1l)
            .name("department1")
            .build();
        DepartmentNameResponse department2 = DepartmentNameResponse.builder()
            .id(2l)
            .name("department2")
            .build();

        given(departmentService.getAllDepartment()).willReturn(List.of(department1, department2));

        //when
        ResultActions perform = mockMvc.perform(get("/"));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void 부서_정보를_조회한다() throws Exception {
        //given
        SmallGroupInfo smallGroupInfo1 = SmallGroupInfo.builder()
            .name("groupName1")
            .leader("leader1")
            .build();
        SmallGroupInfo smallGroupInfo2 = SmallGroupInfo.builder()
            .name("groupName2")
            .leader("leader2")
            .build();

        DepartmentInfoResponse departmentInfoResponse = DepartmentInfoResponse.builder()
            .smallGroupInfoList(
                List.of(
                    smallGroupInfo1, smallGroupInfo2
                )
            )
            .enrollment(20)
            .attendance(10)
            .build();

        given(departmentService.getDepartmentInfo(anyLong())).willReturn(departmentInfoResponse);

        //when
        ResultActions perform = mockMvc.perform(get("/{departmentId}", 1l));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.smallGroupInfoList.length()").value(2));

    }

    @Test
    void 부서_내_그룹을_추가한다() throws Exception {
        //given
        GroupAddRequest request = GroupAddRequest.builder().build();
        GroupAddResponse groupAddResponse = GroupAddResponse.builder()
            .name("newGroup")
            .build();

        given(departmentService.addGroup(anyLong(), any(GroupAddRequest.class))).willReturn(groupAddResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/{departmentId}", 1l)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value("newGroup"));
    }

    @Test
    void 부서_내_그룹_이름을_수정한다() throws Exception {
        //given
        GroupModifyRequest request = GroupModifyRequest.builder().build();
        GroupModifyResponse groupModifyResponse = GroupModifyResponse.builder()
            .id(1l)
            .name("modifyGroupName")
            .build();

        given(departmentService.modifyGroup(anyLong(), anyLong(), any(GroupModifyRequest.class)))
            .willReturn(groupModifyResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/{departmentId}/{groupId}/modify", 1l, 1l)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value("modifyGroupName"));
    }

    @Test
    void 부서_내_그룹_정보를_조회한다() throws Exception {
        //given
        GroupInfoResponse groupInfoResponse1 = GroupInfoResponse.builder()
            .id(1l)
            .profileImage("profileImageUrl1")
            .name("test1")
            .build();

        GroupInfoResponse groupInfoResponse2 = GroupInfoResponse.builder()
            .id(2l)
            .profileImage("profileImageUrl2")
            .name("test2")
            .build();

        given(departmentService.getGroupInfo(anyLong(), anyLong())).willReturn(
            List.of(groupInfoResponse1, groupInfoResponse2));

        //when
        ResultActions perform = mockMvc.perform(get("/{departmentId}/{groupId}", anyLong(), anyLong()));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void 그룹_내_결석인원을_조회한다() throws Exception {
        //given
        GroupAbsentInfoResponse groupAbsentInfoResponse1 = GroupAbsentInfoResponse.builder()
            .name("test1")
            .phone("010-0000-0001")
            .build();
        GroupAbsentInfoResponse groupAbsentInfoResponse2 = GroupAbsentInfoResponse.builder()
            .name("test2")
            .phone("010-0000-0002")
            .build();

        given(departmentService.getGroupAbsentInfo(anyLong(), anyLong(), any(LocalDate.class)))
            .willReturn(List.of(groupAbsentInfoResponse1, groupAbsentInfoResponse2));

        //when
        ResultActions perform = mockMvc.perform(get("/{departmentId}/{groupId}/absent/{absentDate}", 1l, 1l, "2023-12-26"));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void 그룹_내_결석인원을_저장한다() throws Exception {
        //given
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

        GroupAbsentListRequest request = GroupAbsentListRequest.builder()
            .absenteeList(List.of(absenteeInfo1, absenteeInfo2))
            .build();

        GroupAbsentListResponse groupAbsentListResponse1 = GroupAbsentListResponse.builder()
            .id(1l)
            .absentDate(LocalDate.now())
            .build();
        GroupAbsentListResponse groupAbsentListResponse2 = GroupAbsentListResponse.builder()
            .id(2l)
            .absentDate(LocalDate.now())
            .build();

        given(departmentService.checkGroupAbsentInfo(anyLong(), anyLong(), any(GroupAbsentListRequest.class)))
            .willReturn(List.of(groupAbsentListResponse1, groupAbsentListResponse2));

        //when
        ResultActions perform = mockMvc.perform(
            post("/{departmentId}/{groupId}/absent", 1l, 1l)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void 그룹_내_인원을_추가한다() throws Exception {
        //given
        GroupAddMemberListResponse groupAddMemberListResponse1 = GroupAddMemberListResponse.builder()
            .id(1l)
            .name("test1")
            .build();
        GroupAddMemberListResponse groupAddMemberListResponse2 = GroupAddMemberListResponse.builder()
            .id(2l)
            .name("test2")
            .build();

        GroupAddMemberListRequest request = GroupAddMemberListRequest.builder()
            .build();

        given(departmentService.addGroupMember(anyLong(), anyLong(), any(GroupAddMemberListRequest.class)))
            .willReturn(List.of(groupAddMemberListResponse1, groupAddMemberListResponse2));

        //when
        ResultActions perform = mockMvc.perform(
            post("/{departmentId}/{groupId}/add", 1l, 1l)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void 부서_내_모든_인원을_조회한다() throws Exception {
        //given
        LocalDate today = LocalDate.of(2023, 12, 27);

        DepartmentMemberListResponse departmentMemberListResponse1 = DepartmentMemberListResponse.builder()
            .id(1l)
            .name("test1")
            .departmentType(JOSHUA)
            .attendanceCheck(ABSENT)
            .build();
        DepartmentMemberListResponse departmentMemberListResponse2 = DepartmentMemberListResponse.builder()
            .id(2l)
            .name("test2")
            .departmentType(JOSHUA)
            .attendanceCheck(ABSENT)
            .build();

        given(departmentService.getDepartmentMemberList(anyLong(), any(LocalDate.class)))
            .willReturn(List.of(departmentMemberListResponse1, departmentMemberListResponse2));

        //when
        ResultActions perform = mockMvc.perform(
            get("/{departmentId}/list/{todayDate}", 4l, today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

}