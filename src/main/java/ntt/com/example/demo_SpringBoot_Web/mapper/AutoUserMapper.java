package ntt.com.example.demo_SpringBoot_Web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ntt.com.example.demo_SpringBoot_Web.dto.respone.ResponseCategory;
import ntt.com.example.demo_SpringBoot_Web.entity.CategoryEntity;

@Mapper
public interface AutoUserMapper {
	 AutoUserMapper MAPPER = Mappers.getMapper(AutoUserMapper.class);
	 
	 ResponseCategory categoryEntityToCategoryDTO (CategoryEntity categoryEntity);
	 
}