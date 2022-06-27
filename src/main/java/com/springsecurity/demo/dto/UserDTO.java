package com.springsecurity.demo.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

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
public class UserDTO {

	@NotNull
	String fname;

	@NotNull
	String lname;

	@NotNull
	String email;

	@NotNull
	String password;

	@NotNull
	LocalDateTime datetime;

	Integer numOfPeople;

	String message;

}
