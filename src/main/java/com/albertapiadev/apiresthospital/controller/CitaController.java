package com.albertapiadev.apiresthospital.controller;

import com.albertapiadev.apiresthospital.dto.CitaDTO;
import com.albertapiadev.apiresthospital.mapper.CitaMapper;
import com.albertapiadev.apiresthospital.mapper.MedicoMapper;
import com.albertapiadev.apiresthospital.mapper.PacienteMapper;
import com.albertapiadev.apiresthospital.model.Cita;
import com.albertapiadev.apiresthospital.model.StatusCita;
import com.albertapiadev.apiresthospital.service.CitaService;
import com.albertapiadev.apiresthospital.service.MedicoService;
import com.albertapiadev.apiresthospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private MedicoMapper medicoMapper;

    @Autowired
    private PacienteMapper pacienteMapper;

    @GetMapping
    public ResponseEntity<List<CitaDTO>> listarCitas(){
        List<CitaDTO> citas = citaService.getAllCitas();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> listarCitaPorId(@PathVariable Long id){
        Optional<CitaDTO> citaDTOOptional = citaService.getCitaById(id);
        return citaDTOOptional.map(cita -> new ResponseEntity<>(cita,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{idPaciente}/{idMedico}")
    public ResponseEntity<CitaDTO> guardarCita(@RequestBody CitaDTO citaDTO,@PathVariable Long idPaciente,@PathVariable Long idMedico) throws ParseException {
        Cita newCita = citaService.createCita(citaDTO,idPaciente,idMedico);

        if(newCita == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CitaDTO newCitaDTO = citaMapper.toDTO(newCita);
        return new ResponseEntity<>(citaDTO,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizarCita(@PathVariable Long id,@RequestBody CitaDTO citaDTO) throws ParseException {
        CitaDTO citaUpdate = citaService.updateCita(id,citaDTO);
        if(citaUpdate != null){
            return ResponseEntity.ok(citaUpdate);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id){
        citaService.deleteCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<CitaDTO> listarCitasPorPacienteId(@PathVariable Long pacienteId){
        return citaService.getCitasByPacienteId(pacienteId);
    }

    @GetMapping("/medico/{medicoId}")
    public List<CitaDTO> listarCitasPorMedicoId(@PathVariable Long medicoId){
        return citaService.getCitasByMedicoId(medicoId);
    }

    @GetMapping("/status/{statusCita}")
    public List<CitaDTO> listarCitasPorStatus(@PathVariable StatusCita statusCita){
        return citaService.getCitasByStatusCita(statusCita);
    }
}
