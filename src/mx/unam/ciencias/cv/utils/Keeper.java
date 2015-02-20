package mx.unam.ciencias.cv.utils;

import java.beans.XMLEncoder;
import java.io.*;


/*
 * This file is part of tom
 *
 * Jonathan Andrade 2015
 *
 * tom is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * tom is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with tom. If not, see <http://www.gnu.org/licenses/>.
 */

/**
* Use to get the mean Value of a set of training and the values of sigma
* to categorize a new speciment
* (Ougth to be Generic)
*/

public class Keeper {

	public static String save(Object o) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		XMLEncoder xmlEncoder = null;
		try {
			xmlEncoder = new XMLEncoder(new BufferedOutputStream(stream));
			xmlEncoder.writeObject(o);
			xmlEncoder.close();
			return stream.toString("UTF-8");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static void saving(Object o, String path) {
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(path);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(o);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}

	public static Object read(String path) {
		Object o = null;
		try
	      {
	         FileInputStream fileIn = new FileInputStream(path);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         o =  in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	      }catch(ClassNotFoundException c)
	      {
	         c.printStackTrace();
	      }
	     return o;
	}

	

}