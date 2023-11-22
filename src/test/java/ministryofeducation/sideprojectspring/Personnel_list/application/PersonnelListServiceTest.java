package ministryofeducation.sideprojectspring.Personnel_list.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ministryofeducation.sideprojectspring.personnel_list.application.PersonnelListService;
import ministryofeducation.sideprojectspring.personnel_list.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel_list.infrastructure.PersonnelListRepository;
import ministryofeducation.sideprojectspring.personnel_list.presentation.dto.response.PersonnelListResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest
class PersonnelListServiceTest {

    @Autowired
    private PersonnelListRepository personnelListRepository;

    @Test
    public void 전체_인원을_조회할_수_있다() {
        //given
        List<Personnel> beforeList = personnelListRepository.findAll();
        personnelListRepository.saveAll(
            List.of(
                new Personnel("test1"),
                new Personnel("test2"),
                new Personnel("test3")
            )
        );

        //when
        List<Personnel> afterList = personnelListRepository.findAll();

        //then
        assertThat(afterList.size() - beforeList.size()).isEqualTo(3);
        assertThat(afterList.get(afterList.size()-3).getName()).isEqualTo("test1");
        assertThat(afterList.get(afterList.size()-2).getName()).isEqualTo("test2");
        assertThat(afterList.get(afterList.size()-1).getName()).isEqualTo("test3");
    }

}