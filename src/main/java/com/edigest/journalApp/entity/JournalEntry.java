package com.edigest.journalApp.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "journal_entry")
public class JournalEntry {
	
	@Id
	private String journal_id;
	
	@NonNull
	private String title;
	
	@NonNull
	private String content;
	
	private LocalDateTime date;
}
