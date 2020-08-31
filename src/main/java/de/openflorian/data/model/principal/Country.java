package de.openflorian.data.model.principal;

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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Country DTO<br/>
 * <br/>
 * Prefilled Entity Table with all available countries in the world.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Entity
@Table(name="of_coredata_country")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Country implements Serializable {
	private static final long serialVersionUID = -8055415213056496902L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;

	@Column
	protected String iso2;
	
	@Column
	protected String shortName;
	
	@Column
	protected String longName;
	
	@Column
	protected String iso3;
	
	@Column
	protected String numcode;
	
	@Column
	protected String unMember;
	
	@Column
	protected String callingCode;
	
	@Column
	protected String cctld;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIso2() {
		return iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public String getNumcode() {
		return numcode;
	}

	public void setNumcode(String numcode) {
		this.numcode = numcode;
	}

	public String getUnMember() {
		return unMember;
	}

	public void setUnMember(String unMember) {
		this.unMember = unMember;
	}

	public String getCallingCode() {
		return callingCode;
	}

	public void setCallingCode(String callingCode) {
		this.callingCode = callingCode;
	}

	public String getCctld() {
		return cctld;
	}

	public void setCctld(String cctld) {
		this.cctld = cctld;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((callingCode == null) ? 0 : callingCode.hashCode());
		result = prime * result + ((cctld == null) ? 0 : cctld.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((iso2 == null) ? 0 : iso2.hashCode());
		result = prime * result + ((iso3 == null) ? 0 : iso3.hashCode());
		result = prime * result
				+ ((longName == null) ? 0 : longName.hashCode());
		result = prime * result + ((numcode == null) ? 0 : numcode.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result
				+ ((unMember == null) ? 0 : unMember.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Country))
			return false;
		Country other = (Country) obj;
		if (callingCode == null) {
			if (other.callingCode != null)
				return false;
		} else if (!callingCode.equals(other.callingCode))
			return false;
		if (cctld == null) {
			if (other.cctld != null)
				return false;
		} else if (!cctld.equals(other.cctld))
			return false;
		if (id != other.id)
			return false;
		if (iso2 == null) {
			if (other.iso2 != null)
				return false;
		} else if (!iso2.equals(other.iso2))
			return false;
		if (iso3 == null) {
			if (other.iso3 != null)
				return false;
		} else if (!iso3.equals(other.iso3))
			return false;
		if (longName == null) {
			if (other.longName != null)
				return false;
		} else if (!longName.equals(other.longName))
			return false;
		if (numcode == null) {
			if (other.numcode != null)
				return false;
		} else if (!numcode.equals(other.numcode))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		if (unMember == null) {
			if (other.unMember != null)
				return false;
		} else if (!unMember.equals(other.unMember))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return shortName;
	}
	
}
