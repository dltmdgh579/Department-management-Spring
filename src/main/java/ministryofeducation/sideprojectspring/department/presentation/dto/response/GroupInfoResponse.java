package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;

@Getter
public class GroupInfoResponse {

    private Long id;
    private String profileImage;
    private String name;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;

    @Builder
    private GroupInfoResponse(Long id, String profileImage, String name, LocalDate dateOfBirth, String phone, String address){
        this.id = id;
        this.profileImage = profileImage;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
    }

    public static GroupInfoResponse of(Personnel personnel){
        return GroupInfoResponse.builder()
            .id(personnel.getId())
            .profileImage(personnel.getProfileImage())
            .name(personnel.getName())
            .dateOfBirth(personnel.getDateOfBirth())
            .phone(personnel.getPhone())
            .address(personnel.getAddress())
            .build();
    }
}
