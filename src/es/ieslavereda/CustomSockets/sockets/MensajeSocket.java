package es.ieslavereda.CustomSockets.sockets;

import java.io.Serializable;


/**
 * Mensajes utilizados en la comunicacion de sockets
 * 
 * 
 * Created on 5 mar. 2019
 * 
 * @author <a href="mailto:joaaslai@ieslavereda.es">Joaquin Vicente Alonso Saiz</a>
 * @version v1.0        
 *
 */
public class MensajeSocket implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #SOLICITAR_DOWNLOAD_CONF}
	 */
	public static final int SOLICITAR_DOWNLOAD_CONF = 0;
	/**
	 * Solicitar la subida del fichero dhcp.conf. Valor de la variable entera
	 * {@value #SOLICITAR_UPLOAD_CONF}
	 */
	public static final int SOLICITAR_UPLOAD_CONF = 1;
	/**
	 * Solicitar el reincio del servidor DHCP. Valor de la variable entera
	 * {@value #SOLICITAR_RESTART_DHCP}
	 */
	public static final int SOLICITAR_RESTART_DHCP = 2;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #SOLICITAR_DHCP_STATUS}
	 */
	public static final int SOLICITAR_DHCP_STATUS = 3;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #SOLICITAR_JOURNALCTL}
	 */
	public static final int SOLICITAR_JOURNALCTL = 4;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #SOLICITAR_DATOS_USUARIO}
	 */
	public static final int SOLICITAR_DATOS_USUARIO = 5;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #ENVIO_DHCP_CONF}
	 */
	public static final int ENVIO_DHCP_CONF = 6;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #ENVIO_DHCP_STATUS}
	 */
	public static final int ENVIO_DHCP_STATUS = 7;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #ENVIO_SALIDA_RESTART_DHCP}
	 */
	public static final int ENVIO_SALIDA_RESTART_DHCP = 8;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #ENVIO_JOURNALCTL}
	 */
	public static final int ENVIO_JOURNALCTL = 9;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #ENVIO_SALIDA_UPLOAD}
	 */
	public static final int ENVIO_SALIDA_UPLOAD = 10;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #ENVIO_DATOS_USUARIO}
	 */
	public static final int ENVIO_DATOS_USUARIO = 11;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #SOLICITAR_CIERRE}
	 */
	public static final int SOLICITAR_CIERRE = 12;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #ACEPTADO_CIERRE}
	 */
	public static final int ACEPTADO_CIERRE = 13;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #USUARIO_O_PASSWD_NO_VALIDO}
	 */
	public static final int USUARIO_O_PASSWD_NO_VALIDO = 14;
	/**
	 * Solicitar la descarga del fichero dhcp.conf. Valor de la variable entera
	 * {@value #USUARIO_SIN_PRIVILEGIOS}
	 */
	public static final int USUARIO_SIN_PRIVILEGIOS = 15;

	private String contenido;
	private int tipoMensaje;
	private String login;
	private String password;
	private Object adjunto;

	/**
	 * Construye un mensaje a partir de una breve descripcion y un tipo de
	 * mensaje
	 * 
	 * @param contenido
	 *            Texto descriptivo del tipo de mensaje
	 * @param tipoMensaje
	 *            Tipo de mensaje
	 *            <ul>
	 *            <li>{@link #SOLICITAR_DOWNLOAD_CONF} =
	 *            {@value #SOLICITAR_DOWNLOAD_CONF}</li>
	 *            <li>{@link #SOLICITAR_DHCP_STATUS} =
	 *            {@value #SOLICITAR_DHCP_STATUS}</li>
	 *            <li>{@link #SOLICITAR_RESTART_DHCP} =
	 *            {@value #SOLICITAR_RESTART_DHCP}</li>
	 *            <li>{@link #SOLICITAR_DHCP_STATUS} =
	 *            {@value #SOLICITAR_DHCP_STATUS}</li>
	 *            <li>{@link #SOLICITAR_JOURNALCTL} =
	 *            {@value #SOLICITAR_JOURNALCTL}</li>
	 *            <li>{@link #SOLICITAR_DATOS_USUARIO} =
	 *            {@value #SOLICITAR_DATOS_USUARIO}</li>
	 *            <li>{@link #ENVIO_DHCP_CONF} = {@value #ENVIO_DHCP_CONF}</li>
	 *            <li>{@link #ENVIO_DHCP_STATUS} =
	 *            {@value #ENVIO_DHCP_STATUS}</li>
	 *            <li>{@link #ENVIO_SALIDA_RESTART_DHCP} =
	 *            {@value #ENVIO_SALIDA_RESTART_DHCP}</li>
	 *            <li>{@link #ENVIO_JOURNALCTL} =
	 *            {@value #ENVIO_JOURNALCTL}</li>
	 *            <li>{@link #ENVIO_SALIDA_UPLOAD} =
	 *            {@value #ENVIO_SALIDA_UPLOAD}</li>
	 *            <li>{@link #ENVIO_DATOS_USUARIO} =
	 *            {@value #ENVIO_DATOS_USUARIO}</li>
	 *            <li>{@link #SOLICITAR_CIERRE} =
	 *            {@value #SOLICITAR_CIERRE}</li>
	 *            <li>{@link #ACEPTADO_CIERRE} = {@value #ACEPTADO_CIERRE}</li>
	 *            <li>{@link #USUARIO_O_PASSWD_NO_VALIDO} =
	 *            {@value #USUARIO_O_PASSWD_NO_VALIDO}</li>
	 *            <li>{@link #USUARIO_SIN_PRIVILEGIOS} =
	 *            {@value #USUARIO_SIN_PRIVILEGIOS}</li>
	 *            </ul>
	 */
	public MensajeSocket(String contenido, int tipoMensaje) {

		this.contenido = contenido;
		this.tipoMensaje = tipoMensaje;

	}

	/**
	 * Construye un mensaje a partir de una breve descripcion, un tipo de
	 * mensaje, los datos de usuario y password identificativos
	 * 
	 * @param login
	 *            login del usuario
	 * @param password
	 *            password del usuario
	 * @param contenido
	 *            Texto descriptivo del tipo de mensaje
	 * @param tipoMensaje
	 *            Tipo de mensaje
	 *            <ul>
	 *            <li>{@link #SOLICITAR_DOWNLOAD_CONF} =
	 *            {@value #SOLICITAR_DOWNLOAD_CONF}</li>
	 *            <li>{@link #SOLICITAR_DHCP_STATUS} =
	 *            {@value #SOLICITAR_DHCP_STATUS}</li>
	 *            <li>{@link #SOLICITAR_RESTART_DHCP} =
	 *            {@value #SOLICITAR_RESTART_DHCP}</li>
	 *            <li>{@link #SOLICITAR_DHCP_STATUS} =
	 *            {@value #SOLICITAR_DHCP_STATUS}</li>
	 *            <li>{@link #SOLICITAR_JOURNALCTL} =
	 *            {@value #SOLICITAR_JOURNALCTL}</li>
	 *            <li>{@link #SOLICITAR_DATOS_USUARIO} =
	 *            {@value #SOLICITAR_DATOS_USUARIO}</li>
	 *            <li>{@link #ENVIO_DHCP_CONF} = {@value #ENVIO_DHCP_CONF}</li>
	 *            <li>{@link #ENVIO_DHCP_STATUS} =
	 *            {@value #ENVIO_DHCP_STATUS}</li>
	 *            <li>{@link #ENVIO_SALIDA_RESTART_DHCP} =
	 *            {@value #ENVIO_SALIDA_RESTART_DHCP}</li>
	 *            <li>{@link #ENVIO_JOURNALCTL} =
	 *            {@value #ENVIO_JOURNALCTL}</li>
	 *            <li>{@link #ENVIO_SALIDA_UPLOAD} =
	 *            {@value #ENVIO_SALIDA_UPLOAD}</li>
	 *            <li>{@link #ENVIO_DATOS_USUARIO} =
	 *            {@value #ENVIO_DATOS_USUARIO}</li>
	 *            <li>{@link #SOLICITAR_CIERRE} =
	 *            {@value #SOLICITAR_CIERRE}</li>
	 *            <li>{@link #ACEPTADO_CIERRE} = {@value #ACEPTADO_CIERRE}</li>
	 *            <li>{@link #USUARIO_O_PASSWD_NO_VALIDO} =
	 *            {@value #USUARIO_O_PASSWD_NO_VALIDO}</li>
	 *            <li>{@link #USUARIO_SIN_PRIVILEGIOS} =
	 *            {@value #USUARIO_SIN_PRIVILEGIOS}</li>
	 *            </ul>
	 * 
	 */
	public MensajeSocket(String contenido, int tipoMensaje, String login) {

		this.contenido = contenido;
		this.tipoMensaje = tipoMensaje;
		this.login = login;

	}

	/**
	 * 
	 * @return Devuelve la breve descripcion del mensaje
	 */
	public String getContenido() {
		return contenido;
	}

	/**
	 * 
	 * @return Devuelve un tipo de mensaje. Este puede ser:
	 *         <ul>
	 *         <li>{@link #SOLICITAR_DOWNLOAD_CONF} =
	 *         {@value #SOLICITAR_DOWNLOAD_CONF}</li>
	 *         <li>{@link #SOLICITAR_DHCP_STATUS} =
	 *         {@value #SOLICITAR_DHCP_STATUS}</li>
	 *         <li>{@link #SOLICITAR_RESTART_DHCP} =
	 *         {@value #SOLICITAR_RESTART_DHCP}</li>
	 *         <li>{@link #SOLICITAR_DHCP_STATUS} =
	 *         {@value #SOLICITAR_DHCP_STATUS}</li>
	 *         <li>{@link #SOLICITAR_JOURNALCTL} =
	 *         {@value #SOLICITAR_JOURNALCTL}</li>
	 *         <li>{@link #SOLICITAR_DATOS_USUARIO} =
	 *         {@value #SOLICITAR_DATOS_USUARIO}</li>
	 *         <li>{@link #ENVIO_DHCP_CONF} = {@value #ENVIO_DHCP_CONF}</li>
	 *         <li>{@link #ENVIO_DHCP_STATUS} = {@value #ENVIO_DHCP_STATUS}</li>
	 *         <li>{@link #ENVIO_SALIDA_RESTART_DHCP} =
	 *         {@value #ENVIO_SALIDA_RESTART_DHCP}</li>
	 *         <li>{@link #ENVIO_JOURNALCTL} = {@value #ENVIO_JOURNALCTL}</li>
	 *         <li>{@link #ENVIO_SALIDA_UPLOAD} =
	 *         {@value #ENVIO_SALIDA_UPLOAD}</li>
	 *         <li>{@link #ENVIO_DATOS_USUARIO} =
	 *         {@value #ENVIO_DATOS_USUARIO}</li>
	 *         <li>{@link #SOLICITAR_CIERRE} = {@value #SOLICITAR_CIERRE}</li>
	 *         <li>{@link #ACEPTADO_CIERRE} = {@value #ACEPTADO_CIERRE}</li>
	 *         <li>{@link #USUARIO_O_PASSWD_NO_VALIDO} =
	 *         {@value #USUARIO_O_PASSWD_NO_VALIDO}</li>
	 *         <li>{@link #USUARIO_SIN_PRIVILEGIOS} =
	 *         {@value #USUARIO_SIN_PRIVILEGIOS}</li>
	 *         </ul>
	 */
	public int getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * @return el login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @return el password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return el objeto adjunto
	 */
	public Object getAdjunto() {
		return adjunto;
	}

	/**
	 * @param adjunto
	 *            el objeto adjunto a establecer
	 */
	public void setAdjunto(Object adjunto) {
		this.adjunto = adjunto;
	}

	@Override
	public String toString() {
		return "Mensaje [contenido=" + contenido + ", tipoMensaje=" + tipoMensaje + ", adjunto=" + adjunto + "]";
	}

}
