/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import exceptions.PasswordException;
import java.io.Serializable;

/**
 *
 * @author Carlos
 */
public class Password implements Comparable<Password>, Serializable {

    private char[] password;
    private static int MIN_LONGITUD = 3;

    public Password(char[] password) throws PasswordException {
        if(password.length < MIN_LONGITUD){
            throw new PasswordException("La longitud de la contraseña debe ser mínimo de " + MIN_LONGITUD + " dígitos. Revísela e intente nuevamente.");
        }
        this.password = password;
    }

    public char[] getPassword() {
        return this.password;
    }

    @Override
    public int compareTo(Password o) {

        if (password.length - o.getPassword().length != 0) {
            return 0;
        } else {
            for (int i = 0; i < password.length; i++) {
                if (this.password[i] != o.getPassword()[i]) {
                    return 0;
                }
            }
            return 1;
        }
    }

    @Override
    public String toString() {
        return new String(this.getPassword());
    }
}