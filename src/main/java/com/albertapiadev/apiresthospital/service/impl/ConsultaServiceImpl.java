package com.albertapiadev.apiresthospital.service.impl;

import com.albertapiadev.apiresthospital.dto.CitaDTO;
import com.albertapiadev.apiresthospital.dto.ConsultaDTO;
import com.albertapiadev.apiresthospital.mapper.CitaMapper;
import com.albertapiadev.apiresthospital.mapper.ConsultaMapper;
import com.albertapiadev.apiresthospital.model.Cita;
import com.albertapiadev.apiresthospital.model.Consulta;
import com.albertapiadev.apiresthospital.model.StatusCita;
import com.albertapiadev.apiresthospital.repository.CitaRepository;
import com.albertapiadev.apiresthospital.repository.ConsultaRepository;
import com.albertapiadev.apiresthospital.service.CitaService;
import com.albertapiadev.apiresthospital.service.ConsultaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private ConsultaMapper consultaMapper;

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<ConsultaDTO> getAllConsultas() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream()
                .map(consultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConsultaDTO> getConsultaById(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        return consulta.map(consultaMapper::toDTO);
    }

    @Override
    public ConsultaDTO createConsulta(Long citaId, ConsultaDTO consultaDTO) throws ParseException {
        CitaDTO citaDTO = citaService.getCitaById(citaId).orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));

        Consulta consulta = new Consulta();
        consulta.setCita(citaMapper.toEntity(citaDTO));
        consulta.setFechaConsulta(new Date());
        consulta.setInforme(consultaDTO.getInforme());

        Consulta createdConsulta = consultaRepository.save(consulta);
        return consultaMapper.toDTO(createdConsulta);
    }

    @Override
    public ConsultaDTO updateConsulta(Long id, ConsultaDTO consultaDTO) throws ParseException {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);
        if(consultaOptional.isPresent()){
            Consulta consulta = consultaOptional.get();

            consulta.setFechaConsulta(consultaDTO.getFechaConsultaAsDate());
            consulta.setInforme(consultaDTO.getInforme());

            Consulta updateConsulta = consultaRepository.save(consulta);

            CitaDTO citaDTO = consultaDTO.getCitaDTO();

            if(citaDTO != null){

                Cita cita = consulta.getCita();
                if(cita != null){
                    cita.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(citaDTO.getFecha()));
                    cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

                    citaRepository.save(cita);
                }

                citaService.updateCita(cita.getId(),citaDTO);
            }
            return consultaMapper.toDTO(updateConsulta);
        }
        return null;
    }

    @Override
    public void deleteConsulta(Long id) {
        consultaRepository.deleteById(id);
    }

    @Override
    public List<ConsultaDTO> getConsultasByInformeContaining(String searchTerm) {
        List<Consulta> consultas = consultaRepository.findByInformeContainingIgnoreCase(searchTerm);
        return consultas.stream()
                .map(consultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> getConsultasByCita(Cita cita) {
        List<Consulta> consultas = consultaRepository.findByCita(cita);
        return consultas.stream()
                .map(consultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> getConsultasByCita(Long citaId) throws ParseException {
        Optional<Cita> citaOptional = citaRepository.findById(citaId);

        if(citaOptional.isPresent()){
            Cita cita = citaOptional.get();

            if(cita.getId() != null){
                citaRepository.save(cita);
            }

            List<Consulta> consultas = consultaRepository.findByCita(cita);

            List<ConsultaDTO> consultaDTOS = new ArrayList<>();
            for(Consulta consulta : consultas){
                ConsultaDTO consultaDTO = new ConsultaDTO();
                consultaDTO.setId(consulta.getId());
                consultaDTO.setFechaConsulta(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(consulta.getFechaConsulta()));
                consultaDTO.setInforme(consulta.getInforme());

                consultaDTOS.add(consultaDTO);
            }
            return consultaDTOS;
        }
        else{
            throw new EntityNotFoundException("Cita no encontrada con el ID : " + citaId);
        }
    }
}
