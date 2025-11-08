package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Municipio;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementacion implements IMunicipioDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetByIdEstado(int idEstado) {
        return jdbcTemplate.execute("{CALL GetMunicipiosByIdEstado(?,?)}", (CallableStatementCallback < Result >) callableStatement ->{
            
            Result resultSP = new Result();
            
            try{
                
                callableStatement.setInt("pIdEstado", idEstado);
                callableStatement.registerOutParameter("pCursor", java.sql.Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet =(ResultSet) callableStatement.getObject("pCursor");
                resultSP.objects = new ArrayList<>();

                while(resultSet.next()){
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("Nombre"));
                    
                    resultSP.objects.add(municipio);
                }
                
                resultSP.correct=true;
            }catch (Exception ex){
                resultSP.correct=false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
                resultSP.objects = null;
            }
            
            return resultSP;
            
        });
    }

    
}
