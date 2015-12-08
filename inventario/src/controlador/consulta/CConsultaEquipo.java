package controlador.consulta;

import java.io.IOException;
import java.util.List;

import modelo.maestros.Equipo;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import controlador.maestros.CGenerico;

public class CConsultaEquipo extends CGenerico {

	@Wire
	private Listbox ltbEquipo;
	@Wire
	private Combobox cmbBuscar;
	@Wire
	private Textbox textBoxBuscar;
	@Wire
	private Button btnConsultar;
	
	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		
		List<Equipo> equipo = servicioEquipo.buscarTodos();
		ltbEquipo.setModel(new ListModelList<Equipo>(equipo));
		
	}
	
	@Listen("onClick =  #btnConsultar")
	public void consulta(){
		String valor = textBoxBuscar.getValue();
		if(cmbBuscar.getValue().equals("Tipo")){
			List<Equipo> equipo = servicioEquipo.filtroTipos(valor);
			ltbEquipo.setModel(new ListModelList<Equipo>(equipo));
		}
		if(cmbBuscar.getValue().equals("Descripcion")){
			List<Equipo> equipo = servicioEquipo.filtroDescripcion(valor);
			ltbEquipo.setModel(new ListModelList<Equipo>(equipo));
		}
		if(cmbBuscar.getValue().equals("Estatus")){
			List<Equipo> equipo = servicioEquipo.filtroEstatus(valor);
			ltbEquipo.setModel(new ListModelList<Equipo>(equipo));
		}
		if(cmbBuscar.getValue().equals("Indentificacion")){
			List<Equipo> equipo = servicioEquipo.filtroIndentificacion(valor);
			ltbEquipo.setModel(new ListModelList<Equipo>(equipo));
		}
		if(cmbBuscar.getValue().equals("Usuario")){
			List<Equipo> equipo = servicioEquipo.filtroUsuario(valor);
			ltbEquipo.setModel(new ListModelList<Equipo>(equipo));
		}
		if(cmbBuscar.getValue().equals("Serial")){
			List<Equipo> equipo = servicioEquipo.filtroSerial(valor);
			ltbEquipo.setModel(new ListModelList<Equipo>(equipo));
		}
		if(cmbBuscar.getValue().equals("Departamento")){
			List<Equipo> equipo = servicioEquipo.filtroDepar(valor);
			ltbEquipo.setModel(new ListModelList<Equipo>(equipo));
		}
		if(cmbBuscar.getValue().equals(null)){
			List<Equipo> equipo = servicioEquipo.buscarTodos();
			ltbEquipo.setModel(new ListModelList<Equipo>(equipo));
		}
	}
	

}
