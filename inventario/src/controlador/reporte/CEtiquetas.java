package controlador.reporte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tab;

import componente.Botonera;
import componente.Mensaje;
import controlador.maestros.CGenerico;
import modelo.maestros.Departamento;
import modelo.maestros.Equipo;
import modelo.maestros.Tipo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class CEtiquetas extends CGenerico {

	@Wire
	private Combobox cmbTipoEquipo;
	@Wire
	private Combobox cmbDepar;
	//Variables
		private String nombre;
		@Wire
		private Div botoneraEquipo;
		@Wire
		private Combobox cmbTipoReporte;
	
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
		
		cargarCombo();

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(botoneraEquipo, nombre, tabs);
			}

			@Override
			public void limpiar() {		
				cmbTipoEquipo.setValue("");
				cmbDepar.setValue("");
			}

			@Override
			public void guardar() {
				if(validar()){
				Tipo tipoEquipo = null;
					if (!cmbTipoEquipo.getValue().equals("TODOS"))
						tipoEquipo = servicioTipo.buscar(Long.parseLong(cmbTipoEquipo
								.getSelectedItem().getContext()));
				Departamento depart = null;
					if (!cmbDepar.getValue().equals("TODOS"))
						depart = servicioDepartamento.buscar(Long.parseLong(cmbDepar
								.getSelectedItem().getContext()));
				String tipo = cmbTipoReporte.getValue();
				List<Equipo> consultas = new ArrayList<Equipo>();
				if(tipoEquipo==null){
					if(depart!=null)
						consultas = servicioEquipo.reporteDepartamento(depart);
				}else{
					if(depart!=null)
						consultas = servicioEquipo.reporteTipoYDepartamento(tipoEquipo,depart);
				}
				long idTipo = 0;
				if (tipoEquipo != null)
					idTipo = tipoEquipo.getIdTipo();
				long idDepar = 0;
				if (depart != null)
					idDepar = depart.getIdDepartamento();
				
				if (!consultas.isEmpty())
					// ESTO NO SE ESTA EJECUNANDO
					Clients.evalJavaScript("window.open('"
							+ damePath()
							+ "Reportero?valor=2&valor1="
							+ String.valueOf(idTipo)
							+ "&valor2="
							+ String.valueOf(idDepar)
							+ "&valor3="
							+ tipo
							+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
			
				else
					Mensaje.mensajeAlerta(Mensaje.noHayRegistros);
				}
			}

			@Override
			public void eliminar() {
				
			}
			
		};
		
		Button guardar = (Button) botonera.getChildren().get(0);
		guardar.setLabel("Reporte");
		guardar.setImage("/public/imagenes/botones/reporte.png");
		botonera.getChildren().get(1).setVisible(false);
		botoneraEquipo.appendChild(botonera);
		
	}
	protected boolean validar() {
		if (cmbTipoEquipo.getText().compareTo("") == 0
				|| cmbDepar.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}
	
	private void cargarCombo() {
		String todos = "TODOS";
		Tipo tipo = new Tipo();
		tipo.setTipo(todos);
		tipo.setIdTipo(0);
		List<Tipo> tipos = new ArrayList<Tipo>();
		tipos.add(tipo);
		tipos.addAll(servicioTipo.buscarTodos());
		cmbTipoEquipo.setModel(new ListModelList<Tipo>(tipos));
		List<Departamento> departs = new ArrayList<Departamento>();
		departs.addAll(servicioDepartamento.buscarTodos());
		cmbDepar.setModel(new ListModelList<Departamento>(departs));
	}

	public byte[] reporteEtiqueta(Long par2, Long par3, String tipo) throws JRException {
		// TODO Auto-generated method stub
		byte[] fichero = null;
		Tipo tipoEquipo = getServicioTipo().buscar(par2);
		Departamento depart = getServicioDepartamento().buscar(par3);
		List<Equipo> consultas = new ArrayList<Equipo>();
		Map p = new HashMap();
		if(tipoEquipo==null){
			if(depart!=null)
				consultas = getServicioEquipo().reporteDepartamento(depart);
			p.put("varDepar",depart.getDepartamento());
		}else{
			if(depart!=null)
				consultas = getServicioEquipo().reporteTipoYDepartamento(tipoEquipo,depart);
			p.put("varTipo",tipoEquipo.getTipo());
			p.put("varDepar",depart.getDepartamento());
		}
		JasperReport reporte = null;
		try {
			reporte = (JasperReport) JRLoader.loadObject(getClass()
					.getResource("/reportes/REtiquetas.jasper"));
		} catch (JRException e) {
			Mensaje.mensajeError("Recurso no Encontrado");
		}
		if (tipo.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consultas));
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return xlsReport.toByteArray();
		} else {
		try {
			fichero = JasperRunManager.runReportToPdf(reporte, p,
					new JRBeanCollectionDataSource(consultas));
		} catch (JRException e) {
			System.out.println(e.toString());
			Mensaje.mensajeError("Error en Reporte");
		}
		return fichero;
		}
	}

}

