package ministryofeducation.sideprojectspring.unit.Personnel.infrastructure;

import java.util.List;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PersonnelRepositoryTest {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Test
    void 개인정보_전체_리스트를_불러온다(){
        // given
        Personnel personnel1 = testPersonnel(1l, "test1", "test1@email.com");
        Personnel personnel2 = testPersonnel(2l, "test2", "test2@email.com");
        Personnel personnel3 = testPersonnel(3l, "test3", "test3@email.com");
        personnelRepository.saveAll(List.of(personnel1, personnel2, personnel3));

        // when
        List<Personnel> personnelList = personnelRepository.findAll();

        // then
        assertThat(personnelList).hasSize(3)
            .extracting("name", "email")
            .containsExactlyInAnyOrder(
                tuple("test1", "test1@email.com"),
                tuple("test2", "test2@email.com"),
                tuple("test3", "test3@email.com")
            );
    }
}