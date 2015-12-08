package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "equipo")
public class Equipo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_equipo", unique = true, nullable = false)
	private long idEquipo;

	@Column(length = 100, name = "indentificacion_equipo")
	private String identificacionEquipo;

	@Column(length = 1000, name = "descripcion")
	private String descripcion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo", referencedColumnName = "id_tipo")
	private Tipo tipo;

	@Column(name = "fecha_compra")
	private Timestamp fechaCompra;

	@Column(name = "fecha_garantia")
	private Timestamp fechaGarantia;

	@Column(name = "precio")
	private Double precio;

	@Column(length = 150, name = "estatus")
	private String estatus;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario", referencedColumnName = "cedula")
	private UsuarioEquipo usuario;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departamento", referencedColumnName = "id_departamento")
	private Departamento departamento;
	
	@Column(length = 500, name = "serial")
	private String serial;
	

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public long getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getIdentificacionEquipo() {
		return identificacionEquipo;
	}

	public void setIdentificacionEquipo(String identificacionEquipo) {
		this.identificacionEquipo = identificacionEquipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Timestamp getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Timestamp fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Timestamp getFechaGarantia() {
		return fechaGarantia;
	}

	public void setFechaGarantia(Timestamp fechaGarantia) {
		this.fechaGarantia = fechaGarantia;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UsuarioEquipo getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEquipo usuario) {
		this.usuario = usuario;
	}

	public Equipo() {
		super();
	}
}
