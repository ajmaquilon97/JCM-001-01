package com.jcm.methods;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcm.db.Conexion;
import com.jcm.models.City;
import com.jcm.models.LogJson;

public class Methods_JCM {

	public Map<String, String> mth_gtCodFrmStr(String nameCountry, String nameUser) {

		Map<String, String> res = new HashMap<String, String>();
		res.put("status", "N");
		res.put("code", "");

		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			connection = Conexion.connectDatabase();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT public.call_cod_prv_frm_str('" + nameCountry + "','" + nameUser + "')");
			while (rs.next()) {
				if (rs.wasNull() || rs.getString(1).equals("0")) {
					res.replace("status", "N");
				} else {
					res.replace("status", "S");
					res.replace("code", rs.getString(1));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return res;

	}

	public List<City> mth_gtListCity(String codeProvincia) {

		List<City> listCity = new ArrayList<>();

		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		City city = null;
		try {
			connection = Conexion.connectDatabase();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * from public.call_data_cd_frm_cod('" + codeProvincia + "')");
			while (rs.next()) {
				city = new City();
				city.setEntCod(rs.getString(1).trim());
				city.setEntNombre(rs.getString(2).trim());
				city.setEntCodProvincia(rs.getString(3).trim());
				listCity.add(city);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return listCity;
	}

	public String insertLog(String nameUser, String cod, String token) {
		Connection connection = null;
		ResultSet rs = null;
		CallableStatement call = null;
		String result = "";
		try {
			connection = Conexion.connectDatabase();
			call = connection.prepareCall("{? = call insert_log(?, ? , ?) }");
			call.registerOutParameter(1, Types.VARCHAR);
			call.setString(2, nameUser);
			call.setString(3, cod);
			call.setString(4, token);
			call.execute();
			result = call.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				call.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}

	public static String createJWT(String key) {
		String token = "";
		try {
			Algorithm algorithm = Algorithm.HMAC256(key);
			token = JWT.create().withIssuer("auth0").sign(algorithm);
		} catch (JWTCreationException exception) {
			// Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;
	}

	public String[] decryptCredentials(String authorization) {

		// Authorization: Basic base64credentials
		String base64Credentials = authorization.substring("Basic".length()).trim();
		byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
		String credentials = new String(credDecoded, StandardCharsets.UTF_8);
		// credentials = username:password
		return credentials.split(":", 2);
	}

	public void saveDataLog(String[] credentials, Map<String, String> res, HttpServletRequest request, Gson gson,
			String string, String token) {
		List<LogJson> listJsons = new ArrayList<>();
		int lastiIdApi = -1;
		try {
			Reader reader = Files.newBufferedReader(Paths.get("clicks.json"));
			listJsons = gson.fromJson(reader, new TypeToken<List<LogJson>>() {
			}.getType());
			gson.fromJson(reader, new TypeToken<List<LogJson>>() {
			}.getType());
			lastiIdApi = listJsons.get(listJsons.size() - 1).getIdApi();
			reader.close();
		} catch (Exception e) {
			lastiIdApi = -1;
			e.printStackTrace();
		}
		LogJson logJson = new LogJson();
		logJson.setIdApi(lastiIdApi + 1);
		logJson.setIpRemote(request.getRemoteHost());
		logJson.setUserRemote(credentials[0]);
		logJson.setPassRemote(credentials[1]);
		logJson.setString(string);
		logJson.setResult(res.get("status"));
		logJson.setCodProv(res.get("code"));
		logJson.setBrowserRemote(request.getHeader("User-Agent"));
		logJson.setKey(token);
		listJsons.add(logJson);
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("clicks.json"));
			gson.toJson(listJsons, writer);
			writer.append("\n");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
