package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.JPA.DireccionJPA;
import com.digis01.MMateoProgramacionNCapas.JPA.UsuarioJPA;
import com.digis01.MMateoProgramacionNCapas.ML.Direccion;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import com.digis01.MMateoProgramacionNCapas.ML.Rol;
import com.digis01.MMateoProgramacionNCapas.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementacion implements IUsuarioJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {

            TypedQuery queryUsuario = entityManager.createQuery("FROM UsuarioJPA ORDER BY IdUsuario", UsuarioJPA.class);
            List<UsuarioJPA> usuarios = queryUsuario.getResultList();

            result.objects = new ArrayList<>();
            for (UsuarioJPA usuariojpa : usuarios) {
                Usuario usuario = modelMapper.map(usuariojpa, Usuario.class);
                if (usuario.Rol == null) {
                    usuario.Rol = new Rol();
                }
                if (usuario.Direcciones == null || usuario.Direcciones.isEmpty()) {
                    usuario.Direcciones = new ArrayList<>();
                }
                result.objects.add(usuario);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetById(int idUsuario) {
        Result result = new Result();

        try {

            UsuarioJPA usuariojpa = entityManager.find(UsuarioJPA.class, idUsuario);
            Usuario usuario = modelMapper.map(usuariojpa, Usuario.class);

            result.correct = true;
            result.object = usuario;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result Add(Usuario usuario) {
        Result result = new Result();

        try {

            UsuarioJPA usuariojpa = modelMapper.map(usuario, UsuarioJPA.class);
            usuariojpa.Direcciones.get(0).Usuario = usuariojpa;
            entityManager.persist(usuariojpa);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result Update(Usuario usuario) {

        Result result = new Result();

        try {

            UsuarioJPA usuarioExistente = entityManager.find(UsuarioJPA.class, usuario.getIdUsuario());

            //SettearValores
            usuario.setPassword(usuarioExistente.getPassword());
            usuario.setImagen(usuarioExistente.getImagen());
            if (usuario.Direcciones.size() > 0) {
                for (DireccionJPA direccionjpa : usuarioExistente.Direcciones) {
                    Direccion direccion = modelMapper.map(direccionjpa, Direccion.class);
                    usuario.Direcciones.add(direccion);
                }
            }

            UsuarioJPA usuarioSave = modelMapper.map(usuario, UsuarioJPA.class);
            entityManager.merge(usuarioSave);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Override
    @Transactional
    public Result UpdateImagen(int idUsuario, String imagen) {
        Result result = new Result();

        try {

            UsuarioJPA usuariojpa = entityManager.find(UsuarioJPA.class, idUsuario);

            usuariojpa.setImagen(imagen);

            entityManager.merge(usuariojpa);
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Override
    @Transactional
    public Result Delete(int idUsuario) {
        Result result = new Result();
        try {
            UsuarioJPA usuariojpa = entityManager.find(UsuarioJPA.class, idUsuario);
            entityManager.remove(usuariojpa);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetAllDinamico(Usuario usuario) {
        Result result = new Result();
        try {
            String query = "SELECT u FROM UsuarioJPA u WHERE ";
            result.objects = new ArrayList<>();

            query += "LOWER(u.Nombre) LIKE  '%' || :nombre || '%' AND LOWER(u.ApellidoPaterno) LIKE '%' || :pApellidoPaterno || '%' AND LOWER(u.ApellidoMaterno) LIKE '%' || :pApellidoMaterno|| '%'";

            if (usuario.Rol.getIdRol() > 0) {
                query += " AND u.Rol.IdRol = :pIdRol";
            }

            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery(query, UsuarioJPA.class);

            queryUsuario.setParameter("nombre", usuario.getNombre().toLowerCase());
            queryUsuario.setParameter("pApellidoPaterno", usuario.getApellidoPaterno().toLowerCase());
            queryUsuario.setParameter("pApellidoMaterno", usuario.getApellidoMaterno().toLowerCase());

            if (usuario.Rol.getIdRol() > 0) {
                queryUsuario.setParameter("pIdRol", usuario.Rol.getIdRol());
            }

            List<UsuarioJPA> usuarios = queryUsuario.getResultList();

            for (UsuarioJPA usuariojpa : usuarios) {
                Usuario user = modelMapper.map(usuariojpa, Usuario.class);
                if (user.Rol == null) {
                    user.Rol = new Rol();
                }
                if (user.Direcciones == null || user.Direcciones.isEmpty()) {
                    user.Direcciones = new ArrayList<>();
                }
                result.objects.add(user);
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Result SaveAll(List<Usuario> usuarios) {

        Result result = new Result();
//        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            for (Usuario usuario : usuarios) {
                //Convertir  ML a JPA
//                entityTransaction.begin();
                UsuarioJPA usuariojpa = modelMapper.map(usuario, UsuarioJPA.class);
                entityManager.persist(usuariojpa);
//                entityTransaction.commit();
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
//            entityTransaction.rollback();
        }
        return result;
    }

}
