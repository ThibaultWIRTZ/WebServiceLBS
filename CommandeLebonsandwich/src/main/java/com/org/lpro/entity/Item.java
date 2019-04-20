package com.org.lpro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EnableAutoConfiguration
@Table(name="ITEM")
public class Item {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="URI",nullable=false)
	private String uri;
	@Column(name="LIBELLE",nullable=true)
	private String libelle;
	@Column(name="TARIF",nullable=true)
	private float tarif;
	@Column(name="QUANTITE")
	private int quantite=1;
	@JsonIgnore
	@Column(name="COMMAND_ID",nullable=false)
	private String commandid;
	
	public Item(int id, String uri, String libelle, int tarif, int quantite, String commandid) {
		super();
		this.id = id;
		this.uri = uri;
		this.libelle = libelle;
		this.tarif = tarif;
		this.quantite = quantite;
		this.commandid = commandid;
	}
	
	public Item(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public float getTarif() {
		return tarif;
	}
	public void setTarif(float tarif) {
		this.tarif = tarif;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public String getCommandid() {
		return commandid;
	}
	public void setCommandid(String commandid) {
		this.commandid = commandid;
	}
}