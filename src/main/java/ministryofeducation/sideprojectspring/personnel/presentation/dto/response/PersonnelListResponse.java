package ministryofeducation.sideprojectspring.personnel.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.department.Department;

import java.time.LocalDate;

@Getter
@Builder
public class PersonnelListResponse {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private String profileImage;
    private Department department;

    private PersonnelListResponse(){}

    public PersonnelListResponse(Long id, String name, LocalDate dateOfBirth, String phone,
                            String address, String profileImage, Department department){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.profileImage = profileImage;
        this.department = department;
    }

    public PersonnelListResponse(Personnel personnel){
        this.id = personnel.getId();
        this.name = personnel.getName();
        this.dateOfBirth = personnel.getDateOfBirth();
        this.phone = personnel.getPhone();
        this.address = personnel.getAddress();
        this.profileImage = personnel.getProfileImage();
        this.department = personnel.getDepartment();
    }

}
