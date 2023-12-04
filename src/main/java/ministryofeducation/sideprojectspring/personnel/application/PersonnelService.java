package ministryofeducation.sideprojectspring.personnel.application;

import static ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonnelService {

    private final PersonnelRepository personnelRepository;

    public List<PersonnelListResponse> personnelList(){
        List<PersonnelListResponse> personnelListResponse = personnelRepository.findAll().stream()
            .map(PersonnelListResponse::new)
            .collect(Collectors.toList());

        return personnelListResponse;
    }

    public PersonnelDetailResponse personnelDetail(Long id){
        Personnel personnel = personnelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        return PersonnelDetailResponse.from(personnel);
    }

}
