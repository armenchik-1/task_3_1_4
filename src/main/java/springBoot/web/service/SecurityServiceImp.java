package springBoot.web.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceImp implements SecurityService{

    private final PasswordEncoder passwordEncoder;

    public SecurityServiceImp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String getCrypt(String password) {
        return passwordEncoder.encode(password);
    }
}
