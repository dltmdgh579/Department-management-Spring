package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;

@Getter
public class GroupAbsentListResponse {

    private Long id;
    private LocalDate absentDate;

    @Builder
    private GroupAbsentListResponse(Long id, String name, LocalDate absentDate) {
        this.id = id;
        this.absentDate = absentDate;
    }

    public static GroupAbsentListResponse of(Attendance attendance){
        return GroupAbsentListResponse.builder()
            .id(attendance.getId())
            .absentDate(attendance.getAttendanceDate())
            .build();
    }
}
