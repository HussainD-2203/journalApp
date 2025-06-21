package com.edigest.journalApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHeaderModel {
	private String ts;
	private String txn;
	private String respCode;
	private String statusMsg;
	private String err;
	private String errMsg;
	private String info;
}
