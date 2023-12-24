package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupModifyResponse {

    private Long id;
    private String name;

    @Builder
    private GroupModifyResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
