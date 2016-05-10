package com.ucla.models;

// Generated 01/03/2016 02:05:25 AM by Hibernate Tools 4.3.1

/**
 * CalendarioHora generated by hbm2java
 */
public class CalendarioHora implements java.io.Serializable {

	private int idCalendarioHora;
	private CalendarioFecha calendarioFecha;
	private Hora hora;
	private boolean activo = true;

	public CalendarioHora() {
	}

	public CalendarioHora(int idCalendarioHora,
			CalendarioFecha calendarioFecha, Hora hora, boolean activo) {
		this.idCalendarioHora = idCalendarioHora;
		this.calendarioFecha = calendarioFecha;
		this.hora = hora;
		this.activo = activo;
	}

	public int getIdCalendarioHora() {
		return this.idCalendarioHora;
	}

	public void setIdCalendarioHora(int idCalendarioHora) {
		this.idCalendarioHora = idCalendarioHora;
	}

	public CalendarioFecha getCalendarioFecha() {
		return this.calendarioFecha;
	}

	public void setCalendarioFecha(CalendarioFecha calendarioFecha) {
		this.calendarioFecha = calendarioFecha;
	}

	public Hora getHora() {
		return this.hora;
	}

	public void setHora(Hora hora) {
		this.hora = hora;
	}

	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}