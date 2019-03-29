package io.jbc.libertyrxjava.application;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document
public class Account {


	@Id
	@JsonIgnore
	private String id;
	private String owner;
	private Double value;
}