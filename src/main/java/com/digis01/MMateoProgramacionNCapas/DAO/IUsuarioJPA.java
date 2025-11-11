
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Result;
import com.digis01.MMateoProgramacionNCapas.ML.Usuario;

public interface IUsuarioJPA {
    Result GetAll();
    Result Add(Usuario usuario);
}
