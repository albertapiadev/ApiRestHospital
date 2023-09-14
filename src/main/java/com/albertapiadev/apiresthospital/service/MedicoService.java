package com.albertapiadev.apiresthospital.service;

import com.albertapiadev.apiresthospital.dto.CitaDTO;
import com.albertapiadev.apiresthospital.dto.MedicoDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MedicoService {

    List<MedicoDTO> getAllMedicos();

    Optional<MedicoDTO> getMedicoById(Long id);

    MedicoDTO createMedico(MedicoDTO medicoDTO);

    MedicoDTO updateMedico(Long id,MedicoDTO medicoDTO);

    void deleteMedico(Long id);

    Collection<CitaDTO> getCitasByMedicoId(Long medicoId);

    List<MedicoDTO> getMedicosByEspecialidad(String especialidad);

}
