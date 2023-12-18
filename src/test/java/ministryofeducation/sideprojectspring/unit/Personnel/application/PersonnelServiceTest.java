package ministryofeducation.sideprojectspring.unit.Personnel.application;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;
import ministryofeducation.sideprojectspring.personnel.application.PersonnelService;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelPostRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelPostResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PersonnelServiceTest {

    @Mock
    private PersonnelRepository personnelRepository;

    @InjectMocks
    private PersonnelService personnelService;

    @Test
    public void 전체_인원을_조회한다() {
        //given
        Personnel personnel1 = testPersonnel(1l, "test1", "test1@email.com", "010-0000-0001");
        Personnel personnel2 = testPersonnel(2l, "test2", "test2@email.com", "010-0000-0002");
        Personnel personnel3 = testPersonnel(3l, "test3", "test3@email.com", "010-0000-0003");
        List<Personnel> personnelList = List.of(personnel1, personnel2, personnel3);

        given(personnelRepository.findAll()).willReturn(personnelList);

        //when
        List<PersonnelListResponse> personnelListResponse = personnelService.personnelList();

        //then
        assertThat(personnelListResponse).hasSize(3)
            .extracting("name", "phone")
            .containsExactlyInAnyOrder(
                tuple("test1", "010-0000-0001"),
                tuple("test2", "010-0000-0002"),
                tuple("test3", "010-0000-0003")
            );

        verify(personnelRepository, times(1)).findAll();
    }

    @Test
    public void 인원_상세정보를_조회할_수_있다(){
        //given
        Personnel testPersonnel = testPersonnel(1l, "test1");
        given(personnelRepository.findById(anyLong())).willReturn(Optional.of(testPersonnel));

        //when
        PersonnelDetailResponse personnelDetailResponse = personnelService.personnelDetail(testPersonnel.getId());

        //then
        assertThat(personnelDetailResponse.getName()).isEqualTo("test1");

        verify(personnelRepository, times(1)).findById(anyLong());
    }

    @Test
    void 새로운_인원을_추가한다() {
        //given
        PersonnelPostRequest personnelPostRequest = PersonnelPostRequest.builder()
            .name("test")
            .departmentType(DepartmentType.JOSHUA)
            .dateOfBirth(LocalDate.of(1997, 8, 26))
            .phone("010-0000-0000")
            .email("test@email.com")
            .workSpace("인천대학교")
            .address("인천광역시 서구 신현동")
            .build();

        Personnel personnel = testPersonnel(1l, "test", "test@email.com", "010-0000-0000");

        given(personnelRepository.save(any(Personnel.class))).willReturn(personnel);

        //when
        PersonnelPostResponse personnelPostResponse = personnelService.personnelPost(personnelPostRequest);

        //then
        assertThat(personnelPostResponse.getName()).isEqualTo("test");
        assertThat(personnelPostResponse.getPhone()).isEqualTo("010-0000-0000");

    }
}