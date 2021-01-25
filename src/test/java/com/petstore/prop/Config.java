/**
 * @author 
 * 
 * Config class contains configuration methods.
 */

package com.petstore.prop;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import org.testng.Assert;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class Config {

	private static Properties config;
	private static FileInputStream fis;
	private static String sep = File.separator;

	/**
	 * loadProperties methods loads properties file.
	 * 
	 * @return instance of Properties file
	 */
	public static Properties loadProperties() {
		try {
			if (config == null) {
				fis = new FileInputStream(System.getProperty("user.dir") + sep + "src" + sep + "test" + sep
						+ "resources" + sep + "properties" + sep + "configuration.properties");
				config = new Properties();
				config.load(fis);
			}
		} catch (Exception e) {
			Assert.assertTrue(false, "Error occured while loading config file" + e);
		}
		return config;
	}

	/**
	 * readJson --> Reads json Value as String
	 * 
	 * @param jsonName --> Name of json
	 * @return --> Json content as String
	 */
	public static String readJson(String jsonName) {
		try {
			URL file = Resources.getResource("jsonFolder/" + jsonName + ".json");
			String jsonString = Resources.toString(file, Charsets.UTF_8);
			return jsonString;
		} catch (Exception e) {

			Assert.assertTrue(false, "Error while altering json : " + e);
			return null;
		}

	}
}
