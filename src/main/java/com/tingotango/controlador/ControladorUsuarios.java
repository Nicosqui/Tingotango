/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tingotango.controlador;

import java.util.ArrayList;
import java.util.List;
import co.edu.umanizales.listase.modelo.Infante;

/**
 *
 * @author carloaiza
 */
public class ControladorUsuarios {

    private static List<Infante> usuarios;

    public ControladorUsuarios() {
        this.iniciarListados();
    }
    public static List<Infante> getUsuarios() {
        return usuarios;
    }

    public static void setUsuarios(List<Infante> usuarios) {
        ControladorUsuarios.usuarios = usuarios;
    }

    
    
    private void iniciarListados() {
        
        usuarios = new ArrayList<>();
        
        usuarios.add(new Infante("Carlos", true, (byte) 1));
        usuarios.add(new Infante("Diana", false, (byte) 2));
        usuarios.add(new Infante("Juanita", false, (byte) 3));
        usuarios.add(new Infante("David", true, (byte) 4));
        usuarios.add(new Infante("Andrea", false, (byte) 5));
        usuarios.add(new Infante("Esteban", true, (byte) 6));
    }

    public Infante encontrarUsuarioxCorreo(String correo) {
        Infante usuarioEncontrado = null;
        //Recorre la lista de principio a fin 
        for (Infante usu : ControladorUsuarios.usuarios) {
            if (usu.getNombre().equals(correo)) {
                return usu;
            }
        }

        return usuarioEncontrado;
    }

}
