package controlador.maestros;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import modelo.maestros.Departamento;
import modelo.maestros.Ubicacion;
import modelo.maestros.UsuarioEquipo;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CUsuarioEquipo extends CGenerico {
	
	@Wire
	private Div botoneraUsuario;
	@Wire
	private Div divCatalogoUsuario;
	@Wire
	private Textbox textBoxNombre;
	@Wire
	private Textbox textCedula;
	@Wire
	private Combobox cmbDepartamento;
	@Wire
	private Button btnBuscarUsuario;
	@Wire
	private Label lblUbicacion;
	
	//Variables
	private String nombre;
	private long idUsuario = 0;
	private long idDepart = 0;
	private String cedula;
	//catalogos
	Catalogo<UsuarioEquipo> catalogoUsuario;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
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

		List<Departamento> depart = servicioDepartamento.buscarTodos();
		cmbDepartamento.setModel(new ListModelList<Departamento>(depart));
		
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(botoneraUsuario, nombre, tabs);
			}

			@Override
			public void limpiar() {	
				textBoxNombre.setValue("");
				textCedula.setValue("");
				cmbDepartamento.setValue("");
				lblUbicacion.setValue("");
				idUsuario = 0;
			}

			@Override
			public void guardar() {
				if(validar()){
				String nombre = textBoxNombre.getValue();
				String cedula = textCedula.getValue();
				Departamento depar = null;
				if (cmbDepartamento.getSelectedItem().getContext()!= null){
					 depar = servicioDepartamento.buscar(Long.parseLong(cmbDepartamento
							.getSelectedItem().getContext()));
				}
				UsuarioEquipo usuario = new UsuarioEquipo(idUsuario, nombre, cedula, depar);
				servicioUsuarioEquipo.guardar(usuario);
				msj.mensajeInformacion(Mensaje.guardado);
				limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (idUsuario != 0 && textCedula.getText().compareTo("") != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Usuario?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										UsuarioEquipo usuario = servicioUsuarioEquipo.buscar(idUsuario);
										servicioUsuarioEquipo.eliminar(usuario);
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
		
		botoneraUsuario.appendChild(botonera);
	}
	public boolean validar() {

		if (cmbDepartamento.getText().compareTo("") == 0
				|| textBoxNombre.getText().compareTo("") == 0
				|| textCedula.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}
	@Listen("onOpen = #cmbDepartamento")
	public void llenarCombo() {
		List<Departamento> depart = servicioDepartamento.buscarTodos();
		cmbDepartamento.setModel(new ListModelList<Departamento>(depart));
	}
	@Listen("onSelect = #cmbDepartamento")
	public void llenarLabel() {
		Departamento depart =  servicioDepartamento.buscar(Long.parseLong(cmbDepartamento
				.getSelectedItem().getContext()));
		if(depart != null)
		lblUbicacion.setValue(depart.getUbicacion().getUbicacion());
	}
	
	@Listen("onClick =  #btnBuscarUsuario")
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
		textCedula.setValue(usuarioEquipo.getCedula());
		catalogoUsuario.setParent(null);
	}

	private void llenarCamposUsuarios(UsuarioEquipo usuario) {
		idUsuario = usuario.getIdUsuarioEquipo();
		textCedula.setValue(usuario.getCedula());
		if(usuario.getDepartamento()!=null)
		cmbDepartamento.setValue(usuario.getDepartamento().getDepartamento());
		textBoxNombre.setValue(usuario.getNombre());
		lblUbicacion.setValue(usuario.getDepartamento().getUbicacion().getUbicacion());		
	}

}
