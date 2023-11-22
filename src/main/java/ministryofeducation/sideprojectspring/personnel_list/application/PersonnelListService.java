package ministryofeducation.sideprojectspring.personnel_list.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel_list.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel_list.infrastructure.PersonnelListRepository;
import ministryofeducation.sideprojectspring.personnel_list.presentation.dto.response.PersonnelListResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonnelListService {

    private final PersonnelListRepository personnelListRepository;

    public List<PersonnelListResponse> personnelList(){
        List<PersonnelListResponse> personnelListResponse = personnelListRepository.findAll().stream()
            .map(personnel -> new PersonnelListResponse(personnel.getName()))
            .collect(Collectors.toList());

        return personnelListResponse;
    }

}
