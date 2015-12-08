package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import modelo.maestros.Departamento;
import modelo.maestros.Equipo;
import modelo.maestros.EquipoPrograma;
import modelo.maestros.Programas;
import modelo.maestros.Tipo;
import modelo.maestros.Ubicacion;
import modelo.maestros.UsuarioEquipo;
import modelo.seguridad.Arbol;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import arbol.CArbol;

import componente.Botonera;
import componente.Mensaje;
import componente.Catalogo;

public class CEquipo extends CGenerico {
	@Wire
	private Div divEquipo;
	@Wire
	private Div botoneraEquipo;
	@Wire
	private Div divCatalogoUsuario;
	@Wire
	private Div divCatalogoEquipo;
	@Wire
	private Button btnBuscar1;
	@Wire
	private Button btnAbrirTipo;
	@Wire
	private Button btnAbrirDepar;
	@Wire
	private Button btnAbrirUsuario;
	@Wire
	private Button btnBuscar2;
	@Wire
	private Textbox textBoxEquipo;
	@Wire
	private Textbox textBoxSerial;
	@Wire
	private Textbox textBoxDescripcion;
	@Wire
	private Combobox cmbTipoEquipo;
	@Wire
	private Combobox cmbDepar;
	@Wire
	private Datebox dateBoxCompra;
	@Wire
	private Datebox dateBoxGarantia;
	@Wire
	private Doublespinner doubleSpinnerPrecio;
	@Wire
	private Radiogroup rdgEstatus;
	@Wire
	private Radio rdoFunciona;
	@Wire
	private Radio rdoFueraS;
	@Wire
	private Radio rdoBackup;
	@Wire
	private Label lblNombre;
	@Wire
	private Label lblCedula;
	@Wire
	private Label lblDepartamento;
	@Wire
	private Label lblUbicacion;
	@Wire
	private Listbox ltbApp;
	@Wire
	private Listbox ltbAppAgregados;
	@Wire
	private Groupbox gpxApps;
	//Variables
	private String nombre;
	private String cedula;
	Long idEquipo = (long) 0;
	private CArbol cArbol = new CArbol();
	List<Programas> appDisponibles = new ArrayList<Programas>();
	List<EquipoPrograma> appOcupados = new ArrayList<EquipoPrograma>();
	//catalogos
	Catalogo<UsuarioEquipo> catalogoUsuario;
	Catalogo<Equipo> catalogoEquipo;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		contenido = (Include) divEquipo.getParent();
		Tabbox tabox = (Tabbox) divEquipo.getParent().getParent().getParent()
				.getParent();
		tabBox = tabox;
		tab = (Tab) tabox.getTabs().getLastChild();
		
		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (mapa != null) {
			if (mapa.get("tabsGenerales") != null) {
				tabs = (List<Tab>) mapa.get("tabsGenerales");
				nombre = (String) mapa.get("titulo");
				mapa.clear();
				mapa = null;
			}
		}
			//combo tipo inicializado
			List<Tipo> tipo = servicioTipo.buscarTodos();
			cmbTipoEquipo.setModel(new ListModelList<Tipo>(tipo));
			
			List<Departamento> depart = servicioDepartamento.buscarTodos();
			cmbDepar.setModel(new ListModelList<Departamento>(depart));
			// listas 
			llenarListas();
			gpxApps.setVisible(false);

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(botoneraEquipo, nombre, tabs);
			}

			@Override
			public void limpiar() {		
				idEquipo = (long) 0;
				cedula = "";
				//Campos
				textBoxEquipo.setValue("");
				textBoxSerial.setValue("");
				textBoxDescripcion.setValue("");
				cmbTipoEquipo.setValue("");
				cmbDepar.setValue("");
				dateBoxCompra.setValue(fecha);
				dateBoxGarantia.setValue(fecha);
				doubleSpinnerPrecio.setValue(0.0);
				rdoFunciona.setChecked(false);
				rdoFueraS.setChecked(false);
				rdoBackup.setChecked(false);
				lblNombre.setValue("");
				lblCedula.setValue("");
				lblDepartamento.setValue("");
				lblUbicacion.setValue("");
				ltbApp.getItems().clear();
				ltbAppAgregados.getItems().clear();
				llenarListas();
				usaProgramas();
			}

			@Override
			public void guardar() {
				if(validar()){
				Equipo equipo = new Equipo();
				
				String identificacionEquipo = textBoxEquipo.getValue();
				equipo.setIdentificacionEquipo(identificacionEquipo);
				String serial = textBoxSerial.getValue();
				equipo.setSerial(serial);
				String descripcion = textBoxDescripcion.getValue();
				equipo.setDescripcion(descripcion);
				//String tipo = cmbTipoEquipo.getValue();
				Tipo tipo = servicioTipo.buscar(Long.parseLong(cmbTipoEquipo
						.getSelectedItem().getContext()));
				equipo.setTipo(tipo);
				Departamento depar = servicioDepartamento.buscar(Long.parseLong(cmbDepar
						.getSelectedItem().getContext()));
				equipo.setDepartamento(depar);
				if(dateBoxCompra.getValue()!=null){
					Date fechaCompra = dateBoxCompra.getValue();
					Timestamp fechaC = new Timestamp(fechaCompra.getTime());
					equipo.setFechaCompra(fechaC);
					}
				if(dateBoxGarantia.getValue()!=null){
					Date fechaGarantia = dateBoxGarantia.getValue();
					Timestamp fechaG = new Timestamp(fechaGarantia.getTime());
					equipo.setFechaGarantia(fechaG);
					}
				if (doubleSpinnerPrecio.getValue() != null) {
					Double precio = doubleSpinnerPrecio.getValue();
					equipo.setPrecio(precio);
				}
				String estatus = "";
				if (rdoFunciona.isChecked()){
					estatus = rdoFunciona.getLabel();
					equipo.setEstatus(estatus);
				}
				if (rdoFueraS.isChecked()){
					estatus = rdoFueraS.getLabel();
					equipo.setEstatus(estatus);
				}
				if (rdoBackup.isChecked()){
					estatus = rdoBackup.getLabel();
					equipo.setEstatus(estatus);
				}
				
				UsuarioEquipo  usuarioEquipo = servicioUsuarioEquipo
						.buscarPorCedula(cedula);
				if (usuarioEquipo != null)
					equipo.setUsuario(usuarioEquipo);
				equipo.setIdEquipo(idEquipo);
				
				/*Set<Programas> gruposApps = new HashSet<Programas>();
				for (int i = 0; i < ltbAppAgregados.getItemCount(); i++) {
					Programas programa = ltbAppAgregados.getItems().get(i)
							.getValue();
					gruposApps.add(programa);}
				equipo.setProgramas(gruposApps);*/
				servicioEquipo.guardar(equipo);
				if (ltbAppAgregados.getItemCount() != 0) {
					equipo = new Equipo();
					if (idEquipo != 0)
						equipo = servicioEquipo.buscar(idEquipo);
					else
						equipo = servicioEquipo.buscarUltimo();
					System.out.println(equipo);
					guardarProgramas(equipo);
				}
				msj.mensajeInformacion(Mensaje.guardado);
				limpiar();
			}else{
				Mensaje.mensajeError("Debe llenar los datos requeridos del Equipo");
				}
			}

			@Override
			public void eliminar() {
				if (idEquipo != 0 && textBoxDescripcion.getText().compareTo("") != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Equipo?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Equipo equipo = servicioEquipo.buscar(idEquipo);
										if(equipo != null)
											servicioEquipoPrograma.borrar(equipo);
										servicioEquipo.eliminar(equipo);
										limpiar();
										msj.mensajeInformacion(Mensaje.eliminado);
									}
								}
							});
				} else {
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}

			}
			
		};
		
		botoneraEquipo.appendChild(botonera);

	}
	
	private boolean validar() {
		if (textBoxEquipo.getText().compareTo("") == 0
				||cmbTipoEquipo.getText().compareTo("") == 0
				||textBoxSerial.getText().compareTo("") == 0
				||cmbDepar.getText().compareTo("") == 0
				||doubleSpinnerPrecio.getValue() == 0
				||rdgEstatus.getSelectedItem() == null)
			return false;
		else
			return true;
	}
	
	@Listen("onClick =  #btnBuscar1")
	public void buscarTrabajador() {
		final List<UsuarioEquipo> usuarios = servicioUsuarioEquipo
				.buscarTodosUsuarios();
		catalogoUsuario = new Catalogo<UsuarioEquipo>(divCatalogoUsuario,
				"Catalogo de Usuarios", usuarios, false, "Cedula",
				"Nombre") {

			@Override
			protected List<UsuarioEquipo> buscar(String valor, String combo) {

				switch (combo) {
				case "Nombre y Apellido":
					return servicioUsuarioEquipo.filtroNombre(valor);
				case "Cedula":
					return servicioUsuarioEquipo.filtroCedula(valor);
				default:
					return usuarios;
				}
			}

			@Override
			protected String[] crearRegistros(UsuarioEquipo objeto) {
				String[] registros = new String[2];
				registros[0] = objeto.getCedula();
				registros[1] = objeto.getNombre();
				return registros;
			}

		};
		catalogoUsuario.setParent(divCatalogoUsuario);
		catalogoUsuario.doModal();
	}

	@Listen("onSeleccion = #divCatalogoUsuario")
	public void seleccionTrabajador() {
		UsuarioEquipo usuarioEquipo = catalogoUsuario.objetoSeleccionadoDelCatalogo();
		cedula = usuarioEquipo.getCedula();
		llenarCamposUsuarios(usuarioEquipo);
		lblCedula.setValue(usuarioEquipo.getCedula());
		catalogoUsuario.setParent(null);
	}

	private void llenarCamposUsuarios(UsuarioEquipo usuario) {
		lblCedula.setValue(usuario.getCedula());
		lblDepartamento.setValue(usuario.getDepartamento().getDepartamento());
		lblNombre.setValue(usuario.getNombre());
		lblUbicacion.setValue(usuario.getDepartamento().getUbicacion().getUbicacion());		
	}
	//llenar equipo
	@Listen("onClick =  #btnBuscar2")
	public void buscarEquipo() {
		final List<Equipo> equipos = servicioEquipo
				.buscarTodos();
		catalogoEquipo = new Catalogo<Equipo>(divCatalogoEquipo,
				"Catalogo de Equipo", equipos, false, "Indentificacion",
				"Tipo") {

			@Override
			protected List<Equipo> buscar(String valor, String combo) {

				switch (combo) {
				case "Identificacion del equipo":
					return servicioEquipo.filtroIndentificacion(valor);
				case "Tipo":
					return servicioEquipo.filtroTipos(valor);
				default:
					return equipos;
				}
			}

			@Override
			protected String[] crearRegistros(Equipo objeto) {
				String[] registros = new String[5];
				registros[0] = objeto.getIdentificacionEquipo();
				registros[1] = objeto.getTipo().getTipo();
				return registros;
			}

		};
		catalogoEquipo.setParent(divCatalogoEquipo);
		catalogoEquipo.doModal();
	}

	@Listen("onSeleccion = #divCatalogoEquipo")
	public void seleccionPaciente() {
		Equipo equipo = catalogoEquipo.objetoSeleccionadoDelCatalogo();
		idEquipo = equipo.getIdEquipo();
		llenarCamposEquipo(equipo);
		if(equipo.getUsuario()!=null)
		llenarCamposUsuarios(equipo.getUsuario());
		catalogoEquipo.setParent(null);
	}
	private void llenarCamposEquipo(Equipo equipo) {
		idEquipo = equipo.getIdEquipo();
		textBoxEquipo.setValue(equipo.getIdentificacionEquipo());
		textBoxSerial.setValue(equipo.getSerial());
		textBoxDescripcion.setValue(equipo.getDescripcion());
		cmbTipoEquipo.setValue(equipo.getTipo().getTipo());
		cmbDepar.setValue(equipo.getDepartamento().getDepartamento());
		if(equipo.getFechaCompra()!=null){
			dateBoxCompra.setValue(equipo.getFechaCompra());
			}
		if(equipo.getFechaGarantia()!=null){
			dateBoxGarantia.setValue(equipo.getFechaGarantia());
			}
		if (equipo.getPrecio()!= null) {
			doubleSpinnerPrecio.setValue(equipo.getPrecio());
		}
		if(equipo.getEstatus()!=null){
		String valor = equipo.getEstatus();
		if (valor.equals(rdoFunciona.getLabel()))
			rdoFunciona.setChecked(true);
		if (valor.equals(rdoFueraS.getLabel()))
			rdoFueraS.setChecked(true);
		if (valor.equals(rdoBackup.getLabel()))
			rdoBackup.setChecked(true);
		}
		llenarListas();
		usaProgramas();
	}
	
	/* Abre la vista de tipo */
	@Listen("onClick = #btnAbrirTipo")
	public void abrirTipo() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Tipo");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}
	@Listen("onClick = #btnAbrirUsuario")
	public void abrirUsuario() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Usuarios de Equipos");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}
	@Listen("onClick = #btnAbrirDepar")
	public void abrirDepar() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Departamentos");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}
	@Listen("onOpen = #cmbTipoEquipo")
	public void llenarCombo() {
		List<Tipo> tipo = servicioTipo.buscarTodos();
		cmbTipoEquipo.setModel(new ListModelList<Tipo>(tipo));
	}
	@Listen("onOpen = #cmbDepar")
	public void llenarComboDepar() {
		List<Departamento> depart = servicioDepartamento.buscarTodos();
		cmbDepar.setModel(new ListModelList<Departamento>(depart));
	}
	
	/* LLena las listas dado un usario */
	public void llenarListas() {
		Equipo equipo = servicioEquipo.buscar(idEquipo);
		appDisponibles = servicioProgramas.buscarDisponibles(equipo);
		ltbApp.setModel(new ListModelList<Programas>(appDisponibles));
		appOcupados = servicioEquipoPrograma.buscarGruposDelUsuario(equipo);
		ltbAppAgregados.setModel(new ListModelList<EquipoPrograma>(
				appOcupados));
		
		ltbApp.setMultiple(false);
		ltbApp.setCheckmark(false);
		ltbApp.setMultiple(true);
		ltbApp.setCheckmark(true);

		ltbAppAgregados.setMultiple(false);
		ltbAppAgregados.setCheckmark(false);
		ltbAppAgregados.setMultiple(true);
		ltbAppAgregados.setCheckmark(true);
	}
	@Listen("onClick = #pasar1")
	public void derecha() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbApp.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Programas programa = listItem.get(i).getValue();
					appDisponibles.remove(programa);
					EquipoPrograma equipoPrograma = new EquipoPrograma();
					equipoPrograma.setPrograma(programa);
					appOcupados.clear();
					for (int j = 0; j < ltbAppAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbAppAgregados
								.getItemAtIndex(j);
						Integer idPrograma = ((Spinner) ((listItemj.getChildren()
								.get(4))).getFirstChild()).getValue();
						Programas programaj = servicioProgramas.buscar(idPrograma);
						String usuario = ((Textbox) ((listItemj.getChildren().get(1)))
								.getFirstChild()).getValue();
						String password = ((Textbox) ((listItemj.getChildren().get(2)))
								.getFirstChild()).getValue();
						String observacion = ((Textbox) ((listItemj.getChildren().get(3)))
								.getFirstChild()).getValue();
						EquipoPrograma equipoProgramaj = new EquipoPrograma(null,
								programaj, usuario, password, observacion);
						appOcupados.add(equipoProgramaj);
					}
					appOcupados.add(equipoPrograma);
					ltbAppAgregados
							.setModel(new ListModelList<EquipoPrograma>(
									appOcupados));
					ltbAppAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbApp.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		ltbAppAgregados.setMultiple(false);
		ltbAppAgregados.setCheckmark(false);
		ltbAppAgregados.setMultiple(true);
		ltbAppAgregados.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * derecha a la lista de la izquierda
	 */
	@Listen("onClick = #pasar2")
	public void izquierda() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbAppAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					EquipoPrograma equipoPrograma = listItem2.get(i).getValue();
					appOcupados.remove(equipoPrograma);
					appDisponibles.add(equipoPrograma.getPrograma());
					ltbApp.setModel(new ListModelList<Programas>(
							appDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbAppAgregados
					.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		ltbApp.setMultiple(false);
		ltbApp.setCheckmark(false);
		ltbApp.setMultiple(true);
		ltbApp.setCheckmark(true);
	}
	protected void guardarProgramas(Equipo equipo) {
		List<EquipoPrograma> equipoProgramas = new ArrayList<EquipoPrograma>();
		if(equipo != null)
		servicioEquipoPrograma.borrar(equipo);
		
		if (ltbAppAgregados.getItemCount() != 0) {
			for (int i = 0; i < ltbAppAgregados.getItemCount(); i++) {
				Listitem listItem = ltbAppAgregados.getItemAtIndex(i);
				Integer idPrograma = ((Spinner) ((listItem.getChildren().get(4)))
						.getFirstChild()).getValue();
				Programas programa = servicioProgramas.buscar(idPrograma);
				String usuario = ((Textbox) ((listItem.getChildren().get(1)))
						.getFirstChild()).getValue();
				String password = ((Textbox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getValue();
				String observacion = ((Textbox) ((listItem.getChildren().get(3)))
						.getFirstChild()).getValue();
				EquipoPrograma equipoPrograma = new EquipoPrograma(equipo,
						programa, usuario, password, observacion);
				equipoProgramas.add(equipoPrograma);
			}
		}
		servicioEquipoPrograma.guardar(equipoProgramas);
	}
	
	@Listen("onChange =#cmbTipoEquipo")
	public void usaProgramas() {
		if(cmbTipoEquipo.getValue().equals("PC") || cmbTipoEquipo.getValue().equals("Laptop")){
			gpxApps.setVisible(true);
		}else{
			gpxApps.setVisible(false);
		}
		}

}
