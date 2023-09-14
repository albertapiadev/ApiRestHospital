package com.albertapiadev.apiresthospital.mapper;

import com.albertapiadev.apiresthospital.dto.CitaDTO;
import com.albertapiadev.apiresthospital.dto.ConsultaDTO;
import com.albertapiadev.apiresthospital.model.Cita;
import com.albertapiadev.apiresthospital.model.Consulta;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ConsultaMapper {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ConsultaDTO toDTO(Consulta consulta){
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setId(consulta.getId());
        consultaDTO.setFechaConsulta(dateFormat.format(consulta.getFechaConsulta()));
        consultaDTO.setInforme(consulta.getInforme());

        if(consulta.getCita() != null){
            Cita cita = consulta.getCita();
            CitaDTO citaDTO = new CitaDTO();

            citaDTO.setId(cita.getId());
            citaDTO.setFecha(dateFormat.format(cita.getFecha()));
            citaDTO.setCancelado(cita.isCancelado());
            citaDTO.setStatusCita(cita.getStatusCita().toString());
            citaDTO.setPacienteId(citaDTO.getPacienteId());
            citaDTO.setMedicoId(citaDTO.getMedicoId());
            consultaDTO.setCitaDTO(citaDTO);
        }
        return consultaDTO;
    }

    public Consulta toEntity(ConsultaDTO consultaDTO) throws ParseException {
        Consulta consulta = new Consulta();
        consulta.setId(consultaDTO.getId());
        consulta.setFechaConsulta((dateFormat.parse(consultaDTO.getFechaConsulta())));
        consulta.setInforme(consulta.getInforme());

        if(consultaDTO.getCitaDTO() != null){
            CitaDTO citaDTO = consultaDTO.getCitaDTO();
            Cita cita = new Cita();
            cita.setId(citaDTO.getId());
            consulta.setCita(cita);
        }
        return consulta;
    }
}
