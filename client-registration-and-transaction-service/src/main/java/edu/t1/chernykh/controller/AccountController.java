package edu.t1.chernykh.controller;

import edu.t1.chernykh.dto.AccountDto;
import edu.t1.chernykh.dto.AuthRequest;
import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.repository.AccountRepository;
import edu.t1.chernykh.security.JwtUtils;
import edu.t1.chernykh.util.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AccountController(AccountRepository accountRepository, AccountMapper accountMapper, AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(AccountDto accountDto){
        Account account = accountMapper.toAccount(accountDto);
        accountRepository.save(account);

        return ResponseEntity.ok("Accounted successfully created");
    }

    @PostMapping("/auth")
    public String createAuthToken(AuthRequest authRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.login(), authRequest.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            throw new Exception("Invalid username or password");
        }

        final var userDetails = userDetailsService.loadUserByUsername(authRequest.login());
        return jwtUtils.generateToken(userDetails);
    }
}
