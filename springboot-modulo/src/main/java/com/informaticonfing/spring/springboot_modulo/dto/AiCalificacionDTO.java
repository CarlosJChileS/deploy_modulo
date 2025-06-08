package com.informaticonfing.spring.springboot_modulo.dto;

import java.util.List;

/**
 * Estructura recibida desde la IA con la evaluación completa.
 */
public class AiCalificacionDTO {
    private Long calificacionId;
    private Double puntajeGlobalAi;
    private List<AiDetalleDTO> detalles;

    public Long getCalificacionId() { return calificacionId; }
    public void setCalificacionId(Long calificacionId) { this.calificacionId = calificacionId; }

    public Double getPuntajeGlobalAi() { return puntajeGlobalAi; }
    public void setPuntajeGlobalAi(Double puntajeGlobalAi) { this.puntajeGlobalAi = puntajeGlobalAi; }

    public List<AiDetalleDTO> getDetalles() { return detalles; }
    public void setDetalles(List<AiDetalleDTO> detalles) { this.detalles = detalles; }
}
