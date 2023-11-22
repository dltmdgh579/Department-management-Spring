package ministryofeducation.sideprojectspring.personnel_list.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PersonnelListResponse {

    private String name;

    private PersonnelListResponse(){}

    public PersonnelListResponse(String name){
        this.name = name;
    }

}
