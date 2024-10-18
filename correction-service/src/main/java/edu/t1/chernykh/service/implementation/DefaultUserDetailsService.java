package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.entity.User;
import edu.t1.chernykh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException("User Not Found with username: " + username));


        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), new ArrayList<>());
    }
}

