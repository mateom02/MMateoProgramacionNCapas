
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Direccion;
import com.digis01.MMateoProgramacionNCapas.ML.Result;

public interface IDireccionJPADAO {
    
    Result AddByIdUsario(Direccion direccion, int idUsuario);
    Result Update(Direccion direccion);
    Result Delete(int idDireccion);
}
