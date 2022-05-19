package it.progetto.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String nome;
	private String cognome;
	private String citta;
	private String patente;
	private String email;
	private String password;
	private List<Auto> auto;
	private int stato;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cognome")
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	@Column(name = "citta")
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	@Column(name = "n_patente")
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}
	
	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@ManyToMany
	@JoinTable(name = "noleggia", 
	joinColumns = @JoinColumn(name="id_cliente", referencedColumnName = "id_cliente"),
	inverseJoinColumns = @JoinColumn(name="id_auto", referencedColumnName = "id_auto"))
	public List<Auto> getAuto() {
		return auto;
	}
	public void setAuto(List<Auto> auto) {
		this.auto = auto;
	}
	
	@Column(name = "stato")
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	
	
	
	
	
	
	

}
