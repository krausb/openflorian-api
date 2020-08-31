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

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.apache.commons.codec.binary.Base64;

import de.openflorian.util.StringUtils;

/**
 * Classical XOR Cipher
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class XorCipher extends AbstractCipher {

	public XorCipher(String key) {
		super(key);
	}

	@Override
	public String encrypt(String plain) throws GeneralSecurityException {
		if (StringUtils.isEmpty(plain))
			throw new IllegalArgumentException("String AND Key may not be empty.");
		if (StringUtils.isEmpty(key))
			throw new IllegalStateException("Xor cipher key is not set.");

		char[] keychars = key.toCharArray();
		char[] targetchars = plain.toCharArray();

		int keycharsLength = keychars.length, targetcharsLength = targetchars.length;

		char[] newtarget = new char[targetcharsLength];

		for (int i = 0; i < targetcharsLength; i++)
			newtarget[i] = (char) (targetchars[i] ^ keychars[i % keycharsLength]);

		keychars = null;
		targetchars = null;

		try {
			return new String(new Base64().encode(new String(newtarget).getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new GeneralSecurityException(e);
		}
	}

	@Override
	public String decrypt(String target) throws GeneralSecurityException {
		if (StringUtils.isEmpty(target))
			throw new IllegalArgumentException("String AND Key may not be empty.");
		if (StringUtils.isEmpty(key))
			throw new IllegalStateException("Xor cipher key is not set.");

		char[] keychars = key.toCharArray();
		char[] targetchars = new String(Base64.decodeBase64(target.getBytes())).toCharArray();

		int keycharsLength = keychars.length, targetcharsLength = targetchars.length;

		char[] newtarget = new char[targetcharsLength];

		for (int i = 0; i < targetcharsLength; i++)
			newtarget[i] = (char) (targetchars[i] ^ keychars[i % keycharsLength]);

		keychars = null;
		targetchars = null;

		return new String(newtarget);
	}

}
