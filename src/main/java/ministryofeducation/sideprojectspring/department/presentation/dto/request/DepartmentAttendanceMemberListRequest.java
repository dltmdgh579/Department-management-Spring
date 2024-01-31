package ministryofeducation.sideprojectspring.department.presentation.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentAttendanceMemberListRequest {

    private List<AttendanceMemberInfo> attendanceMemberList;

    @Builder
    public DepartmentAttendanceMemberListRequest(List<AttendanceMemberInfo> attendanceMemberList) {
        this.attendanceMemberList = attendanceMemberList;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AttendanceMemberInfo{
        private Long id;
        private String name;
        private LocalDate attendanceDate;

        @Builder
        public AttendanceMemberInfo(Long id, String name, LocalDate attendanceDate) {
            this.id = id;
            this.name = name;
            this.attendanceDate = attendanceDate;
        }
    }

}
