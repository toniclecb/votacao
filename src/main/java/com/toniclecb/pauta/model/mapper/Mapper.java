package com.toniclecb.pauta.model.mapper;

public interface Mapper<E, R> {

	R toResponse(E entity);
}
