package ministryofeducation.sideprojectspring;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

class SideprojectSpringApplicationTests {

	@Test
	void main() {
		try(MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)){
			when(SpringApplication.run(SideprojectSpringApplication.class)).thenReturn(null);

			SideprojectSpringApplication.main(new String[]{});

			springApplication.verify(
				() -> SpringApplication.run(SideprojectSpringApplication.class), only()
			);
		}
	}

}
