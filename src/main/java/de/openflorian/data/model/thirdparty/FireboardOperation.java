package de.openflorian.data.model.thirdparty;

/*
 * This file is part of Openflorian.
 *
 * Copyright (C) 2020  Bastian Kraus
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

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FireboardOperation implements Serializable {
    private static final long serialVersionUID = 718788446661243203L;

    protected String uniqueId;
    protected String externalId;

    protected final String source = "Openlorian 2.0";

    public static class BasicData {

        private String externalNumber;
        private String keyword;
        private String announcement;
        private String location;
        private String locationName;

        public static class GeoLocation {

        }

    }



}
