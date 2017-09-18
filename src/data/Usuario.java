package data;

import generalities.Utilidades;
import exceptions.ImposibleEliminarException;
import exceptions.NomUsuarioException;
import exceptions.UsuarioYaExisteException;
import exceptions.CorreoNoValidoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * @author Carlos
 * @version 1.0
 * @created 28-may-2015 11:15:28 p.m.
 */
public class Usuario implements Serializable, Comparable<Usuario> {

    private String correo;
    private String nomUsuario;
    private Password password;
    protected static List<Object> usuarios;
    public static final String PRIV_INSERTAR = "Insert_priv";
    public static final String PRIV_ACTUALIZAR = "Update_priv";
    public static final String PRIV_CREAR_TABLA_TMP = "Create_tmp_table_priv";
    public static final String PRIV_ELIMINAR = "Delete_priv";

    public Usuario(String nomUsuario, Password password, String correo) throws UsuarioYaExisteException, CorreoNoValidoException, NomUsuarioException {
        this.correo = correo;
        if (nomUsuario.isEmpty()) {
            throw new NomUsuarioException("El campo usuario no puede estar vacío.");
        }
        this.nomUsuario = nomUsuario;
        this.password = password;
        Utilidades.validarCorreo(correo);
    }

    public Usuario(String nomUsuario, Password password) {
        this.nomUsuario = nomUsuario;
        this.password = password;
    }

    public Usuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNomUsuario() {
        return this.nomUsuario;
    }

    public Password getPassword() {
        return this.password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void modificar(String nomUsuario, Password password) throws FileNotFoundException, IOException {
        this.nomUsuario = nomUsuario;
        this.password = password;
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        entityM.getTransaction().begin();
        entityM.createNativeQuery("SET PASSWORD = PASSWORD('" + password.toString() + "')").executeUpdate();
        entityM.getTransaction().commit();
    }

    public void modificar(String correo) throws FileNotFoundException, IOException, CorreoNoValidoException {
        this.correo = correo;
        Utilidades.validarCorreo(correo);
    }

    public static void eliminar(String usuario) throws ImposibleEliminarException {
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        entity.getTransaction().begin();
        entity.createNativeQuery("DROP USER '" + usuario + "'@'localhost'").executeUpdate();
        entity.createNativeQuery("DROP USER '" + usuario + "'@'%'").executeUpdate();
        entity.getTransaction().commit();
    }

    public static List<Object> getUsuarios() {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        usuarios = entityM.createNativeQuery("SELECT User FROM mysql.db WHERE user!= '" + Manejador.getUsuario() + "' AND Db= 'gestionacademica_dptoiee' AND Host ='%'").getResultList();
        setUsuarios(usuarios);
        return usuarios;
    }

    public static void setUsuarios(List<Object> usuarios) {
        Usuario.usuarios = usuarios;
    }

    @Override
    public int compareTo(Usuario o) {
        //Compara si los nombres de usuario y las contraseñas de ambos usuarios son iguales (retorna un 1 si lo son, un 0 si no)
        if (this.getNomUsuario().equals(o.getNomUsuario()) && (this.getPassword().compareTo(o.getPassword()) == 1)) {
            return 1;
        }
        return 0;
    }

    public static boolean consultarPermiso(String nomUsuario, String permiso) {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        if (((String) (entityM.createNativeQuery("SELECT " + permiso + " FROM mysql.db WHERE User ='" + nomUsuario + "' AND Db ='gestionacademica_dptoiee' AND Host = '%'").getSingleResult())).equalsIgnoreCase("Y")) {
            return true;
        }
        return false;
    }

    public static void asignarPermiso(String nomUsuario, String permiso) {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        entityM.getTransaction().begin();
        entityM.createNativeQuery("GRANT " + permiso + " ON gestionacademica_dptoiee.* TO '" + nomUsuario + "'@'localhost'").executeUpdate();
        entityM.createNativeQuery("GRANT " + permiso + " ON gestionacademica_dptoiee.* TO '" + nomUsuario + "'@'%'").executeUpdate();
        entityM.getTransaction().commit();
    }

    public static void removerPermiso(String nomUsuario, String permiso) {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        entityM.getTransaction().begin();
        entityM.createNativeQuery("REVOKE " + permiso + " ON gestionacademica_dptoiee.* FROM '" + nomUsuario + "'@'localhost'").executeUpdate();
        entityM.createNativeQuery("REVOKE " + permiso + " ON gestionacademica_dptoiee.* FROM '" + nomUsuario + "'@'%'").executeUpdate();
        entityM.getTransaction().commit();
    }

    @Override
    public String toString() {
        return this.getNomUsuario() + " " + this.getPassword().toString() + " " + this.getCorreo();
    }

    public static void disponibilidadUsuario(String nomUsuario) throws UsuarioYaExisteException {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        try {
            entityM.createNativeQuery("SELECT User FROM mysql.db WHERE user= '" + nomUsuario + "' AND Db= 'gestionacademica_dptoiee' AND Host ='%'").getSingleResult();
            throw new UsuarioYaExisteException();
        } catch (NoResultException ex) {
        }
    }
}