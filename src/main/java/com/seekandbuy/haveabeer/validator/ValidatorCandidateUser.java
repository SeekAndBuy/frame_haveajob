package com.seekandbuy.haveabeer.validator;

import com.seekandbuy.haveabeer.domain.CandidateUser;

public class ValidatorCandidateUser implements Validator<CandidateUser>{

	@Override
	public boolean validator(CandidateUser candidateUser) {
		
		if(candidateUser.getJobCharacteristic() == null)
			return false;
		
		if(candidateUser.getName() == null)
			return false;
		
		if(candidateUser.getCpf() == null)
			return false;
		
		if(candidateUser.getEmail() == null)
			return false;
		
		return true;
	}
}
