package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IUsuarioEquipoDAO;

import modelo.maestros.UsuarioEquipo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUsuarioEquipo")
public class SUsuarioEquipo {
	@Autowired
	private IUsuarioEquipoDAO usuarioEquipoDAO;

	public void guardar(UsuarioEquipo usuario) {
		usuarioEquipoDAO.save(usuario);
	}
	
	public void eliminar(UsuarioEquipo usuario) {
		usuarioEquipoDAO.delete(usuario);
	}

	public UsuarioEquipo buscar(long id) {
		return usuarioEquipoDAO.findOne(id);
	}
	
	public List<UsuarioEquipo> buscarTodosUsuarios() {
		return usuarioEquipoDAO.findAll();
	}

	public List<UsuarioEquipo> filtroNombre(String valor) {
		return usuarioEquipoDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	public List<UsuarioEquipo> filtroCedula(String valor) {
		return usuarioEquipoDAO.findByCedulaStartingWithAllIgnoreCase(valor);
	}

	public UsuarioEquipo buscarPorCedula(String value) {
		return usuarioEquipoDAO.findByCedula(value);
	}

}
