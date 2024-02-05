package ministryofeducation.sideprojectspring.unit.department.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import ministryofeducation.sideprojectspring.config.TestQueryDslConfig;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.department.infrastructure.SmallGroupRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestQueryDslConfig.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SmallGroupRepositoryTest {

    @Autowired
    private SmallGroupRepository smallGroupRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void 부서_Id로_그룹_리스트를_불러온다(){
        //given
        Department department = Department.builder()
            .name("department")
            .build();
        departmentRepository.save(department);

        SmallGroup smallGroup1 = buildSmallGroup("smallGroup1", "leader1", department);
        SmallGroup smallGroup2 = buildSmallGroup("smallGroup2", "leader2", department);
        SmallGroup smallGroup3 = buildSmallGroup("smallGroup3", "leader3", department);

        smallGroupRepository.saveAll(List.of(smallGroup1, smallGroup2, smallGroup3));

        //when
        List<SmallGroup> smallGroupResponse = smallGroupRepository.findByDepartmentId(department.getId());

        //then
        assertThat(smallGroupResponse).hasSize(3)
            .extracting("name", "leader")
            .containsExactlyInAnyOrder(
                tuple("smallGroup1", "leader1"),
                tuple("smallGroup2", "leader2"),
                tuple("smallGroup3", "leader3")
            );
    }

    private SmallGroup buildSmallGroup(String smallGroup, String leader, Department department) {
        return SmallGroup.builder()
            .name(smallGroup)
            .leader(leader)
            .department(department)
            .build();
    }
}