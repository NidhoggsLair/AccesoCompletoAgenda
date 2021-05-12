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
package org.japo.java.layers.managers;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.japo.java.entities.Alumno;
import org.japo.java.entities.Credencial;
import org.japo.java.entities.Modulo;
import org.japo.java.exceptions.ConnectivityException;
import org.japo.java.layers.services.S2Bussiness;
import org.japo.java.layers.services.S3Data;

/**
 *
 * @author Adrian Bueno Olmedo - <adrian.bueno.alum@iescamp.es>
 */
public final class M2Bussiness implements S2Bussiness {

    //<editor-fold defaultstate="collapsed" desc="--- Bussiness Logic Manager ---">
    // Propiedades de la Aplicación
    private final Properties prp;

    // Servicios de Acceso a Datos
    private final S3Data ds;

    // Constructor Parametrizado
    public M2Bussiness(Properties prp, S3Data ds) {
        // Propiedades Aplicación
        this.prp = prp;

        // Gestor de Acceso a Datos + Iniciar conexión BD
        this.ds = ds;
    }

    // Conectar BD
    @Override
    public final void abrirAccesoDatos(Credencial c)
            throws ConnectivityException {
        ds.abrirAccesoDatos(c);
    }

    // Desconectar BD
    @Override
    public final void cerrarAccesoDatos() throws ConnectivityException {
        ds.cerrarAccesoDatos();
    }
    //</editor-fold>

    // Lógica de Negocio Adicional
    @Override
    public final List<Modulo> obtenerModulos()
            throws NullPointerException, SQLException {
        return ds.obtenerModulos();
    }

    @Override
    public boolean insertarModulosManual(Modulo m)
            throws NullPointerException, SQLException {
        return ds.insertarModulos(m);
    }

    @Override
    public int insertarModulosLotes()
            throws NullPointerException, SQLException {
        return ds.insertarModulosLotes();
    }

    @Override
    public int borrarModulos(String[] param)
            throws NullPointerException, SQLException {
        return ds.borrarModulos(param);
    }

    @Override
    public int borrarAlumnos(String[] param)
            throws NullPointerException, SQLException {
        return ds.borrarAlumnos(param);
    }

    @Override
    public Modulo consultarModulo(Integer id)
            throws NullPointerException, SQLException {
        return ds.consultarModulo(id);
    }

    @Override
    public boolean modificarModulo(Modulo m)
            throws NullPointerException, SQLException {
        return ds.modificarModulo(m);
    }

    @Override
    public boolean insertarAlumnoManual(Alumno a)
            throws NullPointerException, SQLException {
        return ds.insertarAlumno(a);
    }

    @Override
    public int insertarAlumnosLotes()
            throws NullPointerException, SQLException {
        return ds.insertarAlumnosLotes();
    }

    @Override
    public Alumno consultarAlumno(String exp)
            throws NullPointerException, SQLException {
        return ds.consultarAlumno(exp);
    }

    @Override
    public boolean modificarAlumno(Alumno a)
            throws NullPointerException, SQLException {
        return ds.modificarAlumno(a);
    }

    @Override
    public List<Alumno> obtenerAlumnos() 
            throws NullPointerException, SQLException {
        return ds.obtenerAlumnos();
    }
}
