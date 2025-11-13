package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.JPA.EstadoJPA;
import com.digis01.MMateoProgramacionNCapas.JPA.PaisJPA;
import com.digis01.MMateoProgramacionNCapas.ML.Estado;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoJPADAOImplementacion implements IEstadoDAOJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper   modelMapper;
    
    @Override
    public Result GetByIdPais(int idPais) {
        Result result = new Result();

        try {
            
            TypedQuery queryEstado = entityManager.createQuery("SELECT e FROM EstadoJPA e WHERE e.Pais.IdPais = :idPais", EstadoJPA.class).setParameter("idPais", idPais);
            List<EstadoJPA> estados = queryEstado.getResultList();
            result.objects = new ArrayList<>();
            
            for (EstadoJPA estadojpa: estados) {
                Estado estado = modelMapper.map(estadojpa, Estado.class);
                result.objects.add(estado);
            }
            result.correct=true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
