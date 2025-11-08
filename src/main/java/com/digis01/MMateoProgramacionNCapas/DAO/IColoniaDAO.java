
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Result;

public interface IColoniaDAO {
        Result GetByIdMunicipio(int idMunicipio);
        
        Result GetByCodigoPostal(String codigoPostal);
}
