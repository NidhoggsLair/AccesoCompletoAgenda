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
    public static final String PRP_MODULO_LISTA = "agenda.modulo.lista";
    public static final String PRP_MODULO_INSERCION = "agenda.modulo.insercion";
    public static final String PRP_MODULO_CSV = "agenda.modulos.csv";

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
        String selTacc = prp != null ? prp.getProperty(PRP_STMT_TACC, DEF_STMT_TACC) : DEF_STMT_TACC;

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
        String selConc = prp != null ? prp.getProperty(PRP_STMT_CONC, DEF_STMT_CONC) : DEF_STMT_CONC;

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
            throw new ConnectivityException("Error en el Cierre de la Sentencia: " + e.getMessage());
        }

        // Cerrar Conexión con Base de datos
        try {
            conn.close();
        } catch (SQLException | NullPointerException e) {
            throw new ConnectivityException("Error en el Cierre de Conexión: " + e.getMessage());
        }
    }
    //</editor-fold>

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

    // ---
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
        // Ruta Fichero CSV
        String csv = prp.getProperty(PRP_MODULO_CSV);

        // Importar Fichero CSV > ArrayList<Modulo>
        List<Modulo> lista = importarModulos(csv);

        // SQL Inserción
        String sql = prp.getProperty(PRP_MODULO_INSERCION);

        // Filas Afectadas
        int filas = 0;

        // Apertura + Cierre Automático > Prepared Statement
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Modulo m : lista) {
                filas = insertarModulo(ps, m) ? filas + 1 : filas;
            }
        }

        // Devolver Filas Afectadas
        return filas;
    }
    // Lógica de Acceso a Datos Adicional

    private List<Modulo> importarModulos(String csv) {
        // Lista de Módulos
        List<Modulo> lista = new ArrayList<>();

        try (
                FileReader fr = new FileReader(csv);
                BufferedReader br = new BufferedReader(fr)) {
            // Declaración de la Linea
            String linea;
            // Bucle Recorrido del Fichero por Lineas
            do {
                // Linea Actual recien leida
                linea = br.readLine().trim();

                // Linea (String) > Modulo
                Modulo m = convertirLinea(linea);

                // Añadir Módulo a la Lista
                lista.add(m);
            } while (linea != null && linea.length() > 0);
        } catch (Exception e) {
        }

        // Devolver Lista de Módulos
        return lista;
    }

    // String + Separador > String[]
    private Modulo convertirLinea(String linea) {
        // Separador Items
        final String SEPARADOR = "\\s*,\\s*";

        // Lista de Items
        String[] items = linea.split(SEPARADOR);

        // Campos del Módulo
        int id = Integer.parseInt(items[0]);
        String acronimo = items[1];
        String nombre = items[2];
        String codigo = items[3];
        int horasCurso = Integer.parseInt(items[4]);
        int curso = Integer.parseInt(items[5]);

        // Instanciar + Devolver Modulo
        return new Modulo(id, acronimo, nombre, codigo, horasCurso, curso);
    }

    // Modulo > Insercion BD
    
    public boolean insertarModulo(PreparedStatement ps, Modulo m) 
            throws NullPointerException, SQLException{
        // Número de filas Afectadas
        int filas;
        String _acronimo = "";

        try {
            // Obtener Parametros
            String id = m.getId() + "";
            String acronimo = m.getAcronimo();
            _acronimo = m.getAcronimo();
            String nombre = m.getNombre();
            String codigo = m.getCodigo();
            String horasCurso = m.getHorasCurso() + "";
            String curso = m.getCurso() + "";

            // Aplicar Parámetros
            ps.setString(1, id);
            ps.setString(2, acronimo);
            ps.setString(3, nombre);
            ps.setString(4, codigo);
            ps.setString(5, horasCurso);
            ps.setString(6, curso);

            // Ejecutar Insercion
            filas = ps.executeUpdate();
            System.out.printf("Modulo %s SI insertado%n", _acronimo);
        } catch (SQLException | NullPointerException x) {
            filas = 0;
            System.out.printf("Modulo %s NO insertado%n", _acronimo);
        }

        return filas == 1;
    }

    

    

    

    
}
