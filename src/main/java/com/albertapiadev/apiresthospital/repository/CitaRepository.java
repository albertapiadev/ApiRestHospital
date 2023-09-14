package com.albertapiadev.apiresthospital.repository;

import com.albertapiadev.apiresthospital.model.Cita;
import com.albertapiadev.apiresthospital.model.StatusCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita,Long> {

    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByMedicoId(Long medicoId);

    List<Cita> findByStatusCita(StatusCita statusCita);

}
