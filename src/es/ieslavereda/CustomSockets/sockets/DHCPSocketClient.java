package es.ieslavereda.CustomSockets.sockets;

import java.awt.HeadlessException;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

/**
 * Clase para el envio de mensajes al servidor
 * 
 * @author joaalsai
 *
 */
public class DHCPSocketClient {

	private InetAddress ip;
	private int portNumber;

	/**
	 * Clase para comunicarse con el servidor
	 * 
	 * @param ip         IP del servidor donde esta corriendo DHCPManagerMultiServer
	 * @param portNumber Puerto por donde se escuchan las peticiones
	 */
	public DHCPSocketClient(InetAddress ip, int portNumber) {
		super();
		this.ip = ip;
		this.portNumber = portNumber;
	}

	/**
	 * Envio de mensajes. Vease MensajeSocket
	 * 
	 * @param ip          IP del servidor
	 * @param port        Puerto por donde escucha el servidor.
	 * @param mensaje     Mensaje a enviar
	 * @param tipoMensaje Tipo de mensaje. Vease MensajeSocket
	 * @param usuario     UID del usuario que lo envia
	 * @return Texto recibido del servidor.
	 * @throws Exception Lanzamiento de otro tipo de excepcion de usuario
	 */
	public static String send(InetAddress ip, int port, String mensaje, int tipoMensaje, String usuario)
			throws Exception {

		DHCPSocketClient sc = new DHCPSocketClient(ip, port);

		if (ip.isReachable(3000)) {

			MensajeSocket m = sc.sendMessage(new MensajeSocket(mensaje, tipoMensaje, usuario));

			if (m != null) {
				return m.getContenido();
			}

		} else {
			throw new IOException("El host " + ip.getHostAddress() + " no es alcanzable");
		}
		return null;
	}

	/**
	 * 
	 * @param mensaje     Mensaje a enviar
	 * @param tipoMensaje Tipo de mensaje. Vease MensajeSocket
	 * @param usuario     UID del usuario que lo envia
	 * @return Texto recibido del servidor.
	 * @throws ClassNotFoundException Lanzamiento de una excepcion si lo que se
	 *                                envia no es un MensajeSocket
	 * @throws Exception              Lanzamiento de otro tipo de excepcion de
	 *                                usuario
	 * @throws IOException            Lanzamiento de una excepcion principalmente si
	 *                                el host no es alcanzable
	 */
	public String send(String mensaje, int tipoMensaje, String usuario)
			throws HeadlessException, ClassNotFoundException, IOException, Exception {

		if (ip.isReachable(3000)) {

			MensajeSocket m = sendMessage(new MensajeSocket(mensaje, tipoMensaje, usuario));

			if (m != null) {
				return m.getContenido();
			}

		} else {
			throw new IOException("El host " + ip.getHostAddress() + " no es alcanzable");
		}
		return null;
	}

	private MensajeSocket sendMessage(MensajeSocket m) throws IOException, ClassNotFoundException, Exception {

		MensajeSocket respuesta = null;

		try (Socket socket = new Socket(ip, portNumber);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());)

		{
			System.out.println("Estableciendo conexion con: " + socket.getRemoteSocketAddress().toString());
			MensajeSocket mToServer, mFromServer;
			GestionMensajes gm = new GestionMensajes();

			oos.writeObject(m);

			boolean salir = false;

			while ((mFromServer = (MensajeSocket) ois.readObject()) != null) {

				int tipo = mFromServer.getTipoMensaje();
				boolean demasidoLargo = (tipo == MensajeSocket.ENVIO_DHCP_CONF
						|| tipo == MensajeSocket.ENVIO_JOURNALCTL);
				System.out.println("Server: "
						+ (demasidoLargo ? "Demasiados datos para mostrar .... datos omitidos" : mFromServer));

				// Tratamos el mensaje recibido del servidor
				switch (mFromServer.getTipoMensaje()) {
				case (MensajeSocket.ACEPTADO_CIERRE):
					salir = true;
					break;

				case (MensajeSocket.USUARIO_O_PASSWD_NO_VALIDO):
					throw new Exception("Usuario o password no valido");
				case (MensajeSocket.USUARIO_SIN_PRIVILEGIOS):
					throw new Exception("El usuario no tiene los privilegios necesarios.");
				case (MensajeSocket.ENVIO_DHCP_CONF):
				case (MensajeSocket.ENVIO_SALIDA_RESTART_DHCP):
				case (MensajeSocket.ENVIO_DHCP_STATUS):
				case (MensajeSocket.ENVIO_JOURNALCTL):
				case (MensajeSocket.ENVIO_SALIDA_UPLOAD):
				case (MensajeSocket.ENVIO_DATOS_USUARIO):
					respuesta = mFromServer;
					break;
				}

				mToServer = gm.processInput(mFromServer);

				if (mToServer != null) {
					System.out.println("Client: " + mToServer);
					oos.writeObject(mToServer);
				}
				if (salir)
					break;

			}
		}
		return respuesta;
	}
}
