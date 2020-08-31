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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Blowfish Cipher Utility
 * 
 * Used cipher: Blowfish/ECB/PKCS5Padding
 * 
 * Requires Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction
 * Policy in the JVM/JRE for unlimited key length support.
 * 
 * See
 * http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166
 * .html See PROJECT_DIR/config/jce_policy-8.zip
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class BlowfishCipher extends AbstractCipher {

	private static Logger log = LoggerFactory.getLogger(BlowfishCipher.class);

	public BlowfishCipher(String key) {
		super(key);
	}

	@Override
	public String decrypt(String str) throws GeneralSecurityException {
		if (key == null || key.isEmpty())
			throw new IllegalStateException("The key is not set or is length=0.");

		if (str == null)
			return null;

		try {
			SecretKeySpec keySpec;
			keySpec = new SecretKeySpec(key.getBytes("UTF8"), "Blowfish");

			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);

			return new String(cipher.doFinal(Base64.decodeBase64(str.getBytes("UTF8"))), "UTF8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralSecurityException(e.getMessage(), e);
		}
	}

	@Override
	public String encrypt(String str) throws GeneralSecurityException {
		if (key == null || key.isEmpty())
			throw new IllegalStateException("The Blowfish Secret is not set or is length=0.");

		if (str == null)
			return null;

		try {
			SecretKeySpec keySpec;
			keySpec = new SecretKeySpec(key.getBytes("UTF8"), "Blowfish");

			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);

			return new String(new Base64().encode(cipher.doFinal(str.getBytes("UTF8")))).trim();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralSecurityException(e.getMessage(), e);
		}
	}

}
