package ministryofeducation.sideprojectspring.personnel.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.application.PersonnelService;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonnelController {

    private final PersonnelService personnelService;

    @GetMapping("/list")
    public ResponseEntity<List<PersonnelListDto>> personnelList(){
        List<PersonnelListDto> responseDto = personnelService.personnelList();

        return ResponseEntity.ok(responseDto);
    }

}
