package servicio.maestros;

import java.util.List;

import interfacedao.maestros.ITipoDAO;
import modelo.maestros.Tipo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("STipo")
public class STipo {
	
	@Autowired
	private ITipoDAO tipoDAO;
	
	public void guardar(Tipo tipo) {
		tipoDAO.save(tipo);
	}
	public List<Tipo> buscarTodos() {
		return tipoDAO.findAll();
	}
	public List<Tipo> filtroTipo(String valor) {
		return tipoDAO.findByTipoStartingWithAllIgnoreCase(valor);
	}
	public Tipo buscarPorTipo(String value) {
		return tipoDAO.findByTipo(value);
	}
	public Tipo buscarPorTipos(String value) {
		return tipoDAO.findByTipo(value);
	}
	public Tipo buscar(long id) {
		return tipoDAO.findOne(id);
	}
	public void eliminar(Tipo tipo) {
		tipoDAO.delete(tipo);
	}

}
