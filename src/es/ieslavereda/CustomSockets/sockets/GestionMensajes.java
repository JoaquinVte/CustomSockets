package es.ieslavereda.CustomSockets.sockets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona los mensajes que se reciben en el servidor.
 * 
 * @author joaalsai
 *
 */
public class GestionMensajes {

	public MensajeSocket processInput(MensajeSocket m) {

		MensajeSocket mContestacion = null;
		String login = m.getLogin();

		switch (m.getTipoMensaje()) {

		case (MensajeSocket.SOLICITAR_DOWNLOAD_CONF):

			System.out.println("Recibida solicitud de archivo dhcp por " + login);

			if (!usuarioValido(login)) {
				// Comprobamos si el usuario se ha logado
				mContestacion = new MensajeSocket("El usuario o password no son validos.",
						MensajeSocket.USUARIO_O_PASSWD_NO_VALIDO);

			} else if (!usuarioValido(login)) {
				// Comprobamos si el usuario pertenece al grupo con privilegios
				mContestacion = new MensajeSocket("El usuario no tiene los privilegios necesarios.",
						MensajeSocket.USUARIO_SIN_PRIVILEGIOS);

			} else {
				// Enviamos datos si el usuario se loga y tiene permisos
				mContestacion = new MensajeSocket(leerFicheroDHCP(), MensajeSocket.ENVIO_DHCP_CONF);

			}
			break;

		case (MensajeSocket.SOLICITAR_RESTART_DHCP):
			System.out.println("Recibida solicitud de reinicio del dhcp por " + login);

			if (!usuarioValido(login)) {
				// Comprobamos si el usuario se ha logado
				mContestacion = new MensajeSocket("El usuario o password no son validos.",
						MensajeSocket.USUARIO_O_PASSWD_NO_VALIDO);

			} else if (!usuarioValido(login)) {
				// Comprobamos si el usuario pertenece al grupo con privilegios
				mContestacion = new MensajeSocket("El usuario no tiene los privilegios necesarios.",
						MensajeSocket.USUARIO_SIN_PRIVILEGIOS);

			} else {
				// Enviamos datos del reinicio si el usuario se loga y tiene
				// permisos
				Bash bash = new Bash();
				bash.ejecutarComandoSistema("/etc/init.d/isc-dhcp-server restart");
				mContestacion = new MensajeSocket("Comando:\n" + bash.getCommand() + "\n" + "Salida:\n"
						+ bash.getStreamSalida() + "\n" + "Error:\n"
						+ ((bash.getStreamError().equals("")) ? "Ninguno" : bash.getStreamError()) + "\n",
						MensajeSocket.ENVIO_SALIDA_RESTART_DHCP);
			}
			break;
		case (MensajeSocket.SOLICITAR_DHCP_STATUS):
			System.out.println("Recibida solicitud de estado del dhcp por " + login);

			if (!usuarioValido(login)) {
				// Comprobamos si el usuario se ha logado
				mContestacion = new MensajeSocket("El usuario o password no son validos.",
						MensajeSocket.USUARIO_O_PASSWD_NO_VALIDO);

			} else if (!usuarioValido(login)) {
				// Comprobamos si el usuario pertenece al grupo con privilegios
				mContestacion = new MensajeSocket("El usuario no tiene los privilegios necesarios.",
						MensajeSocket.USUARIO_SIN_PRIVILEGIOS);

			} else {
				// Enviamos datos del reinicio si el usuario se loga y tiene
				// permisos
				Bash bash = new Bash();
				bash.ejecutarComandoSistema("/etc/init.d/isc-dhcp-server status");
				mContestacion = new MensajeSocket("Comando:\n" + bash.getCommand() + "\n" + "Salida:\n"
						+ bash.getStreamSalida() + "\n" + "Error:\n"
						+ ((bash.getStreamError().equals("")) ? "Ninguno" : bash.getStreamError()) + "\n",
						MensajeSocket.ENVIO_DHCP_STATUS);
			}

			break;
		case (MensajeSocket.SOLICITAR_JOURNALCTL):
			System.out.println("Recibida solicitud de journal -xe por " + login);

			if (!usuarioValido(login)) {
				// Comprobamos si el usuario se ha logado
				mContestacion = new MensajeSocket("El usuario o password no son validos.",
						MensajeSocket.USUARIO_O_PASSWD_NO_VALIDO);

			} else if (!usuarioValido(login)) {
				// Comprobamos si el usuario pertenece al grupo con privilegios
				mContestacion = new MensajeSocket("El usuario no tiene los privilegios necesarios.",
						MensajeSocket.USUARIO_SIN_PRIVILEGIOS);

			} else {
				// Enviamos datos de journal si el usuario se loga y tiene
				// permisos
				Bash bash = new Bash();
				List<String> commands = new ArrayList<String>();

				commands.add("/bin/journalctl");
				commands.add("-xe");
				commands.add("--no-pager");

				bash.ejecutarComandoSistema(commands);

				mContestacion = new MensajeSocket(
						"Comando:\n" + commands.toString() + "\n" + "Salida:\n" + bash.getStreamSalida() + "\n",
						MensajeSocket.ENVIO_JOURNALCTL);
			}

			break;

		case (MensajeSocket.SOLICITAR_UPLOAD_CONF):
			System.out.println("Recibida solicitud de journal -xe por " + login);

			if (!usuarioValido(login)) {
				// Comprobamos si el usuario se ha logado
				mContestacion = new MensajeSocket("El usuario o password no son validos.",
						MensajeSocket.USUARIO_O_PASSWD_NO_VALIDO);

			} else if (!usuarioValido(login)) {
				// Comprobamos si el usuario pertenece al grupo con privilegios
				mContestacion = new MensajeSocket("El usuario no tiene los privilegios necesarios.",
						MensajeSocket.USUARIO_SIN_PRIVILEGIOS);

			} else {
				// Enviamos la salida de la grabacion si el usuario se loga y
				// tiene
				mContestacion = new MensajeSocket(grabarFicheroDHCP(m.getContenido()),
						MensajeSocket.ENVIO_SALIDA_UPLOAD);
			}

			break;
		case (MensajeSocket.SOLICITAR_CIERRE):
			mContestacion = new MensajeSocket("Solicitado el cierre. Aceptando el cierre de la conexion",
					MensajeSocket.ACEPTADO_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		case (MensajeSocket.ENVIO_DHCP_CONF):
			mContestacion = new MensajeSocket("Recibido archivo dhcp.", MensajeSocket.SOLICITAR_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		case (MensajeSocket.ENVIO_SALIDA_RESTART_DHCP):
			mContestacion = new MensajeSocket("Recibido estado dhcp.", MensajeSocket.SOLICITAR_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		case (MensajeSocket.ENVIO_SALIDA_UPLOAD):
			mContestacion = new MensajeSocket("Recibida salida de upload.", MensajeSocket.SOLICITAR_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		case (MensajeSocket.ENVIO_DHCP_STATUS):
			mContestacion = new MensajeSocket("Recibido estado dhcp", MensajeSocket.SOLICITAR_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		case (MensajeSocket.ENVIO_JOURNALCTL):
			mContestacion = new MensajeSocket("Recibida la salida de journalctl -xe", MensajeSocket.SOLICITAR_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		case (MensajeSocket.ENVIO_DATOS_USUARIO):
			mContestacion = new MensajeSocket("Recibido mensaje de datos de profesor", MensajeSocket.SOLICITAR_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		case (MensajeSocket.USUARIO_O_PASSWD_NO_VALIDO):
			mContestacion = new MensajeSocket("Usuario o Password incorrecto. Solicitando el cierre de la conexion",
					MensajeSocket.SOLICITAR_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		case (MensajeSocket.ACEPTADO_CIERRE):
			mContestacion = new MensajeSocket("Aceptando el cierre de la conexion", MensajeSocket.ACEPTADO_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		case (MensajeSocket.USUARIO_SIN_PRIVILEGIOS):
			mContestacion = new MensajeSocket(
					"Recibido que el usuario no esta en el grupo con privilegios. Solicitando el cierre de sesion",
					MensajeSocket.SOLICITAR_CIERRE);
			System.out.println(mContestacion.getContenido());
			break;
		}

		return mContestacion;
	}

	private String grabarFicheroDHCP(String contenido) {
		PrintWriter pw = null;

		try {

			pw = new PrintWriter(new FileWriter("/etc/dhcp/dhcpd.conf"));

			pw.write(contenido);
		} catch (IOException io) {
			io.printStackTrace();
			return "Se ha producido un error.";
		} finally {
			pw.close();
		}
		return "Grabado con exito";
	}

	private String leerFicheroDHCP() {
		String salida = "";

		BufferedReader in = null;
		String fila;

		try {
			in = new BufferedReader(new FileReader("/etc/dhcp/dhcpd.conf"));

			fila = in.readLine();

			while (fila != null) {
				salida += fila + "\n";
				fila = in.readLine();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return salida;
	}

	/**
	 * Valida al usario mediante LDAP. Para este proyecto siempre devuelve true
	 * 
	 * @param login  Login del usuario
	 * @param passwd Password del usuario
	 * @return Siempre devuelve true
	 */
	public boolean usuarioValido(String login, String passwd) {

		boolean esValido = true;

		// Descomentar si queremos comprobar con LDAP
//		LDAP l = new LDAP();
//		return l.autenticacionLDAP(login, passwd);

		return esValido;
	}

	/**
	 * Clase destinada a saber si el usuario tiene los permisos necesarios para
	 * realizar la accion mediante LDAP. Para este proyecto siempre devuelve true
	 * 
	 * @param login login del usuario
	 * @return Siempre devuelve true
	 */
	public boolean usuarioValido(String login) {

		boolean esValido = true;

		// Descomentar si queremos comprobar con LDAP
//		LDAP l = new LDAP();
//		ConfiguracionSegura conf = new ConfiguracionSegura();
//		esValido = l.search("cn=" + conf.getGroupOtrs() + ",ou=Groups", "memberUid=" + login)
//				|| l.search("cn=" + conf.getGroupWithAllRights() + ",ou=Groups", "memberUid=" + login);

		return esValido;
	}
}
