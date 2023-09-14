package com.albertapiadev.apiresthospital.repository;

import com.albertapiadev.apiresthospital.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long> {

    Medico findByNombre(String nombre);

    List<Medico> findByEspecialidad(String especialidad);

}
