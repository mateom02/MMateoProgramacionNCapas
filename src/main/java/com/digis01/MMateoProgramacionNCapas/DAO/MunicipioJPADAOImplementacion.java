package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.JPA.MunicipioJPA;
import com.digis01.MMateoProgramacionNCapas.ML.Municipio;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioJPADAOImplementacion implements IMunicipioJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetByIdEstado(int idEstado) {
        Result result = new Result();

        try {
            TypedQuery queryMunicipio = entityManager.createQuery("FROM MunicipioJPA m WHERE m.Estado.IdEstado = :idEstado", MunicipioJPA.class).setParameter("idEstado", idEstado);
            List<MunicipioJPA> municipios = queryMunicipio.getResultList();
            result.objects = new ArrayList<>();
            for (MunicipioJPA municipiojpa : municipios) {
                Municipio municipio = modelMapper.map(municipiojpa, Municipio.class);
                result.objects.add(municipio);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
