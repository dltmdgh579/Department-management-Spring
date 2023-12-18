package ministryofeducation.sideprojectspring.personnel.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;

@Getter
public class PersonnelPostResponse {

    private String name;
    private String phone;

    @Builder
    private PersonnelPostResponse(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public static PersonnelPostResponse of(Personnel personnel){
        return PersonnelPostResponse.builder()
            .name(personnel.getName())
            .phone(personnel.getPhone())
            .build();
    }
}
