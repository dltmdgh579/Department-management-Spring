package ministryofeducation.sideprojectspring.personnel.domain;

import static lombok.AccessLevel.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.common.BaseEntity;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "small_group_id")
    private SmallGroup smallGroup;

    @Builder
    private Personnel(Long id, String name, LocalDate dateOfBirth, String phone, String landline, String email,
                     String address, String profileImage, DepartmentType departmentType, Department department, SmallGroup smallGroup) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.landline = landline;
        this.email = email;
        this.address = address;
        this.profileImage = profileImage;
        this.departmentType = departmentType;
        this.department = department;
        this.smallGroup = smallGroup;
    }

    public static Personnel createPersonnel(Long id, String name, LocalDate dateOfBirth, String phone, String landline, String email,
        String address, String profileImage, DepartmentType departmentType, Department department, SmallGroup smallGroup) {
        return Personnel.builder()
            .id(id)
            .name(name)
            .dateOfBirth(dateOfBirth)
            .phone(phone)
            .landline(landline)
            .email(email)
            .address(address)
            .profileImage(profileImage)
            .departmentType(departmentType)
            .department(department)
            .smallGroup(smallGroup)
            .build();
    }

    public void changeName(String name){
        this.name = name;
    }
}
