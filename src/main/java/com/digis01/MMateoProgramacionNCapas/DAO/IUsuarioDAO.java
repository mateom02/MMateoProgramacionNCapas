
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Result;
import com.digis01.MMateoProgramacionNCapas.ML.Usuario;
import java.util.List;

public interface IUsuarioDAO {
    Result GetAll();
    
    Result GetUsuarioDireccionesById(int id);
    
    Result Add(Usuario usuario);
    
    Result Update(Usuario usuario);
    
    Result UpdateImage(int idUsuario, String imagen);
    
    Result GetAllDinamico(Usuario usuario);
    
    Result SaveAll(List<Usuario> usuarios);
}
