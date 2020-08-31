package de.openflorian.geo;

/*
 * This file is part of Openflorian.
 * 
 * Copyright (C) 2016  Bastian Kraus
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

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.osgeo.proj4j.ProjCoordinate;

/**
 * Gauss Krueger 2 LatLong Conversion Strategy Test
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class GaussKruegerConversionStrategyTest {

	protected static List<ProjCoordinate> coordinateList;
	
	@BeforeClass
	public static void init() {
		coordinateList = new ArrayList<ProjCoordinate>();
		coordinateList.add(new ProjCoordinate(4471732.91, 5407213.88));
		coordinateList.add(new ProjCoordinate(4472124.71504356, 5404220.37675899));
		coordinateList.add(new ProjCoordinate(4470632.65064705, 5404841.38761476));
	}
	
	@Test
	public void testConversion() {
		
		GaussKruegerConversionStrategy strategy = new GaussKruegerConversionStrategy();
		for(int c=0; c < coordinateList.size(); c++) {
			ProjCoordinate target = strategy.convert(coordinateList.get(c));
			System.out.println("longitude: " + target.x + ", latitude: " + target.y);
		}
		
	}
	
}
