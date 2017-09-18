package generalities;

import data.Usuario;
import exceptions.AdministradorNoExisteException;
import exceptions.CorreoNoValidoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Carlos
 * @version 1.0
 * @created 28-may-2015 11:10:01 p.m.
 */
public abstract class Utilidades {

    public static void existeArchivo(String nomArchivo) throws AdministradorNoExisteException {
        File archivo = new File(nomArchivo);
        if (!archivo.exists()) {
            throw new AdministradorNoExisteException("No se encuentra ningún administrador del sistema y por tanto debe registrarse.");
        }
    }

    public static void copiarArchivo(File origen, String dest) throws IOException {

        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(origen);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int longitud;
            while ((longitud = is.read(buffer)) > 0) {
                os.write(buffer, 0, longitud);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static String leerLineaArchivo(File archivo) throws FileNotFoundException, IOException {
        BufferedReader br = null;
        String line = "";
        br = new BufferedReader(new FileReader(archivo));
        line = br.readLine();
        return line;
    }

    public static ArrayList<String> leerArchivo(File archivo) throws FileNotFoundException, IOException {
        ArrayList<String> txtArchivo = new ArrayList<String>();
        BufferedReader br = null;
        String line = "";
        br = new BufferedReader(new FileReader(archivo));
        while ((line = br.readLine()) != null) {
            txtArchivo.add(new String(line.getBytes("ISO-8859-1")));
        }
        return txtArchivo;
    }

    public static void enviarCorreo(String para, String asunto, String texto, String ubicacionArchivo, String nombreArchivo) throws MessagingException {

        // Obtenemos las propiedades del sistema
        Properties props = new Properties();
        try {
            InputStream is = new FileInputStream("ConfCorreo.properties");
            props.load(is);
            // La dirección de la cuenta de envío (from)
            final String de = props.getProperty("usuario");
            final String pass = props.getProperty("pass");
            Session sesion = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(de, pass);
                }
            });
            // Creamos un objeto mensaje tipo MimeMessage por defecto.
            MimeMessage mensaje = new MimeMessage(sesion);
            // Asignamos el â€œde o fromâ€ al header del correo.
            mensaje.setFrom(new InternetAddress(de));
            // Asignamos el â€œpara o toâ€ al header del correo.
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            // Asignamos el asunto
            mensaje.setSubject(asunto);
            // Creamos un cuerpo del correo con ayuda de la clase BodyPart
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(texto);
            // Creamos un multipart al correo
            Multipart multiparte = new MimeMultipart();
            //Para el adjunto
            MimeBodyPart attachmentBodyPart = null;
            if (ubicacionArchivo != null) {
                attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(ubicacionArchivo);
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(nombreArchivo);
            }

            // Agregamos el texto al cuerpo del correo multiparte
            multiparte.addBodyPart(textBodyPart);
            if(ubicacionArchivo != null)
            multiparte.addBodyPart(attachmentBodyPart);
            // Asignamos al mensaje todas las partes que creamos anteriormente
            mensaje.setContent(multiparte);
            // Enviamos el correo
            Transport.send(mensaje);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Archivo de configuración de correo no encontrado. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error leyendo el archivo de configuración de correo. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void enviarCorreo(Usuario usuario) throws MessagingException {
        enviarCorreo(usuario.getCorreo(), "Información - Aplicativo Gestión Académica DPTOIEE", "No elimine este mensaje. \n\n Los datos de acceso al aplicativo de Gestión Académica del Departamento de Ingeniería Eléctrica y Electrónica son: \n \n Usuario: " + usuario.getNomUsuario() + "\n Contraseña: " + usuario.getPassword().toString() + "\n \n Se recomienda cambiar su contraseña en el aplicativo: Menú Opciones -> Cambiar contraseña", null, null);
    }

    public static void validarCorreo(String correo) throws CorreoNoValidoException {
        if (!correo.contains("@") || !correo.contains(".")) {
            throw new CorreoNoValidoException("El formato del correo no es válido.");
        }
    }

    public static void exportar(JTable tabla, String rutaArchivo) throws IOException {
        int numeroColumnas = tabla.getColumnCount();
        int numeroFilas = tabla.getModel().getRowCount();
        FileWriter escritor;
        escritor = new FileWriter(rutaArchivo);
        for (int i = 0; i < numeroColumnas; i++) {
            escritor.write(tabla.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString());
            escritor.write(i != numeroColumnas - 1 ? ";" : "\n");
        }
        for (int i = 0; i < numeroFilas; i++) {
            for (int j = 0; j < numeroColumnas; j++) {
                escritor.write(tabla.getValueAt(i, j).toString());
                escritor.write(j != numeroColumnas - 1 ? ";" : "\n");
            }
        }
        escritor.close();
    }

    public static File crearCarpeta(String ruta, String nombreCarpeta) {
        File carpeta = new File(ruta + "/" + nombreCarpeta);
        if (carpeta.exists()) {
            String[] nomArchivos = carpeta.list();
            for (String nomArchivo : nomArchivos) {
                File archivo = new File(carpeta.getPath(), nomArchivo);
                archivo.delete();
            }
            carpeta.delete();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        File nuevaCarpeta = new File(ruta + "/" + nombreCarpeta);
        try {
            nuevaCarpeta.mkdir();
        } catch (SecurityException se) {
            JOptionPane.showMessageDialog(null, "No fue posible crear la carpeta " + nombreCarpeta + " " + se, "Error", JOptionPane.ERROR_MESSAGE);
            nuevaCarpeta = new File(ruta);
        }
        return nuevaCarpeta;
    }

    public static String mostrarVentanaArchivo(String titulo, int visualizacion, FileNameExtensionFilter filter, String nombreArchivo) {
        JFileChooser selectorArchivo = new JFileChooser();
        selectorArchivo.setDialogTitle(titulo);
        selectorArchivo.setFileSelectionMode(visualizacion);
        selectorArchivo.setMultiSelectionEnabled(false);
        selectorArchivo.setFileFilter(filter);
        if (nombreArchivo != null) {
            selectorArchivo.setSelectedFile(new File(nombreArchivo));
        }
        int guardar = selectorArchivo.showSaveDialog(null);
        if (guardar == JFileChooser.APPROVE_OPTION) {
            if (visualizacion == JFileChooser.DIRECTORIES_ONLY) {
                File carpeta = new File(selectorArchivo.getSelectedFile().toString());
                if (!carpeta.exists()) {
                    carpeta.mkdir();
                }
            }
            return selectorArchivo.getSelectedFile().toString();
        }
        return "";
    }

    public static void escribirArchivo(String nombreArchivo, Object objetoAGuardar) {
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(nombreArchivo);
            ObjectOutputStream Oout = new ObjectOutputStream(fOut);
            Oout.writeObject(objetoAGuardar);
            Oout.close();
            fOut.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error FileNotFoundException " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error IOException " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (fOut != null) {
                    fOut.close();
                }
            } catch (IOException ex) {
  
            }
        }
    }

    public static Object leerArchivo(String ubicacionArchivo) {
        FileInputStream fInpt = null;
        Object objetoLeido = null;
        try {
            fInpt = new FileInputStream(ubicacionArchivo);
            ObjectInputStream oInpt = new ObjectInputStream(fInpt);
            objetoLeido = oInpt.readObject();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {

        } finally {
            try {
                if (fInpt != null) {
                    fInpt.close();
                }
            } catch (IOException ex) {

            }
        }
        return objetoLeido;
    }
}
