package com.edigest.journalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edigest.journalApp.entity.ConfigJournalAppEntity;
import com.edigest.journalApp.repository.ConfigJournalRespo;

import jakarta.annotation.PostConstruct;

@Service
public class AppCache {
	
	@Autowired
	private ConfigJournalRespo configJournalRespo;
	
	public Map<String,String> app_cache;
	
	//this method will be automatically called when this class bean is loaded 
	@PostConstruct
	public void init() {
		app_cache = new HashMap<>();
		List<ConfigJournalAppEntity> configJournalAppEntities = configJournalRespo.findAll();
		for(ConfigJournalAppEntity configJournalAppEntity:configJournalAppEntities) {
			app_cache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
		}
	}
	
}
