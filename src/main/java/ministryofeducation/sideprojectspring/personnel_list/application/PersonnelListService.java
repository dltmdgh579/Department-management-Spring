package ministryofeducation.sideprojectspring.personnel_list.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.personnel_list.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel_list.infrastructure.PersonnelListRepository;
import ministryofeducation.sideprojectspring.personnel_list.presentation.dto.response.PersonnelListDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonnelListService {

    private final PersonnelListRepository personnelListRepository;

    public List<PersonnelListDto> personnelList(){
        List<PersonnelListDto> personnelListResponse = personnelListRepository.findAll().stream()
            .map(this::getPersonnelListDto)
            .collect(Collectors.toList());

        return personnelListResponse;
    }

    public PersonnelListDto getPersonnelListDto(Personnel personnel){
        return PersonnelListDto.builder()
                .id(personnel.getId())
                .name(personnel.getName())
                .dateOfBirth(personnel.getDateOfBirth())
                .phone(personnel.getPhone())
                .landline(personnel.getLandline())
                .email(personnel.getEmail())
                .address(personnel.getAddress())
                .profileImage(personnel.getProfileImage())
                .department(personnel.getDepartment())
                .build();
    }

}
