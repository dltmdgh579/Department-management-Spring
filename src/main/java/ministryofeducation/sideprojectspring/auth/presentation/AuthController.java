package ministryofeducation.sideprojectspring.auth.presentation;

import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.auth.application.AuthService;
import ministryofeducation.sideprojectspring.auth.presentation.request.Signup;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/signup")
    public void signup(@RequestBody Signup signup){
        authService.signup(signup);
    }

}
