package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.JPA.PaisJPA;
import com.digis01.MMateoProgramacionNCapas.ML.Pais;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisJPADAOImplementacion implements IPaisJPADAO {

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            TypedQuery queryPais = entityManager.createQuery("FROM PaisJPA", PaisJPA.class);
            List<PaisJPA> paises =queryPais.getResultList();
            result.objects = new ArrayList<>();
            
            for (PaisJPA paisJPA : paises) {
                Pais pais = modelMapper.map(paisJPA, Pais.class);
                result.objects.add(pais);
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
