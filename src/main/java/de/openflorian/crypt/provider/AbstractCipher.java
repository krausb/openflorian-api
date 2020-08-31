package de.openflorian.crypt.provider;

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

import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Cipher<br/>
 * <br/>
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public abstract class AbstractCipher {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected final String key;
	
	public AbstractCipher(String key) {
		this.key = key;
	}
	
	protected String getKey() {
		return this.key;
	}
	
	/**
	 * Decrypt given <code>str</code>
	 * 
	 * @param crypted string
	 * @return 
	 * 		{@link String} Decrypted String (UTF-8)
	 * @throws GeneralSecurityException 
	 */
	public abstract String decrypt(String crypted) throws GeneralSecurityException;
	
	/**
	 * Blowfish encrypt String<br/>
	 * 
	 * @param str plain text
	 * @return 
	 * 		{@link String} Encrypted string (UTF-8)
	 * @throws GeneralSecurityException 
	 */
	public abstract String encrypt(String plain) throws GeneralSecurityException;
	
}
