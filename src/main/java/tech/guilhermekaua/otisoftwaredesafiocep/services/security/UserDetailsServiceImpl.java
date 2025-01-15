package tech.guilhermekaua.otisoftwaredesafiocep.services.security;

import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tech.guilhermekaua.otisoftwaredesafiocep.dao.UserRepository;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.authentication.RegisterDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.User;
import tech.guilhermekaua.otisoftwaredesafiocep.security.UserDetailsImpl;

import java.util.Optional;

@Service
@Validated
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(@Valid RegisterDTO dto) {
        final User user = new User(null, dto.username(), passwordEncoder.encode(dto.password()));
        return userRepository.save(user);
    }
}
