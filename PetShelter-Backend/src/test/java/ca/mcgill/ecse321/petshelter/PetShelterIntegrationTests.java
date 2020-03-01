package ca.mcgill.ecse321.petshelter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import ca.mcgill.ecse321.petshelter.model.PersonRole;


public class PetShelterIntegrationTests {

	private final String APP_URL = "http://localhost:5432";
	private JSONObject response;
	private final String username = "TestUser";
	private final String password = "TestPassword";
	
	/*
	 * FR1 tests
	 */
	
	@Test 
	public void testCreateAppUser() {
		response = send("POST", APP_URL, "/appuser/" + username, "password=" + password +"&personRole=" + PersonRole.ADOPTER);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public JSONObject send(String type, String appURL, String path, String parameters) {
		try {
			URL URL = new URL(appURL + path + ((parameters == null) ? "" : ("?" + parameters)));
			System.out.println("Sending: " + URL.toString());
			HttpURLConnection c = (HttpURLConnection) URL.openConnection();
			c.setRequestMethod(type);
			c.setRequestProperty("Accept", "application/json");
			System.out.println(c.getContentType());
			if (c.getResponseCode() != 200) {
				throw new RuntimeException(URL.toString() + " Returned error: " + c.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((c.getInputStream())));
			String response = br.readLine();
			if (response.equals("true") || response.equals("false")) {
				JSONObject json = new JSONObject();
				json.put("boolean", response);
				c.disconnect();
				return json;
			} else {
				JSONObject json = new JSONObject(response);
				c.disconnect();
				return json;
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	
	
	
}
