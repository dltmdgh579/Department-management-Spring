package ministryofeducation.sideprojectspring.personnel.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.application.PersonnelService;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonnelController {

    private final PersonnelService personnelService;

    @GetMapping("/list")
    public ResponseEntity<List<PersonnelListResponse>> personnelList(){
        List<PersonnelListResponse> responseDto = personnelService.personnelList();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PersonnelDetailResponse> personnelDetail(@PathVariable("id") Long id){
        PersonnelDetailResponse responseDto = personnelService.personnelDetail(id);

        return ResponseEntity.ok(responseDto);
    }
}
