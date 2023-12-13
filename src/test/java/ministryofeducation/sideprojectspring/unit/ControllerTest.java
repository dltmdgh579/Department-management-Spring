package ministryofeducation.sideprojectspring.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import ministryofeducation.sideprojectspring.department.application.DepartmentService;
import ministryofeducation.sideprojectspring.department.presentation.DepartmentController;
import ministryofeducation.sideprojectspring.personnel.application.PersonnelService;
import ministryofeducation.sideprojectspring.personnel.presentation.PersonnelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(controllers = {
    PersonnelController.class,
    DepartmentController.class
})
public abstract class ControllerTest {

    @MockBean
    protected PersonnelService personnelService;

    @MockBean
    protected DepartmentService departmentService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

}
