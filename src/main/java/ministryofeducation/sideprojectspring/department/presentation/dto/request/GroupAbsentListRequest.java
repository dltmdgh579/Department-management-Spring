package ministryofeducation.sideprojectspring.department.presentation.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupAbsentListRequest {

    private List<AbsenteeInfo> absenteeList;

    @Builder
    public GroupAbsentListRequest(List<AbsenteeInfo> absenteeList) {
        this.absenteeList = absenteeList;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AbsenteeInfo{
        private Long id;
        private String name;
        private LocalDate absentDate;

        @Builder
        public AbsenteeInfo(Long id, String name, LocalDate absentDate) {
            this.id = id;
            this.name = name;
            this.absentDate = absentDate;
        }
    }

}
