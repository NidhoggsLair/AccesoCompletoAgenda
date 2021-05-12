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
package org.japo.java.libraries;

/**
 *
 * @author Adrian Bueno Olmedo - <adrian.bueno.alum@iescamp.es>
 */
public class UtilesAgenda {

    // MSG Modulos
    public static String MSG_ID = "ID: ";
    public static String MSG_ACR = "Acronimo: ";
    public static String MSG_NOMBR = "Nombre: ";
    public static String MSG_COD = "Código: ";
    public static String MSG_HOR = "Horas Curso: ";
    public static String MSG_CUR = "Curso: ";

    // MSG Alumnos
    public static String MSG_EXP = "Expediente: ";
    public static String MSG_APELL = "Apellidos: ";
    public static String MSG_NIF = "NIF: ";
    public static String MSG_NAC = "Fecha de Nacimiento: ";
    public static String MSG_TEL = "Telefono: ";

    // Mensaje de Error Genérico
    public static final String MNU_ERROR = "ERROR: Entrada Incorrecta";

    public static final String MNU_PPAL_OPC = "01234";
    public static final String MNU_PPAL
            = "Agenda Escolar - Menú Principal\n"
            + "===============================\n"
            + "\n"
            + "[ 1 ] Agenda     - Programación de Actividades\n"
            + "[ 2 ] Datos      - Mantenimiento de Datos Primarios\n"
            + "[ 3 ] Relaciones - Mantenimiento de Datos Cruzados\n"
            + "[ 4 ] Utilidades - Actuaciones Complementarias\n"
            + "\n"
            + "[ 0 ] Salir\n"
            + "\n"
            + "Opción: ";
    public static final String MNU_ACT_OPC = "012345";
    public static final String MNU_ACT = "Agenda Escolar - Menú de Actividades \n"
            + "====================================\n"
            + "\n"
            + "[ 1 ] Crear      - Nueva Actividad\n"
            + "[ 2 ] Eliminar   - Eliminar Actividad\n"
            + "[ 3 ] Consulta   - Ver Actividad Específica\n"
            + "[ 4 ] Cambiar    - Modificar Actividad\n"
            + "[ 5 ] Listado    - Ver todas las Actividades\n"
            + "\n"
            + "[ 0 ] Menú Anterior\n"
            + "\n"
            + "Opción: ";
    public static final String MNU_DATOS_OPC = "012345";
    public static final String MNU_DATOS = "Agenda Escolar - Menú de Datos\n"
            + "==============================\n"
            + "\n"
            + "[ 1 ] Alumnos    - Datos de Alumnos\n"
            + "[ 2 ] Ciclos     - Datos de Ciclos\n"
            + "[ 3 ] Modulos    - Datos de Módulos\n"
            + "[ 4 ] Profesores - Datos de Profesores\n"
            + "[ 5 ] Segmentos  - Datos de Clases\n"
            + "\n"
            + "[ 0 ] Menú Anterior\n"
            + "\n"
            + "Opción: ";
    public static final String MNU_MOD_OPC = "012345";
    public static final String MNU_MOD = "Agenda Escolar - Menú de Módulos\n"
            + "================================\n"
            + "\n"
            + "[ 1 ] Insertar   - Nuevo/s Módulo/s\n"
            + "[ 2 ] Eliminar   - Eliminar Módulo\n"
            + "[ 3 ] Consultar  - Ver Módulo Específico\n"
            + "[ 4 ] Cambiar    - Modificar Datos de Módulo\n"
            + "[ 5 ] Listar     - Ver todos los Módulos\n"
            + "\n"
            + "[ 0 ] Menú Anterior\n"
            + "\n"
            + "Opción: ";
    public static final String MNU_INSM_OPC = "012";
    public static final String MNU_INSM = "Agenda Escolar - Menú de Inserción de Módulos\n"
            + "=============================================\n"
            + "\n"
            + "[ 1 ] Manual     - Datos por Consola\n"
            + "[ 2 ] Importar   - Datos Archivo CSV\n"
            + "\n"
            + "[ 0 ] Menú Anterior\n"
            + "\n"
            + "Opción: ";
    public static final String MNU_ALUM_OPC = "012345";
    public static final String MNU_ALUM = "Agenda Escolar - Menú de Alumnos\n"
            + "================================\n"
            + "\n"
            + "[ 1 ] Insertar   - Nuevo/s Alumno/s\n"
            + "[ 2 ] Eliminar   - Eliminar Alumno\n"
            + "[ 3 ] Consultar  - Ver Alumno Específico\n"
            + "[ 4 ] Cambiar    - Modificar Datos de Alumno\n"
            + "[ 5 ] Listar     - Ver todos los Alumnos\n"
            + "\n"
            + "[ 0 ] Menú Anterior\n"
            + "\n"
            + "Opción: ";
    public static final String MNU_INSA_OPC = "0123";
    public static final String MNU_INSA = "Agenda Escolar - Menú de Inserción de Alumnos\n"
            + "=============================================\n"
            + "\n"
            + "[ 1 ] Manual     - Datos por Consola\n"
            + "[ 2 ] Importar   - Datos Archivo CSV\n"
            + "[ 3 ] Aleatorio  - Datos Aleatorios\n"
            + "\n"
            + "[ 0 ] Menú Anterior\n"
            + "\n"
            + "Opción: ";
    public static final String MNU_RELAC_OPC = "0123456";
    public static final String MNU_RELAC = "Agenda Escolar - Menú de Relaciones\n"
            + "===================================\n"
            + "\n"
            + "[ 1 ] Currículos - Asociación de Ciclos y Módulos\n"
            + "[ 2 ] Docencias  - Profesor + Módulo + Grupo + Aula\n"
            + "[ 3 ] Grupos     - Definición de Grupos de Docencia\n"
            + "[ 4 ] Matrículas - Docencia + Alumno\n"
            + "[ 5 ] Notas      - Alumno + Evaluación\n"
            + "[ 6 ] Horarios   - Docencia + Segmento Horario\n"
            + "\n"
            + "[ 0 ] Menú Anterior\n"
            + "\n"
            + "Opción: ";
    public static final String MNU_UTIL_OPC = "0123456";
    public static final String MNU_UTIL = "Agenda Escolar - Menú de Utilidades\n"
            + "===================================\n"
            + "\n"
            + "[ 1 ] Generación de Nombre\n"
            + "[ 2 ] Generación de Apellido\n"
            + "[ 3 ] Generación de Fecha de Nacimiento\n"
            + "[ 4 ] Generación de DNI\n"
            + "[ 5 ] Generación de Usuario\n"
            + "[ 6 ] Generación de Contraseña\n"
            + "\n"
            + "[ 0 ] Menú Anterior\n"
            + "\n"
            + "Opción: ";
}
