package br.ufes.participacidadao.dto;

public record CityDTO(
    String uri,
    String name,
    String state,
    Long population,
    Double areaKm2,
    Double latitude,
    Double longitude,
    String summary
) {}