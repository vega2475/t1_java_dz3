package edu.t1.chernykh.controller;

import edu.t1.chernykh.dto.MessageResponse;
import edu.t1.chernykh.dto.AuthRequest;
import edu.t1.chernykh.entity.User;
import edu.t1.chernykh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(AuthRequest authRequest){
        User user = new User(authRequest.login(),
                passwordEncoder.encode(authRequest.password()));
        if (userRepository.existsUserByLogin(authRequest.login())){
            return ResponseEntity.badRequest().body(new MessageResponse("User with this login already exists"));
        }
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
