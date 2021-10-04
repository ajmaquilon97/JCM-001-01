package com.jcm.controllers;


import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.google.gson.Gson;
import com.jcm.methods.Methods_JCM;

@RestControllerAdvice
@RequestMapping("/link")
public class ControllerJCM extends Methods_JCM {
	Gson gson = new Gson();
	
	@GetMapping("/contain/{code}")
	public ResponseEntity<?> gtListCity(@PathVariable String code) {
		var listCity = mth_gtListCity(code);
		return ResponseEntity.ok(gson.toJson(listCity));
	}

	@GetMapping("/{string}")
	public ResponseEntity<?> gtCodFrmStr(@PathVariable String string, HttpServletRequest request)
			throws URISyntaxException {
		HttpHeaders headers = new HttpHeaders();
		URI urlToSend = null;
		String[] values = null;
		String token = "";
		values = decryptCredentials(request.getHeader("Authorization"));
		var res = mth_gtCodFrmStr(string, values[0]);
		
		if (res.get("status").equals("S")) {
			token = createJWT(values[0] + ":" + res.get("code"));
			token = insertLog(values[0], res.get("code"), token);
			urlToSend = new URI("/link/contain/" + res.get("code"));
			headers.add("Set-Cookie", "jwt=" + token);
			saveDataLog(values,res,request,gson,string,token);
			return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).location(urlToSend).build();
		}
		saveDataLog(values,res,request,gson,string,token);
		return ResponseEntity.ok().build();
	}
}
