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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Antonio
 *
 */
@Entity
@Table(name = "auto")
@NamedQuery(name="Auto.findAll", query="SELECT a FROM Auto a")
public class Auto implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String targa;
	private String marca;
	private String modello;
	private Categoria categoria;
	private int disponibile;
	private String url;
	private List<Cliente> cliente;
	private List<Staff> staff;
	
	
	
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_auto")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "targa")
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	
	@Column(name = "marca")
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	@Column(name = "modello")
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	@ManyToOne()
	@JoinColumn(name = "id_categoria")
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	@ManyToMany
	@JoinTable(name = "noleggia", 
	joinColumns = @JoinColumn(name="id_auto", referencedColumnName = "id_auto"),
	inverseJoinColumns = @JoinColumn(name="id_cliente", referencedColumnName = "id_cliente"))
	public List<Cliente> getCliente() {
		return cliente;
	}
	public void setCliente(List<Cliente> cliente) {
		this.cliente = cliente;
	}
	
	@ManyToMany
	@JoinTable(name = "gestisce", 
	joinColumns = @JoinColumn(name="id_staff"),
	inverseJoinColumns = @JoinColumn(name="id_auto"))
	public List<Staff> getStaff() {
		return staff;
	}
	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}
	
	@Column(name = "disponibile")
	public int getDisponibile() {
		return disponibile;
	}
	public void setDisponibile(int disponibile) {
		this.disponibile = disponibile;
	}
	
	@Column(name = "url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
