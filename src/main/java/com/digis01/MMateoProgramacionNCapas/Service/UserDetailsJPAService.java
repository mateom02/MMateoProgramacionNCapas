package com.digis01.MMateoProgramacionNCapas.Service;

import com.digis01.MMateoProgramacionNCapas.DAO.IUsuarioRepositoryDAO;
import com.digis01.MMateoProgramacionNCapas.JPA.UsuarioJPA;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsJPAService implements UserDetailsService {
    
    private IUsuarioRepositoryDAO iUsuarioRepositoryDAO;
    
    public UserDetailsJPAService(IUsuarioRepositoryDAO iUsuarioRepositoryDAO){
        this.iUsuarioRepositoryDAO = iUsuarioRepositoryDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioJPA usuario =  iUsuarioRepositoryDAO.findByUserName(username);
        
        return User.withUsername(usuario.getUserName())
                .password(usuario.getPassword())
                .roles(usuario.Rol.getNombre())
                .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    
}
