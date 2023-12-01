package ministryofeducation.sideprojectspring.personnel_list.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel_list.application.PersonnelListService;
import ministryofeducation.sideprojectspring.personnel_list.presentation.dto.response.PersonnelListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonnelListController {

    private final PersonnelListService personnelListService;

    @GetMapping("/list")
    public ResponseEntity<List<PersonnelListDto>> personnelList(){
        List<PersonnelListDto> responseDto = personnelListService.personnelList();

        return ResponseEntity.ok(responseDto);
    }
}
