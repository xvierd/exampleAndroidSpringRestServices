package com.ucla.models;

// Generated 19/02/2016 11:51:02 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/**
 * SolicitudEvento generated by hbm2java
 */
public class SolicitudEvento implements java.io.Serializable {

	private int idSolicitudEvento;
	private Socio socio;
	private Date fechaSolicitud;
	private String descripcion;
	private Date fechaInicioSolicitud;
	private Date fechaFinSolicitud;
	private Date horaInicio;
	private Date horaFin;
	private boolean publico;
	private Set<EstadoSolicitud> estadoSolicituds = new HashSet(0);

	public SolicitudEvento() {
	}

	public SolicitudEvento(int idSolicitudEvento, Socio socio,
			Date fechaSolicitud, String descripcion, Date fechaInicioSolicitud,
			Date fechaFinSolicitud, Date horaInicio, Date horaFin,
			boolean publico) {
		this.idSolicitudEvento = idSolicitudEvento;
		this.socio = socio;
		this.fechaSolicitud = fechaSolicitud;
		this.descripcion = descripcion;
		this.fechaInicioSolicitud = fechaInicioSolicitud;
		this.fechaFinSolicitud = fechaFinSolicitud;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.publico = publico;
	}

	public SolicitudEvento(int idSolicitudEvento, Socio socio,
			Date fechaSolicitud, String descripcion, Date fechaInicioSolicitud,
			Date fechaFinSolicitud, Date horaInicio, Date horaFin,
			boolean publico, Set<EstadoSolicitud> estadoSolicituds) {
		this.idSolicitudEvento = idSolicitudEvento;
		this.socio = socio;
		this.fechaSolicitud = fechaSolicitud;
		this.descripcion = descripcion;
		this.fechaInicioSolicitud = fechaInicioSolicitud;
		this.fechaFinSolicitud = fechaFinSolicitud;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.publico = publico;
		this.estadoSolicituds = estadoSolicituds;
	}

	public int getIdSolicitudEvento() {
		return this.idSolicitudEvento;
	}

	public void setIdSolicitudEvento(int idSolicitudEvento) {
		this.idSolicitudEvento = idSolicitudEvento;
	}

	public Socio getSocio() {
		return this.socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInicioSolicitud() {
		return this.fechaInicioSolicitud;
	}

	public void setFechaInicioSolicitud(Date fechaInicioSolicitud) {
		this.fechaInicioSolicitud = fechaInicioSolicitud;
	}

	public Date getFechaFinSolicitud() {
		return this.fechaFinSolicitud;
	}

	public void setFechaFinSolicitud(Date fechaFinSolicitud) {
		this.fechaFinSolicitud = fechaFinSolicitud;
	}

	public Date getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public boolean isPublico() {
		return this.publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public Set<EstadoSolicitud> getEstadoSolicituds() {
		return this.estadoSolicituds;
	}

	public void setEstadoSolicituds(Set<EstadoSolicitud> estadoSolicituds) {
		this.estadoSolicituds = estadoSolicituds;
	}

}