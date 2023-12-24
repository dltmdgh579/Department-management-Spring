package ministryofeducation.sideprojectspring.department.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupModifyRequest {

    private String name;

    @Builder
    public GroupModifyRequest(String name) {
        this.name = name;
    }
}
