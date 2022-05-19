package it.progetto.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "gestisce")
@NamedQuery(name="Gestisce.findAll", query="SELECT g FROM Gestisce g")
public class Gestisce implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idAuto;
	private int idStaff;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_gestione")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "id_auto")
	public int getIdAuto() {
		return idAuto;
	}
	public void setIdAuto(int idAuto) {
		this.idAuto = idAuto;
	}
	
	@Column(name = "id_staff")
	public int getIdStaff() {
		return idStaff;
	}
	public void setIdStaff(int idStaff) {
		this.idStaff = idStaff;
	}
	
	
	

}
