package com.toniclecb.pauta.util;

import java.util.Date;

public class DateUtil {

	public static boolean dentroDoPeriodo(Date data, Date dataInicio, Date dataFim) {
	    long dataMillis = data.getTime();
	    long dataInicioMillis = dataInicio.getTime();
	    long dataFimMillis = dataFim.getTime();
	  
	    return dataMillis >= dataInicioMillis && dataMillis <= dataFimMillis;
	}
}
