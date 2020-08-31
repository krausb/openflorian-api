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
public class XorCipherTest {

	private final String XOR_SECRET_1 = "asidfu2i3/23as9ef80yiuh{}@#0fas";
	private final String XOR_SECRET_2 = "87g8*&G*87[][90_]_09u09usd98fsd";
	
	private final String PLAIN_TEXT = "Husum: 'Neptun-Seehaus' (aufgeloest 2008)";
	
	private final String CRYPTED_TEST_1 = "KQYaEQtPEk59SkJHFB0UNgNdWBgcBk9bVSFWVgEEHw4WGhBGRwJZCwY=";
	private final String CRYPTED_TEST_2 = "cEIUTUccZw12UispLlcdDDg6WFgAQx5VWwVMXgEWCFdSFEwKFHcaAB4=";
		
	@Test
	public void cryptTextTest() throws Exception {
		String pwd = new XorCipher(XOR_SECRET_2).encrypt(PLAIN_TEXT);
		System.out.println("\"" + pwd + "\"");
		System.out.println("decrypted string: " + new XorCipher(XOR_SECRET_2).decrypt(pwd));
		
	}

}
