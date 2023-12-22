package ministryofeducation.sideprojectspring.department.presentation.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupAddMemberListRequest {

    private List<AddMemberInfo> addMemberList;

    @Builder
    public GroupAddMemberListRequest(List<AddMemberInfo> addMemberList) {
        this.addMemberList = addMemberList;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddMemberInfo{
        private Long id;
        private String name;
        private LocalDate addDate;

        @Builder
        public AddMemberInfo(Long id, String name, LocalDate addDate) {
            this.id = id;
            this.name = name;
            this.addDate = addDate;
        }
    }

}
