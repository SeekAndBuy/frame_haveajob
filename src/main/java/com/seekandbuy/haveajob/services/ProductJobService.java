package com.seekandbuy.haveajob.services;

import com.seekandbuy.haveajob.dao.JobDao;
import com.seekandbuy.haveajob.domain.CandidateUser;
import com.seekandbuy.haveajob.domain.Job;
import com.seekandbuy.haveajob.exceptions.ProductNotFoundException;
import com.seekandbuy.haveajob.match.SearchJob;
import com.seekandbuy.haveajob.notification.NotificationJob;
import com.seekandbuy.haveajob.validator.ValidatorJob;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

@Service
public class ProductJobService extends GenericService<Job>
{	
	@Autowired
	private JobDao productDao;
	
	SearchJob searchJob = new SearchJob();
	
	public ProductJobService()
	{
		super.validateItem = new ValidatorJob();
	}
	
	public List<Job> listItemByUserCharacteristic(CandidateUser user, List<Job> allJobs){

		return searchJob.ListAllProductsByUser(user, allJobs);
	}
	
	@Override
	public List<Job> listItem()
	{
		return productDao.findAll();  
	}
	
	@Override
	public Optional<Job> findItem(Long id)
	{
		Optional<Job> job = productDao.findById(id);
		
		if(job == null)
		{
			throw new ProductNotFoundException("Job can not be found");
		}
		
		return job;
	}
	
	public boolean createItemAndNotifyUser(Job product, List<CandidateUser> listOfUsers) {
		NotificationJob notificationBeer = new NotificationJob();
		if(this.createItem(product)) {
			notificationBeer.sendNotification(product, listOfUsers);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean createItem(Job product) 
	{
		if(validateItem(product)) {
			product.setId(null); //Garantir que criaremos uma instância nova e não atualizaremos nenhuma	
			product.getJobCharacteristic().setId(null);
			productDao.save(product);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void deleteItem(Long id) 
	{
		try 
		{
			productDao.deleteById(id);
		}
		catch(EmptyResultDataAccessException e)
		{
			throw new ProductNotFoundException("Job can not be found");
		}
	}
	
	@Override
	public void updateItem(Job job)
	{
		verifyExistence(job);
		productDao.save(job);
	}
	
	@Override
	public void verifyExistence(Job job)
	{
		findItem(job.getId());
	}
	
	public List<Job> getJobByEmployerId(Long id) 
	{
		return productDao.getJobByEmployerId(id);
	}
	
}
