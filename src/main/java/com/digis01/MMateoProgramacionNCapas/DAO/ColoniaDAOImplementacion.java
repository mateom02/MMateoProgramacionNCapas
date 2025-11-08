/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Colonia;
import com.digis01.MMateoProgramacionNCapas.ML.Estado;
import com.digis01.MMateoProgramacionNCapas.ML.Municipio;
import com.digis01.MMateoProgramacionNCapas.ML.Pais;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementacion implements IColoniaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetByIdMunicipio(int idMunicipio) {
        return jdbcTemplate.execute("{CALL GetColoniasByIdMunicipio(?,?)}", (CallableStatementCallback< Result>) callableStatement -> {

            Result resultSP = new Result();

            try {

                callableStatement.setInt("pIdMunicipio", idMunicipio);
                callableStatement.registerOutParameter("pCursor", java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject("pCursor");
                resultSP.objects = new ArrayList<>();

                while (resultSet.next()) {
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    resultSP.objects.add(colonia);
                }

                resultSP.correct = true;
            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
                resultSP.objects = null;
            }

            return resultSP;

        });
    }

    @Override
    public Result GetByCodigoPostal(String codigoPostal) {
        return jdbcTemplate.execute("{CALL GetDireccionesByCodigoPostal(?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();

            try {

                callableStatement.setString("pCodigoPostal", codigoPostal);
                callableStatement.registerOutParameter("pCursor", java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject("pCursor");
                resultSP.objects = new ArrayList<>();

                while (resultSet.next()) {
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("NombreColonia"));
                    colonia.Municipio = new Municipio();
                    colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                    colonia.Municipio.Estado = new Estado();
                    colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                    colonia.Municipio.Estado.Pais = new Pais();
                    colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                    resultSP.objects.add(colonia);
                }

                resultSP.correct = true;
            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
                resultSP.objects = null;
            }

            return resultSP;
        });
        
    }

}
