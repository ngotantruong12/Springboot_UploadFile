package ntt.com.example.demo_SpringBoot_Web.dto.respone;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResponseCategory extends BaseResponse {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private String imageName;

}
