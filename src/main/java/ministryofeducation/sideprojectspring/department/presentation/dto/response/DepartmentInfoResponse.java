package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DepartmentInfoResponse {

    private List<SmallGroupInfo> smallGroupInfoList;
    private Integer enrollment;
    private Integer attendance;

    @Builder
    private DepartmentInfoResponse(List<SmallGroupInfo> smallGroupInfoList, Integer enrollment, Integer attendance){
        this.smallGroupInfoList = smallGroupInfoList;
        this.enrollment = enrollment;
        this.attendance = attendance;
    }

    public static DepartmentInfoResponse of(List<SmallGroupInfo> smallGroupInfoList, Integer enrollment, Integer attendance){
        return DepartmentInfoResponse.builder()
            .smallGroupInfoList(smallGroupInfoList)
            .enrollment(enrollment)
            .attendance(attendance)
            .build();
    }

    @Getter
    @Builder
    public static class SmallGroupInfo {
        private String name;
        private String leader;
    }

}
