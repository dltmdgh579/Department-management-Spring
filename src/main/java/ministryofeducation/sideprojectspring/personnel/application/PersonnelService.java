package ministryofeducation.sideprojectspring.personnel.application;

import static ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.config.FileSaveToLocal;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelPostRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelPostResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PersonnelService {

    private final PersonnelRepository personnelRepository;
    private final DepartmentRepository departmentRepository;
    private final FileSaveToLocal fileSaveToLocal;

    public List<PersonnelListResponse> personnelList() {
        List<PersonnelListResponse> personnelListResponse = personnelRepository.findAll().stream()
            .map(PersonnelListResponse::of)
            .collect(Collectors.toList());

        return personnelListResponse;
    }

    public PersonnelDetailResponse personnelDetail(Long id) {
        Personnel personnel = personnelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        return PersonnelDetailResponse.of(personnel);
    }

    @Transactional
    public PersonnelPostResponse personnelPost(PersonnelPostRequest personnelPostRequest, MultipartFile file) throws IOException {

        Personnel personnel = Personnel.builder()
            .name(personnelPostRequest.getName())
            .departmentType(personnelPostRequest.getDepartmentType())
            .dateOfBirth(personnelPostRequest.getDateOfBirth())
            .phone(personnelPostRequest.getPhone())
            .email(personnelPostRequest.getEmail())
            .workSpace(personnelPostRequest.getWorkSpace())
            .address(personnelPostRequest.getAddress())
            .build();

        Personnel savedPersonnel = personnelRepository.save(personnel);

        String name = savedPersonnel.getName();
        String path = fileSaveToLocal.saveProfileImageFile(name, file);
        personnel.changeProfileImage(path);

        Department department = departmentRepository.findByName(personnelPostRequest.getDepartmentType().toString())
            .orElseThrow(() -> new IllegalArgumentException());
        department.increaseEnrollment();

        return PersonnelPostResponse.of(savedPersonnel);
    }

}
