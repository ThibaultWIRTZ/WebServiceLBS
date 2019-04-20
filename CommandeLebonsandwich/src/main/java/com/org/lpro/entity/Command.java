package com.org.lpro.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@EnableAutoConfiguration
@Table(name="COMMANDE")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Command {
	@Id	
	@Column(name="ID",nullable=false)
	private String id;
	@Column(name="CREATED_AT",nullable=false)
	private String createdat;
	@Column(name="UPDATED_AT",nullable=true)
	private String updatedat;	
	@Column(name="LIVRAISON",nullable=false)
	private String livraison;
	@Column(name="NOM",nullable=false)
	private String nom;	
	@Column(name="MAIL",nullable=false)
	private String mail;
	@Column(name="MONTANT",nullable=true)
	private Float montant;
	@Column(name="REMISE",nullable=true)
	private Float remise;
	@Column(name="TOKEN",nullable=true)
	private String token;
	@Column(name="CLIENT_ID",nullable=true)
	private Integer clientid;
	@Column(name="REF_PAIEMENT",nullable=true)
	private String refpaiement;	
	@Column(name="DATE_PAIEMENT",nullable=true)
	private String datepaiement;
	@Column(name="MODE_PAIEMENT",nullable=true)
	private Integer modepaiement;		
	@Column(name="STATUS",nullable=false)
	private Integer status;	
	@Transient
	private List<Item> items;
		
	Command(){
	 //pour JPA	
	}

	public Command(String id, String createdat, String updatedat, String livraison, String nom, String mail,
			Float montant, Float remise, String token, Integer clientid, String refpaiement, String datepaiement,
			Integer modepaiement, Integer status) {
		super();
		this.id = id;
		this.createdat = createdat;
		this.updatedat = updatedat;
		this.livraison = livraison;
		this.nom = nom;
		this.mail = mail;
		this.montant = montant;
		this.remise = remise;
		this.token = token;
		this.clientid = clientid;
		this.refpaiement = refpaiement;
		this.datepaiement = datepaiement;
		this.modepaiement = modepaiement;
		this.status = status;
		this.items = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedat() {
		return createdat;
	}

	public void setCreatedat(String createdat) {
		this.createdat = createdat;
	}

	public String getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(String updatedat) {
		this.updatedat = updatedat;
	}

	public String getLivraison() {
		return livraison;
	}

	public void setLivraison(String livraison) {
		this.livraison = livraison;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Float getMontant() {
		return montant;
	}

	public void setMontant(Float montant) {
		this.montant = montant;
	}

	public Float getRemise() {
		return remise;
	}

	public void setRemise(Float remise) {
		this.remise = remise;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public String getRefpaiement() {
		return refpaiement;
	}

	public void setRefpaiement(String refpaiement) {
		this.refpaiement = refpaiement;
	}

	public String getDatepaiement() {
		return datepaiement;
	}

	public void setDatepaiement(String datepaiement) {
		this.datepaiement = datepaiement;
	}

	public Integer getModepaiement() {
		return modepaiement;
	}

	public void setModepaiement(Integer modepaiement) {
		this.modepaiement = modepaiement;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	
	
}
