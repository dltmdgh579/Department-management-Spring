package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;

@Getter
public class GroupAbsentInfoResponse {

    private Long id;
    private String profileImage;
    private String name;
    private String phone;

    @Builder
    private GroupAbsentInfoResponse(Long id, String profileImage, String name, String phone){
        this.id = id;
        this.profileImage = profileImage;
        this.name = name;
        this.phone = phone;
    }

    public static GroupAbsentInfoResponse of(Personnel personnel){
        return GroupAbsentInfoResponse.builder()
            .id(personnel.getId())
            .profileImage(personnel.getProfileImage())
            .name(personnel.getName())
            .phone(personnel.getPhone())
            .build();
    }
}
