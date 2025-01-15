package tech.guilhermekaua.otisoftwaredesafiocep.services.security;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.authentication.LoginDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.authentication.RegisterDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.authentication.SuccessfulAuthenticationDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.User;
import tech.guilhermekaua.otisoftwaredesafiocep.error.enums.ErrorCode;
import tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.ErrorCodeException;

@Service
@Validated
public class AuthenticationService {
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public SuccessfulAuthenticationDTO login(@Valid LoginDTO dto) {
        final User user = userDetailsService.findByUsername(dto.username()).orElseThrow(() -> new ErrorCodeException(ErrorCode.INCORRECT_CREDENTIALS));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new ErrorCodeException(ErrorCode.INCORRECT_CREDENTIALS);
        }

        return new SuccessfulAuthenticationDTO(jwtService.encode(user));
    }

    public SuccessfulAuthenticationDTO register(@Valid RegisterDTO dto) {
        userDetailsService.findByUsername(dto.username()).ifPresent((user) -> {
            throw new ErrorCodeException(ErrorCode.USER_ALREADY_EXISTS);
        });

        final User user = userDetailsService.createUser(dto);

        return new SuccessfulAuthenticationDTO(jwtService.encode(user));
    }
}
