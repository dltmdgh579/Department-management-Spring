package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;

@Getter
public class GroupInfoResponse {

    private Long id;
    private String profileImage;
    private String name;

    @Builder
    private GroupInfoResponse(Long id, String profileImage, String name){
        this.id = id;
        this.profileImage = profileImage;
        this.name = name;
    }

    public static GroupInfoResponse of(Personnel personnel){
        return GroupInfoResponse.builder()
            .id(personnel.getId())
            .profileImage(personnel.getProfileImage())
            .name(personnel.getName())
            .build();
    }
}
