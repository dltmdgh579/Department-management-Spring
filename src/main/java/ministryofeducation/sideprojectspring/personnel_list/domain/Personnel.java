package ministryofeducation.sideprojectspring.personnel_list.domain;

import static lombok.AccessLevel.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ministryofeducation.sideprojectspring.personnel_list.domain.department.Department;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Personnel {
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
    private Department department;

    public Personnel(String name, LocalDate dateOfBirth, String phone, String landline, String email,
                     String address, String profileImage, Department department) {
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
