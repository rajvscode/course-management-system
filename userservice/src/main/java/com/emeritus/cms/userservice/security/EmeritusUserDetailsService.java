package com.emeritus.cms.userservice.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emeritus.cms.userservice.model.User;
import com.emeritus.cms.userservice.repository.UserRepository;

@Service
public class EmeritusUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // User user = userRepository.findByUsername(username)
        // .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // return new EmeritusUserDetails(user);

        Optional<User> userInfo = userRepository.findByUsername(username);
        return userInfo.map(EmeritusUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }

}
