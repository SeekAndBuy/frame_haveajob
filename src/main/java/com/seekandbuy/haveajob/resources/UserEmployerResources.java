package com.seekandbuy.haveajob.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seekandbuy.haveajob.domain.EmployerUser;
import com.seekandbuy.haveajob.domain.User;
import com.seekandbuy.haveajob.exceptions.UserNotFoundException;
import com.seekandbuy.haveajob.services.EmployerUserService;

@RestController
@RequestMapping("/employers")
@CrossOrigin(origins="http://localhost:4200")
public class UserEmployerResources implements GenericResources<EmployerUser> {
	
	@Autowired
	private EmployerUserService userService;
	
	public UserEmployerResources(EmployerUserService userService) 
	{
		this.userService = userService;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EmployerUser>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.listItem());
	}

	@Override
	@RequestMapping(method = RequestMethod.POST)	
	public ResponseEntity<Void> createItem(@RequestBody EmployerUser user) {
		boolean createProduct = userService.createItem(user);
		
		if(!createProduct)
			return ResponseEntity.badRequest().build();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<EmployerUser>> findItem(@PathVariable("id") Long id) {
		Optional<EmployerUser> user = null;
		try
		{
			user = userService.findItem(id);
		}catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id) {
		try
		{
			userService.deleteItem(id);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(@RequestBody EmployerUser user, @PathVariable("id") Long id) {
		user.setId(id); // Garantir que o que vai ser atualizado é o que está vindo na URI
		try
		{
			userService.updateItem(user);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<User> findUser(@RequestBody Map<String, Object> credential) {
		User user = null;
		
		String password = (String) credential.get("password");
		String email = (String) credential.get("email");
		
		try
		{
			user = userService.findUser(password, email);
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().build();
			
		}

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
}
