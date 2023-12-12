package ministryofeducation.sideprojectspring.personnel.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;

import java.time.LocalDate;

@Getter
public class PersonnelListResponse {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private String profileImage;
    private DepartmentType departmentType;

    private PersonnelListResponse(){}

    @Builder
    public PersonnelListResponse(Long id, String name, LocalDate dateOfBirth, String phone,
                            String address, String profileImage, DepartmentType departmentType){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.profileImage = profileImage;
        this.departmentType = departmentType;
    }

    public static PersonnelListResponse of(Personnel personnel){
        return PersonnelListResponse.builder()
                .id(personnel.getId())
                .name(personnel.getName())
                .dateOfBirth(personnel.getDateOfBirth())
                .phone(personnel.getPhone())
                .address(personnel.getAddress())
                .profileImage(personnel.getProfileImage())
                .departmentType(personnel.getDepartmentType())
                .build();
    }

}
