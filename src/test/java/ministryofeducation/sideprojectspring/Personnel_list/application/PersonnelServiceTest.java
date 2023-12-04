package ministryofeducation.sideprojectspring.Personnel_list.application;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import ministryofeducation.sideprojectspring.personnel.application.PersonnelListService;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelListRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListDto;
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
class PersonnelListServiceTest {

    @Mock
    private PersonnelListRepository personnelListRepository;

    @InjectMocks
    private PersonnelListService personnelListService;

    @Test
    public void 전체_인원을_조회할_수_있다() {
        //given
        doReturn(testPersonnelList()).when(personnelListRepository).findAll();

        //when
        List<PersonnelListDto> personnelList = personnelListService.personnelList();

        //then
        assertThat(personnelList.size()).isEqualTo(3);
    }

}