package ministryofeducation.sideprojectspring.department.presentation.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepartmentInfoResponse {

    private List<SmallGroupInfo> smallGroupInfoList;
    private Integer enrollment;
    private Integer attendance;

    @Getter
    @Builder
    public static class SmallGroupInfo {
        private String name;
        private String leader;
    }

}
