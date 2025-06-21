package com.edigest.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edigest.journalApp.dto.CommonRequestModel;
import com.edigest.journalApp.dto.ResponseHeaderModel;
import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.Users;
import com.edigest.journalApp.repository.JournalEntryRepo;
import com.edigest.journalApp.repository.UsersRepo;

@Service
public class JournalEntryService {	
	
	@Autowired
	private JournalEntryRepo journalEntryRepo;
	
	@Autowired
	private UsersRepo usersRepo;
	
	private Logger logger = LoggerFactory.getLogger(JournalEntryService.class);
	
	//get all entries by userName 
	@Transactional
	public ResponseEntity<Object> getJournalEntriesByUsername() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Users user = usersRepo.findByUsername(username);
			if(user == null) {
				ResponseHeaderModel headerModel = new ResponseHeaderModel();
				headerModel.setRespCode("404"); 
				headerModel.setStatusMsg("Failed");
				headerModel.setErr(HttpStatus.NOT_FOUND.toString());
				headerModel.setErrMsg("Journal Entries Not Found");
				logger.error("Journal Entries Not Found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(headerModel);
			}
			else {	
				List<JournalEntry> journalEntries = user.getJournalEntries();
				logger.debug("Journal entries fetched succesfully {}",journalEntries.toString());
				return ResponseEntity.status(HttpStatus.OK).body(journalEntries);
			}
		}
		catch(Exception e) {
			logger.error(e.toString());
			ResponseHeaderModel headerModel = new ResponseHeaderModel();
			headerModel.setRespCode("400"); 
			headerModel.setStatusMsg("Failed");
			headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
			headerModel.setErrMsg(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
		}		
	}
	
	//get by id
	@Transactional
	public ResponseEntity<Object> getJournalEntryById(CommonRequestModel requestModel) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Users users = usersRepo.findByUsername(authentication.getName());
			List<JournalEntry> journalEntries = users.getJournalEntries();
			for(JournalEntry journalEntry:journalEntries) {
				if(!journalEntry.getJournal_id().equals(requestModel.getId())) {
					ResponseHeaderModel headerModel = new ResponseHeaderModel();
					headerModel.setRespCode("404"); 
					headerModel.setStatusMsg("Failed");
					headerModel.setErr(HttpStatus.NOT_FOUND.toString());
					headerModel.setErrMsg("Journal Entry Not Found");
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(headerModel);
				}
			}
			JournalEntry journalEntry = journalEntryRepo.findById(requestModel.getId()).orElse(null);
			if(journalEntry == null) {
				ResponseHeaderModel headerModel = new ResponseHeaderModel();
				headerModel.setRespCode("404"); 
				headerModel.setStatusMsg("Failed");
				headerModel.setErr(HttpStatus.NOT_FOUND.toString());
				headerModel.setErrMsg("Journal Entry Not Found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(headerModel);
			}
			else {
				return ResponseEntity.status(HttpStatus.OK).body(journalEntry);
			}
		}
		catch(Exception e) {
			ResponseHeaderModel headerModel = new ResponseHeaderModel();
			headerModel.setRespCode("400"); 
			headerModel.setStatusMsg("Failed");
			headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
			headerModel.setErrMsg(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
		}
	}
	
	//save
	@Transactional
	public ResponseEntity<Object> saveJournalEntry(JournalEntry body){
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Users user = usersRepo.findByUsername(username);
			String journal_id = "JRN" + UUID.randomUUID().toString().replace("-", "").substring(0, 13);
			body.setJournal_id(journal_id);
			body.setDate(LocalDateTime.now());
			JournalEntry journalEntry = journalEntryRepo.save(body);
			if(journalEntry == null && user == null) {
				ResponseHeaderModel headerModel = new ResponseHeaderModel();
				headerModel.setRespCode("400"); 
				headerModel.setStatusMsg("Failed");
				headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
				headerModel.setErrMsg("Failed to create Journal Entry");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
			}
			else {
				user.getJournalEntries().add(journalEntry);
				usersRepo.save(user);
				return ResponseEntity.status(HttpStatus.CREATED).body(journalEntry);
			}			
		}
		catch(Exception e) {
			ResponseHeaderModel headerModel = new ResponseHeaderModel();
			headerModel.setRespCode("400"); 
			headerModel.setStatusMsg("Failed");
			headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
			headerModel.setErrMsg(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
		}
	}
	
	//update
	@Transactional
	public ResponseEntity<Object> updateJournalEnrty(JournalEntry body) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Users users = usersRepo.findByUsername(username);
			JournalEntry journalEntry = journalEntryRepo.findById(body.getJournal_id()).orElse(null);			
			if(journalEntry == null && users == null) {
				ResponseHeaderModel headerModel = new ResponseHeaderModel();
				headerModel.setRespCode("404"); 
				headerModel.setStatusMsg("Failed");
				headerModel.setErr(HttpStatus.NOT_FOUND.toString());
				headerModel.setErrMsg("Journal Entries Not Found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(headerModel);
			}
			else {
				journalEntry.setTitle(body.getTitle() == null || body.getTitle().equals("")
						? journalEntry.getTitle() : body.getTitle());
				journalEntry.setContent(body.getContent() == null || body.getContent().equals("")
						? journalEntry.getContent() : body.getContent());
				JournalEntry updated = journalEntryRepo.save(journalEntry);
				return ResponseEntity.status(HttpStatus.OK).body(updated);
			}
		}
		catch(Exception e) {
			ResponseHeaderModel headerModel = new ResponseHeaderModel();
			headerModel.setRespCode("400"); 
			headerModel.setStatusMsg("Failed");
			headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
			headerModel.setErrMsg(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
		}
	}
	
	//delete 
	@Transactional
	public ResponseEntity<Object> deleteJournalById(CommonRequestModel request) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Users users = usersRepo.findByUsername(username);
			boolean removed = users.getJournalEntries().removeIf(x->x.getJournal_id().equals(request.getId()));
			if(removed) {
				usersRepo.save(users);
				journalEntryRepo.deleteById(request.getId());
				return ResponseEntity.status(HttpStatus.OK).body("Deleted succesfully");
			}			
			return ResponseEntity.status(HttpStatus.OK).body("Journal Deletion failed");
		}
		catch(Exception e) {
			ResponseHeaderModel headerModel = new ResponseHeaderModel();
			headerModel.setRespCode("400"); 
			headerModel.setStatusMsg("Failed");
			headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
			headerModel.setErrMsg(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
		}
	}
		
}
