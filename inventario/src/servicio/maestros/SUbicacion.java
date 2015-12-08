package servicio.maestros;

import interfacedao.maestros.IUbicacionDAO;

import java.util.List;


import modelo.maestros.Ubicacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUbicacion")
public class SUbicacion {

	@Autowired
	private IUbicacionDAO ubicacionDAO;
	
	public void guardar(Ubicacion ubicacion) {
		ubicacionDAO.save(ubicacion);
	}
	public List<Ubicacion> buscarTodos() {
		return ubicacionDAO.findAll();
	}
	public List<Ubicacion> filtroUbicacion(String valor) {
		return ubicacionDAO.findByUbicacionStartingWithAllIgnoreCase(valor);
	}
	public Ubicacion buscarPorTipo(String value) {
		return ubicacionDAO.findByUbicacion(value);
	}
	public Ubicacion buscar(long id) {
		return ubicacionDAO.findOne(id);
	}
	public void eliminar(Ubicacion ubicacion) {
		ubicacionDAO.delete(ubicacion);
	}
}
