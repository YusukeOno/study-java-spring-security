package com.example.SecurityGetUser.domain.service;

import com.example.SecurityGetUser.domain.repository.LoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("UserDetailsServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private LoginUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // リポジトリー（DAO）からUserDetailsを取得
        UserDetails user = repository.selectOne(username);

        return user;
    }
}
