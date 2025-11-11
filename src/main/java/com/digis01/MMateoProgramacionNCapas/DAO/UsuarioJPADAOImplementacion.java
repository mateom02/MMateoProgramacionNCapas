
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.JPA.UsuarioJPA;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import com.digis01.MMateoProgramacionNCapas.ML.Rol;
import com.digis01.MMateoProgramacionNCapas.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementacion implements IUsuarioJPA {
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {

        Result result = new Result();
        
        try{
            
            TypedQuery queryUsuario = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            List<UsuarioJPA> usuarios = queryUsuario.getResultList();
            
            result.objects = new ArrayList<>();
            
            for (UsuarioJPA usuariojpa : usuarios) {
                
                Usuario usuario = modelMapper.map(usuariojpa, Usuario.class);
                if(usuario.Rol == null){
                    usuario.Rol = new Rol();
                }
                
                if(usuario.Direcciones == null || usuario.Direcciones.isEmpty()){
                    usuario.Direcciones = new ArrayList<>();
                }
                
                result.objects.add(usuario);
                
            }
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    
    
}
