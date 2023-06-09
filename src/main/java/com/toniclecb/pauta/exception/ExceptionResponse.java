package com.toniclecb.pauta.exception;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Used to return to the client information about the exception to client
 */
@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse implements Serializable {
    private static final long serialVersionUID = 9100279187579243662L;

    private Date timestamp;
	private String message;
	private String details;

}
