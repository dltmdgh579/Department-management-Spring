package ministryofeducation.sideprojectspring.personnel.application;

import static ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.config.FileSaveToLocal;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelFilterCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelOrderCondRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.request.PersonnelPostRequest;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelDetailResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelListResponse;
import ministryofeducation.sideprojectspring.personnel.presentation.dto.response.PersonnelPostResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PersonnelService {

    private final PersonnelRepository personnelRepository;
    private final DepartmentRepository departmentRepository;
    private final FileSaveToLocal fileSaveToLocal;

    public List<PersonnelListResponse> personnelList(PersonnelFilterCondRequest filterCond,
        PersonnelOrderCondRequest orderCond) {
        return personnelRepository.findAllByCondition(filterCond, orderCond);
    }

    public List<PersonnelListResponse> searchPersonnel(PersonnelFilterCondRequest filterCond,
        PersonnelOrderCondRequest orderCond, String searchWord) {
        String[] searchWordRange = WordIndex.searchWordRange(searchWord);

        return personnelRepository.findPersonnelByName(filterCond, orderCond, searchWordRange);
    }

    public PersonnelDetailResponse personnelDetail(Long id) {
        Personnel personnel = personnelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        return PersonnelDetailResponse.of(personnel);
    }

    @Transactional
    public PersonnelPostResponse personnelPost(PersonnelPostRequest personnelPostRequest, MultipartFile file)
        throws IOException {
        Department department = departmentRepository.findByName(personnelPostRequest.getDepartmentType().name())
            .orElseThrow(() -> new IllegalArgumentException());

        Personnel personnel = Personnel.builder()
            .name(personnelPostRequest.getName())
            .departmentType(personnelPostRequest.getDepartmentType())
            .dateOfBirth(personnelPostRequest.getDateOfBirth())
            .phone(personnelPostRequest.getPhone())
            .email(personnelPostRequest.getEmail())
            .workSpace(personnelPostRequest.getWorkSpace())
            .address(personnelPostRequest.getAddress())
            .department(department)
            .build();

        Personnel savedPersonnel = personnelRepository.save(personnel);

        if (file != null) {
            String name = savedPersonnel.getName();
            String path = fileSaveToLocal.saveProfileImageFile(name, file);
            personnel.changeProfileImage(path);
        }

        department.increaseEnrollment();

        return PersonnelPostResponse.of(savedPersonnel);
    }

}
