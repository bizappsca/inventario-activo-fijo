package controlador.reporte;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.json.JSONException;
import org.json.JSONObject;


@WebServlet("/Reportero")
public class Reportero extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Reportero() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		
		CEquipo equipo = new CEquipo();
		CEtiquetas etiqueta = new CEtiquetas();
		CAuditoria auditoria = new CAuditoria();
		
		ServletOutputStream out;
		String par1 = request.getParameter("valor");
		Long par2 = (long) 0;
		String par21 = request.getParameter("valor1");
		if (par21 != null)
			par2 = Long.parseLong(par21);
		Long par3 = (long) 0;
		String par31 = request.getParameter("valor2");
		if (par31 != null)
			par3 = Long.parseLong(par31);
		String tipo = request.getParameter("valor3");

		// Reporte Tipo Consulta

		byte[] fichero = null;
		switch (par1) {
		case "1":
			try {
				fichero = equipo.reporteEquipo(par2, par3, tipo);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "2":
			try {
				fichero = etiqueta.reporteEtiqueta(par2, par3, tipo);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "3":
			try {
				fichero = auditoria.reporteAuditoria(par2, par3, tipo);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		if (tipo != null) {
			if (tipo.equals("EXCEL")) {
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition",
						"inline; filename=Reporte.xlsx");
			} else {
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition",
						"inline; filename=Reporte.pdf");
			}
		} else {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"inline; filename=Reporte.pdf");
		}
		response.setHeader("Cache-Control", "max-age=30");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentLength(fichero.length);
		out = response.getOutputStream();
		out.write(fichero, 0, fichero.length);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


}
