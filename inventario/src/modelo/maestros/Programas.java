package modelo.maestros;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import modelo.seguridad.Arbol;
import modelo.seguridad.Usuario;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "programas")
public class Programas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_programa", unique = true, nullable = false)
	private long idPrograma;
	
	@Column(length = 100, name="programa")
	private String programa;
	
	@Column(name="estado")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean estado;

	public long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Programas() {
		super();
	}

	public Programas(long idPrograma, String programa) {
		super();
		this.idPrograma = idPrograma;
		this.programa = programa;
	}

	public Programas(long idPrograma, boolean estado, String programa) {
		super();
		this.idPrograma = idPrograma;
		this.estado = estado;
		this.programa = programa;
	}

}
