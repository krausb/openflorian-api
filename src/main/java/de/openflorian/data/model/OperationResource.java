package de.openflorian.data.model;

/*
 * This file is part of Openflorian.
 * 
 * Copyright (C) 2015  Bastian Kraus
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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import de.openflorian.data.model.principal.Station;
import de.openflorian.util.StringUtils;

/**
 * {@link OperationResource} DTO
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Entity
@Table(name = "of_operation_resource")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationResource implements Serializable {
	private static final long serialVersionUID = -878705966123966091L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@JoinColumn(name = "stationId", nullable = true, insertable = true, updatable = true)
	protected Station station;

	@Column
	private String callName;

	@Column
	private String licensePlate;

	@Column
	private String type;

	@Column
	private String description;

	@Column
	private String crew;

	@Column
	private boolean isExternal;

	@Transient
	private String purpose;

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		if (this.station != null && StringUtils.isEmpty(this.station.getName()))
			sb.append(this.station.getName() + " ");
		sb.append(this.callName);
		return this.callName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public String getCallName() {
		return callName;
	}

	public void setCallName(String callName) {
		this.callName = callName;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCrew() {
		return crew;
	}

	public void setCrew(String crew) {
		this.crew = crew;
	}

	public void setExternal(boolean val) {
		this.isExternal = val;
	}

	public boolean isExternal() {
		return this.isExternal;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPurpose() {
		return this.purpose;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callName == null) ? 0 : callName.hashCode());
		result = prime * result + ((crew == null) ? 0 : crew.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isExternal ? 1231 : 1237);
		result = prime * result + ((licensePlate == null) ? 0 : licensePlate.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OperationResource other = (OperationResource) obj;
		if (callName == null) {
			if (other.callName != null)
				return false;
		}
		else if (!callName.equals(other.callName))
			return false;
		if (crew == null) {
			if (other.crew != null)
				return false;
		}
		else if (!crew.equals(other.crew))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (isExternal != other.isExternal)
			return false;
		if (licensePlate == null) {
			if (other.licensePlate != null)
				return false;
		}
		else if (!licensePlate.equals(other.licensePlate))
			return false;
		if (station == null) {
			if (other.station != null)
				return false;
		}
		else if (!station.equals(other.station))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		}
		else if (!type.equals(other.type))
			return false;
		return true;
	}

}
