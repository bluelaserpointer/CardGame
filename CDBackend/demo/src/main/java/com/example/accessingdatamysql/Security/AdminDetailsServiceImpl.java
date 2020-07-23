package com.example.accessingdatamysql.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

import java.util.ArrayList;

import com.example.accessingdatamysql.repository.AdminRepository;
import com.example.accessingdatamysql.repository.UserRepository;

@Service
public class AdminDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AdminRepository applicationUserRepository;

    // public AdminDetailsServiceImpl(UserRepository applicationUserRepository) {
    // this.applicationUserRepository = applicationUserRepository;
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.accessingdatamysql.entity.Admin applicationUser = applicationUserRepository
                .findAdminByAdminNameEquals(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getAdminName(), applicationUser.getPassword(), new ArrayList<>());
    }
}
