package controlador.maestros;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Programas;
import modelo.maestros.Ubicacion;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CProgramas extends CGenerico {
	
	@Wire
	private Div botoneraApp;
	@Wire
	private Div divCatalogoApp;
	@Wire
	private Textbox textBoxApp;
	@Wire
	private Button btnBuscarApp;

	// Variables
	private String nombre;
	private long idApp = 0;
	// catalogos
		Catalogo<Programas> catalogoApp;

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

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(botoneraApp, nombre, tabs);
			}

			@Override
			public void limpiar() {
				textBoxApp.setValue("");
				idApp = 0;
			}

			@Override
			public void guardar() {
				if (textBoxApp.getValue() != "") {
					String app = textBoxApp.getValue();
					Programas programa = new Programas(idApp, app);
					programa.setPrograma(app);

					servicioProgramas.guardar(programa);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				} else {
					Mensaje.mensajeError("Debe llenar los datos requeridos");
				}
			}

			@Override
			public void eliminar() {
				if (idApp != 0
						&& textBoxApp.getText().compareTo("") != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el programa?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Programas programas = servicioProgramas
												.buscar(idApp);
										servicioProgramas.eliminar(programas);
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
		botoneraApp.appendChild(botonera);
		
	}
	
	/* Muestra el catalogo */
	@Listen("onClick = #btnBuscarApp")
	public void mostrarCatalogo() {
		final List<Programas> programa = servicioProgramas.buscarTodos();
		catalogoApp = new Catalogo<Programas>(divCatalogoApp,
				"Catalogo de Programas", programa, false, "Nombre") {

			@Override
			protected List<Programas> buscar(String valor, String combo) {
				if (combo.equals("Nombre"))
					return servicioProgramas.filtroPrograma(valor);
				else
					return programa;
			}

			@Override
			protected String[] crearRegistros(Programas estado) {
				String[] registros = new String[1];
				registros[0] = estado.getPrograma();
				return registros;
			}
		};
		catalogoApp.setParent(divCatalogoApp);
		catalogoApp.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #divCatalogoApp")
	public void seleccinar() {
		Programas programa = catalogoApp.objetoSeleccionadoDelCatalogo();
		llenarCampos(programa);
		catalogoApp.setParent(null);
	}

	@Listen("onChange = #textBoxApp")
	public void buscarPorTipo() {
		Programas programa = servicioProgramas.buscarPorTipo(textBoxApp
				.getValue());
		if (programa != null)
			llenarCampos(programa);
	}

	private void llenarCampos(Programas programa) {
		textBoxApp.setValue(programa.getPrograma());
		idApp = programa.getIdPrograma();
	}

}
