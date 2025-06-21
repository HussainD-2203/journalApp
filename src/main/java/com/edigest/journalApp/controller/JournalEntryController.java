package com.edigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalApp.dto.CommonRequestModel;
import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.service.JournalEntryService;

@RestController	
@RequestMapping("/journalEntry")
public class JournalEntryController {
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	//get All
	@GetMapping("/getJournalEntriesByUsername")
	public ResponseEntity<Object> getJournalEntriesByUsername(){
		return journalEntryService.getJournalEntriesByUsername();	
	}
	
	//Get By Id
	@PostMapping("/getJournalEntryById")
	public ResponseEntity<Object> getJournalEntryById(@RequestBody CommonRequestModel requestModel){ 
		return journalEntryService.getJournalEntryById(requestModel);
	}
	
	//Save
	@PostMapping("/saveJournalEntry")
	public ResponseEntity<Object> saveJournalEntry(@RequestBody JournalEntry journalEntry){
		return journalEntryService.saveJournalEntry(journalEntry);
	}
	
	//Update
	@PostMapping("/updateJournalEnrty")
	public ResponseEntity<Object> updateJournalEnrty(@RequestBody JournalEntry journalEntry){
		return journalEntryService.updateJournalEnrty(journalEntry);
	}
	
	//Delete
	@DeleteMapping("/deleteJournalById")
	public ResponseEntity<Object> deleteJournalById(@RequestBody CommonRequestModel request){
		return journalEntryService.deleteJournalById(request);
	}
	
}
