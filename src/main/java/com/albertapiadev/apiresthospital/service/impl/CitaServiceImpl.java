package com.albertapiadev.apiresthospital.service.impl;

import com.albertapiadev.apiresthospital.dto.CitaDTO;
import com.albertapiadev.apiresthospital.dto.MedicoDTO;
import com.albertapiadev.apiresthospital.dto.PacienteDTO;
import com.albertapiadev.apiresthospital.mapper.CitaMapper;
import com.albertapiadev.apiresthospital.mapper.MedicoMapper;
import com.albertapiadev.apiresthospital.mapper.PacienteMapper;
import com.albertapiadev.apiresthospital.model.*;
import com.albertapiadev.apiresthospital.repository.CitaRepository;
import com.albertapiadev.apiresthospital.repository.ConsultaRepository;
import com.albertapiadev.apiresthospital.repository.MedicoRepository;
import com.albertapiadev.apiresthospital.repository.PacienteRepository;
import com.albertapiadev.apiresthospital.service.CitaService;
import com.albertapiadev.apiresthospital.service.MedicoService;
import com.albertapiadev.apiresthospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {


    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private MedicoMapper medicoMapper;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Override
    public List<CitaDTO> getAllCitas() {
        List<Cita> citas = citaRepository.findAll();
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CitaDTO> getCitaById(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        return citaOptional.map(citaMapper::toDTO);
    }

    @Override
    public Cita createCita(CitaDTO citaDTO, Long idPaciente, Long idMedico) throws ParseException {
        PacienteDTO pacienteDTO = pacienteService.getPacienteById(idPaciente).orElse(null);
        MedicoDTO medicoDTO = medicoService.getMedicoById(idMedico).orElse(null);

        if(pacienteDTO == null || medicoDTO == null){
            return null;
        }

        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        Medico medico = medicoMapper.toEntity(medicoDTO);
        Cita cita = citaMapper.toEntity(citaDTO,paciente,medico);

        return citaRepository.save(cita);
    }

    @Override
    public CitaDTO updateCita(Long id, CitaDTO citaDTO) throws ParseException {
        Optional<Cita> citaOptional = citaRepository.findById(id);

        if(citaOptional.isPresent()){
            Cita cita = citaOptional.get();

            cita.setId(citaDTO.getId());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha = dateFormat.parse(citaDTO.getFecha());

            cita.setCancelado(citaDTO.isCancelado());
            cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

            Optional<Paciente> pacienteOptional = pacienteRepository.findById(citaDTO.getPacienteId());
            cita.setPaciente(pacienteOptional.get());

            Optional<Medico> medicoOptional = medicoRepository.findById(citaDTO.getMedicoId());
            cita.setMedico(medicoOptional.get());

            return citaMapper.toDTO(citaRepository.save(cita));
        }
        return null;
    }

    @Override
    public void deleteCita(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);

        if(citaOptional.isPresent()){
            Cita cita = citaOptional.get();

            if(cita.getConsulta() != null){
                Consulta consulta = cita.getConsulta();
                consulta.setCita(null);
                consultaRepository.delete(consulta);
            }
            citaRepository.delete(cita);
        }
    }

    @Override
    public List<CitaDTO> getCitasByPacienteId(Long pacienteId) {
        List<Cita> citas = citaRepository.findByPacienteId(pacienteId);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasByMedicoId(Long medicoId) {
        List<Cita> citas = citaRepository.findByMedicoId(medicoId);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasByStatusCita(StatusCita statusCita) {
        List<Cita> citas = citaRepository.findByStatusCita(statusCita);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
