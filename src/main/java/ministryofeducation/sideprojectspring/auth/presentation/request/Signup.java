package ministryofeducation.sideprojectspring.auth.presentation.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Signup {

    private String userId;
    private String password;

}
