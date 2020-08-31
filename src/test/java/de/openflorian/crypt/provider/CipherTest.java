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

import org.junit.Test;

/**
 * TestCase for Testing {@link XorCipher}
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class CipherTest {

	private final String BLOWFISH_SECRET = "12e9812he98fa9h21e98f(*Wehr9283898h9a8f98232398f";
	private final String XOR_SECRET = "viufdgb8ieu87*G&*&GIUGIUygjygjHjhBj788y7giYGkkjs";
	
	private final String PLAIN_TEXT = "test";
	
	@Test
	public void cryptTextTest() throws Exception {
		String crypted = new BlowfishCipher(BLOWFISH_SECRET).encrypt(new XorCipher(XOR_SECRET).encrypt(PLAIN_TEXT));
		System.out.println("\"" + crypted + "\"");
		System.out.println("decrypted string: " + new XorCipher(XOR_SECRET).decrypt(new BlowfishCipher(BLOWFISH_SECRET).decrypt(crypted)));
		
	}

}
