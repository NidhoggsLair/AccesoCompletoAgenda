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

import java.util.List;
import java.util.Properties;
import java.sql.SQLException;
import org.japo.java.entities.Credencial;
import org.japo.java.entities.Modulo;
import org.japo.java.exceptions.ConnectivityException;
import org.japo.java.layers.services.S2Bussiness;
import org.japo.java.layers.services.S1User;
import org.japo.java.libraries.UtilesEntrada;
import org.japo.java.libraries.UtilesAgenda;

/**
 *
 * @author Adrian Bueno Olmedo - <adrian.bueno.alum@iescamp.es>
 */
public final class M1User implements S1User {

    //<editor-fold defaultstate="collapsed" desc="--- User Interface Manager ---">
    // Propiedades Credencial
    public static final String PRP_CONN_MODE = "jdbc.conn.mode";
    public static final String PRP_CONN_USER = "jdbc.conn.user";
    public static final String PRP_CONN_PASS = "jdbc.conn.pass";

    // Propiedades Aplicación
    private final Properties prp;

    // Gestor Lógica de Negocio
    private final S2Bussiness bs;

    // Constructor Parametrizado
    public M1User(Properties prp, S2Bussiness bs) {
        // Propiedades Aplicación
        this.prp = prp;

        // Gestor Lógica de Negocio
        this.bs = bs;
    }

    // Validación de Usuario
    @Override
    public final void loginApp() throws ConnectivityException {
        // Usuario > Credenciales
        String user = prp.getProperty(PRP_CONN_USER);
        String pass = prp.getProperty(PRP_CONN_PASS);

        // Modo de Conexión
        String mode = prp.getProperty(PRP_CONN_MODE);

        // Evaluación del modo de conexión
        if (mode != null && mode.equals("login")) {
            // Cabecera
            System.out.println("Acceso a la Aplicación");
            System.out.println("======================");

            // Entrada de Campos
            user = UtilesEntrada.leerTexto("Usuario ..............: ");
            pass = UtilesEntrada.leerTexto("Contraseña ...........: ");

            // Separador
            System.out.println("---");
        }

        // Creación de Entidad Credencial
        Credencial c = new Credencial(user, pass);

        // Conexión de Credenciales
        bs.abrirAccesoDatos(c);

        // Mensaje - Bitácora ( Comentar en producción )
//        System.out.println("Plantilla de Patrón Estructural por Capas Funcionales");
//        System.out.println("=====================================================");
//        System.out.println("Acceso Establecido");
//        System.out.println("---");
    }

    // Cierre de la Aplicación
    @Override
    public final void closeApp() throws ConnectivityException {
        // Cierre Base de Datos
        bs.cerrarAccesoDatos();

//        // Mensaje - Bitácora ( Comentar en producción )
//        System.out.println("Acceso Finalizado");
        // Despedida
        System.out.println("---");
        System.out.println("Copyright JAPO Labs - Servicios Informáticos");
    }
    //</editor-fold>
    public static final String OPCION_MENU = "Opción: ";

    // Ejecución de la Aplicación
    @Override
    public final void launchApp() {
        procesarMenuPrincipal();
    }

    // Menú Principal
    private void procesarMenuPrincipal() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(UtilesAgenda.MNU_PPAL,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_PPAL_OPC);

            // Separador
            System.out.println("---");

            // Procesar Opcion
            switch (opc) {
                case '1':
                    procesarMenuAgenda();
                    break;
                case '2':
                    procesarMenuDatos();
                    break;
                case '3':
                    procesarMenuRelaciones();
                    break;
                case '4':
                    procesarMenuUtilidades();
            }
        } while (opc != '0');
    }

    // Menu Actividades
    private void procesarMenuAgenda() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(UtilesAgenda.MNU_ACT_OPC,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_ACT);

            // Separador
            System.out.println("---");

            // Procesar Opcion
            switch (opc) {
                case '1':
                    procesarMenuCrearActividad();
                    break;
                case '2':
                    procesarMenuEliminarActividad();
                    break;
                case '3':
                    procesarMenuConsultarActividad();
                    break;
                case '4':
                    procesarMenuCambiarActividad();
                case '5':
                    procesarMenuListarActividad();
            }
        } while (opc != '0');
    }

    // Menu Datos
    private void procesarMenuDatos() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(
                    UtilesAgenda.MNU_DATOS,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_DATOS_OPC);

            // Separador
            System.out.println("---");

            // Procesar Opcion
            switch (opc) {
                case '1':
                    procesarMenuAlumnos();
                    break;
                case '2':
                    procesarMenuCiclos();
                    break;
                case '3':
                    procesarMenuModulos();
                    break;
                case '4':
                    procesarMenuProfesores();
                    break;
                case '5':
                    procesarMenuSegmentos();
            }
        } while (opc != '0');
    }

    // Menu Relaciones
    private void procesarMenuRelaciones() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
        System.out.println("---");
    }

    // Menu Utilidades
    private void procesarMenuUtilidades() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
        System.out.println("---");
    }

    // Menu Alumnos
    private void procesarMenuAlumnos() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(
                    UtilesAgenda.MNU_ALUM_OPC,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_ALUM);

            // Separador
            System.out.println("---");

            // Procesar Opcion
            switch (opc) {
                case '1':
                    procesarMenuInsertarAlumno();
                    break;
                case '2':
                    procesarMenuEliminarAlumno();
                    break;
                case '3':
                    procesarMenuConsultarAlumno();
                    break;
                case '4':
                    procesarMenuCambiarAlumno();
                case '5':
                    procesarMenuListarAlumno();
            }
        } while (opc != '0');
    }

    private void procesarMenuCiclos() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
        System.out.println("---");
    }

    private void procesarMenuModulos() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(
                    UtilesAgenda.MNU_MOD,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_MOD_OPC);

            // Separador
            System.out.println("---");

            // Procesar Opcion
            switch (opc) {
                case '1':
                    procesarMenuInsercionModulos();
                    break;
                case '2':
                    eliminarModulo();
                    break;
                case '3':
                    consultarModulo();
                    break;
                case '4':
                    modificarModulo();
                    break;
                case '5':
                    listarModulos();
            }
        } while (opc != '0');

    }

    private void procesarMenuProfesores() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
        System.out.println("---");
    }

    private void procesarMenuSegmentos() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
        System.out.println("---");
    }

    private void procesarMenuInsercionModulos() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(
                    UtilesAgenda.MNU_INSM,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_INSM_OPC);

            // Separador
            System.out.println("---");

            // Procesar Opcion
            switch (opc) {
                case '1':
                    procesarMenuInsercionModulosManual();
                    break;
                case '2':
                    procesarMenuImportacionModulos();

            }
        } while (opc != '0');
    }

    private void eliminarModulo() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
        System.out.println("---");
    }

    private void consultarModulo() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
        System.out.println("---");
    }

    private void modificarModulo() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
        System.out.println("---");
    }

    private void listarModulos() {
        try {
            // Cabecera
            System.out.println("Listado de Módulos");
            System.out.println("==================");

            List<Modulo> lista = bs.obtenerModulos();

            // Proceso de listado
            if (lista.size() > 0) {
                // Cabecera Listado
                System.out.println(" #  Id          Acrónimo   Nombre                                     Código     Horas Curso");
                System.out.println("=== =========== ========== ========================================== ========== ===== =====");

                // Recorre Lista de Registros
                for (int i = 0; i < lista.size(); i++) {
                    // Imprime los datos del registro actual
                    System.out.printf("%03d %s%n", i + 1, lista.get(i));
                }
            } else {
                System.out.println("Datos de Módulos no disponibles");
            }
        } catch (NullPointerException | SQLException ex) {
            System.out.println("ERROR: Modulos no disponibles");
        } finally {
            System.out.println("---");
        }
    }

    //Opciones Agenda
    private void procesarMenuCrearActividad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procesarMenuEliminarActividad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procesarMenuConsultarActividad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procesarMenuCambiarActividad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procesarMenuListarActividad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //opciones Alumno
    private void procesarMenuInsertarAlumno() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(
                    UtilesAgenda.MNU_INSA_OPC,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_INSA);

            // Separador
            System.out.println("---");

            // Procesar Opcion
            switch (opc) {
                case '1':
                    procesarMenuInsertarAlumnoManual();
                    break;
                case '2':
                    procesarMenuImportarAlumno();
                    break;
                case '3':
                    procesarMenuAlumnoAleatorio();

            }
        } while (opc != '0');
    }

    private void procesarMenuEliminarAlumno() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procesarMenuConsultarAlumno() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procesarMenuCambiarAlumno() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procesarMenuListarAlumno() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Menú de inserción de Módulos
    private void procesarMenuInsercionModulosManual() {
        System.out.println("INSERCION MODULO");
        System.out.println("================");
        int id = UtilesEntrada.leerEntero(UtilesAgenda.MSG_ID,
                UtilesAgenda.MNU_ERROR);

        String acronimo = UtilesEntrada.leerTexto(UtilesAgenda.MSG_ACR);
        String nombre = UtilesEntrada.leerTexto(UtilesAgenda.MSG_NOMBR);
        String codigo = UtilesEntrada.leerTexto(UtilesAgenda.MSG_COD);
        int horasCurso = UtilesEntrada.leerEntero(UtilesAgenda.MSG_HOR,
                UtilesAgenda.MNU_ERROR);
        int curso = UtilesEntrada.leerEntero(UtilesAgenda.MSG_CUR,
                UtilesAgenda.MNU_ERROR);

        //Parametros
        Modulo m = new Modulo(
                id,
                acronimo,
                nombre,
                codigo,
                horasCurso,
                curso);

        //Ejecución
        try {
            boolean insercionOK = bs.insertarModulosManual(m);

            //Resultado de la operacion
            System.out.printf("Operacion realizada: insercion %s realizada %n",
                    insercionOK ? "SI" : "NO");
            //Separador cosmético
            System.out.println("---");

        } catch (NullPointerException | SQLException ex) {
            System.out.println("ERROR: Inserción cancelada" + '\n'
                    + ex.getMessage());

            //Separador cosmético
            System.out.println("---");
        }

    }

    private void procesarMenuImportacionModulos() {
        System.out.println("IMPORTACION MODULO");
        System.out.println("==================");

        try {
            int filas = bs.insertarModulosLotes();
            System.out.printf(filas + "Filas insertadas%n");

        } catch (NullPointerException | SQLException e) {
            System.out.println("ERROR: Operción de inserción cancelada");
        }
    }

    //Menu insercion alumno 
    private void procesarMenuInsertarAlumnoManual() {

    }

    private void procesarMenuImportarAlumno() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procesarMenuAlumnoAleatorio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
