package ministryofeducation.sideprojectspring.personnel.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.department.Department;

import java.time.LocalDate;

@Getter
@Builder
public class PersonnelListDto {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String phone;
    private String landline;
    private String email;
    private String address;
    private String profileImage;
    private Department department;

    private PersonnelListDto(){}

    public PersonnelListDto(Long id, String name, LocalDate dateOfBirth, String phone, String landline, String email,
                            String address, String profileImage, Department department){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.landline = landline;
        this.email = email;
        this.address = address;
        this.profileImage = profileImage;
        this.department = department;
    }

}
