package com.seekandbuy.haveajob.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seekandbuy.haveajob.auth.Authentication;
import com.seekandbuy.haveajob.validator.Validator;

@Service
public abstract class GenericService<T> 
{
	public Validator<T> validateItem;
	
	public Authentication auth = new Authentication();
	
	public abstract List<T> listItem();
	 
	public abstract Optional<T> findItem(Long id);
	
	public abstract boolean createItem(T itensList); 
	
	public abstract void deleteItem(Long id);
	
	public abstract void updateItem(T item);
	
	public abstract void verifyExistence(T item);
	
	public boolean validateItem(T item)
	{
		return validateItem.validator(item);
	}
}
