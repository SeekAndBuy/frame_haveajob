package com.seekandbuy.haveabeer.validator;

//import com.seekandbuy.haveabeer.domain.CandidateUser;
import com.seekandbuy.haveabeer.domain.EmployerUser;

public class ValidatorEmployerUser implements Validator<EmployerUser>{

	@Override
	public boolean validator(EmployerUser employerUser) {
		
		if(employerUser.getName() == null)
			return false;
		
		if(employerUser.getCnpj() == null)
			return false;
		
		if(employerUser.getEmail() == null)
			return false;
		
		return true;
	}
}
