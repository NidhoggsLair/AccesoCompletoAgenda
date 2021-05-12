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
package org.japo.java.layers.services;

import java.sql.SQLException;
import java.util.List;
import org.japo.java.entities.Alumno;
import org.japo.java.entities.Credencial;
import org.japo.java.entities.Modulo;
import org.japo.java.exceptions.ConnectivityException;

/**
 *
 * @author Adrian Bueno Olmedo - <adrian.bueno.alum@iescamp.es>
 */
public interface S2Bussiness {

    public void abrirAccesoDatos(Credencial c) throws ConnectivityException;

    public void cerrarAccesoDatos() throws ConnectivityException;

    // Modulo
    public List<Modulo> obtenerModulos()
            throws NullPointerException, SQLException;

    public boolean insertarModulosManual(Modulo m)
            throws NullPointerException, SQLException;

    public int insertarModulosLotes()
            throws NullPointerException, SQLException;

    public int borrarModulos(String[] param)
            throws NullPointerException, SQLException;

    public Modulo consultarModulo(Integer id)
            throws NullPointerException, SQLException;

    public boolean modificarModulo(Modulo m)
            throws NullPointerException, SQLException;

    // Alumno
    public int borrarAlumnos(String[] param)
            throws NullPointerException, SQLException;

    public boolean insertarAlumnoManual(Alumno a)
            throws NullPointerException, SQLException;

    public int insertarAlumnosLotes()
            throws NullPointerException, SQLException;

    public Alumno consultarAlumno(String exp)
            throws NullPointerException, SQLException;

    public boolean modificarAlumno(Alumno a)
            throws NullPointerException, SQLException;

    public List<Alumno> obtenerAlumnos()
            throws NullPointerException, SQLException;

}
