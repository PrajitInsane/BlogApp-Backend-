package com.prajit.blog.payloads;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	private int id;
	@NotBlank
	@Size(min=4,message = "Username must be min of 4 characters")
	private String name;
	
	@Email(message="Email address not valid")
	private String email;
	
	@NotBlank
	@Size(min=3,max=10,message="Password must be min 3 and max 10 characters")
	private String password;
	
	@NotBlank
	private String about;
}
