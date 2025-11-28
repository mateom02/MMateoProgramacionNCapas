
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.JPA.UsuarioJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepositoryDAO extends JpaRepository<UsuarioJPA, Integer>{
    UsuarioJPA findByUserName(String userName);
}
