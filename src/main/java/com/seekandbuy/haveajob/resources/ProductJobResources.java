/*Promotion Controller*/
package com.seekandbuy.haveajob.resources;

import java.net.URI;
import java.util.List;
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

import com.seekandbuy.haveajob.domain.CandidateUser;
import com.seekandbuy.haveajob.domain.Job;
import com.seekandbuy.haveajob.exceptions.ProductNotFoundException;
import com.seekandbuy.haveajob.exceptions.UserNotFoundException;
import com.seekandbuy.haveajob.services.CandidateUserService;
import com.seekandbuy.haveajob.services.ProductJobService;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins="http://localhost:4200")
public class ProductJobResources implements GenericResources<Job>
{
	@Autowired
	private ProductJobService productService;
	
	@Autowired
	private CandidateUserService userService;
	
	public ProductJobResources(ProductJobService productService, CandidateUserService userService) 
	{
		this.productService = productService;
		this.userService = userService;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Job>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(productService.listItem());
	}

	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createItem(@RequestBody Job product) {
		boolean createProduct = productService.createItem(product);
		
		if(!createProduct)	
		{
			System.out.println("XXXXXXXXXXXXXXX");
			return ResponseEntity.badRequest().build();
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
		path("/{id}").buildAndExpand(product.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/createandnotify/", method = RequestMethod.POST)
	public ResponseEntity<Void>  createAndNotify(@RequestBody Job product){
		List<CandidateUser> allUsers = null;	
		allUsers = userService.listItem();
		
		boolean createProduct = productService.createItemAndNotifyUser(product, allUsers);
		
		if(!createProduct)
			return ResponseEntity.badRequest().build();
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(product.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Job>> findItem(@PathVariable("id") Long id) {
		Optional<Job> job = null;
		try
		{
			job = productService.findItem(id);
		}catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(job);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id) {
		try
		{
			productService.deleteItem(id);
		}
		catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(@RequestBody Job product, @PathVariable("id") Long id) {
		product.setId(id); // Garantir que o que vai ser atualizado é o que está vindo na URI
		try
		{
			productService.updateItem(product);
		}
		catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/employer/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getJobByEmployerId(@PathVariable("id") Long id){
		List<Job> employerJobsOffered = null;
		
		try
		{
			employerJobsOffered = productService.getJobByEmployerId(id);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(employerJobsOffered);
	}
	
	@RequestMapping(value = "/bycharacteristics/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> findJobByCandidateCharacteristic(@PathVariable("id") Long id){
		List<Job> productsByCharacteristic = null;
		
		List<Job> allJobs = null;
		Optional<CandidateUser> candidate = null;		
		
		try
		{
			candidate = userService.findItem(id);
			CandidateUser user = (CandidateUser) candidate.get();
			
			allJobs = productService.listItem();
			
			productsByCharacteristic = productService.listItemByUserCharacteristic(user, allJobs);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(productsByCharacteristic);
	}
	
}


