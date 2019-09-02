package com.igeek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalAuth {
	private Long localAuthId;
	private String username;
	private String password;
	private Date createTime;
	private Date lastEditTime;
	private PersonInfo personInfo;
}
