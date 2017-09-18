/*
 * To change null template, choose Tools | Templates
 * and open the template in the editor.
 */
package generalities;

import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class Errores {

    public static String MODIFICAR = "Modificar";
    public static String OCULTAR = "Ocultar";
    public static String ELIMINAR = "Eliminar";
    public static String CREAR = "Crear";
    

    public static void confirmarEnvioCorreo() {
        JOptionPane.showMessageDialog(null, "Un mensaje con los datos de ingreso ha sido enviado al correo electrónico proporcionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void errorEnvioCorreo(String ex) {
        JOptionPane.showMessageDialog(null, "El usuario fue creado o actualizado, aunque hubo un error enviando el correo electrónico con los datos de acceso. ", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorPasswordsDiferentes() {
        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden. Digítelas nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorArchivoUsuario(String ex) {
        JOptionPane.showMessageDialog(null, "Error en la creación del usuario. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorEntradaSalidaUsuario(String ex) {
        JOptionPane.showMessageDialog(null, "Error en la creación del usuario E/S. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorArchivoDocente(String ex) {
        JOptionPane.showMessageDialog(null, "Error en la creación del docente. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorEntradaSalidaDocente(String ex) {
        JOptionPane.showMessageDialog(null, "Error en la creación del docente E/S. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorCorreoUsuario() {
        JOptionPane.showMessageDialog(null, "No se encontró ningún usuario registrado con ese correo electrónico. Verifíquelo e intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorUsuario() {
        JOptionPane.showMessageDialog(null, "El usuario o la contraseña ingresada son incorrectos. Verifique los datos e intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorPassword() {
        JOptionPane.showMessageDialog(null, "La contraseña ingresada es incorrecta. Verifíquela e intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorCorreoVacio() {
        JOptionPane.showMessageDialog(null, "El campo de correo electrónico no puede estar vacío. Verifíquelo e intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorEliminarTipoUsuario() {
        JOptionPane.showMessageDialog(null, "No es posible eliminar este tipo de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorUsuarioYaExiste() {
        JOptionPane.showMessageDialog(null, "Ya existe un usuario registrado con este nombre de usuario. Intente con otro diferente.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void confirmacionUsuarioEliminado() {
        JOptionPane.showMessageDialog(null, "El usuario ha sido eliminado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void correoNoValido(String ex) {
        JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void nomUsuarioException(String ex) {
        JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorLongitudPassword(String ex) {
        JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
