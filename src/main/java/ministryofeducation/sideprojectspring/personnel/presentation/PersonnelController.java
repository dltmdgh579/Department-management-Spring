package ministryofeducation.sideprojectspring.personnel.presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.application.PersonnelService;
import ministryofeducation.sideprojectspring.personnel.domain.Gender;
import ministryofeducation.sideprojectspring.personnel.domain.department_type.DepartmentType;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelFilterCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelPostRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelPostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonnelController {

    private final PersonnelService personnelService;

    @GetMapping("/list")
    public ResponseEntity<List<PersonnelListResponse>> personnelList(
        @RequestParam(value = "departmentFilter", required = false) List<DepartmentType> departmentFilter,
        @RequestParam(value = "genderFilter", required = false) Gender genderFilter,
        @RequestParam(value = "order", required = false) PersonnelOrderCondRequest orderCond
        ){

        if(departmentFilter == null)
            departmentFilter = new ArrayList<>();

        List<PersonnelListResponse> responseDto = personnelService.personnelList(
            new PersonnelFilterCondRequest(departmentFilter, genderFilter), orderCond);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PersonnelDetailResponse> personnelDetail(@PathVariable("id") Long id){
        PersonnelDetailResponse responseDto = personnelService.personnelDetail(id);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/personnel/post")
    public ResponseEntity<PersonnelPostResponse> personnelPost(
        @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
        @RequestPart(value = "requestDto") PersonnelPostRequest requestDto) throws IOException {
        PersonnelPostResponse responseDto = personnelService.personnelPost(requestDto, profileImage);

        return ResponseEntity.ok(responseDto);
    }
}
