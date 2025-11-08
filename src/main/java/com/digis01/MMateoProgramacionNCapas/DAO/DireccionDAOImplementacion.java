/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Colonia;
import com.digis01.MMateoProgramacionNCapas.ML.Direccion;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementacion implements IDireccionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result AddByIdUsario(Direccion direccion, int idUsuario) {

        return jdbcTemplate.execute("{CALL UsuarioDireccionAddByIdUsuario(?, ?, ?, ?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {
                callableStatement.setInt("pIdUsuario", idUsuario);
                callableStatement.setString("pCalle", direccion.getCalle());
                callableStatement.setString("pNumeroInterior", direccion.getNumeroInterior());
                callableStatement.setString("pNumeroExterior", direccion.getNumeroExterior());
                callableStatement.setInt("pIdColonia", direccion.Colonia.getIdColonia());

                int rowsAffected = callableStatement.executeUpdate();

                if (rowsAffected > 0) {
                    result.correct = true;

                } else {
                    result.correct = false;
                    result.errorMessage = "No se pudo insertar el Usuario";
                }
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

            return result;
        });
    }

    @Override
    public Result GetById(int idDireccion) {
        return jdbcTemplate.execute("{CALL DireccionGetById(?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {

                callableStatement.registerOutParameter("pDireccion", Types.REF_CURSOR);
                callableStatement.setInt("pIdDireccion", idDireccion);

                callableStatement.execute();

                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject("pDireccion");

                if (resultSetDireccion.next()) {

                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDirecion"));
                    direccion.setCalle(resultSetDireccion.getString("Calle"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSetDireccion.getInt("IdColonia"));
                    //Agregar pais, estado etc

                    result.object = direccion;
                    result.correct = true;

                } else {
                    result.correct = false;
                    result.errorMessage = "No se pudo insertar el Usuario";
                }
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

            return result;
        });

    }

    @Override
    public Result Update(Direccion direccion) {
        return jdbcTemplate.execute("{CALL DireccionUpdate(?, ?, ?, ?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {
                callableStatement.setInt("pIdDireccion", direccion.getIdDireccion());
                callableStatement.setString("pCalle", direccion.getCalle());
                callableStatement.setString("pNumeroInterior", direccion.getNumeroInterior());
                callableStatement.setString("pNumeroExterior", direccion.getNumeroExterior());
                callableStatement.setInt("pIdColonia", direccion.Colonia.getIdColonia());

                int rowsAffected = callableStatement.executeUpdate();

                if (rowsAffected > 0) {
                    result.correct = true;

                } else {
                    result.correct = false;
                    result.errorMessage = "No se pudo actualizar la Direccion";
                }
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

            return result;
        });
    }

    @Override
    public Result Delete(int idDireccion) {
        return jdbcTemplate.execute("{CALL DireccionDeleteByIdDireccion(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {
                callableStatement.setInt("pIdDireccion", idDireccion);

                int rowsAffected = callableStatement.executeUpdate();

                if (rowsAffected > 0) {
                    result.correct = true;

                } else {
                    result.correct = false;
                    result.errorMessage = "No se pudo actualizar la Direccion";
                }

            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
            return result;
        });
    }

}
