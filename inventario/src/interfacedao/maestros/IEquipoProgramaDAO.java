package interfacedao.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import modelo.maestros.Equipo;
import modelo.maestros.EquipoPrograma;

public interface IEquipoProgramaDAO extends JpaRepository<EquipoPrograma, Long>{

	List<EquipoPrograma> findByIdEquipo(Equipo equipo);

}
