package ministryofeducation.sideprojectspring.department.presentation.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupAddRequest {

    private String name;

    @Builder
    public GroupAddRequest(String name) {
        this.name = name;
    }
}
