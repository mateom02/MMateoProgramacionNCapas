
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Direccion;
import com.digis01.MMateoProgramacionNCapas.ML.Result;


public interface IDireccionDAO {
    
    Result AddByIdUsario(Direccion direccion, int idUsuario);
    
    Result GetById(int id);
    
    Result Update(Direccion direccion);
    
    Result Delete(int idDireccion);
    
}
