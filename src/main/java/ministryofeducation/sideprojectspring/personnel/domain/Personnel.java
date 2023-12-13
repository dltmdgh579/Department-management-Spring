package ministryofeducation.sideprojectspring.personnel.domain;

import static lombok.AccessLevel.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.common.BaseEntity;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Personnel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate dateOfBirth;

    private String phone;

    private String landline;

    private String email;

    private String address;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private DepartmentType departmentType;

    @Builder
    private Personnel(String name, LocalDate dateOfBirth, String phone, String landline, String email,
                     String address, String profileImage, DepartmentType departmentType) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.landline = landline;
        this.email = email;
        this.address = address;
        this.profileImage = profileImage;
        this.departmentType = departmentType;
    }

    public static Personnel createPersonnel(String name, LocalDate dateOfBirth, String phone, String landline, String email,
        String address, String profileImage, DepartmentType departmentType) {
        return Personnel.builder()
            .name(name)
            .dateOfBirth(dateOfBirth)
            .phone(phone)
            .landline(landline)
            .email(email)
            .address(address)
            .profileImage(profileImage)
            .departmentType(departmentType)
            .build();
    }
}
