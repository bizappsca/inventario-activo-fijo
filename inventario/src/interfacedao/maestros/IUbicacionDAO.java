package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Ubicacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUbicacionDAO extends JpaRepository<Ubicacion, Long> {

	List<Ubicacion> findByUbicacionStartingWithAllIgnoreCase(String valor);

	Ubicacion findByUbicacion(String value);

}
