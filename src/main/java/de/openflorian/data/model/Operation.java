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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.openflorian.OpenflorianGlobals;
import de.openflorian.util.StringUtils;

/**
 * {@link Operation} DTO
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Entity
@Table(name = "of_operation")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Operation implements Serializable {
	private static final long serialVersionUID = 718788446662271203L;

	public static final String TECHNICAL_ASSISTANCE_ZCLASS = "operation-ta";
	public static final String FIRE_ZCLASS = "operation-fire";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@Column
	protected String operationNr;

	@Column
	protected double positionLongitude = 0;

	@Column
	protected double positionLatitude = 0;

	@Column
	protected String object;

	@Column
	protected String street;

	@Column
	protected String crossway;

	@Column
	protected String city;

	@Column
	protected String priority;

	@Column
	protected String keyword;

	@Column
	protected String buzzword;

	@Lob
	protected String resourcesRaw;

	@Column
	protected Date incurredAt;

	@Column
	protected Date takenOverAt;

	@Column
	protected Date dispatchedAt;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(joinColumns = { @JoinColumn(name = "operation_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "operation_resource_id", referencedColumnName = "id") })
	protected List<OperationResource> resources;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOperationNr() {
		return operationNr;
	}

	public void setOperationNr(String operationNr) {
		this.operationNr = operationNr;
	}

	public double getPositionLongitude() {
		return positionLongitude;
	}

	public void setPositionLongitude(double positionLongitude) {
		this.positionLongitude = positionLongitude;
	}

	public double getPositionLatitude() {
		return positionLatitude;
	}

	public void setPositionLatitude(double positionLatitude) {
		this.positionLatitude = positionLatitude;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCrossway() {
		return crossway;
	}

	public void setCrossway(String crossway) {
		this.crossway = crossway;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getBuzzword() {
		return buzzword;
	}

	public void setBuzzword(String buzzword) {
		this.buzzword = buzzword;
	}

	public String getResourcesRaw() {
		return resourcesRaw;
	}

	public void setResourcesRaw(String resourcesRaw) {
		this.resourcesRaw = resourcesRaw;
	}

	public Date getIncurredAt() {
		return incurredAt;
	}

	public void setIncurredAt(Date incurredAt) {
		this.incurredAt = incurredAt;
	}

	public Date getTakenOverAt() {
		return takenOverAt;
	}

	public void setTakenOverAt(Date takenOverAt) {
		this.takenOverAt = takenOverAt;
	}

	public Date getDispatchedAt() {
		return dispatchedAt;
	}

	public void setDispatchedAt(Date dispatchedAt) {
		this.dispatchedAt = dispatchedAt;
	}

	public List<OperationResource> getResources() {
		return resources;
	}

	public void setResources(List<OperationResource> resources) {
		this.resources = resources;
	}

	public boolean isTechnicalAssistanceOperation() {
		if (!StringUtils.isEmpty(this.operationNr)) {
			if (this.operationNr.toLowerCase().startsWith("t"))
				return true;
		}
		return false;
	}

	public boolean isFireOperation() {
		if (!StringUtils.isEmpty(this.operationNr)) {
			if (this.operationNr.toLowerCase().startsWith("b"))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buzzword == null) ? 0 : buzzword.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((crossway == null) ? 0 : crossway.hashCode());
		result = prime * result + ((dispatchedAt == null) ? 0 : dispatchedAt.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((incurredAt == null) ? 0 : incurredAt.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((operationNr == null) ? 0 : operationNr.hashCode());
		long temp;
		temp = Double.doubleToLongBits(positionLatitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(positionLongitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((resources == null) ? 0 : resources.hashCode());
		result = prime * result + ((resourcesRaw == null) ? 0 : resourcesRaw.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((takenOverAt == null) ? 0 : takenOverAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Operation))
			return false;
		final Operation other = (Operation) obj;
		if (buzzword == null) {
			if (other.buzzword != null)
				return false;
		}
		else if (!buzzword.equals(other.buzzword))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		}
		else if (!city.equals(other.city))
			return false;
		if (crossway == null) {
			if (other.crossway != null)
				return false;
		}
		else if (!crossway.equals(other.crossway))
			return false;
		if (dispatchedAt == null) {
			if (other.dispatchedAt != null)
				return false;
		}
		else if (!dispatchedAt.equals(other.dispatchedAt))
			return false;
		if (id != other.id)
			return false;
		if (incurredAt == null) {
			if (other.incurredAt != null)
				return false;
		}
		else if (!incurredAt.equals(other.incurredAt))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		}
		else if (!keyword.equals(other.keyword))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		}
		else if (!object.equals(other.object))
			return false;
		if (operationNr == null) {
			if (other.operationNr != null)
				return false;
		}
		else if (!operationNr.equals(other.operationNr))
			return false;
		if (Double.doubleToLongBits(positionLatitude) != Double.doubleToLongBits(other.positionLatitude))
			return false;
		if (Double.doubleToLongBits(positionLongitude) != Double.doubleToLongBits(other.positionLongitude))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		}
		else if (!priority.equals(other.priority))
			return false;
		if (resources == null) {
			if (other.resources != null)
				return false;
		}
		else if (!resources.equals(other.resources))
			return false;
		if (resourcesRaw == null) {
			if (other.resourcesRaw != null)
				return false;
		}
		else if (!resourcesRaw.equals(other.resourcesRaw))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		}
		else if (!street.equals(other.street))
			return false;
		if (takenOverAt == null) {
			if (other.takenOverAt != null)
				return false;
		}
		else if (!takenOverAt.equals(other.takenOverAt))
			return false;
		return true;
	}

	/**
	 * Deserialize from json
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static Operation fromJson(String json) throws Exception {
		final ObjectMapper om = new ObjectMapper();
		final Operation o = om.readValue(json, Operation.class);
		return o;
	}

	/**
	 * Serialize to json
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toJson() throws Exception {
		final ObjectMapper om = new ObjectMapper();
		return om.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append(this.operationNr + " " + this.buzzword);
		if (this.incurredAt != null) {
			final SimpleDateFormat format = new SimpleDateFormat(OpenflorianGlobals.FORMAT_DATETIME);
			sb.append(", " + format.format(incurredAt));
		}
		return sb.toString();
	}

}
