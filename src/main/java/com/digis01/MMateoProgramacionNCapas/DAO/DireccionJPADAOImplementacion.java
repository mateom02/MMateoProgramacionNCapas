package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.JPA.DireccionJPA;
import com.digis01.MMateoProgramacionNCapas.JPA.UsuarioJPA;
import com.digis01.MMateoProgramacionNCapas.ML.Direccion;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionJPADAOImplementacion implements IDireccionJPADAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Result AddByIdUsario(Direccion direccion, int idUsuario) {
        Result result = new Result();

        try {
            DireccionJPA direccionjpa = modelMapper.map(direccion, DireccionJPA.class);
            UsuarioJPA usuariojpa = entityManager.find(UsuarioJPA.class, idUsuario);
            direccionjpa.Usuario = usuariojpa;
            entityManager.persist(direccionjpa);
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
    public Result Update(Direccion direccion) {
        Result result = new Result();

        try {

            DireccionJPA direccionExistente = entityManager.find(DireccionJPA.class, direccion.getIdDireccion());
            UsuarioJPA usuarioDireccion = entityManager.find(UsuarioJPA.class, direccionExistente.Usuario.getIdUsuario());

            direccionExistente = modelMapper.map(direccion, DireccionJPA.class);
            direccionExistente.Usuario = usuarioDireccion;

            entityManager.merge(direccionExistente);
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
    public Result Delete(int idDireccion) {
        Result result = new Result();
        try {
            DireccionJPA direccionJPA = entityManager.find(DireccionJPA.class, idDireccion);
            entityManager.remove(direccionJPA);
            result.correct=true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
