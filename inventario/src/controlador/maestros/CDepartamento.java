package controlador.maestros;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Departamento;
import modelo.maestros.Ubicacion;
//import modelo.seguridad.Arbol;
import security.modelo.Arbol;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import security.controlador.CArbol;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CDepartamento extends CGenerico {
	@Wire
	private Div divDepartamento;
	@Wire
	private Div botoneraDepart;
	@Wire
	private Div divCatalogoDepart;
	@Wire
	private Textbox textBoxDepart;
	@Wire
	private Button btnBuscarDepart;
	@Wire
	private Combobox cmbSucursal;
	
	//Variables
	private CArbol cArbol = new CArbol();
	private String nombre;
	private long idDepart = 0;
	//catalogos
	Catalogo<Departamento> catalogoDepart;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		
		contenido = (Include) divDepartamento.getParent();
		Tabbox tabox = (Tabbox) divDepartamento.getParent().getParent().getParent()
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
		
		List<Ubicacion> sucursal = servicioUbicacion.buscarTodos();
		cmbSucursal.setModel(new ListModelList<Ubicacion>(sucursal));

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(botoneraDepart, nombre, tabs);
			}

			@Override
			public void limpiar() {				
				textBoxDepart.setValue("");
				idDepart = 0;
				cmbSucursal.setValue("");
			}

			@Override
			public void guardar() {
				if(textBoxDepart.getValue()!="" || cmbSucursal.getText().compareTo("") == 0){
					String departamento = textBoxDepart.getValue();
					Ubicacion ubicacion = servicioUbicacion.buscar(Long.parseLong(cmbSucursal
							.getSelectedItem().getContext()));
					Departamento depart = new Departamento(idDepart, departamento, ubicacion);
					depart.setDepartamento(departamento);
					servicioDepartamento.guardar(depart);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					}else{
						Mensaje.mensajeError("Debe llenar los datos requeridos");
					}
			}

			@Override
			public void eliminar() {
				if (idDepart != 0 && textBoxDepart.getText().compareTo("") != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Departamento?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Departamento depart = servicioDepartamento.buscar(idDepart);
										servicioDepartamento.eliminar(depart);
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
		
		botoneraDepart.appendChild(botonera);

	}
	/* Muestra el catalogo */
	@Listen("onClick = #btnBuscarDepart")
	public void mostrarCatalogo() {
		final List<Departamento> depart = servicioDepartamento.buscarTodos();
		catalogoDepart = new Catalogo<Departamento>(divCatalogoDepart, "Catalogo de Departamentos",
				depart, false,"Nombre") {

			@Override
			protected List<Departamento> buscar(String valor, String combo) {
				if (combo.equals("Nombre"))
					return servicioDepartamento.filtroDepartamento(valor);
				else
					return depart;
			}

			@Override
			protected String[] crearRegistros(Departamento estado) {
				String[] registros = new String[1];
				registros[0] = estado.getDepartamento();
				return registros;
			}
		};
		catalogoDepart.setParent(divCatalogoDepart);
		catalogoDepart.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #divCatalogoDepart")
	public void seleccinar() {
		Departamento depart = catalogoDepart.objetoSeleccionadoDelCatalogo();
		llenarCampos(depart);
		catalogoDepart.setParent(null);
	}

	@Listen("onChange = #textBoxDepart")
	public void buscarPorTipo() {
		Departamento depart = servicioDepartamento.buscarPorDepartamento(textBoxDepart.getValue());
		if (depart != null)
			llenarCampos(depart);
	}

	private void llenarCampos(Departamento depart) {
		textBoxDepart.setValue(depart.getDepartamento());
		idDepart = depart.getIdDepartamento();
		cmbSucursal.setValue(depart.getUbicacion().getUbicacion());
	}
	
	@Listen("onOpen = #cmbSucursal")
	public void llenarCombo() {
		List<Ubicacion> sucursal = servicioUbicacion.buscarTodos();
		cmbSucursal.setModel(new ListModelList<Ubicacion>(sucursal));
	}
	@Listen("onClick = #btnAbrir")
	public void abrir() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Ubicacion");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);			
			cArbol.abrirVentanas(arbolItem);
		}
	}

}
