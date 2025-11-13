package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.JPA.ColoniaJPA;
import com.digis01.MMateoProgramacionNCapas.ML.Colonia;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementacion implements IColoniaJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetByIdMunicipio(int idMunicipio) {
        Result result = new Result();

        try {

            TypedQuery queryColonia = entityManager.createQuery("FROM ColoniaJPA c WHERE c.Municipio.IdMunicipio = :idMunicipio", ColoniaJPA.class).
                    setParameter("idMunicipio", idMunicipio);
            List<ColoniaJPA> colonias = queryColonia.getResultList();
            
            result.objects = new ArrayList<>();
            
            for (ColoniaJPA coloniaJPA : colonias) {
                Colonia colonia = modelMapper.map(coloniaJPA, Colonia.class);
                result.objects.add(colonia);
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
