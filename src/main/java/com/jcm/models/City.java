package com.jcm.models;

public class City {
	private String entCod;
	private String entNombre;
	private String entCodProvincia;

	public City() {
		super();
	}

	public City(String entCod, String entNombre, String entCodProvincia) {
		super();
		this.entCod = entCod;
		this.entNombre = entNombre;
		this.entCodProvincia = entCodProvincia;
	}

	public String getEntCod() {
		return entCod;
	}

	public void setEntCod(String entCod) {
		this.entCod = entCod;
	}

	public String getEntNombre() {
		return entNombre;
	}

	public void setEntNombre(String entNombre) {
		this.entNombre = entNombre;
	}

	public String getEntCodProvincia() {
		return entCodProvincia;
	}

	public void setEntCodProvincia(String entCodProvincia) {
		this.entCodProvincia = entCodProvincia;
	}
}
