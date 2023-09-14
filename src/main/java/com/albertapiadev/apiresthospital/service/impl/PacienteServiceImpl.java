package com.albertapiadev.apiresthospital.service.impl;

import com.albertapiadev.apiresthospital.dto.CitaDTO;
import com.albertapiadev.apiresthospital.dto.PacienteDTO;
import com.albertapiadev.apiresthospital.mapper.CitaMapper;
import com.albertapiadev.apiresthospital.mapper.PacienteMapper;
import com.albertapiadev.apiresthospital.model.Cita;
import com.albertapiadev.apiresthospital.model.Paciente;
import com.albertapiadev.apiresthospital.repository.PacienteRepository;
import com.albertapiadev.apiresthospital.service.CitaService;
import com.albertapiadev.apiresthospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private CitaMapper citaMapper;

    @Override
    public List<PacienteDTO> getAllPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PacienteDTO> getPacienteById(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        return optionalPaciente.map(pacienteMapper::toDto);
    }

    @Override
    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(paciente);
    }

    @Override
    public PacienteDTO updatePaciente(Long id, PacienteDTO pacienteDTO) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if(optionalPaciente.isPresent()){
            Paciente paciente = optionalPaciente.get();
            paciente.setNombre(pacienteDTO.getNombre());
            paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
            paciente.setEnfermedad(pacienteDTO.isEnfermedad());

            paciente = pacienteRepository.save(paciente);
            return pacienteMapper.toDto(paciente);
        }
        return null;
    }

    @Override
    public void deletePaciente(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if(optionalPaciente.isPresent()){
            Paciente paciente = optionalPaciente.get();

            for(Cita cita : paciente.getCitas()){
                citaService.deleteCita(cita.getId());
            }

            pacienteRepository.deleteById(id);
        }
    }

    @Override
    public Collection<CitaDTO> getCitasByPacienteId(Long pacienteId) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(pacienteId);
        return optionalPaciente.map(paciente -> paciente.getCitas().stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList()))
                .orElse(null);
    }
}
