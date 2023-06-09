package com.toniclecb.pauta.util;

import java.time.LocalDateTime;

public class DateUtil {

	public static boolean dentroDoPeriodo(LocalDateTime data, LocalDateTime dataInicio, LocalDateTime dataFim) {
		return (data.isAfter(dataInicio) && data.isBefore(dataFim));
	}
}
