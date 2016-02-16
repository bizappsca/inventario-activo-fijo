package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IEquipoDAO;
import modelo.maestros.Departamento;
import modelo.maestros.Equipo;
import modelo.maestros.Tipo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEquipo")
public class SEquipo {
	
	@Autowired
	private IEquipoDAO equipoDAO;
	
	public void guardar(Equipo equipo) {
		equipoDAO.save(equipo);
	}
	public List<Equipo> buscarTodos() {
		return equipoDAO.findAll();
	}
	public List<Equipo> filtroIndentificacion(String valor) {
		return equipoDAO.findByIdentificacionEquipoStartingWithAllIgnoreCase(valor);
	}

	public List<Equipo> filtroTipo(String valor) {
		return equipoDAO.findByTipoStartingWithAllIgnoreCase(valor);
	}
	public Equipo buscar(long id) {
		return equipoDAO.findOne(id);
	}
	public void eliminar(Equipo equipo) {
		equipoDAO.delete(equipo);
	}
	public Equipo buscarUltimo() {
		long id = equipoDAO.findMaxIdEquipo();
		if (id != 0)
			return equipoDAO.findOne(id);
		return null;
	}
	//para la consulta
	public List<Equipo> filtroTipos(String valor) {
		return equipoDAO.findByTipoTipoStartingWithAllIgnoreCase(valor);
	}
	public List<Equipo> filtroEstatus(String valor) {
		return equipoDAO.findByEstatusStartingWithAllIgnoreCase(valor);
	}
	public List<Equipo> filtroDescripcion(String valor) {
		return equipoDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	public List<Equipo> filtroUsuario(String valor) {
		return equipoDAO.findByUsuarioNombreStartingWithAllIgnoreCase(valor);
	}
	public List<Equipo> filtroDepar(String valor) {
		return equipoDAO.findByDepartamentoDepartamentoStartingWithAllIgnoreCase(valor);
	}
	public List<Equipo> filtroSerial(String valor) {
		return equipoDAO.findBySerialStartingWithAllIgnoreCase(valor);
	}
	
	//reporte
	public Equipo filtroTipoReporte(String valor) {
		return equipoDAO.findByTipo(valor);
	}
	public Equipo filtroDeparReporte(String valor) {
		return equipoDAO.findByDepartamento(valor);
	}
	//reporte Auditoria
	public List<Equipo> reporteTipoYDepartamento(Tipo valor, Departamento valorD){
		return equipoDAO.findByTipoAndDepartamento(valor,valorD);
	}
	
	public List<Equipo> reporteDepartamento(Departamento valorD){
		return equipoDAO.findByDepartamentoOrderByTipoAsc(valorD);
	}

}
