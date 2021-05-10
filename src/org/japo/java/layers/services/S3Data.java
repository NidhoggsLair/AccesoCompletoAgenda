/*
 * Copyright 2021 José A. Pacheco Ondoño - japolabs@gmail.com.
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

import org.japo.java.entities.Credencial;
import org.japo.java.exceptions.ConnectivityException;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public interface S3Data {

    public void abrirAccesoDatos(Credencial c)
            throws ConnectivityException;

    public void cerrarAccesoDatos() throws ConnectivityException;

    // ---
}
