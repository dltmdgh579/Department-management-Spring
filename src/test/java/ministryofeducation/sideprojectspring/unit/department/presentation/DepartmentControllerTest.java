package ministryofeducation.sideprojectspring.unit.department.presentation;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse.SmallGroupInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentNameResponse;
import ministryofeducation.sideprojectspring.unit.ControllerTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DepartmentControllerTest extends ControllerTest {

    @Test
    void 홈_화면의_부서_리스트를_조회한다() throws Exception{
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
    void 부서_정보를_조회한다() throws Exception{
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

}