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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link Department} DTO
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Entity
@Table(name="of_coredata_department")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Department implements Serializable {
	private static final long serialVersionUID = 2091257586575485664L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;

	@Column
	protected String name;

	@Column
	protected String url;
	
	@Column
	protected String contactFirstname;
	
	@Column
	protected String contactLastname;
	
	@Column
	protected String street;
	
	@Column
	protected Integer zip;
	
	@Column
	protected String city;
	
    @OneToOne
    @JoinColumn(name = "countryId", nullable = false, insertable=true, updatable=true)
	protected Country country;
	
	@Column
	protected String vatid;
	
	@Column
	protected String email;
	
	@Column
	protected String phone;
	
	@Column
	protected String fax;

    @Column
    protected Double geoLatitude = 0.0;
    
    @Column
    protected Double geoLongitude = 0.0;
    
    @Column
    protected boolean isActive = false;
	
	@Column
	private String token;
	
	@Column
	private String tokenSecret;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContactFirstname() {
		return contactFirstname;
	}

	public void setContactFirstname(String contactFirstname) {
		this.contactFirstname = contactFirstname;
	}

	public String getContactLastname() {
		return contactLastname;
	}

	public void setContactLastname(String contactLastname) {
		this.contactLastname = contactLastname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getVatid() {
		return vatid;
	}

	public void setVatid(String vatid) {
		this.vatid = vatid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Double getGeoLatitude() {
		return geoLatitude;
	}

	public void setGeoLatitude(Double geoLatitude) {
		this.geoLatitude = geoLatitude;
	}

	public Double getGeoLongitude() {
		return geoLongitude;
	}

	public void setGeoLongitude(Double geoLongitude) {
		this.geoLongitude = geoLongitude;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime
				* result
				+ ((contactFirstname == null) ? 0 : contactFirstname.hashCode());
		result = prime * result
				+ ((contactLastname == null) ? 0 : contactLastname.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result
				+ ((geoLatitude == null) ? 0 : geoLatitude.hashCode());
		result = prime * result
				+ ((geoLongitude == null) ? 0 : geoLongitude.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result
				+ ((tokenSecret == null) ? 0 : tokenSecret.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((vatid == null) ? 0 : vatid.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Department))
			return false;
		Department other = (Department) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (contactFirstname == null) {
			if (other.contactFirstname != null)
				return false;
		} else if (!contactFirstname.equals(other.contactFirstname))
			return false;
		if (contactLastname == null) {
			if (other.contactLastname != null)
				return false;
		} else if (!contactLastname.equals(other.contactLastname))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (geoLatitude == null) {
			if (other.geoLatitude != null)
				return false;
		} else if (!geoLatitude.equals(other.geoLatitude))
			return false;
		if (geoLongitude == null) {
			if (other.geoLongitude != null)
				return false;
		} else if (!geoLongitude.equals(other.geoLongitude))
			return false;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (tokenSecret == null) {
			if (other.tokenSecret != null)
				return false;
		} else if (!tokenSecret.equals(other.tokenSecret))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (vatid == null) {
			if (other.vatid != null)
				return false;
		} else if (!vatid.equals(other.vatid))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Deserialize from json
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static Department fromJson(String json) throws Exception {
		ObjectMapper om = new ObjectMapper();
		Department d = om.readValue(json, Department.class);
	    return d;
	}
	
	/**
	 * Serialize to json
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toJson() throws Exception {
		ObjectMapper om = new ObjectMapper();
		return om.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}
	
}
