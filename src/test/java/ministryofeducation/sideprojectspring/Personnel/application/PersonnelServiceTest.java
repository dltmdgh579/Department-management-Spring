package ministryofeducation.sideprojectspring.Personnel.application;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import java.util.Optional;
import ministryofeducation.sideprojectspring.personnel.application.PersonnelService;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
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
class PersonnelServiceTest {

    @Mock
    private PersonnelRepository personnelRepository;

    @InjectMocks
    private PersonnelService personnelService;

    @Test
    public void 전체_인원을_조회할_수_있다() {
        //given
        doReturn(testPersonnelList()).when(personnelRepository).findAll();

        //when
        List<PersonnelListResponse> personnelList = personnelService.personnelList();

        //then
        assertThat(personnelList.size()).isEqualTo(3);
    }

    @Test
    public void Personnel_id로_인원_상세정보를_조회할_수_있다(){
        //given
        Personnel testPersonnel = testPersonnel("test1");
        doReturn(Optional.of(testPersonnel)).when(personnelRepository).findById(1l);

        //when
        PersonnelDetailResponse personnelDetail = personnelService.personnelDetail(1l);

        //then
        assertThat(personnelDetail.getName()).isEqualTo("test1");
    }

}