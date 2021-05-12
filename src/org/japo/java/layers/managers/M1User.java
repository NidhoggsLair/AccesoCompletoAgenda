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
import org.japo.java.entities.Alumno;
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
            opc = UtilesEntrada.leerCaracter(UtilesAgenda.MNU_ACT,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_ACT_OPC);

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
    }

    // Menu Utilidades
    private void procesarMenuUtilidades() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
    }

    // Menu Alumnos
    private void procesarMenuAlumnos() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(
                    UtilesAgenda.MNU_ALUM,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_ALUM_OPC);

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
                    procesarMenuEliminarModulo();
                    break;
                case '3':
                    procesarMenuConsultarModulo();
                    break;
                case '4':
                    procesarMenuModificarModulo();
                    break;
                case '5':
                    procesarMenuListarModulos();
            }
        } while (opc != '0');

    }

    private void procesarMenuProfesores() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
    }

    private void procesarMenuSegmentos() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
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

    private void procesarMenuEliminarModulo() {
        System.out.println("Borrado de Módulos");
        System.out.println("==================");

        // Parámetros de Borrado
        String[] param = {"%S%", "2"};

        try {
            int filas = bs.borrarModulos(param);
            System.out.printf("Operación realizada: %d módulos borrados %n", filas);
        } catch (NullPointerException | SQLException ex) {
            System.out.println("ERROR: Operación de borrado cancelada");
        }
    }

    private void procesarMenuConsultarModulo() {
        try {
            // Cabecera
            System.out.println("Consulta de Módulo");
            System.out.println("==================");

            // Selección de Módulo
            int id = UtilesEntrada.leerEntero("ID del módulo ...: ");

            // Separador
            System.out.println("---");

            // Obtener Módulo
            Modulo m = bs.consultarModulo(id);

            // Mostrar Módulo
            System.out.printf("ID del módulo ...: %d%n", m.getId());
            System.out.printf("Acrónimo ........: %s%n", m.getAcronimo());
        } catch (NullPointerException | SQLException e) {
            System.out.println("Consulta de Modulo Cancelada: " + e.getMessage());
        }

        // Hacer una Pausa
        UtilesEntrada.hacerPausa();
    }

    private void procesarMenuModificarModulo() {
        // Datos Módulo a Modificar
        Modulo m = new Modulo(
                373,
                "LM",
                "Lenguajes de Marcas",
                "MP0373",
                96,
                1);

        try {
            // Ejecutar Modificación
            boolean modificacionOK = bs.modificarModulo(m);

            // Resultado de la Operación
            System.out.printf("OPERACIÓN: Modificación %s realizada%n",
                    modificacionOK ? "SI" : "NO");
        } catch (NullPointerException | SQLException e) {
            System.out.println("Modificación cancelada: "
                    + e.getMessage());
        }
    }

    private void procesarMenuListarModulos() {
        try {
            // Cabecera
            System.out.println("Listado de Módulos");
            System.out.println("==================");

            List<Modulo> lista = bs.obtenerModulos();

            // Proceso de listado
            if (lista.size() > 0) {
                // Cabecera Listado
                System.out.println(" #  Id          Acrónimo   Nombre          "
                        + "                           Código     Horas Curso");
                System.out.println("=== =========== ========== ================"
                        + "========================== ========== ===== =====");

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

    //Menu Alumno
    private void procesarMenuInsertarAlumno() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(
                    UtilesAgenda.MNU_INSA,
                    UtilesAgenda.MNU_ERROR,
                    UtilesAgenda.MNU_INSA_OPC);

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
        System.out.println("Borrado de Alumnos");
        System.out.println("==================");

        // Parámetros de Borrado
        String[] param = {"1990-01-01", "V%"};

        try {
            int filas = bs.borrarAlumnos(param);
            System.out.printf("Operación realizada: %d módulos borrados%n", filas);
        } catch (NullPointerException | SQLException ex) {
            System.out.println("ERROR: Operación de borrado cancelada");
        }
    }

    private void procesarMenuConsultarAlumno() {
        try {
            // Cabecera
            System.out.println("Consulta de Alumno");
            System.out.println("==================");

            // Selección de Alumno
            String exp = UtilesEntrada.leerTexto("EXP del Alumno ...: ");

            // Separador
            System.out.println("---");

            // Obtener Módulo
            Alumno a = bs.consultarAlumno(exp);

            // Mostrar Módulo
            System.out.printf("EXP del Alumno ...: %s%n", a.getExp());
            System.out.printf("Nombre ...........: %s%n", a.getNombre());
            System.out.printf("Apellidos ........: %s%n", a.getApellidos());
        } catch (NullPointerException | SQLException e) {
            System.out.println("Consulta de Modulo Cancelada: " + e.getMessage());
        }

        // Hacer una Pausa
        UtilesEntrada.hacerPausa();
    }

    private void procesarMenuCambiarAlumno() {
        // Datos Alumno a Modificar
        Alumno a = new Alumno(
                "1111",
                "Adrian",
                "Bueno Olmedo",
                "45803307W",
                "19960204",
                722730432);

        try {
            // Ejecutar Modificación
            boolean modificacionOK = bs.modificarAlumno(a);

            // Resultado de la Operación
            System.out.printf("OPERACIÓN: Modificación %s realizada%n",
                    modificacionOK ? "SI" : "NO");
        } catch (NullPointerException | SQLException e) {
            System.out.println("Modificación cancelada: "
                    + e.getMessage());
        }
    }

    private void procesarMenuListarAlumno() {
        try {
            // Cabecera
            System.out.println("Listado de Alumnos");
            System.out.println("==================");

            List<Alumno> lista = bs.obtenerAlumnos();

            // Proceso de listado
            if (lista.size() > 0) {
                // Cabecera Listado
                System.out.println(" #  Exp         Nombre              "
                        + "Apellidos                        Nif        Nac     "
                        + " Telefono");
                System.out.println("=== =========== =================== "
                        + "================================ ========== ========"
                        + " =========");

                // Recorre Lista de Registros
                for (int i = 0; i < lista.size(); i++) {
                    // Imprime los datos del registro actual
                    System.out.printf("%03d %s%n", i + 1, lista.get(i));
                }
            } else {
                System.out.println("Datos de Alumnos no disponibles");
            }
        } catch (NullPointerException | SQLException ex) {
            System.out.println("ERROR: Alumnos no disponibles");
        } finally {
            System.out.println("---");
        }
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
            System.out.printf("%nOperacion realizada: insercion %s realizada %n%n",
                    insercionOK ? "SI" : "NO");

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
            System.out.printf(filas + " Filas insertadas%n");

        } catch (NullPointerException | SQLException e) {
            System.out.println("ERROR: Operción de inserción cancelada");
        }
    }

    //Menu insercion alumno 
    private void procesarMenuInsertarAlumnoManual() {
        System.out.println("INSERCION ALUMNO");
        System.out.println("================");
        String exp = UtilesEntrada.leerTexto(UtilesAgenda.MSG_EXP);
        String nombre = UtilesEntrada.leerTexto(UtilesAgenda.MSG_NOMBR);
        String apellidos = UtilesEntrada.leerTexto(UtilesAgenda.MSG_APELL);
        String nif = UtilesEntrada.leerTexto(UtilesAgenda.MSG_NIF);
        String nac = UtilesEntrada.leerTexto(UtilesAgenda.MSG_NAC);
        int telefono = UtilesEntrada.leerEntero(UtilesAgenda.MSG_TEL,
                UtilesAgenda.MNU_ERROR);
        //Parametros
        Alumno a = new Alumno(
                exp,
                nombre,
                apellidos,
                nif,
                nac,
                telefono
        );

        //Ejecución
        try {
            boolean insercionOK = bs.insertarAlumnoManual(a);

            //Resultado de la operacion
            System.out.printf("%nOperacion realizada: insercion %s realizada %n%n",
                    insercionOK ? "SI" : "NO");

        } catch (NullPointerException | SQLException ex) {
            System.out.println("ERROR: Inserción cancelada" + '\n'
                    + ex.getMessage());

            //Separador cosmético
            System.out.println("---");
        }
    }

    private void procesarMenuImportarAlumno() {
        System.out.println("IMPORTACION ALUMNO");
        System.out.println("==================");

        try {
            int filas = bs.insertarAlumnosLotes();
            System.out.printf(filas + " Filas insertadas%n");

        } catch (NullPointerException | SQLException e) {
            System.out.println("ERROR: Operción de inserción cancelada");
        }
    }

    private void procesarMenuAlumnoAleatorio() {
        System.out.println("Opción Disponible Proximamente");
        UtilesEntrada.hacerPausa();
    }
}
