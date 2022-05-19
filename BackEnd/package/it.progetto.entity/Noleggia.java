package it.progetto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "noleggia")
@NamedQuery(name="Noleggia.findAll", query="SELECT n FROM Noleggia n")
public class Noleggia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date dataInizio;
	private Date dataFine;
	private int idCliente;
	private int idAuto;
	private int numeroPrenotazione;
	private double totale;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_noleggio")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "id_cliente")
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
	this.idCliente = idCliente;
	}
	
	
	@Column(name = "id_auto")
	public int getIdAuto() {
		return idAuto;
	}
	public void setIdAuto(int idAuto) {
		this.idAuto = idAuto;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_inizio")
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fine")
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
	@Column(name = "n_prenotazione")
	public int getNumeroPrenotazione() {
		return numeroPrenotazione;
	}
	public void setNumeroPrenotazione(int numeroPrenotazione) {
		this.numeroPrenotazione = numeroPrenotazione;
	}
	
	@Column(name = "totale")
	public double getTotale() {
		return totale;
	}
	public void setTotale(double totale) {
		this.totale = totale;
	}
	
	
	
	
	
	
	
	

}
