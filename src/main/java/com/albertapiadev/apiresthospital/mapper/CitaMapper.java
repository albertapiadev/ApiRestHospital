package com.albertapiadev.apiresthospital.mapper;

import com.albertapiadev.apiresthospital.dto.CitaDTO;
import com.albertapiadev.apiresthospital.model.Cita;
import com.albertapiadev.apiresthospital.model.Medico;
import com.albertapiadev.apiresthospital.model.Paciente;
import com.albertapiadev.apiresthospital.model.StatusCita;
import com.albertapiadev.apiresthospital.repository.MedicoRepository;
import com.albertapiadev.apiresthospital.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class CitaMapper {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public CitaDTO toDTO(Cita cita){
        CitaDTO citaDTO = new CitaDTO();

        citaDTO.setId(cita.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formmatedFecha = sdf.format(cita.getFecha());

        citaDTO.setFecha(formmatedFecha);

        citaDTO.setCancelado(cita.isCancelado());
        citaDTO.setStatusCita(cita.getStatusCita().name());
        citaDTO.setPacienteId(cita.getPaciente().getId());
        citaDTO.setMedicoId(cita.getMedico().getId());
        return citaDTO;
    }

    public Cita toEntity(CitaDTO citaDTO, Paciente paciente, Medico medico) throws ParseException {
        Cita cita = new Cita();

        cita.setId(citaDTO.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citaDTO.getFecha());
        cita.setFecha(fecha);

        cita.setCancelado(citaDTO.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));
        cita.setPaciente(paciente);
        cita.setMedico(medico);

        return cita;
    }

    public Cita toEntity(CitaDTO citaDTO) throws ParseException {
        Cita cita = new Cita();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citaDTO.getFecha());
        cita.setFecha(fecha);

        cita.setCancelado(citaDTO.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

        Optional<Paciente> paciente = pacienteRepository.findById(citaDTO.getPacienteId());
        Paciente pacienteBBDD = paciente.get();
        cita.setPaciente(pacienteBBDD);

        Optional<Medico> medico = medicoRepository.findById(citaDTO.getMedicoId());
        Medico medicoBBDD = medico.get();
        cita.setMedico(medicoBBDD);

        return cita;
    }
}
