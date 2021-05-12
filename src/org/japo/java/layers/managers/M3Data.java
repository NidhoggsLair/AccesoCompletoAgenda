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

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.japo.java.entities.Alumno;
import org.japo.java.entities.Credencial;
import org.japo.java.entities.Modulo;
import org.japo.java.exceptions.ConnectivityException;
import org.japo.java.layers.services.S3Data;

/**
 *
 * @author Adrian Bueno Olmedo - <adrian.bueno.alum@iescamp.es>
 */
public final class M3Data implements S3Data {

    //<editor-fold defaultstate="collapsed" desc="--- Data Access Manager ---">
    // Conexión BBDD - Propiedades 
    public static final String PRP_CONN_CPAT = "jdbc.conn.cpat";
    public static final String PRP_CONN_PROT = "jdbc.conn.prot";
    public static final String PRP_CONN_HOST = "jdbc.conn.host";
    public static final String PRP_CONN_PORT = "jdbc.conn.port";
    public static final String PRP_CONN_DBNM = "jdbc.conn.dbnm";
    public static final String PRP_CONN_USER = "jdbc.conn.user";
    public static final String PRP_CONN_PASS = "jdbc.conn.pass";
    
    // Propiedades SQL
    // Modulo
    public static final String PRP_MODULO_LISTA = "agenda.modulo.lista";
    public static final String PRP_MODULO_INSERCION = "agenda.modulo.insercion";
    public static final String PRP_MODULOS_CSV = "modulo.insercion.csv";
    public static final String PRP_MODULO_CSV = "agenda.modulo.csv";
    public static final String PRP_MODULO_CONSULTAR = "agenda.modulo.consultar";
    public static final String PRP_MODULO_MODIFICACION = "agenda.modulo.modificacion";
    public static final String PRP_MODULO_BORRADO = "agenda.modulo.borrado";
    // Alumno
    public static final String PRP_ALUMNO_INSERCION = "agenda.alumno.insercion";
    public static final String PRP_ALUMNOS_CSV = "alumno.insercion.csv";
    public static final String PRP_ALUMNO_BORRADO = "agenda.alumno.borrado";
    public static final String PRP_ALUMNO_CONSULTAR = "agenda.alumno.consultar";
    public static final String PRP_ALUMNO_MODIFICACION = "agenda.alumno.modificacion";
    public static final String PRP_ALUMNO_LISTA = "agenda.alumno.lista";
    
    // Sentencia BBDD - Tipo de Acceso
    public static final String STMT_ACCT_TFLY = "TYPE_FORWARD_ONLY";
    public static final String STMT_ACCT_TSIN = "TYPE_SCROLL_INSENSITIVE";
    public static final String STMT_ACCT_TSSE = "TYPE_SCROLL_SENSITIVE";
    private static final String DEF_STMT_TACC = STMT_ACCT_TSSE;
    public static final String PRP_STMT_TACC = "jdbc.stmt.tacc";

    // Sentencia BBDD - Concurrencia
    public static final String STMT_CONC_RNLY = "CONCUR_READ_ONLY";
    public static final String STMT_CONC_UPDT = "CONCUR_UPDATABLE";
    private static final String DEF_STMT_CONC = STMT_CONC_UPDT;
    public static final String PRP_STMT_CONC = "jdbc.stmt.conc";

    // Referencias
    private final Properties prp;

    // Artefactos Base de Datos
    private Connection conn = null;
    private Statement stmt = null;

    // Constructor Parametrizado - Properties
    public M3Data(Properties prp) {
        this.prp = prp;
    }

    @Override
    public final void abrirAccesoDatos(Credencial c)
            throws ConnectivityException {
        try {
            // Propiedades > Parámetros Conexión
            String cpat = prp.getProperty(PRP_CONN_CPAT);
            String prot = prp.getProperty(PRP_CONN_PROT);
            String host = prp.getProperty(PRP_CONN_HOST);
            String port = prp.getProperty(PRP_CONN_PORT);
            String dbnm = prp.getProperty(PRP_CONN_DBNM);

            // Credencial > Parámetros Conexión
            String user = c.getUser();
            String pass = c.getPass();

            // Cadena de Conexión
            String cstr = String.format(cpat, prot, host, port, dbnm, user, pass);

            // Conexión BD
            conn = DriverManager.getConnection(cstr);

            // Tipo de Acceso
            int tacc = obtenerTipoAcceso(prp);

            // Concurrencia
            int conc = obtenerConcurrencia(prp);

            // Sentencia BD
            stmt = conn.createStatement(tacc, conc);
        } catch (NullPointerException | SQLException e) {
            throw new ConnectivityException("Acceso Denegado: "
                    + e.getMessage());
        }
    }

    // Statement - Tipo de Acceso
    private int obtenerTipoAcceso(Properties prp) {
        // ---- TIPOS DE ACCESO ----
        // ResultSet.TYPE_FORWARD_ONLY (*) - Indica que el objeto ResultSet se
        //      puede recorrer unicamente hacia adelante.
        // ResultSet.TYPE_SCROLL_INSENSITIVE - Indica que el objeto ResultSet se
        //      puede recorrer, pero en general no es sensible a los cambios en
        //      los datos que subyacen en él.
        // ResultSet.TYPE_SCROLL_SENSITIVE - Indica que el objeto ResultSet se
        //      puede  recorrer, y además, los cambios en él repercuten
        //      en la base de datos subyacente.
        //
        // Properties > Selector Tipo de Acceso 
        String selTacc = prp != null ? 
                prp.getProperty(PRP_STMT_TACC, DEF_STMT_TACC) : DEF_STMT_TACC;

        // Tipo de Acceso
        int tacc;

        // Obtener valor
        switch (selTacc) {
            case STMT_ACCT_TFLY:
                tacc = ResultSet.TYPE_FORWARD_ONLY;
                break;
            case STMT_ACCT_TSIN:
                tacc = ResultSet.TYPE_SCROLL_INSENSITIVE;
                break;
            case STMT_ACCT_TSSE:
                tacc = ResultSet.TYPE_SCROLL_SENSITIVE;
                break;
            default:
                tacc = ResultSet.TYPE_FORWARD_ONLY;
        }

        // Devolver Tipo de Acceso
        return tacc;
    }

    // Statement - Concurrencia
    private int obtenerConcurrencia(Properties prp) {
        // ---- MODOS DE CONCURRENCIA ----
        // ResultSet.CONCUR_READ_ONLY (*) - Indica que en el modo de concurrencia
        //      para el objeto ResultSet éste no puede ser actualizado.
        // ResultSet.CONCUR_UPDATABLE - Indica que en el modo de concurrencia
        //      para el objeto ResultSet éste podria ser actualizado.
        //        
        // Properties > Selector Concurrencia
        String selConc = prp != null ? 
                prp.getProperty(PRP_STMT_CONC, DEF_STMT_CONC) : DEF_STMT_CONC;

        // Concurrencia
        int conc;
        switch (selConc) {
            case STMT_CONC_RNLY:
                conc = ResultSet.CONCUR_READ_ONLY;
                break;
            case STMT_CONC_UPDT:
                conc = ResultSet.CONCUR_UPDATABLE;
                break;
            default:
                conc = ResultSet.CONCUR_READ_ONLY;
        }

        // Devolver Concurrencia
        return conc;
    }

    // Cerrar Artefactos BD
    @Override
    public final void cerrarAccesoDatos() throws ConnectivityException {
        // Cerrar Sentencia de Base de datos
        try {
            stmt.close();
        } catch (SQLException | NullPointerException e) {
            throw new ConnectivityException
        ("Error en el Cierre de la Sentencia: " + e.getMessage());
        }

        // Cerrar Conexión con Base de datos
        try {
            conn.close();
        } catch (SQLException | NullPointerException e) {
            throw new ConnectivityException
        ("Error en el Cierre de Conexión: " + e.getMessage());
        }
    }
    //</editor-fold>

    // MODULO
    // Lógica de Acceso a Datos Adicional
    // Obtener Modulos
    @Override
    public final List<Modulo> obtenerModulos() 
            throws NullPointerException, SQLException {
        // SQL > Datos
        ResultSet rs = stmt.executeQuery(prp.getProperty(PRP_MODULO_LISTA));
        
        // Lista de Modulos
        List<Modulo> lista = new ArrayList<>();
        
        // ResultSet > ArrayList
        while (rs.next()) {
            lista.add(obtenerModulo(rs));
        }
        
        // Devolver ArrayList
        return lista;
    }

    // Insertar modulos manualmente
    @Override
    public boolean insertarModulos(Modulo m) 
            throws NullPointerException, SQLException {
        // Filas Afectadas
        int filas;
        
        // SQL Inserción ( Parametrizado )
        String sql = prp.getProperty(PRP_MODULO_INSERCION);
        
        // Módulo > Parámetros Inserción
        String id = m.getId() + "";
        String acronimo = m.getAcronimo();
        String nombre = m.getNombre();
        String codigo = m.getCodigo();
        String horasCurso = m.getHorasCurso() + "";
        String curso = m.getCurso() + "";
        
        // Sentencia Preparada ( Compilación SQL )
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Asignar Parámetros
            ps.setString(1, id);
            ps.setString(2, acronimo);
            ps.setString(3, nombre);
            ps.setString(4, codigo);
            ps.setString(5, horasCurso);
            ps.setString(6, curso);
            
            // Ejecución Inserción
            filas = ps.executeUpdate();
        }
        
        // Semáforo Resultado
        return filas == 1;
    }
    
    // ---
    // Insertar Modulo por lotes
    // ResultSet + Fila Actual > Modulo
    private Modulo obtenerModulo(ResultSet rs) 
            throws NullPointerException, SQLException{
        int id = rs.getInt("id");
        String acronimo = rs.getString("acronimo");
        String nombre = rs.getString("nombre");
        String codigo = rs.getString("codigo");
        int horasCurso = rs.getInt("horasCurso");
        int curso = rs.getInt("curso");
        
        return new Modulo(id, acronimo, nombre, codigo, horasCurso, curso);
    }

   @Override
    public int insertarModulosLotes()
            throws NullPointerException, SQLException {
        // Filas Insertadas
        int filas = 0;

        // Ruta Fichero CSV
        String csv = prp.getProperty(PRP_MODULOS_CSV);

        // Importar Módulos
        List<Modulo> lista = importarModulos(csv);

        // Obtener SQL
        String sql = prp.getProperty(PRP_MODULO_INSERCION);

        // Prepared Statement 
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Modulo m : lista) {
                filas = insertarModulo(ps, m) ? ++filas : filas;
            }
        } catch (Exception e) {
            throw new NullPointerException("Error en la inserción de modulos: "
                    + e.getMessage());
        }

        // Devolver Filas Insertadas
        return filas;
    }

    private List<Modulo> importarModulos(String csv) {
        // Contenedor de Módulos
        List<Modulo> lista = new ArrayList<>();

        // Abrir + Utilizar + Cerrar Fichero
        try (
                FileReader fr = new FileReader(csv);
                BufferedReader br = new BufferedReader(fr)) {
            String linea;
            do {
                // Leer Linea Actual
                linea = br.readLine();

                // Validar Lectura
                if (linea != null) {
                    // Limpiar Espacios
                    linea = linea.trim();

                    // Linea > Array Items
                    String[] items = linea.split("\\s*,\\s*");

                    // Array Items > Modulo
                    Modulo m = importarModulo(items);
//                    System.out.println(m);
                    
                    // Modulo > Lista
                    lista.add(m);
                }
            } while (linea != null);
        } catch (Exception e) {
            throw new NullPointerException("Importación CSV Cancelada: "
                    + e.getMessage());
        }

        // Devolver Lista
        return lista;
    }

    private Modulo importarModulo(String[] items) {
        // Extraer Campos
        int id = Integer.parseInt(items[0]);
        String acronimo = items[1];
        String nombre = items[2];
        String codigo = items[3];
        int horasCurso = Integer.parseInt(items[4]);
        int curso = Integer.parseInt(items[5]);

        // Instanciar + Devolver Modulo
        return new Modulo(id, acronimo, nombre, codigo, horasCurso, curso);
    }

    private boolean insertarModulo(PreparedStatement ps, Modulo m) {
        // Semaforo de Inserción
        boolean procesoOK;

        try {
            // Extraer Parametros
            String id = m.getId() + "";
            String acronimo = m.getAcronimo();
            String nombre = m.getNombre();
            String codigo = m.getCodigo();
            String horasCurso = m.getHorasCurso() + "";
            String curso = m.getCurso() + "";

            // Asignar Parametros
            ps.setString(1, id);
            ps.setString(2, acronimo);
            ps.setString(3, nombre);
            ps.setString(4, codigo);
            ps.setString(5, horasCurso);
            ps.setString(6, curso);

            // Ejecutar prepared Statement
            procesoOK = ps.executeUpdate() == 1;
        } catch (SQLException | NullPointerException e) {
            procesoOK = false;
        }
        
        return procesoOK;
    }
    
    @Override
    public Modulo consultarModulo(Integer id) 
            throws NullPointerException, SQLException {
        // Referencia al Modulo
        Modulo m = null;
        
        // Sentencia SQL
        String sql = prp.getProperty(PRP_MODULO_CONSULTAR);
        
        // Abre Sentencia SQL con Parámetros
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Asignar Parámetros
            ps.setString(1, id.toString());
            
            // Obtener Datos
            ResultSet rs = ps.executeQuery();
            
            // Obtener Campos
            if (rs.next()) {
                // Obtener Campos
//                int id = rs.getInt("id");
                String acronimo = rs.getString("acronimo");
                String nombre = rs.getString("nombre");
                String codigo = rs.getString("codigo");
                int horasCurso = rs.getInt("horasCurso");
                int curso = rs.getInt("curso");
                
                // Instanciar modulo
                m = new Modulo(
                        id, 
                        acronimo, 
                        nombre, 
                        codigo, 
                        horasCurso, 
                        curso);
            } else {
                throw new NullPointerException("No hay datos con esa ID");
            }
        }
        
        return m;
    }

    @Override
    public int borrarModulos(String[] param) 
            throws NullPointerException, SQLException {
        // SQL Borrado 
        String sql = prp.getProperty(PRP_MODULO_BORRADO);
        
        // Crear PreparedStatement
        PreparedStatement ps = conn.prepareStatement(sql);
        
        for (int i = 0; i < param.length; i++) {
            ps.setString(i + 1, param[i]);
        }
        return ps.executeUpdate();
    }
    
    @Override
    public int borrarAlumnos(String[] param) 
            throws NullPointerException, SQLException {
        // SQL Borrado 
        String sql = prp.getProperty(PRP_ALUMNO_BORRADO);

        // Crear PreparedStatement
        PreparedStatement ps = conn.prepareStatement(sql);

        // Añadir Parámetros
//        ps.setString(1, "D%");      // Nombre empiece por D
//        ps.setString(2, "150");     // Numero de horas
        for (int i = 0; i < param.length; i++) {
            ps.setString(i + 1, param[i]);
        }

        return ps.executeUpdate();
    }

    @Override
    public boolean modificarModulo(Modulo m) 
            throws NullPointerException, SQLException {
        // Filas Afectadas
        int filas;

        // Properties > SQL
        String sql = prp.getProperty(PRP_MODULO_MODIFICACION);
        
        // Extraer Parámetros
        String id = m.getId() + "";
        String acronimo = m.getAcronimo();
        String nombre = m.getNombre();
        String codigo = m.getCodigo();
        String horasCurso = m.getHorasCurso() + "";
        String curso = m.getCurso() + "";
        
        // Sentencia Preparada
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            // Asignar Parámetros
            ps.setString(6, id);
            ps.setString(1, acronimo);
            ps.setString(2, nombre);
            ps.setString(3, codigo);
            ps.setString(4, horasCurso);
            ps.setString(5, curso);
            
            // Ejecutar SQL
            filas = ps.executeUpdate();
        } catch (Exception e) {
            filas = 0;
        }
        
        // Devolver Semáforo
        return filas == 1;
    }

    // ---
    
    // ALUMNO
    @Override
    public boolean insertarAlumno(Alumno a) 
            throws NullPointerException, SQLException {
         // Filas Afectadas
        int filas;
        
        // SQL Inserción ( Parametrizado )
        String sql = prp.getProperty(PRP_ALUMNO_INSERCION);
        
        // Alumno > Parámetros Inserción
        String exp = a.getExp() + "";
        String nombre = a.getNombre();
        String apellidos = a.getApellidos();
        String nif = a.getNif();
        String nac = a.getNac();
        int telefono = a.getTelefono();
        
        // Sentencia Preparada ( Compilación SQL )
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Asignar Parámetros
            ps.setString(1, exp);
            ps.setString(2, nombre);
            ps.setString(3, apellidos);
            ps.setString(4, nif);
            ps.setString(5, nac);
            ps.setInt(6, telefono);
            
            // Ejecución Inserción
            filas = ps.executeUpdate();
        }
        
        // Semáforo Resultado
        return filas == 1;
    }

    // Insertar Alumno por lotes
    // ResultSet + Fila Actual > Alumno
    private Alumno obtenerAlumno(ResultSet rs) 
            throws NullPointerException, SQLException{
        String exp = rs.getString("exp");
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        String nif = rs.getString("nif");
        String nac = rs.getString("nac");
        int telefono = rs.getInt("telefono");
        
        return new Alumno(exp, nombre, apellidos, nif, nac, telefono);
    }
    
    @Override
    public int insertarAlumnosLotes() {
        // Filas Insertadas
        int filas = 0;

        // Ruta Fichero CSV
        String csv = prp.getProperty(PRP_ALUMNOS_CSV);

        // Importar Módulos
        List<Alumno> lista = importarAlumnos(csv);

        // Obtener SQL
        String sql = prp.getProperty(PRP_ALUMNO_INSERCION);

        // Prepared Statement 
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Alumno a : lista) {
                filas = insertarAlumno(ps, a) ? ++filas : filas;
            }
        } catch (Exception e) {
            throw new NullPointerException("Error en la inserción de alumnos: "
                    + e.getMessage());
        }

        // Devolver Filas Insertadas
        return filas;
    }

    private List<Alumno> importarAlumnos(String csv) {
        // Contenedor de Módulos
        List<Alumno> lista = new ArrayList<>();

        // Abrir + Utilizar + Cerrar Fichero
        try (
                FileReader fr = new FileReader(csv);
                BufferedReader br = new BufferedReader(fr)) {
            String linea;
            do {
                // Leer Linea Actual
                linea = br.readLine();

                // Validar Lectura
                if (linea != null) {
                    // Limpiar Espacios
                    linea = linea.trim();

                    // Linea > Array Items
                    String[] items = linea.split("\\s*,\\s*");

                    // Array Items > Modulo
                    Alumno a = importarAlumnos(items);
//                    System.out.println(m);
                    
                    // Modulo > Lista
                    lista.add(a);
                }
            } while (linea != null);
        } catch (Exception e) {
            throw new NullPointerException("Importación CSV Cancelada: "
                    + e.getMessage());
        }

        // Devolver Lista
        return lista;
    }

    private boolean insertarAlumno(PreparedStatement ps, Alumno a) {
        // Semaforo de Inserción
        boolean procesoOK;

        try {
            // Extraer Parametros
            String exp = a.getExp() + "";
            String nombre = a.getNombre();
            String apellidos = a.getApellidos();
            String nif = a.getNif();
            String nac = a.getNac() + "";
            String telefono = a.getTelefono() + "";

            // Asignar Parametros
            ps.setString(1, exp);
            ps.setString(2, nombre);
            ps.setString(3, apellidos);
            ps.setString(4, nif);
            ps.setString(5, nac);
            ps.setString(6, telefono);

            // Ejecutar prepared Statement
            procesoOK = ps.executeUpdate() == 1;
        } catch (SQLException | NullPointerException e) {
            procesoOK = false;
        }
        
        return procesoOK;
    }

    private Alumno importarAlumnos(String[] items) {
        String exp = items[0];
        String nombre = items[1];
        String apellidos = items[2];
        String nif = items[3];
        String nac = items[4];
        int telefono = Integer.parseInt(items[5]);
    
        return new Alumno(exp, nombre, apellidos, nif, nac, telefono);
    }

    @Override
    public Alumno consultarAlumno(String exp) 
            throws NullPointerException, SQLException {
        // Referencia al Alumno
        Alumno a = null;
        
        // Sentencia SQL
        String sql = prp.getProperty(PRP_ALUMNO_CONSULTAR);
        
        // Abre Sentencia SQL con Parámetros
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Asignar Parámetros
            ps.setString(1, exp);
            
            // Obtener Datos
            ResultSet rs = ps.executeQuery();
            
            // Obtener Campos
            if (rs.next()) {
                // Obtener Campos
//                String exp = rs.getString("exp");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String nif = rs.getString("nif");
                String nac = rs.getString("nac");
                int telefono = rs.getInt("telefono");
                
                // Instanciar modulo
                a = new Alumno(
                        exp, 
                        nombre, 
                        apellidos,
                        nif, 
                        nac, 
                        telefono);
            } else {
                throw new NullPointerException("No hay datos con ese EXP");
            }
        }
        
        return a;
    }

    @Override
    public boolean modificarAlumno(Alumno a) {
        // Filas Afectadas
        int filas;

        // Properties > SQL
        String sql = prp.getProperty(PRP_ALUMNO_MODIFICACION);
        
        // Extraer Parámetros
        String exp = a.getExp() + "";
        String nombre = a.getNombre();
        String apellidos = a.getApellidos();
        String nif = a.getNif();
        String nac = a.getNac() + "";
        String telefono = a.getTelefono() + "";
        
        // Sentencia Preparada
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            // Asignar Parámetros
            ps.setString(6, exp);
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, nif);
            ps.setString(4, nac);
            ps.setString(5, telefono);
            
            // Ejecutar SQL
            filas = ps.executeUpdate();
        } catch (Exception e) {
            filas = 0;
        }
        
        // Devolver Semáforo
        return filas == 1;
    }

    @Override
    public List<Alumno> obtenerAlumnos() {
        // SQL > Datos
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(prp.getProperty(PRP_ALUMNO_LISTA));
        } catch (SQLException ex) {
            Logger.getLogger(M3Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Lista de Alumnos
        List<Alumno> lista = new ArrayList<>();
        
        try {
            // ResultSet > ArrayList
            while (rs.next()) {
                lista.add(obtenerAlumno(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(M3Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Devolver ArrayList
        return lista;
    }

}
    