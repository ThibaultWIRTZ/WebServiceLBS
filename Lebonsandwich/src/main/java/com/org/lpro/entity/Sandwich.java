package com.org.lpro.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="SANDWICH")
public class Sandwich {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID",nullable=false)
	private Long id;
	@Column(name="NOM",nullable=false)
	private String nom;
	@Column(name="TYPEPAIN",nullable=false)
	private String typepain;
	@Column(name="DESCRIPTION",nullable=false)
	private String desc;
	@Column(name="TARIF",nullable=false)
	private String tarif;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Category> categories;
	
	Sandwich(){
	 //pour JPA	
	}

	public Sandwich(Long id, String nom, String typepain, String desc, String tarif) {
		super();
		this.id = id;
		this.nom = nom;
		this.typepain = typepain;
		this.desc = desc;
		this.tarif = tarif;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTypepain() {
		return typepain;
	}

	public void setTypepain(String typepain) {
		this.typepain = typepain;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTarif() {
		return tarif;
	}

	public void setTarif(String tarif) {
		this.tarif = tarif;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
