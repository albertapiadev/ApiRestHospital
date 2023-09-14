package com.albertapiadev.apiresthospital.mapper;

import com.albertapiadev.apiresthospital.dto.PacienteDTO;
import com.albertapiadev.apiresthospital.model.Paciente;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public PacienteDTO toDto(Paciente paciente){
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(paciente.getId());
        pacienteDTO.setNombre(paciente.getNombre());
        pacienteDTO.setFechaNacimiento(paciente.getFechaNacimiento());
        pacienteDTO.setEnfermedad(paciente.isEnfermedad());
        return pacienteDTO;
    }

    public Paciente toEntity(PacienteDTO pacienteDTO){
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDTO.getId());
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setEnfermedad(pacienteDTO.isEnfermedad());
        return paciente;
    }
}
