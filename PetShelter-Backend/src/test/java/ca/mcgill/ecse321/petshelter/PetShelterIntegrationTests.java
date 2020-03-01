package ca.mcgill.ecse321.petshelter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;


import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import ca.mcgill.ecse321.petshelter.model.PersonRole;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PetShelterIntegrationTests {

	private final String APP_URL = "http://localhost:5432";
	private JSONObject response;
	private final String usernameUser = "TestUser";
	private final String passwordUser = "TestPassword";
	private final String usernameAdmin = "TestAdmin";
	private final String passwordAdmin = "Password123";
	private final String address = "123 rue Mcgill";
	private final int numberOfPetsCurrentlyOwned = 2;
	private final String typeOfLivingAccommodation = "I live in a condo";
	

	
	
	/*
	 * FR1 tests
	 * No need for testing of missing inputs, http throws errors automatically
	 */
	
	@Test 
	public void AtestCreateAppUser() {
		try{
			response = send("POST", APP_URL, "/appuser/" + usernameUser, "password=" + passwordUser +"&appUserRole=" + PersonRole.ADOPTER);
			assertEquals(usernameUser, response.getString("username"));
			assertEquals(passwordUser, response.getString("password"));
			assertEquals("ADOPTER", response.getString("appUserRole"));
			
		}catch(IllegalArgumentException |JSONException e) {
			fail();
		}	
	}
	
//	@Test
//	public void testCreateAppAdmin() {
//		try{
//			response = send("POST", APP_URL, "/appadmin/" + usernameAdmin, "password=" + passwordAdmin);
//			assertEquals(usernameAdmin, response.getString("username"));
//			assertEquals(passwordAdmin, response.getString("password"));
//			
//		}catch(IllegalArgumentException |JSONException e) {
//			fail();
//		}	
//		
//	}
	
	
	/*
	 * F2 tests
	 */
	
	@Test
	public void CtestLogInAppUser() {
		//user created in F1
		try{
			
			response = send("POST", APP_URL, "/loginuser/" + usernameUser, "password=" + passwordUser);
			assertEquals("Login Successful!", response.toString());
		}catch(IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test
	public void DtestLogOffUser() {
		try{
			response = send("PUT", APP_URL, "/logout/");
			assertEquals("Logout Successful!", response.toString());
			
		}catch(IllegalArgumentException e) {
			fail();
		}
	}
	
	
//	@Test
//	public void testLogInAppAdmin() {
//		//create admin first
//		try{
//			response = send("POST", APP_URL, "/loginadmin/" + usernameAdmin, "password=" + passwordAdmin);
//			
//			
//		}catch(IllegalArgumentException e) {
//			fail();
//		}
//	}
	
	
	
	
	@Test
	public void FtestLogOffAdmin() {
		
	}
	
	
	/*
	 * FR3 Tests
	 */
	
	@Test
	public void GtestCreateUserProfile() {
		CtestLogInAppUser();
		response = send("POST", APP_URL, "/userprofile/" + usernameUser, "address=" + address +"&hasExperienceWithPets=" + true
				+ "&numberOfPetsCurrentlyOwned"+ numberOfPetsCurrentlyOwned+"&typeOfLivingAccommodation" + typeOfLivingAccommodation);
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
		
		public JSONObject send(String type, String appURL, String path) {
			try {
				URL URL = new URL(appURL + path);
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
		
		public JSONObject send(String type, String appURL) {
			try {
				URL URL = new URL(appURL);
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
