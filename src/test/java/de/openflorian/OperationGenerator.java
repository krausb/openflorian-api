package de.openflorian;

/*
 * This file is part of Openflorian.
 *
 * Copyright (C) 2015 - 2018  Bastian Kraus
 *
 * Openflorian is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version)
 *
 * Openflorian is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Openflorian.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import de.openflorian.data.model.Operation;

import java.time.Instant;
import java.util.Date;

/**
 * Operation Payload Generator
 *
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class OperationGenerator {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Generate a test operation
     *
     * @return
     * @throws Exception
     */
    public static Operation generate() throws Exception{
        Operation o = new Operation();
        o.setId(123L);
        o.setBuzzword("Test Incident");
        o.setKeyword("Test");
        o.setIncurredAt(Date.from(Instant.now()));
        o.setCity("Vohburg");
        o.setStreet("Burgstr. 5");
        o.setObject("Feuerwehr Vohburg");
        o.setOperationNr("B123.9282763");
        o.setPositionLatitude(48.76944739);
        o.setPositionLongitude(11.61546439);

        return o;
    }

    /**
     * Generate {@link Operation} serialized JSON String
     *
     * @return
     * @throws Exception
     */
    public static String generateJson() throws Exception {
        return mapper.writeValueAsString(generate());
    }

    /**
     * Generate {@link Operation} serialized JSON String
     *
     * @param operation
     * @return
     * @throws Exception
     */
    public static String generateJson(Operation operation) throws Exception {
        return mapper.writeValueAsString(operation);
    }

}
