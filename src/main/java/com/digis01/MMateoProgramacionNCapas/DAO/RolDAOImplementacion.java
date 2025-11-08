package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Result;
import com.digis01.MMateoProgramacionNCapas.ML.Rol;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RolDAOImplementacion implements IRolDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result getAll() {
        Result result = jdbcTemplate.execute("{CALL RolGetAll(?)}", (CallableStatementCallback<Result>) callableStatement -> {

            Result resultSP = new Result();

            try {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultSP.objects = new ArrayList<>();

                while (resultSet.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(resultSet.getInt("IdRol"));
                    rol.setNombre(resultSet.getString("Nombre"));

                    resultSP.objects.add(rol);
                }

                resultSP.correct = true;
            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;
        });
        return result;
    }

}
