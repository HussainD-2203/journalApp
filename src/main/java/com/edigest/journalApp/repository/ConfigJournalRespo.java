package com.edigest.journalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigest.journalApp.entity.ConfigJournalAppEntity;

public interface ConfigJournalRespo extends MongoRepository<ConfigJournalAppEntity, String>{

}
