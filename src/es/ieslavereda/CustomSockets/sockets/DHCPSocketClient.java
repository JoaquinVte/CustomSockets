package es.ieslavereda.CustomSockets.sockets;

import java.awt.HeadlessException;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class DHCPSocketClient {

	private InetAddress ip;
	private int portNumber;

	public DHCPSocketClient(InetAddress ip, int portNumber) {
		super();
		this.ip = ip;
		this.portNumber = portNumber;
	}
	
	public static String send(InetAddress ip, int port, String mensaje, int tipoMensaje, String usuario) throws HeadlessException, ClassNotFoundException, IOException {
		
		DHCPSocketClient sc = new DHCPSocketClient(ip,port);
		
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

	public String send(String mensaje, int tipoMensaje, String usuario) throws HeadlessException, ClassNotFoundException, IOException {

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

	private MensajeSocket sendMessage(MensajeSocket m) throws IOException, HeadlessException, ClassNotFoundException {

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
					JOptionPane.showMessageDialog(null, "Usuario o password no valido", "Error",
							JOptionPane.ERROR_MESSAGE, null);
					break;
				case (MensajeSocket.USUARIO_SIN_PRIVILEGIOS):
					JOptionPane.showMessageDialog(null, "El usuario no tiene los privilegios necesarios.", "Error",
							JOptionPane.ERROR_MESSAGE, null);
					break;
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
