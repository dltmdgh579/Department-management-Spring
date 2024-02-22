package ministryofeducation.sideprojectspring.unit.Personnel.presentation;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType.JOSHUA;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ministryofeducation.sideprojectspring.personnel.domain.Gender;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelPostRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelPostResponse;
import ministryofeducation.sideprojectspring.unit.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.ResultActions;

class PersonnelControllerTest extends ControllerTest {

    @DisplayName("전체 인원 리스트를 조회할 수 있다.")
    @Test
    public void personnelList() throws Exception {
        //given
        List<PersonnelListResponse> personnelListResponse = List.of(
            PersonnelListResponse.of(testPersonnel(1l, "test1", "test1@email.com")),
            PersonnelListResponse.of(testPersonnel(2l, "test2", "test2@email.com")),
            PersonnelListResponse.of(testPersonnel(3l, "test3", "test3@email.com"))
        );

        given(personnelService.personnelList(any(), any())).willReturn(personnelListResponse);

        String departmentParam = DepartmentType.JOSHUA.name();
        String genderParam = Gender.M.name();
        String orderParam = PersonnelOrderCondRequest.AGE.name();

        //when
        ResultActions perform = mockMvc.perform(get("/api/list")
            .param("department", departmentParam)
            .param("gender", genderParam)
            .param("order", orderParam)
        );

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3));
    }

    @DisplayName("전체 인원 리스트를 조회할 수 있다. - 모든 param은 required false")
    @Test
    public void personnelList_no_param() throws Exception {
        //given
        List<PersonnelListResponse> personnelListResponse = List.of(
            PersonnelListResponse.of(testPersonnel(1l, "test1", "test1@email.com")),
            PersonnelListResponse.of(testPersonnel(2l, "test2", "test2@email.com")),
            PersonnelListResponse.of(testPersonnel(3l, "test3", "test3@email.com"))
        );

        given(personnelService.personnelList(any(), any())).willReturn(personnelListResponse);

        //when
        ResultActions perform = mockMvc.perform(get("/api/list"));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3));
    }

    @DisplayName("이름 검색어로 인원을 조회한다.")
    @Test
    public void searchPersonnel() throws Exception {
        //given
        List<PersonnelListResponse> personnelListResponse = List.of(
            PersonnelListResponse.of(testPersonnel(1l, "홍길동", "test1@email.com")),
            PersonnelListResponse.of(testPersonnel(2l, "김철수", "test2@email.com"))
        );

        given(personnelService.searchPersonnel(any(), any(), any())).willReturn(personnelListResponse);

        String departmentParam = DepartmentType.JOSHUA.name();
        String genderParam = Gender.M.name();
        String orderParam = PersonnelOrderCondRequest.AGE.name();
        String searchParam = "ㄱ";

        //when
        ResultActions perform = mockMvc.perform(get("/api/list/search")
            .param("department", departmentParam)
            .param("gender", genderParam)
            .param("order", orderParam)
            .param("search", searchParam)
        );

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @DisplayName("이름 검색어로 인원을 조회한다. - 모든 param은 required false")
    @Test
    public void searchPersonnel_no_param() throws Exception {
        //given
        List<PersonnelListResponse> personnelListResponse = List.of(
            PersonnelListResponse.of(testPersonnel(1l, "홍길동", "test1@email.com")),
            PersonnelListResponse.of(testPersonnel(2l, "김철수", "test2@email.com"))
        );

        given(personnelService.searchPersonnel(any(), any(), any())).willReturn(personnelListResponse);

        //when
        ResultActions perform = mockMvc.perform(get("/api/list/search"));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @DisplayName("인원 상세정보를 조회할 수 있다.")
    @Test
    public void personnelDetail() throws Exception{
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

    @DisplayName("새로운 인원을 추가한다.")
    @Test
    void personnelPost() throws Exception {
        //given
        PersonnelPostRequest request = PersonnelPostRequest.builder()
            .name("test")
            .departmentType(JOSHUA)
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