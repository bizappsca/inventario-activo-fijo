package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Tipo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoDAO extends JpaRepository<Tipo, Long> {

	Tipo findByTipo(String value);

	List<Tipo> findByTipoStartingWithAllIgnoreCase(String valor);

	Tipo findByIdTipo(long value);

}
