package com.albertapiadev.apiresthospital.controller;

import com.albertapiadev.apiresthospital.dto.CitaDTO;
import com.albertapiadev.apiresthospital.dto.PacienteDTO;
import com.albertapiadev.apiresthospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarPacientes(){
        List<PacienteDTO> pacienteDTOS = pacienteService.getAllPacientes();
        return new ResponseEntity<>(pacienteDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> listarPacientePorId(@PathVariable Long id){
        return pacienteService.getPacienteById(id)
                .map(pacienteDTO -> new ResponseEntity<>(pacienteDTO,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> guardarPaciente(@RequestBody PacienteDTO pacienteDTO){
        PacienteDTO createdPacienteDTO = pacienteService.createPaciente(pacienteDTO);
        return new ResponseEntity<>(createdPacienteDTO,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(@PathVariable Long id,@RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO updatePaciente = pacienteService.updatePaciente(id,pacienteDTO);
        if(updatePaciente != null){
            return new ResponseEntity<>(updatePaciente,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id){
        pacienteService.deletePaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/citas")
    public ResponseEntity<Collection<CitaDTO>> listarCitasPorPacienteId(@PathVariable Long id){
        Collection<CitaDTO> citas = pacienteService.getCitasByPacienteId(id);
        return new ResponseEntity<>(citas,HttpStatus.OK);
    }
}
