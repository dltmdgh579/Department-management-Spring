package ministryofeducation.sideprojectspring.auth.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import ministryofeducation.sideprojectspring.auth.domain.Member;
import ministryofeducation.sideprojectspring.auth.infrastructure.MemberRepository;
import ministryofeducation.sideprojectspring.auth.presentation.request.Signup;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(Signup signup){
        Optional<Member> memberOptional = memberRepository.findByUserId(signup.getUserId());
        if(memberOptional.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signup.getPassword());

        Member member = Member.builder()
            .userId(signup.getUserId())
            .password(encodedPassword)
            .build();

        memberRepository.save(member);
    }

}
