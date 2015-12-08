package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Equipo;
import modelo.maestros.Programas;
import modelo.seguridad.Grupo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IProgramasDAO extends JpaRepository<Programas, Long>{

	List<Programas> findByProgramaStartingWithAllIgnoreCase(String valor);

	Programas findByPrograma(String value);

	List<Programas> findByIdProgramaNotInAndEstadoTrue(List<Long> ids);

	List<Programas> findByIdProgramaNotIn(List<Long> ids);

	


}
