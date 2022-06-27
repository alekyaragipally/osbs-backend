package com.springsecurity.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "firstName", nullable = false)
	String firstName;

	@Column(name = "lastName", nullable = false)
	String lastName;

	@Column(name = "email", nullable = false)
	String email;

	@Column(name = "password", nullable = false)
	String password;

	@Column(name = "date", nullable = false)
	LocalDateTime date;

	@Column(name = "numOfPeople", nullable = true)
	Integer numOfPeople;

	@Column(name = "specialReq", nullable = true)
	String specialReq;
	
	@Column(name = "isEnabled", nullable = false)
	Boolean isEnabled;

}
