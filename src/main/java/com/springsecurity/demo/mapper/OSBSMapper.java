package com.springsecurity.demo.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.springsecurity.demo.dto.UserDTO;
import com.springsecurity.demo.entity.UserEntity;

@Mapper
public interface OSBSMapper {

	OSBSMapper MAPPER = Mappers.getMapper(OSBSMapper.class);

	@Mappings({ 
		@Mapping(target = "fname", source = "firstName"), 
		@Mapping(target = "lname", source = "lastName"),
		@Mapping(target = "datetime", source = "date"), 
		@Mapping(target = "message", source = "specialReq") 
		})
	UserEntity userDtoToUserEntity(UserDTO userDTO);

	@InheritInverseConfiguration(name = "userDtoToUserEntity")
	UserDTO userEntityToUserDTO(UserEntity userEntity);

}
