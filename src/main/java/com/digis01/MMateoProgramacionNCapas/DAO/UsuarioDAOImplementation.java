package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.ML.Direccion;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import com.digis01.MMateoProgramacionNCapas.ML.Rol;
import com.digis01.MMateoProgramacionNCapas.ML.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {

        Result result = jdbcTemplate.execute("{CALL UsuarioDireccionGetAll(?)}", (CallableStatementCallback<Result>) callableStatement -> {

            Result resultSP = new Result();
            try {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultSP.objects = new ArrayList<>();
                int idUsuario = 0;

                while (resultSet.next()) {

                    idUsuario = resultSet.getInt("IdUsuario");
                    //Usuario ya esta en la lista
                    if (!resultSP.objects.isEmpty() && idUsuario == ((Usuario) resultSP.objects.get(resultSP.objects.size() - 1)).getIdUsuario()) {

                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1))).Direcciones.add(direccion);
                    } else {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuario.setUserName(resultSet.getString("Username"));
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setPassword(resultSet.getString("Password"));
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.setCurp(resultSet.getString("Curp"));
                        usuario.Rol = new Rol();
                        usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuario.Rol.setNombre(resultSet.getString("NombreRol"));

                        usuario.Direcciones = new ArrayList<>();

                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        usuario.Direcciones.add(direccion);
                        resultSP.objects.add(usuario);
                    }

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

    @Override
    public Result GetUsuarioDireccionesById(int id) {
        Result result = jdbcTemplate.execute("{CALL UsuarioDireccionGetByIdUsuario(?,?, ?)}", (CallableStatementCallback<Result>) callableStatement -> {

            Result resultSP = new Result();
            try {
                callableStatement.setInt(1, id);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.registerOutParameter(3, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(2);
                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(3);

                Usuario usuario = new Usuario();

                if (resultSetUsuario.next()) {

                    usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuario.setUserName(resultSetUsuario.getString("Username"));
                    usuario.setNombre(resultSetUsuario.getString("NombreUsuario"));
                    usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuario.setEmail(resultSetUsuario.getString("Email"));
                    usuario.setPassword(resultSetUsuario.getString("Password"));
                    usuario.setFechaNacimiento(resultSetUsuario.getDate("FechaNacimiento"));
                    usuario.setSexo(resultSetUsuario.getString("Sexo"));
                    usuario.setCelular(resultSetUsuario.getString("Celular"));
                    usuario.setTelefono(resultSetUsuario.getString("Telefono"));
                    usuario.setCurp(resultSetUsuario.getString("Curp"));
                    usuario.setImagen(resultSetUsuario.getString("Imagen"));
                    usuario.Rol = new Rol();
                    usuario.Rol.setIdRol(resultSetUsuario.getInt("IdRol"));
                    usuario.Rol.setNombre(resultSetUsuario.getString("NombreRol"));

                }

                usuario.Direcciones = new ArrayList<>();
                while (resultSetDireccion.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDirecion"));
                    direccion.setCalle(resultSetDireccion.getString("Calle"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));
                    usuario.Direcciones.add(direccion);
                }
                resultSP.object = usuario;
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

    @Override
    public Result Add(Usuario usuario) {
        Result result = jdbcTemplate.execute("{CALL UsuarioDireccionAdd(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?)}", (CallableStatementCallback<Result>) callableStatement -> {

            Result resultSP = new Result();
            try {

                callableStatement.setString("pUsername", usuario.getUserName());
                callableStatement.setString("pNombre", usuario.getNombre());
                callableStatement.setString("pApellidoPaterno", usuario.getApellidoPaterno());
                callableStatement.setString("pApellidoMaterno", usuario.getApellidoMaterno());
                callableStatement.setString("pEmail", usuario.getEmail());
                callableStatement.setString("pPassword", usuario.getPassword());
                callableStatement.setDate("pFechaNacimiento", new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString("pSexo", String.valueOf(usuario.getSexo()));
                callableStatement.setString("pTelefono", usuario.getTelefono());
                callableStatement.setString("pCelular", usuario.getCelular());
                callableStatement.setString("pCurp", usuario.getCurp());
                callableStatement.setString("pImagen", usuario.getImagen());
                callableStatement.setInt("pIdRol", usuario.Rol.getIdRol());
                callableStatement.setString("pCalle", usuario.Direcciones.get(0).getCalle());
                callableStatement.setString("pNumeroInterior", usuario.Direcciones.get(0).getNumeroInterior());
                callableStatement.setString("pNumeroExterior", usuario.Direcciones.get(0).getNumeroExterior());
                callableStatement.setInt("pIdColonia", usuario.Direcciones.get(0).Colonia.getIdColonia());

                int rowsAffected = callableStatement.executeUpdate();
                if (rowsAffected > 0) {
                    resultSP.correct = true;

                } else {
                    resultSP.correct = false;
                    resultSP.errorMessage = "No se pudo insertar el Usuario";
                }

            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }

            return resultSP;
        });
        return result;
    }

    @Override
    public Result Update(Usuario usuario) {
        return jdbcTemplate.execute("{CALL UsuarioUpdateById(?,?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();

            try {
                callableStatement.setInt("pIdUsuario", usuario.getIdUsuario());
                callableStatement.setString("pUsername", usuario.getUserName());
                callableStatement.setString("pNombre", usuario.getNombre());
                callableStatement.setString("pApellidoPaterno", usuario.getApellidoPaterno());
                callableStatement.setString("pApellidoMaterno", usuario.getApellidoMaterno());
                callableStatement.setString("pEmail", usuario.getEmail());
                callableStatement.setDate("pFechaNacimiento", new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString("pSexo", String.valueOf(usuario.getSexo()));
                callableStatement.setString("pTelefono", usuario.getTelefono());
                callableStatement.setString("pCelular", usuario.getCelular());
                callableStatement.setString("pCurp", usuario.getCurp());
                callableStatement.setInt("pIdRol", usuario.Rol.getIdRol());

                int rowsAffecteds = callableStatement.executeUpdate();

                if (rowsAffecteds > 0) {
                    resultSP.correct = true;
                } else {
                    resultSP.correct = false;
                }

            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;
        });
    }

    @Override
    public Result UpdateImage(int idUsuario, String imagen) {
        return jdbcTemplate.execute("{CALL UsuarioImagenUpdate(?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();

            try {
                callableStatement.setInt("pIdUsuario", idUsuario);
                callableStatement.setString("pImagen", imagen);

                int rowsAffecteds = callableStatement.executeUpdate();

                if (rowsAffecteds > 0) {
                    resultSP.correct = true;
                } else {
                    resultSP.correct = false;
                }

            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;
        });
    }

    @Override
    public Result GetAllDinamico(Usuario usuario) {
        return jdbcTemplate.execute("CALL UsuarioGetAllDinamico(?, ?, ?, ?, ?)", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();

            try {
                callableStatement.setString("pNombreUsuario", usuario.getNombre());
                callableStatement.setString("pApellidoPaterno", usuario.getApellidoPaterno());
                callableStatement.setString("pApellidoMaterno", usuario.getApellidoMaterno());
                callableStatement.setInt("pIdRol", usuario.Rol.getIdRol());
                callableStatement.registerOutParameter("pCursor", java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject("pCursor");
                resultSP.objects = new ArrayList<>();
                int idUsuario = 0;

                while (resultSet.next()) {

                    idUsuario = resultSet.getInt("IdUsuario");
                    //Usuario ya esta en la lista
                    if (!resultSP.objects.isEmpty() && idUsuario == ((Usuario) resultSP.objects.get(resultSP.objects.size() - 1)).getIdUsuario()) {

                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1))).Direcciones.add(direccion);
                    } else {
                        Usuario user = new Usuario();
                        user.setIdUsuario(resultSet.getInt("IdUsuario"));
                        user.setUserName(resultSet.getString("Username"));
                        user.setNombre(resultSet.getString("NombreUsuario"));
                        user.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        user.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        user.setEmail(resultSet.getString("Email"));
                        user.setPassword(resultSet.getString("Password"));
                        user.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        user.setSexo(resultSet.getString("Sexo"));
                        user.setCelular(resultSet.getString("Celular"));
                        user.setTelefono(resultSet.getString("Telefono"));
                        user.setCurp(resultSet.getString("Curp"));
                        user.Rol = new Rol();
                        user.Rol.setIdRol(resultSet.getInt("IdRol"));
                        user.Rol.setNombre(resultSet.getString("NombreRol"));

                        user.Direcciones = new ArrayList<>();

                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        user.Direcciones.add(direccion);
                        resultSP.objects.add(user);
                    }

                }
                resultSP.correct = true;
            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;

        });
    }
    
    @Override
    @Transactional
    public Result SaveAll(List<Usuario> usuarios) {

        Result result = new Result();
        
        try {
             jdbcTemplate.batchUpdate("{CALL UsuarioAdd(?, ?, ?,?, ?, ?, ?, ?, ?, ?,?, ?)}",
                    usuarios, 100,
                    ( callableStatement,  usuario) -> {
                        callableStatement.setString(1, usuario.getUserName());
                        callableStatement.setString(2, usuario.getNombre());
                        callableStatement.setString(3, usuario.getApellidoPaterno());
                        callableStatement.setString(4, usuario.getApellidoMaterno());
                        callableStatement.setString(5, usuario.getEmail());
                        callableStatement.setString(6, usuario.getPassword());
                        callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                        callableStatement.setString(8, String.valueOf(usuario.getSexo()));
                        callableStatement.setString(9, usuario.getTelefono());
                        callableStatement.setString(10, usuario.getCelular());
                        callableStatement.setString(11, usuario.getCurp());
                        callableStatement.setInt(12, usuario.Rol.getIdRol());
                    });
            
            result.correct=true;
            
        } catch (Exception ex) {
            result.correct=false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex=ex;
            throw ex;
        }
        return result;
    }

}

