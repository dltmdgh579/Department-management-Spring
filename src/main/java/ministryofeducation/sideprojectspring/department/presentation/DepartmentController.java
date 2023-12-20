package ministryofeducation.sideprojectspring.department.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.department.application.DepartmentService;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentNameResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAbsentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentNameResponse>> home() {
        List<DepartmentNameResponse> responseDto = departmentService.getAllDepartment();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentInfoResponse> departmentInfo(@PathVariable("departmentId") Long departmentId) {
        DepartmentInfoResponse responseDto = departmentService.getDepartmentInfo(departmentId);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{departmentId}/{groupId}")
    public ResponseEntity<List<GroupInfoResponse>> groupInfo(
        @PathVariable("departmentId") Long departmentId,
        @PathVariable("groupId") Long groupId) {
        List<GroupInfoResponse> responseDto = departmentService.getGroupInfo(departmentId, groupId);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{departmentId}/{groupId}/absent")
    public ResponseEntity<List<GroupAbsentInfoResponse>> groupAbsentInfo(
        @PathVariable("departmentId") Long departmentId,
        @PathVariable("groupId") Long groupId) {
        List<GroupAbsentInfoResponse> responseDto = departmentService.getGroupAbsentInfo(departmentId, groupId);

        return ResponseEntity.ok(responseDto);
    }
}
