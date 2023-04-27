package com.users;

import com.users.domain.Roles;
import com.users.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class UsersApplication  implements ApplicationRunner {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	private final RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Roles roles1 = Roles.builder().role("ADMIN").build();
		Roles roles2 = Roles.builder().role("USER").build();
		roleRepository.save(roles1);
		roleRepository.save(roles2);
	}
}
