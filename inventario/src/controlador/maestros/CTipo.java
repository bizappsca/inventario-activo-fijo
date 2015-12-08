package controlador.maestros;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Tipo;

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
import componente.Mensaje;
import componente.Catalogo;

public class CTipo extends CGenerico {
	
	@Wire
	private Div botoneraTipo;
	@Wire
	private Div divCatalogoTipo;
	@Wire
	private Textbox textBoxTipo;
	@Wire
	private Button btnBuscarTipo;
	
	//Variables
	private String nombre;
	private long idTipo = 0;
	//catalogos
	Catalogo<Tipo> catalogoTipo;

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
				cerrarVentana(botoneraTipo, nombre, tabs);
			}

			@Override
			public void limpiar() {	
				textBoxTipo.setValue("");
				idTipo = 0;
			}

			@Override
			public void guardar() {
				if(textBoxTipo.getValue()!=""){
				String tipoEquipo = textBoxTipo.getValue();
				Tipo tipo = new Tipo(idTipo, tipoEquipo);
				tipo.setTipo(tipoEquipo);
			
				servicioTipo.guardar(tipo);
				msj.mensajeInformacion(Mensaje.guardado);
				limpiar();
				}else{
					Mensaje.mensajeError("Debe llenar los datos requeridos");
				}
			}

			@Override
			public void eliminar() {
				if (idTipo != 0 && textBoxTipo.getText().compareTo("") != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Tipo de Equipo?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Tipo tipo = servicioTipo.buscar(idTipo);
										servicioTipo.eliminar(tipo);
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
		
		botoneraTipo.appendChild(botonera);

	}
	
	/* Muestra el catalogo */
	@Listen("onClick = #btnBuscarTipo")
	public void mostrarCatalogo() {
		final List<Tipo> tipos = servicioTipo.buscarTodos();
		catalogoTipo = new Catalogo<Tipo>(divCatalogoTipo, "Catalogo de Tipos",
				tipos, false,"Nombre") {

			@Override
			protected List<Tipo> buscar(String valor, String combo) {
				if (combo.equals("Nombre"))
					return servicioTipo.filtroTipo(valor);
				else
					return tipos;
			}

			@Override
			protected String[] crearRegistros(Tipo estado) {
				String[] registros = new String[1];
				registros[0] = estado.getTipo();
				return registros;
			}
		};
		catalogoTipo.setParent(divCatalogoTipo);
		catalogoTipo.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #divCatalogoTipo")
	public void seleccinar() {
		Tipo tipo = catalogoTipo.objetoSeleccionadoDelCatalogo();
		llenarCampos(tipo);
		catalogoTipo.setParent(null);
	}

	@Listen("onChange = #textBoxTipo")
	public void buscarPorTipo() {
		Tipo tipo = servicioTipo.buscarPorTipo(textBoxTipo.getValue());
		if (tipo != null)
			llenarCampos(tipo);
	}

	private void llenarCampos(Tipo tipo) {
		textBoxTipo.setValue(tipo.getTipo());
		idTipo = tipo.getIdTipo();
	}

}
