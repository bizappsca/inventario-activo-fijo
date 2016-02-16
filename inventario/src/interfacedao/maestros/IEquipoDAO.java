package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Departamento;
import modelo.maestros.Equipo;
import modelo.maestros.Tipo;
import modelo.maestros.UsuarioEquipo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEquipoDAO extends JpaRepository<Equipo, Long> {

	List<Equipo> findByIdentificacionEquipoStartingWithAllIgnoreCase(
			String valor);
	
	List<Equipo> findByTipoStartingWithAllIgnoreCase(String valor);

	//para la consulta
	
	List<Equipo> findByTipoTipoStartingWithAllIgnoreCase(String valor);

	List<Equipo> findByEstatusStartingWithAllIgnoreCase(String valor);

	List<Equipo> findByDescripcionStartingWithAllIgnoreCase(String valor);

	List<Equipo> findByUsuarioNombreStartingWithAllIgnoreCase(String valor);

	List<Equipo> findByDepartamentoDepartamentoStartingWithAllIgnoreCase(String valor);

	List<Equipo> findBySerialStartingWithAllIgnoreCase(String valor);

	@Query("select coalesce(max(v.idEquipo), '0') from Equipo v")
	long findMaxIdEquipo();

	Equipo findByTipo(String valor);

	Equipo findByDepartamento(String valor);
	
	//reporte auditoria
	List<Equipo> findByTipoAndDepartamento(Tipo valor, Departamento valorD);
	List<Equipo> findByDepartamentoOrderByTipoAsc(Departamento valorD);

	

	

}
