package ministryofeducation.sideprojectspring.department.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.common.BaseEntity;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String leader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "smallGroup")
    private List<Personnel> personnelList = new ArrayList<>();

    @Builder
    private SmallGroup(Long id, String name, String leader, Department department){
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.department = department;
    }

    public static SmallGroup createSmallGroup(Long id, String name, String leader, Department department){
        return SmallGroup.builder()
            .id(id)
            .name(name)
            .leader(leader)
            .department(department)
            .build();
    }



}
