package com.informaticonfing.spring.springboot_modulo.controller;

import com.informaticonfing.spring.springboot_modulo.dto.CalificacionRequestDTO;
import com.informaticonfing.spring.springboot_modulo.dto.CalificacionResponseDTO;
import com.informaticonfing.spring.springboot_modulo.service.CalificacionService;
import com.informaticonfing.spring.springboot_modulo.dto.AiCalificacionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de calificaciones.
 */
@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    private final CalificacionService service;

    public CalificacionController(CalificacionService service) {
        this.service = service;
    }

    /**
     * Obtiene todas las calificaciones.
     * @return lista de CalificacionResponseDTO
     */
    @GetMapping
    public List<CalificacionResponseDTO> all() {
        return service.findAll();
    }

    /**
     * Busca una calificación por su ID.
     * @param id identificador de la calificación
     * @return CalificacionResponseDTO si existe, 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionResponseDTO> byId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva calificación.
     * @param dto datos de la nueva calificación
     * @return la calificación creada
     */
    @PostMapping
    public CalificacionResponseDTO create(@RequestBody CalificacionRequestDTO dto) {
        return service.create(dto);
    }

    /**
     * Actualiza una calificación existente por su ID.
     * @param id identificador de la calificación
     * @param dto nuevos datos de la calificación
     * @return la calificación actualizada, o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalificacionResponseDTO> update(@PathVariable Long id,
                                                          @RequestBody CalificacionRequestDTO dto) {
        return service.findById(id)
                .map(existing -> ResponseEntity.ok(service.update(id, dto)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina una calificación por su ID.
     * @param id identificador de la calificación
     * @return respuesta vacía (204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Aplica calificaciones generadas por IA y actualiza la calificación existente.
     * @param dto datos provenientes de la IA
     * @return la calificación actualizada con el puntaje final
     */
    @PostMapping("/ai")
    public CalificacionResponseDTO aplicarIA(@RequestBody AiCalificacionDTO dto) {
        return service.aplicarCalificacionAI(dto);
    }

    /**
     * Devuelve los ejemplos de petición y respuesta para el endpoint de IA.
     * @return mapa con los JSON de ejemplo
     */
    @GetMapping("/ai/ejemplos")
    public Map<String, Object> ejemplosIA() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        ClassPathResource peticion = new ClassPathResource("examples/ai_ejemplo_peticion.json");
        ClassPathResource respuesta = new ClassPathResource("examples/ai_ejemplo_respuesta.json");
        result.put("peticion", mapper.readValue(peticion.getInputStream(), Object.class));
        result.put("respuesta", mapper.readValue(respuesta.getInputStream(), Object.class));
        return result;
    }
}
