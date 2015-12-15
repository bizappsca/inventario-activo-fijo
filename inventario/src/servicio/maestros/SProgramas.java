package servicio.maestros;

import java.util.ArrayList;
import java.util.List;

import interfacedao.maestros.IEquipoProgramaDAO;
import interfacedao.maestros.IProgramasDAO;

import modelo.maestros.Equipo;
import modelo.maestros.EquipoPrograma;
import modelo.maestros.Programas;
import modelo.maestros.Ubicacion;
import modelo.seguridad.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SProgramas")
public class SProgramas {
	@Autowired
	private IProgramasDAO programasDAO;
	@Autowired
	private IEquipoProgramaDAO equipoProgramaDAO;
	
	public void guardar(Programas programa) {
		programasDAO.save(programa);
	}
	public List<Programas> buscarTodos() {
		return programasDAO.findAll();
	}
	public List<Programas> filtroPrograma(String valor) {
		return programasDAO.findByProgramaStartingWithAllIgnoreCase(valor);
	}
	public Programas buscarPorTipo(String value) {
		return programasDAO.findByPrograma(value);
	}
	public Programas buscar(long id) {
		return programasDAO.findOne(id);
	}
	public void eliminar(Programas programa) {
		programasDAO.delete(programa);
	}
	public List<Programas> buscarGruposDisponibles(List<Long> ids){
		return programasDAO.findByIdProgramaNotInAndEstadoTrue(ids);
	}
	/*public List<Programas> buscarGruposDelUsuario(Equipo equipo){
		return programasDAO.findByEquipo(equipo);
	}*/
	public List<Programas> buscarDisponibles(Equipo equipo) {
		List<EquipoPrograma> equipoPrograma = equipoProgramaDAO.findByIdEquipo(equipo);
		List<Long> ids = new ArrayList<Long>();
		if(equipoPrograma.isEmpty())
			return programasDAO.findAll();
		else{
			for(int i=0; i<equipoPrograma.size();i++){
				ids.add(equipoPrograma.get(i).getPrograma().getIdPrograma());
			}
			return programasDAO.findByIdProgramaNotIn(ids);
		}
	}

}
