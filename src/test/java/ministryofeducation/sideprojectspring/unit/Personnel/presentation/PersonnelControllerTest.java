package ministryofeducation.sideprojectspring.unit.Personnel.presentation;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelPostRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelPostResponse;
import ministryofeducation.sideprojectspring.unit.ControllerTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
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

        given(personnelService.personnelList(any())).willReturn(personnelListResponse);

        String response = objectMapper.writeValueAsString(personnelListResponse);

        //when
        ResultActions perform = mockMvc.perform(get("/api"
            + "/list"));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void 인원_상세정보를_조회할_수_있다() throws Exception{
        //given
        PersonnelDetailResponse response = PersonnelDetailResponse.of(testPersonnel(1l, "test"));

        given(personnelService.personnelDetail(anyLong())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/detail/{personnelId}", 1l));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    void 새로운_인원을_추가한다() throws Exception {
        //given
        PersonnelPostRequest request = PersonnelPostRequest.builder()
            .name("test")
            .departmentType(DepartmentType.JOSHUA)
            .dateOfBirth(LocalDate.of(1997, 8, 26))
            .phone("010-0000-0000")
            .email("test@email.com")
            .workSpace("인천대학교")
            .address("인천광역시 서구 신현동")
            .build();

        PersonnelPostResponse response = PersonnelPostResponse.builder()
            .name("test")
            .phone("010-0000-0000")
            .build();

        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "test.jpg", "image/jpeg",
            "test file".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile requestDto = new MockMultipartFile("requestDto", null, "application/json",
            objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8));

        given(personnelService.personnelPost(any(), any())).willReturn(response);

        //when
        ResultActions perform = mockMvc.perform(
            multipart("/api/personnel/post")
                .file(profileImage)
                .file(requestDto)
        );

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("test"))
            .andExpect(jsonPath("$.phone").value("010-0000-0000"));
    }

}