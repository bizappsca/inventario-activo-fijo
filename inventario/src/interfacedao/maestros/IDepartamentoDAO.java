package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Departamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartamentoDAO extends JpaRepository<Departamento, Long> {

	List<Departamento> findByDepartamentoStartingWithAllIgnoreCase(String valor);

	Departamento findByDepartamento(String value);

	Departamento findByIdDepartamento(long value);

}
