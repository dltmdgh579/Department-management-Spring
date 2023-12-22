package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;

@Getter
public class GroupAddMemberListResponse {

    private Long id;
    private String name;

    @Builder
    private GroupAddMemberListResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GroupAddMemberListResponse of(Personnel personnel){
        return GroupAddMemberListResponse.builder()
            .id(personnel.getId())
            .name(personnel.getName())
            .build();
    }

}
