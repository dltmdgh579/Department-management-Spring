package ministryofeducation.sideprojectspring.department.application;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.department.infrastructure.SmallGroupRepository;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse.SmallGroupInfo;
import ministryofeducation.sideprojectspring.personnel.infrastructure.AttendanceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final SmallGroupRepository smallGroupRepository;
    private final AttendanceRepository attendanceRepository;

    public DepartmentInfoResponse getDepartmentInfo(Long departmentId){
        Integer thisWeekAttendance = attendanceRepository.countByAttendanceDateAndDepartmentId(LocalDate.now(), departmentId).intValue();
        Integer departmentEnrollment = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new IllegalArgumentException()).getEnrollment();

        // smallGroupInfo 리스트 생성
        List<SmallGroupInfo> smallGroupInfoList = smallGroupRepository.findByDepartmentId(departmentId).stream()
            .map(smallGroup -> SmallGroupInfo.builder()
                .name(smallGroup.getName())
                .leader(smallGroup.getLeader())
                .build())
            .collect(Collectors.toList());

        // responseDto 생성
        return DepartmentInfoResponse.builder()
            .smallGroupInfoList(smallGroupInfoList)
            .attendance(thisWeekAttendance)
            .enrollment(departmentEnrollment)
            .build();
    }

}
