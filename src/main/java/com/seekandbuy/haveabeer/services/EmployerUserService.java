package com.seekandbuy.haveabeer.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

//import com.seekandbuy.haveabeer.dao.CandidateUserDao;
import com.seekandbuy.haveabeer.dao.EmployerUserDao;
//import com.seekandbuy.haveabeer.domain.CandidateUser;
import com.seekandbuy.haveabeer.domain.EmployerUser;
import com.seekandbuy.haveabeer.domain.User;
import com.seekandbuy.haveabeer.exceptions.UserNotFoundException;

@Service
public class EmployerUserService extends GenericService<EmployerUser>
{	
	@Autowired
	private EmployerUserDao employerUserDao;
	
	@Override
	public List<EmployerUser> listItem()
	{	
		return employerUserDao.findAll();  
	}
	
	@Override
	public Optional<EmployerUser> findItem(Long id)
	{
		Optional<EmployerUser> user = employerUserDao.findById(id);
		
		if(user == null)
		{
			throw new UserNotFoundException("Employer can not be found");
		}
		
		return user;
	}
	
	public static <T> List<T> toList(Optional<T> option) 
	{
	    if (option.isPresent())
	        return Collections.singletonList(option.get());
	    else
	        return Collections.emptyList();
	}
		
	@Override
	public EmployerUser createItem(EmployerUser user) 
	{	
		user.setId(null); //Garantir que criaremos uma instância nova e não atualizaremos nenhuma
		//user.getJobCharacteristic().setId(null);		

		String password = user.getPassword();
				
		String token = auth.tokenizerPassword(password);
		user.setPassword(token);
		
		return employerUserDao.save(user);
	}
	
	@Override
	public void deleteItem(Long id) 
	{
		try 
		{
			employerUserDao.deleteById(id);
		}
		catch(EmptyResultDataAccessException e)
		{
			throw new UserNotFoundException("Employer can not be found");
		}
	}
	
	@Override
	public void updateItem(EmployerUser user)
	{
		verifyExistence(user);
		employerUserDao.save(user);
	}
	
	//Semântica melhor, só verifica existência 
	@Override
	public void verifyExistence(EmployerUser user)
	{
		findItem(user.getId());
	}	
		
	public User findUser(String password, String email)
	{
		User user = this.findUserByEmail(email);
		
		auth.findTokenByPassword(password);
		
		//Se o usuário é encontrado pelo email, então verifica a senha.
		//Se não houverem exceções, então retorna o usuário
		return user;	
	}
	
	private User findUserByEmail(String email)
	{
		User user;
		
		user = employerUserDao.findUserByEmail(email);
		
		if(user == null)
		{
			throw new UserNotFoundException("Employer can not be found");
		}		
		return user;
	}
}
