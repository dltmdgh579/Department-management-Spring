package ministryofeducation.sideprojectspring.Personnel.presentation;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import lombok.extern.slf4j.Slf4j;
import ministryofeducation.sideprojectspring.personnel.application.PersonnelService;
import ministryofeducation.sideprojectspring.personnel.presentation.PersonnelController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Slf4j
class PersonnelControllerTest {

    @InjectMocks
    private PersonnelController personnelController;

    @Mock
    private PersonnelService personnelService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(personnelController).build();
    }

    @Test
    public void GET_요청으로_전체_인원_리스트를_조회할_수_있다() throws Exception {
        //given
        doReturn(testPersonnelList()).when(personnelService).personnelList();

        //when
        ResultActions resultActions = mockMvc.perform(get("/list"));

        //then
        MvcResult mvcResult = resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.length()", is(3)))
            .andExpect(jsonPath("$[0].name", is("test1")))
            .andExpect(jsonPath("$[0].email", is("testEmail@gmail.com")))
            .andExpect(jsonPath("$[1].name", is("test2")))
            .andExpect(jsonPath("$[1].email", is("testEmail@gmail.com")))
            .andExpect(jsonPath("$[2].name", is("test3")))
            .andExpect(jsonPath("$[2].email", is("testEmail@gmail.com")))
            .andReturn();
    }

}