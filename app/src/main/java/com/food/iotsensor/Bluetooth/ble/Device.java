package com.food.iotsensor.Bluetooth.ble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Device {

	public String cardid, rfidnumber, owner, address, location, cellular,
			email, kind, brand, typenumber, resolution, datever, daterever;

	public Device() {

	}

	public Device(String json) {
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj = (JSONObject) jsonArray.opt(i);
				cardid = jsonObj.getString("cardid");
				rfidnumber = jsonObj.getString("rfidnumber");
				owner = jsonObj.getString("owner");
				address = jsonObj.getString("address");
				location = jsonObj.getString("location");
				cellular = jsonObj.getString("cellular");
				email = jsonObj.getString("email");
				kind = jsonObj.getString("kind");
				brand = jsonObj.getString("brand");
				typenumber = jsonObj.getString("typenumber");
				resolution = jsonObj.getString("resolution");
				datever = jsonObj.getString("datever");
				daterever = jsonObj.getString("daterever");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public Map toMap() {
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("cardid", cardid);
		parmas.put("rfidnumber", rfidnumber);
		parmas.put("owner", owner);
		parmas.put("address", address);
		parmas.put("location", location);
		parmas.put("cellular", cellular);
		parmas.put("email", email);
		parmas.put("kind", kind);
		parmas.put("brand", brand);
		parmas.put("typenumber", typenumber);
		parmas.put("resolution", resolution);
		parmas.put("datever", datever);
		parmas.put("daterever", daterever);
		return parmas;
	}

	public String getRfidnumber() {
		return rfidnumber;
	}

	public void setRfidnumber(String rfidnumber) {
		this.rfidnumber = rfidnumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getTypenumber() {
		return typenumber;
	}

	public void setTypenumber(String typenumber) {
		this.typenumber = typenumber;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getDatever() {
		return datever;
	}

	public void setDatever(String datever) {
		this.datever = datever;
	}

	public String getDaterever() {
		return daterever;
	}

	public void setDaterever(String daterever) {
		this.daterever = daterever;
	}

	public static void main(String[] args) {
	}

}
