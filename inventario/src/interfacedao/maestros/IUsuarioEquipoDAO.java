package interfacedao.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import modelo.maestros.UsuarioEquipo;

public interface IUsuarioEquipoDAO extends JpaRepository<UsuarioEquipo, Long> {

	List<UsuarioEquipo> findByNombreStartingWithAllIgnoreCase(String valor);

	List<UsuarioEquipo> findByCedulaStartingWithAllIgnoreCase(String valor);

	UsuarioEquipo findByCedula(String value);

}
