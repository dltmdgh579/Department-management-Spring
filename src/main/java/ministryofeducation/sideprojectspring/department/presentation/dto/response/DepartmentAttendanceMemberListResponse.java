package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;

@Getter
public class DepartmentAttendanceMemberListResponse {

    private Long id;
    private String name;

    @Builder
    private DepartmentAttendanceMemberListResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DepartmentAttendanceMemberListResponse of(Personnel personnel){
        return DepartmentAttendanceMemberListResponse.builder()
            .id(personnel.getId())
            .name(personnel.getName())
            .build();
    }

}
