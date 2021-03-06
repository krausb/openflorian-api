package de.openflorian.crypt;

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
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Crypt Cipher Key Generator
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class CipherKeyGenerator {

	private static Logger log = LoggerFactory.getLogger(CipherKeyGenerator.class);

	/**
	 * Generate Cipher Secret<br/>
	 * <br/>
	 * Secret is generated by Blowfish {@link KeyGenerator} and a system default
	 * {@link SecureRandom} provider and Base64 encoded afterwards.
	 * 
	 * @return Base64 encoded {@link SecureRandom} generated encryption key
	 * @throws GeneralSecurityException
	 */
	public static String generateKey() throws GeneralSecurityException {
		try {
			KeyGenerator gen = KeyGenerator.getInstance("Blowfish");
			gen.init(192, new SecureRandom());
			SecretKey key = gen.generateKey();

			return new String(new Base64().encode(key.getEncoded())).trim();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralSecurityException(e.getMessage(), e);
		}
	}

}
