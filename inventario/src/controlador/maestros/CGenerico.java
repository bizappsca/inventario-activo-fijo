package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*import modelo.inventario.F00021;
 import modelo.inventario.F00021PK;
 import modelo.maestros.Paciente;*/
import modelo.seguridad.Usuario;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;

import security.servicio.SArbol;
import security.servicio.SGrupo;
import security.servicio.SUsuarioSeguridad;

import servicio.maestros.SDepartamento;
import servicio.maestros.SEquipo;
import servicio.maestros.SEquipoPrograma;
import servicio.maestros.SProgramas;
import servicio.maestros.STipo;
import servicio.maestros.SUbicacion;
import servicio.maestros.SUsuarioEquipo;
/*import servicio.control.SControlConsulta;
 import servicio.control.SControlOrden;
 import servicio.inventario.SF00021;
 import servicio.inventario.SF4101;
 import servicio.inventario.SF41021;
 import servicio.inventario.SF4105;
 import servicio.inventario.SF4211;
 import servicio.maestros.SAccidente;
 import servicio.maestros.SAntecedente;
 import servicio.maestros.SAntecedenteTipo;
 import servicio.maestros.SCargo;
 import servicio.maestros.SCategoriaDiagnostico;
 import servicio.maestros.SCategoriaMedicina;
 import servicio.maestros.SCiudad;
 import servicio.maestros.SClasificacionDiagnostico;
 import servicio.maestros.SConsultorio;
 import servicio.maestros.SDiagnostico;
 import servicio.maestros.SEmpresa;
 import servicio.maestros.SEmpresaNomina;
 import servicio.maestros.SEspecialidad;
 import servicio.maestros.SEspecialista;
 import servicio.maestros.SEstado;
 import servicio.maestros.SEstadoCivil;
 import servicio.maestros.SExamen;
 import servicio.maestros.SFamiliar;
 import servicio.maestros.SIntervencion;
 import servicio.maestros.SLaboratorio;
 import servicio.maestros.SMedicina;
 import servicio.maestros.SMedicinaPresentacionUnidad;
 import servicio.maestros.SMotivoCita;
 import servicio.maestros.SNomina;
 import servicio.maestros.SPaciente;
 import servicio.maestros.SPacienteAntecedente;
 import servicio.maestros.SPais;
 import servicio.maestros.SParteCuerpo;
 import servicio.maestros.SPeriodo;
 import servicio.maestros.SPeriodoPaciente;
 import servicio.maestros.SPresentacionComercial;
 import servicio.maestros.SPresentacionMedicina;
 import servicio.maestros.SProveedor;
 import servicio.maestros.SProveedorExamen;
 import servicio.maestros.SProveedorServicio;
 import servicio.maestros.SRecipe;
 import servicio.maestros.SServicioExterno;
 import servicio.maestros.SUnidadMedicina;
 import servicio.maestros.SVacuna;*/
import servicio.seguridad.SUsuario;
/*import servicio.sha.SArea;
 import servicio.sha.SClasificacionAccidente;
 import servicio.sha.SCondicion;
 import servicio.sha.SGrupoInspectores;
 import servicio.sha.SHorasHombre;
 import servicio.sha.SInforme;
 import servicio.sha.SPlanAccion;
 import servicio.social.SComposicionFamiliar;
 import servicio.social.SFicha;
 import servicio.social.SVisitaSocial;
 import servicio.transacciones.SCita;
 import servicio.transacciones.SConsulta;
 import servicio.transacciones.SConsultaDiagnostico;
 import servicio.transacciones.SConsultaEspecialista;
 import servicio.transacciones.SConsultaExamen;
 import servicio.transacciones.SConsultaMedicina;
 import servicio.transacciones.SConsultaParteCuerpo;
 import servicio.transacciones.SConsultaServicioExterno;
 import servicio.transacciones.SF4111;
 import servicio.transacciones.SHistoria;
 import servicio.transacciones.SHistoriaAccidente;
 import servicio.transacciones.SHistoriaIntervencion;
 import servicio.transacciones.SHistoriaVacuna;
 import servicio.transacciones.SOrden;
 import servicio.transacciones.SOrdenEspecialista;
 import servicio.transacciones.SOrdenExamen;
 import servicio.transacciones.SOrdenMedicina;
 import servicio.transacciones.SOrdenServicioExterno;
 import servicio.transacciones.SPacienteMedicina;*/
import componente.Mensaje;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class CGenerico extends SelectorComposer<Component> {

	private static final long serialVersionUID = -2264423023637489596L;

	@WireVariable("SArbol")
	protected SArbol servicioArbol;
	@WireVariable("SGrupo")
	protected SGrupo servicioGrupo;
	@WireVariable("SUsuarioSeguridad")
	protected SUsuarioSeguridad servicioUsuarioSeguridad;


	@WireVariable("SUsuario")
	protected SUsuario servicioUsuario;
	

	// Servicios del inventario
	@WireVariable("SUsuarioEquipo")
	protected SUsuarioEquipo servicioUsuarioEquipo;
	@WireVariable("SEquipo")
	protected SEquipo servicioEquipo;
	@WireVariable("STipo")
	protected STipo servicioTipo;
	@WireVariable("SUbicacion")
	protected SUbicacion servicioUbicacion;
	@WireVariable("SDepartamento")
	protected SDepartamento servicioDepartamento;
	@WireVariable("SProgramas")
	protected SProgramas servicioProgramas;
	@WireVariable("SEquipoPrograma")
	protected SEquipoPrograma servicioEquipoPrograma;

	public String titulo;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/META-INF/ConfiguracionAplicacion.xml");
	public Mensaje msj = new Mensaje();
	public Tabbox tabBox;
	public Include contenido;
	public Tab tab;
	protected DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	protected DateFormat formatoHorasHombre = new SimpleDateFormat("MM/yyyy");
	protected DateFormat formatoReporte = new SimpleDateFormat("dd-MM-yyyy");
	protected DateFormat formatoYear = new SimpleDateFormat("yyyy");
	public List<Tab> tabs = new ArrayList<Tab>();
	protected DateFormat df = new SimpleDateFormat("HH:mm:ss");
	protected DateFormat formatoImportar = new SimpleDateFormat("yyyy-MM-dd");
	public java.util.Date fecha = new Date();
	public String cod = formatoYear.format(fecha);
	public Calendar calendario2 = Calendar.getInstance();
	public Calendar calendario = Calendar.getInstance();
	public String horaAuditoria = String.valueOf(calendario
			.get(Calendar.HOUR_OF_DAY))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.MINUTE))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.SECOND));

	public Timestamp fechaHora = new Timestamp(fecha.getTime());
	public static double id = 0;
	public static boolean nextNumber = true;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		inicializar();
	}

	public Timestamp metodoFecha() {
		fecha = new Date();
		return fechaHora = new Timestamp(fecha.getTime());
	}

	public String metodoHora() {
		fecha = new Date();
		calendario.setTime(fecha);
		return String.valueOf(calendario.get(Calendar.HOUR_OF_DAY)) + ":"
				+ String.valueOf(calendario.get(Calendar.MINUTE)) + ":"
				+ String.valueOf(calendario.get(Calendar.SECOND));
	}

	public abstract void inicializar() throws IOException;

	public void cerrarVentana(Div div, String id, List<Tab> tabs2) {
		div.setVisible(false);
		tabs = tabs2;
		for (int i = 0; i < tabs.size(); i++) {
			if (tabs.get(i).getLabel().equals(id)) {
				if (i == (tabs.size() - 1) && tabs.size() > 1) {
					tabs.get(i - 1).setSelected(true);
				}
				tabs.get(i).onClose();
				tabs.remove(i);
			}
		}
	}

	/*
	 * public static SF41021 getServicioF41021() { return
	 * applicationContext.getBean(SF41021.class); }
	 * 
	 * public static SF4101 getServicioF4101() { return
	 * applicationContext.getBean(SF4101.class); }
	 * 
	 * public static SF4111 getServicioF4111() { return
	 * applicationContext.getBean(SF4111.class); }
	 * 
	 * public static SConsulta getServicioConsulta() { return
	 * applicationContext.getBean(SConsulta.class); }
	 * 
	 * public static SConsultaParteCuerpo getServicioConsultaParteCuerpo() {
	 * return applicationContext.getBean(SConsultaParteCuerpo.class); }
	 */

	public static SUsuario getServicioUsuario() {
		return applicationContext.getBean(SUsuario.class);
	}

	/*
	 * public static SConsultaEspecialista getServicioConsultaEspecialista() {
	 * return applicationContext.getBean(SConsultaEspecialista.class); }
	 * 
	 * public static SEspecialista getServicioEspecialista() { return
	 * applicationContext.getBean(SEspecialista.class); }
	 * 
	 * public static SConsultaServicioExterno
	 * getServicioConsultaServicioExterno() { return
	 * applicationContext.getBean(SConsultaServicioExterno.class); }
	 * 
	 * public static SConsultaExamen getServicioConsultaExamen() { return
	 * applicationContext.getBean(SConsultaExamen.class); }
	 * 
	 * public static SArea getServicioArea() { return
	 * applicationContext.getBean(SArea.class); }
	 * 
	 * public static SCita getServicioCita() { return
	 * applicationContext.getBean(SCita.class); }
	 * 
	 * public static SHistoria getServicioHistoria() { return
	 * applicationContext.getBean(SHistoria.class); }
	 * 
	 * public static SVisitaSocial getServicioVisita() { return
	 * applicationContext.getBean(SVisitaSocial.class); }
	 * 
	 * public static SFicha getServicioFicha() { return
	 * applicationContext.getBean(SFicha.class); }
	 * 
	 * public static SNomina getServicioNomina() { return
	 * applicationContext.getBean(SNomina.class); }
	 * 
	 * public static SCategoriaDiagnostico getServicioCategoria() { return
	 * applicationContext.getBean(SCategoriaDiagnostico.class); }
	 * 
	 * public static SClasificacionDiagnostico getServicioClasificacion() {
	 * return applicationContext.getBean(SClasificacionDiagnostico.class); }
	 * 
	 * public static SCargo getServicioCargo() { return
	 * applicationContext.getBean(SCargo.class); }
	 * 
	 * public static SEmpresa getServicioEmpresa() { return
	 * applicationContext.getBean(SEmpresa.class); }
	 * 
	 * public static SInforme getServicioInforme() { return
	 * applicationContext.getBean(SInforme.class); }
	 * 
	 * public static SConsultaDiagnostico getServicioConsultaDiagnostico() {
	 * return applicationContext.getBean(SConsultaDiagnostico.class); }
	 * 
	 * public static SPaciente getServicioPaciente() { return
	 * applicationContext.getBean(SPaciente.class); } public static SFamiliar
	 * getServicioFamiliar() { return
	 * applicationContext.getBean(SFamiliar.class); }
	 * 
	 * public static SCondicion getServicioCondicion() { return
	 * applicationContext.getBean(SCondicion.class); }
	 * 
	 * public static SPacienteAntecedente getServicioPacienteAntecedente() {
	 * return applicationContext.getBean(SPacienteAntecedente.class); }
	 * 
	 * public static SPacienteMedicina getServicioPacienteMedicina() { return
	 * applicationContext.getBean(SPacienteMedicina.class); }
	 * 
	 * public static SPeriodoPaciente getServicioPeriodoPaciente() { return
	 * applicationContext.getBean(SPeriodoPaciente.class); }
	 * 
	 * public static SProveedor getServicioProveedor() { return
	 * applicationContext.getBean(SProveedor.class); }
	 * 
	 * public static SConsultaMedicina getServicioConsultaMedicina() { return
	 * applicationContext.getBean(SConsultaMedicina.class); }
	 * 
	 * public static SOrden getServicioOrden() { return
	 * applicationContext.getBean(SOrden.class); }
	 * 
	 * public static SOrdenMedicina getServicioOrdenMedicina() { return
	 * applicationContext.getBean(SOrdenMedicina.class); }
	 * 
	 * public static SOrdenEspecialista getServicioOrdenEspecialista() { return
	 * applicationContext.getBean(SOrdenEspecialista.class); }
	 * 
	 * public static SControlOrden getServicioControlOrden() { return
	 * applicationContext.getBean(SControlOrden.class); }
	 * 
	 * public static SControlConsulta getServicioControlConsulta() { return
	 * applicationContext.getBean(SControlConsulta.class); }
	 * 
	 * public static SOrdenServicioExterno getServicioOrdenServicioExterno() {
	 * return applicationContext.getBean(SOrdenServicioExterno.class); }
	 * 
	 * public static SOrdenExamen getServicioOrdenExamen() { return
	 * applicationContext.getBean(SOrdenExamen.class); }
	 */
	
	//get Servicios inventario
	public static SEquipo getServicioEquipo() {
		return applicationContext.getBean(SEquipo.class);
	}
	public static STipo getServicioTipo() {
		return applicationContext.getBean(STipo.class);
	}
	public static SDepartamento getServicioDepartamento() {
		return applicationContext.getBean(SDepartamento.class);
	}
	

	public String nombreUsuarioSesion() {
		Authentication sesion = SecurityContextHolder.getContext()
				.getAuthentication();
		return sesion.getName();
	}

	public Usuario usuarioSesion(String valor) {
		return servicioUsuario.buscarUsuarioPorNombre(valor);
	}

	/* Metodo que permite enviar un correo electronico a cualquier destinatario */
	public boolean enviarEmailNotificacion(String correo, String mensajes) {
		try {

			String cc = "NOTIFICACION SIMS";
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "172.23.20.66");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "2525");
			props.setProperty("mail.smtp.auth", "true");

			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			String remitente = "cdusa@dusa.com.ve";
			String destino = correo;
			String mensaje = mensajes;
			String destinos[] = destino.split(",");
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(remitente));

			Address[] receptores = new Address[destinos.length];
			int j = 0;
			while (j < destinos.length) {
				receptores[j] = new InternetAddress(destinos[j]);
				j++;
			}

			message.addRecipients(Message.RecipientType.TO, receptores);
			message.setSubject(cc);
			message.setText(mensaje);

			Transport.send(message);

			return true;
		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int calcularEdad(Date birthDate) {
		Calendar birth = new GregorianCalendar();
		Calendar today = new GregorianCalendar();
		int age = 0;
		int factor = 0;
		Date currentDate = new Date();
		birth.setTime(birthDate);
		today.setTime(currentDate);
		if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
			factor = -1;
		}
		age = (today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)) + factor;
		if (age == -1)
			age = 0;
		if (today.get(Calendar.YEAR) == birth.get(Calendar.YEAR))
			// age = today.get(Calendar.MONTH) - birth.get(Calendar.MONTH);
			age = 0;
		return age;
	}

	/*
	 * public void inhabilitarTrabajadorYTodosFamiliares(Paciente paciente) {
	 * List<Paciente> inactivos = new ArrayList<Paciente>();
	 * paciente.setEstatus(false); inactivos.add(paciente); if
	 * (paciente.isTrabajador()) { List<Paciente> carga =
	 * servicioPaciente.buscarParientes(paciente .getCedula()); for
	 * (Iterator<Paciente> iterator = carga.iterator(); iterator .hasNext();) {
	 * Paciente paciente2 = (Paciente) iterator.next(); if
	 * (!paciente.isMuerte()) { paciente2.setEstatus(false);
	 * inactivos.add(paciente2); } else { if
	 * (!paciente2.getParentescoFamiliar().equals("Hijo(a)")) {
	 * paciente2.setEstatus(false); inactivos.add(paciente2); } } } }
	 * servicioPaciente.guardarVarios(inactivos); }
	 */

	public BigDecimal transformarGregorianoAJulia(Date fecha) {
		String valor = "";

		calendario2 = new GregorianCalendar();
		calendario2.setTime(fecha);
		String dia = "";
		if (calendario2.get(Calendar.DAY_OF_YEAR) < 10)
			dia = "00";
		else {
			if (calendario2.get(Calendar.DAY_OF_YEAR) >= 10
					&& calendario2.get(Calendar.DAY_OF_YEAR) < 100)
				dia = "0";
		}
		if ((fecha.getYear() + 1900) < 2000)
			valor = "";
		else
			valor = "1";
		long al = Long.valueOf(valor
				+ String.valueOf(calendario2.get(Calendar.YEAR)).substring(2)
				+ dia + String.valueOf(calendario2.get(Calendar.DAY_OF_YEAR)));
		BigDecimal a = BigDecimal.valueOf(al);
		return a;
	}

	/*
	 * protected double nextNumber(String numero, String user) { synchronized
	 * (this) { while (!nextNumber) { try { wait(); } catch
	 * (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * } nextNumber = false; try { double numeroNext =
	 * servicioF00021.Numero(numero, user); if (numeroNext != 0) { id =
	 * numeroNext + 1; F00021 f021 = servicioF00021.buscar(numero, user);
	 * f021.setNln001(id); servicioF00021.guardar(f021); } else { id = 1; F00021
	 * f021 = new F00021(); F00021PK clave21 = new F00021PK();
	 * clave21.setNldct(user); clave21.setNlkco(numero);
	 * clave21.setNlctry((double) 0); clave21.setNlfy((double) 0);
	 * f021.setId(clave21); f021.setNln001(id); servicioF00021.guardar(f021); }
	 * } catch (Exception a) { nextNumber = true; a.printStackTrace(); return 0;
	 * } } synchronized (this) { nextNumber = true; notifyAll(); return id; } }
	 */

	public String diaSemanaString(Calendar calendar) {
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		String diaSemana = "";
		switch (dia) {
		case 2:
			diaSemana = "Lunes";
			break;
		case 3:
			diaSemana = "Martes";
			break;

		case 4:
			diaSemana = "Miercoles";
			break;
		case 5:
			diaSemana = "Jueves";
			break;
		case 6:
			diaSemana = "Viernes";
			break;
		case 7:
			diaSemana = "Sabado";
			break;
		case 1:
			diaSemana = "Domingo";
			break;
		}
		return diaSemana;
	}

	public List<String> obtenerPropiedades() {
		List<String> arreglo = new ArrayList<String>();
		DriverManagerDataSource ds = (DriverManagerDataSource) applicationContext
				.getBean("dataSource");
		arreglo.add(ds.getUsername());
		arreglo.add(ds.getPassword());
		arreglo.add(ds.getUrl());
		return arreglo;
	}

	class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("cdusa", "cartucho");
		}
	}

	public String damePath() {
		return Executions.getCurrent().getContextPath() + "/";
	}

	public String traerFecha2(Timestamp fecha) {
		String valor = "";
		if (fecha != null) {
			DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
			valor = String.valueOf(formatoFecha.format(fecha));
		}
		return valor;
	}

	public Date agregarDia(Date fecha) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		calendario.add(Calendar.DAY_OF_YEAR, +1);
		return fecha = calendario.getTime();
	}
}