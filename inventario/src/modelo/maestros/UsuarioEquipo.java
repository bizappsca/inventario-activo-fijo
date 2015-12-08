package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_equipo")
public class UsuarioEquipo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario_equipo", unique = true, nullable = false)
	private long idUsuarioEquipo;

	@Column(length = 100, name = "nombre")
	private String nombre;

	@Column(length = 9, name = "cedula")
	private String cedula;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departamento", referencedColumnName = "id_departamento")
	private Departamento departamento;

	public UsuarioEquipo(long idUsuarioEquipo, String nombre, String cedula,
			Departamento departamento) {
		super();
		this.idUsuarioEquipo = idUsuarioEquipo;
		this.nombre = nombre;
		this.cedula = cedula;
		this.departamento = departamento;
	}

	public UsuarioEquipo(String nombre, String cedula, Departamento departamento) {
		super();
		this.nombre = nombre;
		this.cedula = cedula;
		this.departamento = departamento;
	}

	public UsuarioEquipo(String nombre, String cedula) {
		super();
		this.nombre = nombre;
		this.cedula = cedula;
	}

	public UsuarioEquipo() {
		super();

	}

	public long getIdUsuarioEquipo() {
		return idUsuarioEquipo;
	}

	public void setIdUsuarioEquipo(long idUsuarioEquipo) {
		this.idUsuarioEquipo = idUsuarioEquipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
