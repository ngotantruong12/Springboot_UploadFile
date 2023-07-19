package ntt.com.example.demo_SpringBoot_Web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
//@Entity
public class RequestCategory  {

	@NotNull( message = "Name must not NULL !")
	@NotBlank( message = "Name must not Blank !")
    @Size(min = 2, message = "Name should have at least 2 characters")
	private String name;
	
	@NotNull( message = "Description must not NULL !")
	@NotBlank( message = "Description must not Blank !")
	@Size(min = 2, message = "Description should have at least 2 characters")
	private String description;
}
