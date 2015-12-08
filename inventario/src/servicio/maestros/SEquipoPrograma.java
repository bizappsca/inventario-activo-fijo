package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IEquipoProgramaDAO;

import modelo.maestros.Equipo;
import modelo.maestros.EquipoPrograma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEquipoPrograma")
public class SEquipoPrograma {

	@Autowired
	private IEquipoProgramaDAO equipoProgramasDAO;
	
	public List<EquipoPrograma> buscarGruposDelUsuario(Equipo equipo){
		return equipoProgramasDAO.findByIdEquipo(equipo);
	}
	public void borrar(Equipo equipo) {
		List<EquipoPrograma> borrados = equipoProgramasDAO.findByIdEquipo(equipo);
		if (!borrados.isEmpty())
			equipoProgramasDAO.delete(borrados);
	}

	public void guardar(List<EquipoPrograma> equipoProgramas) {
		equipoProgramasDAO.save(equipoProgramas);
	}
}
