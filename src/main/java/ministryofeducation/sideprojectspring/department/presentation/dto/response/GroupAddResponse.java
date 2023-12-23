package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;

@Getter
public class GroupAddResponse {

    private Long id;
    private String name;

    @Builder
    private GroupAddResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
