package servicio.maestros;

import interfacedao.maestros.IDepartamentoDAO;

import java.util.List;


import modelo.maestros.Departamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SDepartamento")
public class SDepartamento {
	
	@Autowired
	private IDepartamentoDAO departamentoDAO;
	
	public void guardar(Departamento departamento) {
		departamentoDAO.save(departamento);
	}
	public List<Departamento> buscarTodos() {
		return departamentoDAO.findAll();
	}
	public List<Departamento> filtroDepartamento(String valor) {
		return departamentoDAO.findByDepartamentoStartingWithAllIgnoreCase(valor);
	}
	public Departamento buscarPorDepartamento(String value) {
		return departamentoDAO.findByDepartamento(value);
	}
	public Departamento buscarPorDepartamentos(String value) {
		return departamentoDAO.findByDepartamento(value);
	}
	public Departamento buscar(long id) {
		return departamentoDAO.findOne(id);
	}
	public void eliminar(Departamento departamento) {
		departamentoDAO.delete(departamento);
	}

}
