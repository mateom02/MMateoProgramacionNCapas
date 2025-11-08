/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Estado;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementacion implements IEstadoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result EstadosByIdPais(int idPais) {
        Result result = jdbcTemplate.execute("{CALL EstadosByIdPais(?, ?)}", (CallableStatementCallback<Result>) callableStatement -> {
            
            Result resultSP = new Result();
            
            try{
                
                callableStatement.setInt("pIdPais", idPais);
                callableStatement.registerOutParameter("pCursor", java.sql.Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet =(ResultSet) callableStatement.getObject("pCursor");
                resultSP.objects = new ArrayList<>();

                while(resultSet.next()){
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    resultSP.objects.add(estado);
                }
                
                resultSP.correct=true;
            }catch (Exception ex){
                resultSP.correct=false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
        });
        
        return result;
    }
    
    
    
}
