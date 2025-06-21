package com.edigest.journalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigest.journalApp.entity.JournalEntry;

public interface JournalEntryRepo extends MongoRepository<JournalEntry, String>{

}
