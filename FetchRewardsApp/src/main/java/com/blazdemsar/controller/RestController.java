package com.blazdemsar.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blazdemsar.domain.Account;
import com.blazdemsar.domain.Transaction;
import com.blazdemsar.service.AccountService;
import com.blazdemsar.service.TransactionService;

@Controller
public class RestController {

	@Autowired
	TransactionService transactionService;

	@Autowired
	AccountService accountService;
	
	/*
	 * addTransaction route
	 * 
	 * REST endpoint to add points to the account
	 * 
	 * Expects a request body in form of a JSON object such as :
	 * 
	 * {
     *		"accountId": 1,
     *		"payer": "DANNON",
     *		"points": 1000,
     *		"timestamp": "2020-11-02T14:00:00"
	 * }
	 * 
	 * NOTE: I had issues parsing the timestamp into a LocalDateTime
	 * so for purpose of completing the assignment I made the following change
	 * 		
	 * 		FROM:
	 * 		"timestamp": "2020-11-02T14:00:00Z"
	 * 
	 * 		TO:
	 *      "timestamp": "2020-11-02T14:00:00" (omitting Z)
	 */

	@RequestMapping(value = "addTransaction", method = RequestMethod.POST)
	public ResponseEntity<?> addTransaction(@RequestBody String request) {

		System.out.println("Inside RestController.addTransaction().....");

		JSONObject req = new JSONObject(request);

		Transaction t = new Transaction();

		int accId = req.getInt("accountId");

		Account a = accountService.findById(accId);

		if (a == null) {
			a = new Account();
			a.setAccountId(accId);
			a = accountService.save(a);
		}

		t.setAccount(a);
		t.setPayer(req.getString("payer"));
		t.setPoints(req.getInt("points"));
		LocalDateTime timestamp = LocalDateTime.parse(req.getString("timestamp"));
		t.setTimestamp(timestamp);

		Transaction transDb = transactionService.save(t);

		JSONObject response = new JSONObject();
		response.put("message", "Transaction saved!");

		return new ResponseEntity<String>(response.toString(), HttpStatus.CREATED);
	}
	
	/*
	 * spendPoints route
	 * 
	 * REST endpoint to spend points based on required criteria:
	 * 
	 * - oldest points first
	 * - no points should be negative
	 * 
	 * Expects a request body in form of a JSON object such as :
	 * 
	 * {
     *		"accountId": 1,
     *		"points": 1000,
	 * }
	 * 
	 * Returns a JSON object as an array:
	 * 
	 * [
	 * 		{ "payer": "DANNON", "points": -100 },
	 * 		{ "payer": "UNILEVER", "points": -200 },
	 * 		{ "payer": "MILLER COORS", "points": -4,700 }
	 * ]
	 */
	@RequestMapping(value = "spendPoints", method = RequestMethod.PUT)
	public ResponseEntity<?> spendPoints(@RequestBody String request) {

		System.out.println("Inside RestController.spendPoints().....");

		JSONObject req = new JSONObject(request);
		int accountId = req.getInt("accountId");
		int pointsToSpend = req.getInt("points");

		Account a = accountService.findById(accountId);

		if (a != null) {

			Map<LocalDate, Map<String, Integer>> oldestPoints = a.spendPoints(pointsToSpend);

			if (oldestPoints != null) {

				JSONArray response = new JSONArray();
				
				List<LocalDate> dates = new ArrayList<>();
				
				for (LocalDate d : oldestPoints.keySet()) {
					dates.add(d);
				}
				
				dates.sort((d1, d2) -> d1.compareTo(d2));

				for (LocalDate date : dates) {

					Map<String, Integer> temp = oldestPoints.get(date);
					
					for (String payer : temp.keySet()) {
						
						int ptsFirst = temp.get(payer);
						Transaction t = new Transaction();
												
						if (pointsToSpend > 0) {
							
							if (ptsFirst - pointsToSpend < 0) {

								t.setTimestamp(LocalDateTime.now());
								t.setAccount(a);
								t.setPayer(payer);
								t.setPoints(-ptsFirst);

								JSONObject jsonObj = new JSONObject();
								jsonObj.put(payer, -ptsFirst);

								response.put(jsonObj);

								pointsToSpend -= ptsFirst;

								transactionService.save(t);
							}
							else {

								t.setTimestamp(LocalDateTime.now());
								t.setAccount(a);
								t.setPayer(payer);
								t.setPoints(-pointsToSpend);

								JSONObject jsonObj = new JSONObject();
								jsonObj.put(payer, -pointsToSpend);

								response.put(jsonObj);

								pointsToSpend -= pointsToSpend;
								
								transactionService.save(t);
							}
						}
					}
				}
				
				return new ResponseEntity<String>(response.toString(), HttpStatus.ACCEPTED);
			}
			else {

				JSONObject response = new JSONObject();
				response.put("message", "Insufficient balance!");

				return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
			}

		}
		else {

			JSONObject response = new JSONObject();

			response.put("message", "Account with accountID = " + accountId + " does not exist!");

			return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
		}

	}
	
	/*
	 * checkBalance route
	 * 
	 * REST endpoint to check account balance:
	 * 
	 * Expects a request body in form of a JSON object such as :
	 * 
	 * {
     *		"accountId": 1,
	 * }
	 * 
	 * Returns a JSON object as follows:
	 * 
	 * {
	 * 		"UNILEVER":0,
	 * 		"MILLER COORS":5300,
	 * 		"DANNON":1000
	 * }
	 */
	@RequestMapping(value = "checkBalance", method = RequestMethod.GET)
	public ResponseEntity<?> checkBalance(@RequestBody String request) {

		System.out.println("Inside RestController.checkBalance().....");

		JSONObject req = new JSONObject(request);
		int accountId = req.getInt("accountId");

		Account a = accountService.findById(accountId);

		if (a != null) {

			Map<String, Integer> balance = a.getBalance();

			JSONObject response = new JSONObject();

			for (String key : balance.keySet()) {
				response.put(key, balance.get(key));
			}

			return new ResponseEntity<String>(response.toString(), HttpStatus.OK);

		}
		else {

			JSONObject response = new JSONObject();

			response.put("message", "Account with accountID = " + accountId + " does not exist!");

			return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);

		}
	}
}
