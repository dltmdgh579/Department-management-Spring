package ministryofeducation.sideprojectspring.unit.department.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ministryofeducation.sideprojectspring.department.application.DepartmentService;
import ministryofeducation.sideprojectspring.department.domain.Department;
import ministryofeducation.sideprojectspring.department.domain.SmallGroup;
import ministryofeducation.sideprojectspring.department.infrastructure.DepartmentRepository;
import ministryofeducation.sideprojectspring.department.infrastructure.SmallGroupRepository;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse;
import ministryofeducation.sideprojectspring.department.presentation.dto.response.DepartmentInfoResponse.SmallGroupInfo;
import ministryofeducation.sideprojectspring.personnel.infrastructure.AttendanceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private SmallGroupRepository smallGroupRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void 부서_정보를_조회한다() {
        //given
        Department department = Department.createDepartment(1l, "department1", 20);
        SmallGroup smallGroup1 = SmallGroup.builder()
            .id(1l)
            .name("groupName1")
            .leader("leader1")
            .department(department)
            .build();
        SmallGroup smallGroup2 = SmallGroup.builder()
            .id(2l)
            .name("groupName2")
            .leader("leader2")
            .department(department)
            .build();

        given(attendanceRepository.countByAttendanceDateAndDepartmentId(any(LocalDate.class), anyLong()))
            .willReturn(10l);
        given(departmentRepository.findById(anyLong()))
            .willReturn(Optional.of(department));
        given(smallGroupRepository.findByDepartmentId(anyLong()))
            .willReturn(List.of(smallGroup1, smallGroup2));

        //when
        DepartmentInfoResponse departmentInfoResponse = departmentService.getDepartmentInfo(department.getId());

        //then
        assertThat(departmentInfoResponse.getAttendance()).isEqualTo(10);
        assertThat(departmentInfoResponse.getEnrollment()).isEqualTo(20);
        assertThat(departmentInfoResponse.getSmallGroupInfoList()).hasSize(2)
            .extracting("name", "leader")
            .containsExactlyInAnyOrder(
                tuple("groupName1", "leader1"),
                tuple("groupName2", "leader2")
            );

    }


}