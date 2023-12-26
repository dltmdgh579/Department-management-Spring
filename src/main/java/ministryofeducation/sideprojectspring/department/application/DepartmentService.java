package ministryofeducation.sideprojectspring.department.application;

import static ministryofeducation.sideprojectspring.personnel.domain.attendance.AttendanceCheck.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.department.infrastructure.SmallGroupRepository;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAbsentListRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAbsentListRequest.AbsenteeInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAddMemberListRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAddMemberListRequest.AddMemberInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupAddRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.request.GroupModifyRequest;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse.SmallGroupInfo;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentMemberListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentNameResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAbsentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAbsentListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAddMemberListResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupAddResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.GroupModifyResponse;
import ministryofeducation.sideprojectspring.personnel.domain.Attendance;
import ministryofeducation.sideprojectspring.personnel.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel.infrastructure.AttendanceRepository;
import ministryofeducation.sideprojectspring.personnel.infrastructure.PersonnelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final SmallGroupRepository smallGroupRepository;
    private final AttendanceRepository attendanceRepository;
    private final PersonnelRepository personnelRepository;

    public DepartmentInfoResponse getDepartmentInfo(Long departmentId) {
        Integer thisWeekAttendance = attendanceRepository.countByAttendanceDateAndDepartmentId(LocalDate.now(),
            departmentId).intValue();
        Integer departmentEnrollment = departmentRepository.findById(departmentId)
            .map(Department::getEnrollment)
            .orElseThrow(() -> new IllegalArgumentException());

        // smallGroupInfo 리스트 생성
        List<SmallGroupInfo> smallGroupInfoList = getSmallGroupInfoList(departmentId);

        // responseDto 생성
        return DepartmentInfoResponse.of(smallGroupInfoList, departmentEnrollment, thisWeekAttendance);
    }

    public List<DepartmentMemberListResponse> getDepartmentMemberList(Long departmentId, LocalDate todayDate){
        List<Personnel> personnelList = personnelRepository.findByDepartmentId(departmentId);

        return personnelList.stream()
            .map(personnel -> DepartmentMemberListResponse.of(personnel, todayDate))
            .collect(Collectors.toList());

    }

    public GroupAddResponse addGroup(Long departmentId, GroupAddRequest requestDto){
        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new IllegalArgumentException());

        SmallGroup smallGroup = SmallGroup.builder()
            .name(requestDto.getName())
            .department(department)
            .build();

        SmallGroup savedSmallGroup = smallGroupRepository.save(smallGroup);
        return GroupAddResponse.builder()
            .id(savedSmallGroup.getId())
            .name(savedSmallGroup.getName())
            .build();
    }

    @Transactional
    public GroupModifyResponse modifyGroup(Long departmentId, Long groupId, GroupModifyRequest requestDto){
        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new IllegalArgumentException());

        SmallGroup smallGroup = smallGroupRepository.findById(groupId)
            .orElseThrow(() -> new IllegalArgumentException());

        smallGroup.changeName(requestDto.getName());
        return GroupModifyResponse.builder()
            .id(groupId)
            .name(requestDto.getName())
            .build();
    }

    private List<SmallGroupInfo> getSmallGroupInfoList(Long departmentId) {
        return smallGroupRepository.findByDepartmentId(departmentId).stream()
            .map(smallGroup -> SmallGroupInfo.builder()
                .id(smallGroup.getId())
                .name(smallGroup.getName())
                .leader(smallGroup.getLeader())
                .build())
            .collect(Collectors.toList());
    }

    public List<DepartmentNameResponse> getAllDepartment() {
        return departmentRepository.findAll().stream()
            .map(DepartmentNameResponse::of)
            .collect(Collectors.toList());
    }

    public List<GroupInfoResponse> getGroupInfo(Long departmentId, Long groupId) {
        return personnelRepository.findByDepartmentIdAndSmallGroupId(departmentId, groupId).stream()
            .map(GroupInfoResponse::of)
            .collect(Collectors.toList());
    }

    public List<GroupAbsentInfoResponse> getGroupAbsentInfo(Long departmentId, Long groupId, LocalDate absentDate) {
        return personnelRepository.findByAbsentPersonnel(departmentId, groupId, absentDate).stream()
            .map(GroupAbsentInfoResponse::of)
            .collect(Collectors.toList());
    }

    public List<GroupAbsentListResponse> checkGroupAbsentInfo(Long departmentId, Long groupId,
        GroupAbsentListRequest requestDto) {
        List<AbsenteeInfo> absenteeList = requestDto.getAbsenteeList();

        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new IllegalArgumentException());

        List<Attendance> attendanceList = absenteeList.stream()
            .map(absenteeInfo -> buildAttendance(absenteeInfo, department))
            .collect(Collectors.toList());

        List<Attendance> savedAttendanceList = attendanceRepository.saveAll(attendanceList);

        return savedAttendanceList.stream()
            .map(GroupAbsentListResponse::of)
            .collect(Collectors.toList());
    }

    private Attendance buildAttendance(AbsenteeInfo absenteeInfo, Department department) {
        Personnel personnel = personnelRepository.findById(absenteeInfo.getId())
            .orElseThrow(() -> new IllegalArgumentException());

        Attendance attendance = Attendance.builder()
            .attendanceDate(absenteeInfo.getAbsentDate())
            .attendanceCheck(ABSENT)
            .department(department)
            .personnel(personnel)
            .build();

        personnel.addAttendance(attendance);
        return attendance;
    }
    @Transactional
    public List<GroupAddMemberListResponse> addGroupMember(Long departmentId, Long groupId,
        GroupAddMemberListRequest requestDto) {
        List<AddMemberInfo> memberList = requestDto.getAddMemberList();

        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new IllegalArgumentException());

        SmallGroup smallGroup = smallGroupRepository.findById(groupId)
            .orElseThrow(() -> new IllegalArgumentException());

        List<Personnel> personnelList = memberList.stream()
            .map(member -> changePersonnelSmallGroup(member, department, smallGroup))
            .collect(Collectors.toList());

        return personnelList.stream()
            .map(GroupAddMemberListResponse::of)
            .collect(Collectors.toList());
    }

    private Personnel changePersonnelSmallGroup(AddMemberInfo member, Department department, SmallGroup smallGroup) {
        Personnel personnel = personnelRepository.findById(member.getId())
            .orElseThrow(() -> new IllegalArgumentException());
        personnel.changeSmallGroup(smallGroup);

        return personnel;
    }
}
