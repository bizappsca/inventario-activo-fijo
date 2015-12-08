package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "departamento")
public class Departamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_departamento", unique = true, nullable = false)
	private long idDepartamento;
	
	@Column(length = 100, name="departamento")
	private String departamento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_ubicacion")
	private Ubicacion ubicacion;

	public Departamento(long idDepartamento, String departamento,
			Ubicacion ubicacion) {
		this.idDepartamento = idDepartamento;
		this.departamento = departamento;
		this.ubicacion = ubicacion;
	}
	
	public Departamento() {
		super();
	}

	public long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

}
