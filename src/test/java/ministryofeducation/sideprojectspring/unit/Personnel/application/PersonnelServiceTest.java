package ministryofeducation.sideprojectspring.unit.Personnel.application;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import ministryofeducation.sideprojectspring.config.FileSaveToLocal;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.personnel.application.PersonnelService;
import ministryofeducation.sideprojectspring.personnel.domain.Gender;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelFilterCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelPostRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelPostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PersonnelServiceTest {

    @Mock
    private PersonnelRepository personnelRepository;

    @InjectMocks
    private PersonnelService personnelService;

    @Mock
    private FileSaveToLocal fileSaveToLocal;

    @Mock
    private DepartmentRepository departmentRepository;

    @DisplayName("전체 인원을 조회힌다.")
    @Test
    public void personnelList() {
        //given
        PersonnelListResponse response1 = PersonnelListResponse.builder()
            .name("test1")
            .phone("010-0000-0001")
            .build();
        PersonnelListResponse response2 = PersonnelListResponse.builder()
            .name("test2")
            .phone("010-0000-0002")
            .build();

        PersonnelFilterCondRequest filterRequest = PersonnelFilterCondRequest.builder()
            .departmentTypeList(new ArrayList<>())
            .build();

        given(personnelRepository.findAllByCondition(any(), any()))
            .willReturn(List.of(response1, response2));

        //when
        List<PersonnelListResponse> personnelListResponse = personnelService.personnelList(filterRequest, null);

        //then
        assertThat(personnelListResponse).hasSize(2)
            .extracting("name", "phone")
            .containsExactlyInAnyOrder(
                tuple("test1", "010-0000-0001"),
                tuple("test2", "010-0000-0002")
            );
    }

    @DisplayName("검색 조건에 맞도록 검색어를 변환하여 검색한다.")
    @Test
    public void searchPersonnel() {
        //given
        PersonnelListResponse response1 = PersonnelListResponse.builder()
            .name("홍길동")
            .phone("010-0000-0001")
            .build();
        PersonnelListResponse response2 = PersonnelListResponse.builder()
            .name("김철수")
            .phone("010-0000-0002")
            .build();

        PersonnelFilterCondRequest filterRequest = PersonnelFilterCondRequest.builder()
            .departmentTypeList(new ArrayList<>())
            .build();

        given(personnelRepository.findPersonnelByName(any(), any(), any()))
            .willReturn(List.of(response1));

        //when
        List<PersonnelListResponse> personnelListResponse = personnelService.searchPersonnel(filterRequest, null, "ㅎ");

        //then
        assertThat(personnelListResponse).hasSize(1)
            .extracting("name", "phone")
            .containsExactly(
                tuple("홍길동", "010-0000-0001")
            );
    }

    @DisplayName("인원 상세정보를 조회할 수 있다.")
    @Test
    public void personnelDetail() {
        //given
        Personnel testPersonnel = testPersonnel(1l, "test1");
        given(personnelRepository.findById(anyLong())).willReturn(Optional.of(testPersonnel));

        //when
        PersonnelDetailResponse personnelDetailResponse = personnelService.personnelDetail(testPersonnel.getId());

        //then
        assertThat(personnelDetailResponse.getName()).isEqualTo("test1");

        verify(personnelRepository, times(1)).findById(anyLong());
    }

    @DisplayName("새로운 인원을 추가한다.")
    @Test
    void personnelPost() throws IOException {
        //given
        PersonnelPostRequest personnelPostRequest = PersonnelPostRequest.builder()
            .name("test")
            .departmentType(JOSHUA)
            .dateOfBirth(LocalDate.of(1997, 8, 26))
            .phone("010-0000-0000")
            .email("test@email.com")
            .workSpace("인천대학교")
            .address("인천광역시 서구 신현동")
            .build();

        Department department = Department.builder()
            .name("JOSHUA")
            .enrollment(10)
            .build();
        Personnel personnel = testPersonnel(1l, "test", "test@email.com", "010-0000-0000");

        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "test.jpg", "image/jpeg",
            "jpeg data".getBytes());

        given(personnelRepository.save(any(Personnel.class))).willReturn(personnel);
        given(departmentRepository.findByName(anyString())).willReturn(Optional.of(department));
        given(fileSaveToLocal.saveProfileImageFile(anyString(), any())).willReturn("test_1234");

        //when
        PersonnelPostResponse personnelPostResponse = personnelService.personnelPost(personnelPostRequest,
            profileImage);

        //then
        assertThat(personnelPostResponse.getName()).isEqualTo("test");
        assertThat(personnelPostResponse.getPhone()).isEqualTo("010-0000-0000");
        assertThat(department.getEnrollment()).isEqualTo(11);

    }
}