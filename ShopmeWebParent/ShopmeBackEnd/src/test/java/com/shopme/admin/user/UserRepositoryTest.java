package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = testEntityManager.find(Role.class, 2);
		User user = new User("namnd2@gmail.com","123456", "Nam2", "Nghiem Duc2");
		user.addRole(roleAdmin);
		User result = userRepository.save(user);
		
		assertThat(result.getId()).isGreaterThan(0);
	}
	
	
	@Test
	public void testCreateUserWithTwoRoles() {
		
		Role roleAdmin = new Role(2);
		Role roleEditor = new Role(10);
		User user = new User("namnd3@gmail.com","123456", "Nam3", "Nghiem Duc3");
		user.addRole(roleAdmin);
		user.addRole(roleEditor);
		User result = userRepository.save(user);
		
		assertThat(result.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUser() {
		Iterable<User> users = userRepository.findAll();
		users.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userNam = userRepository.findById(1L).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetail() {
		User userNam = userRepository.findById(1L).get();
		userNam.setEmail("namndUpdate@gmail.com");
		userNam.setEnabled(true);
		userRepository.save(userNam);
	}
	
	@Test
	public void testUpdateUserRole() {
		User userNam = userRepository.findById(1L).get();
		Role roleAdmin = new Role(2);
		Role roleEditor = new Role(10);
		userNam.getRoles().remove(roleAdmin);
		userNam.addRole(roleEditor);
		userRepository.save(userNam);
	}
	
	@Test
	public void testDeleteUser() {
		userRepository.deleteById(1L);
	}

}
