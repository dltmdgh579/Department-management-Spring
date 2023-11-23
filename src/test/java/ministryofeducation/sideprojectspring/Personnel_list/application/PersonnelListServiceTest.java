package ministryofeducation.sideprojectspring.Personnel_list.application;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import ministryofeducation.sideprojectspring.factory.PersonnelFactory;
import ministryofeducation.sideprojectspring.personnel_list.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel_list.infrastructure.PersonnelListRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest
@ActiveProfiles("test")
class PersonnelListServiceTest {

    @Autowired
    private PersonnelListRepository personnelListRepository;

    @Test
    public void 전체_인원을_조회할_수_있다() {
        //given
        List<Personnel> beforeList = personnelListRepository.findAll();
        personnelListRepository.saveAll(
            List.of(
                testPersonnel("test1"),
                testPersonnel("test2"),
                testPersonnel("test3")
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