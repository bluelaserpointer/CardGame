package com.example.accessingdatamysql.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.accessingdatamysql.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //因为性能关系强制使用用户ID作为username存入
        final int userId = Integer.parseInt(username);
        final com.example.accessingdatamysql.entity.User applicationUser = applicationUserRepository.getOne(userId);
        return new User(String.valueOf(userId), applicationUser.getPassword(), AuthorityUtils.createAuthorityList(applicationUser.getIdentity()));
    }
}
