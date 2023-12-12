package ministryofeducation.sideprojectspring.personnel.presentation.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;

@Getter
public class PersonnelDetailResponse {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String phone;
    private String landline;
    private String email;
    private String address;
    private String profileImage;
    private DepartmentType departmentType;

    private PersonnelDetailResponse(){}

    @Builder
    public PersonnelDetailResponse(Long id, String name, LocalDate dateOfBirth, String phone, String landline, String email,
        String address, String profileImage, DepartmentType departmentType){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.landline = landline;
        this.email = email;
        this.address = address;
        this.profileImage = profileImage;
        this.departmentType = departmentType;
    }

    public static PersonnelDetailResponse of(Personnel personnel){
        return PersonnelDetailResponse.builder()
            .id(personnel.getId())
            .name(personnel.getName())
            .dateOfBirth(personnel.getDateOfBirth())
            .phone(personnel.getPhone())
            .landline(personnel.getLandline())
            .email(personnel.getEmail())
            .address(personnel.getAddress())
            .profileImage(personnel.getProfileImage())
            .departmentType(personnel.getDepartmentType())
            .build();
    }
}
