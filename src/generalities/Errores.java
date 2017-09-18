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
        JOptionPane.showMessageDialog(null, "Un mensaje con los datos de ingreso ha sido enviado al correo electr�nico proporcionado.", "Informaci�n", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void errorEnvioCorreo(String ex) {
        JOptionPane.showMessageDialog(null, "El usuario fue creado o actualizado, aunque hubo un error enviando el correo electr�nico con los datos de acceso. ", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorPasswordsDiferentes() {
        JOptionPane.showMessageDialog(null, "Las contrase�as no coinciden. Dig�telas nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorArchivoUsuario(String ex) {
        JOptionPane.showMessageDialog(null, "Error en la creaci�n del usuario. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorEntradaSalidaUsuario(String ex) {
        JOptionPane.showMessageDialog(null, "Error en la creaci�n del usuario E/S. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorArchivoDocente(String ex) {
        JOptionPane.showMessageDialog(null, "Error en la creaci�n del docente. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorEntradaSalidaDocente(String ex) {
        JOptionPane.showMessageDialog(null, "Error en la creaci�n del docente E/S. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorCorreoUsuario() {
        JOptionPane.showMessageDialog(null, "No se encontr� ning�n usuario registrado con ese correo electr�nico. Verif�quelo e intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorUsuario() {
        JOptionPane.showMessageDialog(null, "El usuario o la contrase�a ingresada son incorrectos. Verifique los datos e intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorPassword() {
        JOptionPane.showMessageDialog(null, "La contrase�a ingresada es incorrecta. Verif�quela e intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorCorreoVacio() {
        JOptionPane.showMessageDialog(null, "El campo de correo electr�nico no puede estar vac�o. Verif�quelo e intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorEliminarTipoUsuario() {
        JOptionPane.showMessageDialog(null, "No es posible eliminar este tipo de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorUsuarioYaExiste() {
        JOptionPane.showMessageDialog(null, "Ya existe un usuario registrado con este nombre de usuario. Intente con otro diferente.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void confirmacionUsuarioEliminado() {
        JOptionPane.showMessageDialog(null, "El usuario ha sido eliminado exitosamente.", "Informaci�n", JOptionPane.INFORMATION_MESSAGE);
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
