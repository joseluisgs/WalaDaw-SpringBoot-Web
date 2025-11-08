package com.joseluisgs.walaspringboot.security;

import com.joseluisgs.walaspringboot.models.User;
import com.joseluisgs.walaspringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository repositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usuario = repositorio.findFirstByEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("User no encontrado");
        }

        return usuario;
    }
}
