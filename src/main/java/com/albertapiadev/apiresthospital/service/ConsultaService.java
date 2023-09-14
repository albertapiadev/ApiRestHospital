package com.albertapiadev.apiresthospital.service;

import com.albertapiadev.apiresthospital.dto.CitaDTO;
import com.albertapiadev.apiresthospital.dto.ConsultaDTO;
import com.albertapiadev.apiresthospital.model.Cita;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ConsultaService {

    List<ConsultaDTO> getAllConsultas();

    Optional<ConsultaDTO> getConsultaById(Long id);

    ConsultaDTO createConsulta(Long citaId,ConsultaDTO consultaDTO) throws ParseException;

    ConsultaDTO updateConsulta(Long id,ConsultaDTO consultaDTO) throws ParseException;

    void deleteConsulta(Long id);

    List<ConsultaDTO> getConsultasByInformeContaining(String searchTerm);

    List<ConsultaDTO> getConsultasByCita(Cita cita);

    List<ConsultaDTO> getConsultasByCita(Long citaId) throws ParseException;

}
