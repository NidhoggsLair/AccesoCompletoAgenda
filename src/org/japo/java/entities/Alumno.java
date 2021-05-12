/* 
 * Copyright 2021 Adrian Bueno Olmedo - <adrian.bueno.alum@iescamp.es>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.entities;

import java.io.Serializable;

/**
 *
 * @author Adrian Bueno Olmedo <adrian.bueno.alum@iescamp.es>
 */
public class Alumno implements Serializable {

    // Campos
    private String exp;
    private String nombre;
    private String apellidos;
    private String nif;
    private String nac;
    private int telefono;

    // Constructor Parametrizado
    public Alumno(String exp, String nombre, String apellidos, String nif,
            String nac, int telefono) {
        this.exp = exp;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.nac = nac;
        this.telefono = telefono;
    }

    /**
     * @return the exp
     */
    public String getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(String exp) {
        this.exp = exp;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * @param nif the nif to set
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * @return the nac
     */
    public String getNac() {
        return nac;
    }

    /**
     * @param nac the nac to set
     */
    public void setNac(String nac) {
        this.nac = nac;
    }

    /**
     * @return the telefono
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return String.format("%-11d %-10s %-35s %-10s %5d %3d", 
                getExp(), 
                getNombre(), 
                getApellidos(), 
                getNif(), 
                getNac(), 
                getTelefono());
    }
}
