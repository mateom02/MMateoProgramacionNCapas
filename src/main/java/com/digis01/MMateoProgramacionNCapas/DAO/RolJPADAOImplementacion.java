/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.MMateoProgramacionNCapas.DAO;

import com.digis01.MMateoProgramacionNCapas.JPA.RolJPA;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import com.digis01.MMateoProgramacionNCapas.ML.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementacion implements IRolJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {

        Result result = new Result();

        try {

            TypedQuery queryRol = entityManager.createQuery("FROM RolJPA", RolJPA.class);
            List<RolJPA> roles = queryRol.getResultList();

            result.objects = new ArrayList<>();
            for (RolJPA roljpa : roles) {
                Rol rol = modelMapper.map(roljpa, Rol.class);
                result.objects.add(rol);
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
