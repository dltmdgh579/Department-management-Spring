package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.department.domain.Department;

@Getter
public class DepartmentNameResponse {

    private Long id;
    private String name;

    @Builder
    private DepartmentNameResponse(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static DepartmentNameResponse of(Department department){
        return DepartmentNameResponse.builder()
            .id(department.getId())
            .name(department.getName())
            .build();
    }

}
