package ministryofeducation.sideprojectspring.personnel.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonnelService {

    private final PersonnelRepository personnelRepository;

    public List<PersonnelListResponse> personnelList(){
        List<PersonnelListResponse> personnelListResponse = personnelRepository.findAll().stream()
            .map(this::getPersonnelListDto)
            .collect(Collectors.toList());

        return personnelListResponse;
    }

    public PersonnelListResponse getPersonnelListDto(Personnel personnel){
        return PersonnelListResponse.builder()
                .id(personnel.getId())
                .name(personnel.getName())
                .dateOfBirth(personnel.getDateOfBirth())
                .phone(personnel.getPhone())
                .address(personnel.getAddress())
                .profileImage(personnel.getProfileImage())
                .department(personnel.getDepartment())
                .build();
    }

}
