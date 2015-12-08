package controlador.maestros;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Tipo;
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

public class CUbicacion extends CGenerico {

	@Wire
	private Div botoneraSucursal;
	@Wire
	private Div divCatalogoSucursal;
	@Wire
	private Textbox textBoxSucursal;
	@Wire
	private Button btnBuscarSucursal;

	// Variables
	private String nombre;
	private long idUbicacion = 0;
	// catalogos
	Catalogo<Ubicacion> catalogoSucursal;

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
				cerrarVentana(botoneraSucursal, nombre, tabs);
			}

			@Override
			public void limpiar() {
				textBoxSucursal.setValue("");
				idUbicacion = 0;
			}

			@Override
			public void guardar() {
				if (textBoxSucursal.getValue() != "") {
					String sucursal = textBoxSucursal.getValue();
					Ubicacion ubicacion = new Ubicacion(idUbicacion, sucursal);
					ubicacion.setUbicacion(sucursal);

					servicioUbicacion.guardar(ubicacion);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				} else {
					Mensaje.mensajeError("Debe llenar los datos requeridos");
				}
			}

			@Override
			public void eliminar() {
				if (idUbicacion != 0
						&& textBoxSucursal.getText().compareTo("") != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar la Sucursal?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Ubicacion ubicacion = servicioUbicacion
												.buscar(idUbicacion);
										servicioUbicacion.eliminar(ubicacion);
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
		botoneraSucursal.appendChild(botonera);

	}

	/* Muestra el catalogo */
	@Listen("onClick = #btnBuscarSucursal")
	public void mostrarCatalogo() {
		final List<Ubicacion> ubicacion = servicioUbicacion.buscarTodos();
		catalogoSucursal = new Catalogo<Ubicacion>(divCatalogoSucursal,
				"Catalogo de Sucursales", ubicacion, false, "Nombre") {

			@Override
			protected List<Ubicacion> buscar(String valor, String combo) {
				if (combo.equals("Nombre"))
					return servicioUbicacion.filtroUbicacion(valor);
				else
					return ubicacion;
			}

			@Override
			protected String[] crearRegistros(Ubicacion estado) {
				String[] registros = new String[1];
				registros[0] = estado.getUbicacion();
				return registros;
			}
		};
		catalogoSucursal.setParent(divCatalogoSucursal);
		catalogoSucursal.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #divCatalogoSucursal")
	public void seleccinar() {
		Ubicacion ubicacion = catalogoSucursal.objetoSeleccionadoDelCatalogo();
		llenarCampos(ubicacion);
		catalogoSucursal.setParent(null);
	}

	@Listen("onChange = #textBoxSucursal")
	public void buscarPorTipo() {
		Ubicacion ubicacion = servicioUbicacion.buscarPorTipo(textBoxSucursal
				.getValue());
		if (ubicacion != null)
			llenarCampos(ubicacion);
	}

	private void llenarCampos(Ubicacion ubicacion) {
		textBoxSucursal.setValue(ubicacion.getUbicacion());
		idUbicacion = ubicacion.getIdUbicacion();
	}

}
