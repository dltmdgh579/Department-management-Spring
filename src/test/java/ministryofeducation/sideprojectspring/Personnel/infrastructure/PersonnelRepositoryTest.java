package ministryofeducation.sideprojectspring.Personnel.infrastructure;

import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PersonnelRepositoryTest {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Test
    void 사용자_추가(){
        // given
        Personnel personnel = testPersonnel("test1");

        // when
        Personnel savedPersonnel = personnelRepository.save(personnel);

        // then
        assertThat(savedPersonnel.getName()).isEqualTo(personnel.getName());
        assertThat(savedPersonnel.getEmail()).isEqualTo(personnel.getEmail());
        assertThat(savedPersonnel.getAddress()).isEqualTo(personnel.getAddress());
    }
}