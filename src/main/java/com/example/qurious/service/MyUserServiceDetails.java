package com.example.qurious.service;

import com.example.qurious.entity.UserEntity;
import com.example.qurious.repository.UserRepository;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Used as Dao Provider for authentication
 */
@Data
@Transactional()
@Service
public class MyUserServiceDetails implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * loads user from the database with the given userName
     *
     * @param userName name of the user
     * @return user details of the user
     * @throws UsernameNotFoundException if username doesn't exists in the system
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + userName));

        System.out.println(user.getRole().getRoleType());
        List<SimpleGrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleType()));

        return new User(user.getUserName(),
                user.getPassword(),
                user.isVerified(),
                true,
                true,
                true,
                authorities);
    }

}
