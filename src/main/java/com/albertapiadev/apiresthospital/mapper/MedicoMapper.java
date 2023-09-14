package com.albertapiadev.apiresthospital.mapper;

import com.albertapiadev.apiresthospital.dto.MedicoDTO;
import com.albertapiadev.apiresthospital.model.Medico;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper {

    public MedicoDTO toDTO(Medico medico){
        MedicoDTO medicoDTO = new MedicoDTO();
        medicoDTO.setId(medico.getId());
        medicoDTO.setNombre(medico.getNombre());
        medicoDTO.setEmail(medico.getEmail());
        medicoDTO.setEspecialidad(medico.getEspecialidad());
        return medicoDTO;
    }

    public Medico toEntity(MedicoDTO medicoDTO){
        Medico medico = new Medico();
        medico.setId(medicoDTO.getId());
        medico.setNombre(medicoDTO.getNombre());
        medico.setEmail(medicoDTO.getEmail());
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        return medico;
    }
}
