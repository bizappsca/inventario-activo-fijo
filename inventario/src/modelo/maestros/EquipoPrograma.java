package modelo.maestros;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "equipo_programa")
public class EquipoPrograma {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_equipo_programa", unique = true, nullable = false)
	private long idEquipoPrograma;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
	private Equipo idEquipo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_programa", referencedColumnName = "id_programa")
	private Programas programa;
	
	@Column(name="usuario_programa")
	private String usuario;
	
	@Column(name="usuario_password")
	private String password;
	
	@Column(name="observacion_programa")
	private String observacion;

	public Equipo getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Equipo idEquipo) {
		this.idEquipo = idEquipo;
	}

	public Programas getPrograma() {
		return programa;
	}

	public void setPrograma(Programas programa) {
		this.programa = programa;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public long getIdEquipoPrograma() {
		return idEquipoPrograma;
	}

	public void setIdEquipoPrograma(long idEquipoPrograma) {
		this.idEquipoPrograma = idEquipoPrograma;
	}

	public EquipoPrograma(Equipo idEquipo, Programas programa, String usuario,
			String password, String observacion) {
		super();
		this.idEquipo = idEquipo;
		this.programa = programa;
		this.usuario = usuario;
		this.password = password;
		this.observacion = observacion;
	}

	public EquipoPrograma() {
		super();
	}

}
