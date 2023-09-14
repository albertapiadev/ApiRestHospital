package com.albertapiadev.apiresthospital.repository;

import com.albertapiadev.apiresthospital.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    Paciente findByNombre(String nombre);

}
