package com.seekandbuy.haveajob.match;

import java.util.List;

import com.seekandbuy.haveajob.domain.CandidateUser;
import com.seekandbuy.haveajob.domain.Job;
import com.seekandbuy.haveajob.domain.JobCharacteristic;

//import com.seekandbuy.haveabeer.domain.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.Optional;

public class SearchJob extends SearchItems<CandidateUser, Job> {
	
	@Override
	public List<Job> ListAllProductsByUser(CandidateUser user, List<Job> allJobs) {

		JobCharacteristic characteristicUser = user.getJobCharacteristic();

		
		//classe para armazenar a tupla <product, quantidade de matchs>
		class CharacteristicAndMatchs{
			public Job job;
			public int matchValue; 
			
			public CharacteristicAndMatchs(Job job, int matchValue) {
				this.job = job;
				this.matchValue =matchValue;
			}
			public Job getJob() {
				return this.job;
			}
			
			public int getMatchValue() {
				return this.matchValue;
			}
		}
		
		class SortbyJob implements Comparator<Job> 
		{ 
		    // Used for sorting in ascending order of 
		    // roll number 
			@Override
			public int compare(Job j1, Job j2) {
				
				double value = j1.getJobCharacteristic().getSalario() - j2.getJobCharacteristic().getSalario();
				
				if(value < 0)
					return 1;
				if(value > 0)
					return -1;
				else
					return 0;
			} 
		}
		
		List<CharacteristicAndMatchs> characteristicMatchs = new ArrayList<CharacteristicAndMatchs>();
		List<Job> productsByUser = new ArrayList<Job>();
		
		Collections.sort(allJobs, new SortbyJob());
		
		for(Job p: allJobs) {
			
			JobCharacteristic characteristicProduct = (JobCharacteristic) p.getJobCharacteristic();
			int matchSize = this.countMatchs(characteristicUser, characteristicProduct);		
			
			CharacteristicAndMatchs matchCharacter = new CharacteristicAndMatchs(p, matchSize);
			characteristicMatchs.add(matchCharacter);
		}
		
		Collections.sort(characteristicMatchs, 
		Comparator.comparingInt(CharacteristicAndMatchs::getMatchValue).reversed()); //ordenando em ordem decrescente
		
		for(CharacteristicAndMatchs c : characteristicMatchs) //armazenando apenas os produtos em productByUser
		{
			productsByUser.add(c.getJob());
		}
		
		
		return productsByUser;
	}
	
	//Metodo auxiliar para contar os matchs das caracteristicas do produto com as do usuario
	private int countMatchs(JobCharacteristic charaUser, JobCharacteristic charaJob) {
		int equal = 0;
		
		if(charaUser.getEscolaridade().equals(charaJob.getEscolaridade()))
			equal += 2;
		if(charaJob.getSalario() <= charaUser.getSalario())
			equal +=1;
		if(charaUser.getArea().equals(charaJob.getArea()))
			equal += 3;
		if(charaUser.getCargo().equals(charaJob.getCargo()))
			equal += 4;
		if(charaUser.getIdioma().equals(charaJob.getIdioma()))
			equal += 1;
		
		return equal;
	}

}