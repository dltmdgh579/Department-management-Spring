package ministryofeducation.sideprojectspring.unit.Personnel.presentation;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import ministryofeducation.sideprojectspring.unit.ControllerTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PersonnelControllerTest extends ControllerTest {

    @Test
    public void 전체_인원_리스트를_조회할_수_있다() throws Exception {
        //given
        List<PersonnelListResponse> personnelListResponse = List.of(
            PersonnelListResponse.of(testPersonnel(1l, "test1", "test1@email.com")),
            PersonnelListResponse.of(testPersonnel(2l, "test2", "test2@email.com")),
            PersonnelListResponse.of(testPersonnel(3l, "test3", "test3@email.com"))
        );

        given(personnelService.personnelList()).willReturn(personnelListResponse);

        String response = objectMapper.writeValueAsString(personnelListResponse);

        //when
        ResultActions perform = mockMvc.perform(get("/list"));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void 인원_상세정보를_조회할_수_있다() throws Exception{
        //given

        PersonnelDetailResponse request = PersonnelDetailResponse.of(testPersonnel(1l, "test"));

        given(personnelService.personnelDetail(anyLong())).willReturn(request);

        //when
        ResultActions resultActions = mockMvc.perform(get("/detail/{personnelId}", 1l));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("test")));
    }

}