
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Result;
import com.digis01.MMateoProgramacionNCapas.ML.Usuario;

public interface IUsuarioJPA {
    Result GetAll();
    Result Add(Usuario usuario);
    Result Update(Usuario usuario);
    Result UpdateImagen(int idUsuario, String imagen);
    Result GetAllDinamico(Usuario usuario);
    Result GetById(int idUsuario);
    Result Delete(int idUsuario);
}
