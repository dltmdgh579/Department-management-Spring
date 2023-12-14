package ministryofeducation.sideprojectspring.common;

import static ministryofeducation.sideprojectspring.factory.PersonnelFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import ministryofeducation.sideprojectspring.factory.PersonnelFactory;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BaseEntityTest {

    @Autowired
    private PersonnelRepository personnelRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void 생성시간_수정시간을_입력한다() {
        //given
        LocalDateTime currentTime = LocalDateTime.now();

        Personnel personnel = testPersonnel(1l, "test");
        personnelRepository.save(personnel);

        //when
        Personnel savedPersonnel = personnelRepository.findById(personnel.getId()).get();

        //then
        assertThat(savedPersonnel.getCreatedDateTime()).isAfter(currentTime);
        assertThat(savedPersonnel.getModifiedDateTime()).isAfter(currentTime);
    }

    @Test
    void 변경사항이_발생하면_수정시간이_갱신된다() throws InterruptedException {
        //given
        Personnel personnel = testPersonnel(1l, "test");
        Personnel savedPersonnel = personnelRepository.save(personnel);

        //when
        savedPersonnel.changeName("changeName");
        em.flush();

        //then
        assertThat(savedPersonnel.getModifiedDateTime()).isAfter(savedPersonnel.getCreatedDateTime());
    }

}