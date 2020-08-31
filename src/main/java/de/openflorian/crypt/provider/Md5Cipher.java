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

import java.security.MessageDigest;

import org.apache.commons.lang3.NotImplementedException;

/**
 * Classical MD5 Cipher<br/>
 * <br/>
 * Creates a MD5 Hex Hash from given data.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class Md5Cipher extends AbstractCipher{

	public Md5Cipher() {
		super("");
	}
	
	@Override
	public String encrypt(String plaintext) {
		
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		if(md != null) {
			md.update(plaintext.getBytes());
			byte[] data = md.digest();
			
			StringBuffer out = new StringBuffer();
			for(int c=0; c < data.length; c++) 
				out.append(Integer.toString((data[c] & 0xff) + 0x100, 16).substring(1));
			
			return out.toString();
		}
		
		throw new IllegalStateException("MD5 MessageDigest not present");
	}
	
	@Override
	public String decrypt(String crypted) {
		throw new NotImplementedException(Md5Cipher.class.getName());
	}
	
}
