package com.example.accessingdatamysql.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.accessingdatamysql.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository applicationUserRepository;

    // public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
    // this.applicationUserRepository = applicationUserRepository;
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final com.example.accessingdatamysql.entity.User applicationUser = applicationUserRepository
                .findUserByUserNameEquals(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }

        // Identity 要写成 "ROLE_ADMIN","ROLE_USER"
        return new User(applicationUser.getUserName(), applicationUser.getPassword(), AuthorityUtils.createAuthorityList(applicationUser.getIdentity()));
    }
}
