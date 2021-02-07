package io.neonomics.codingchallenge.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import io.neonomics.codingchallenge.util.HeyHoUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class ApiRestController {

	@GetMapping(value = "/get-hey-ho")
	public ResponseEntity<?> getHeyHo() throws Exception {
		try {
			log.info("Request received. Running 'hey ho' operation...");

			// Simple util to help us get some dummy data
			var HeyHoList = HeyHoUtil.getHeyHo();


			// Get a JSON from a remote location
			StringBuffer content = new StringBuffer();
			URL url = new URL("https://jsonplaceholder.typicode.com/posts");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int status = con.getResponseCode();
			log.info("Response Code: {}", status);

			if (status == 200) {
				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
			} else {
				log.error("Error trying to get JSON content from remote location");
				throw new Exception();
			}

			con.disconnect();

			// Save JSON response to a file
			FileWriter file = new FileWriter("/root/service_b_remote_response.json");
			file.write(content.toString());


			return ResponseEntity.ok(HeyHoList);
		}catch (Exception e){
			return new ResponseEntity<String>(
					e.getLocalizedMessage(), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

}
