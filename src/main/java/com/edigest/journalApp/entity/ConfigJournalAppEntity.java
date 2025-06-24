package com.edigest.journalApp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="config_journal_app")
public class ConfigJournalAppEntity {
	
	@Id
	private String config_id; 
	private String key;
	private String value;
}
