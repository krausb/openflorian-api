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

import static org.junit.Assert.assertEquals;

import java.security.GeneralSecurityException;

import org.junit.Test;

/**
 * TestCase for Testing Blowfish Cipher
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class BlowfishCipherTest {

	private final String BLOWFISH_SECRET_1 = "asidfu2i3/23as9ef80yiuh{}@#0fas";
	private final String BLOWFISH_SECRET_2 = "87g8*&G*87[][90_]_09u09usd98fsd";
	
	private final String PLAIN_TEXT = "Husum: 'Neptun-Seehaus' (aufgeloest 2008)";
	
	private final String CRYPTED_TEST_1 = "LZwjH0Ldkfj7T9xWNO0dJXVlS7jnjNWd0vz5ExOPl0FXfS7_jySH2Z6yi0Q0AncS";
	private final String CRYPTED_TEST_2 = "U9tUuRk_hMnEF83jhtz-fzdAhA1101XHemhmRkYakskR13X4eJkGKobQjfhsagH7";
		
	@Test
	public void encryptPasswordTest() throws Exception {
		BlowfishCipher cipher = new BlowfishCipher(BLOWFISH_SECRET_1);

		System.out.println("Crypted String 1: " + cipher.encrypt(PLAIN_TEXT));
		assertEquals(CRYPTED_TEST_1, cipher.encrypt(PLAIN_TEXT));
		
		// test encryption of second blowfish secret
		cipher = new BlowfishCipher(BLOWFISH_SECRET_2);
		
		assertEquals(CRYPTED_TEST_2, cipher.encrypt(PLAIN_TEXT));
	}
	
	@Test
	public void decryptPasswordTest() throws GeneralSecurityException {
		BlowfishCipher cipher = new BlowfishCipher(BLOWFISH_SECRET_1);
		
		assertEquals(PLAIN_TEXT, cipher.decrypt(cipher.encrypt(PLAIN_TEXT)));

		cipher = new BlowfishCipher(BLOWFISH_SECRET_2);
		assertEquals(PLAIN_TEXT, cipher.decrypt(cipher.encrypt(PLAIN_TEXT)));
	}
	
	@Test
	public void cryptTextTest() throws Exception {
		BlowfishCipher cipher = new BlowfishCipher("EJmmvRnXEHZGbpVqaJdRWVb29jjzEppg");
		String pwd = cipher.encrypt(PLAIN_TEXT).trim();
		System.out.println("\"" + pwd + "\"");
		
		System.out.println("decrypted string: " + cipher.decrypt(pwd));
		
	}

}
