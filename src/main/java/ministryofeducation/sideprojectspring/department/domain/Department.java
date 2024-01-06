package ministryofeducation.sideprojectspring.department.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer enrollment;

    @Builder
    private Department(Long id, String name, Integer enrollment){
        this.id = id;
        this.name = name;
        this.enrollment = enrollment;
    }

    public static Department createDepartment(Long id, String name, Integer enrollment){
        return Department.builder()
            .id(id)
            .name(name)
            .enrollment(enrollment)
            .build();
    }

    public void increaseEnrollment(){
        this.enrollment += 1;
    }

}
