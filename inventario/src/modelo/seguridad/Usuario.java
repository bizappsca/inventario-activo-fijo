package modelo.seguridad;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*import modelo.maestros.Especialidad;
import modelo.transacciones.Consulta;*/

import org.hibernate.annotations.Type;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario", length=12, unique=true, nullable=false)
	private String cedula;

	@Column(length=500)
	private String direccion;

	@Column(length=50)
	private String email;

	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean estado;

	@Column(name="estado_usuario", length=50)
	private String estadoUsuario;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria", length=10)
	private String horaAuditoria;

	@Lob
	private byte[] imagen;

	@Column(length=50)
	private String login;
	
	@Column(name="primer_apellido", length=100)
	private String primerApellido;

	@Column(name="primer_nombre", length=100)
	private String primerNombre;
	
	@Column(name="segundo_apellido", length=100)
	private String segundoApellido;

	@Column(name="segundo_nombre", length=100)
	private String segundoNombre;

	@Column(length=256)
	private String password;

	@Column(length=1)
	private String sexo;

	@Column(length=50)
	private String telefono;

	@Column(name="usuario_auditoria", length=50)
	private String usuarioAuditoria;

	/*@ManyToMany
	@JoinTable(
		name="grupo_usuario"
		, joinColumns={
			@JoinColumn(name="id_usuario", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_grupo", nullable=false)
			}
		)
	private Set<Grupo> grupos;*/
	
	public Usuario() {
	}

	public Usuario(String cedula, String direccion,
			String email, boolean estado, String estadoUsuario,
			Timestamp fechaAuditoria, String horaAuditoria,
			byte[] imagen, String login, String nombre, String apellido,String segundoNombre, String segundoApellido,
			String password, String sexo,
			String telefono,
			String usuarioAuditoria) {
		super();
		this.cedula = cedula;
		this.direccion = direccion;
		this.email = email;
		this.estado = estado;
		this.estadoUsuario = estadoUsuario;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.imagen = imagen;
		this.login = login;
		this.primerNombre = nombre;
		this.primerApellido = apellido;
		this.segundoNombre = segundoNombre;
		this.segundoApellido = segundoApellido;
		this.password = password;
		this.sexo = sexo;
		this.telefono = telefono;
		this.usuarioAuditoria = usuarioAuditoria;
		//this.grupos = grupos;
	}



	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getEstadoUsuario() {
		return this.estadoUsuario;
	}

	public void setEstadoUsuario(String estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public Timestamp getFechaAuditoria() {
		return this.fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return this.horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsuarioAuditoria() {
		return this.usuarioAuditoria;
	}

	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
	}

	/*public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}*/

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/*public Set<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(Set<Consulta> consultas) {
		this.consultas = consultas;
	}*/

}