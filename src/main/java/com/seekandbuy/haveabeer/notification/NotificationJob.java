package com.seekandbuy.haveabeer.notification;

import java.util.List;

import com.seekandbuy.haveabeer.domain.Job;
import com.seekandbuy.haveabeer.domain.JobCharacteristic;
import com.seekandbuy.haveabeer.domain.CandidateUser;

public class NotificationJob extends Notification<CandidateUser, Job>{

	@Override
	public void sendNotification(Job product, List<CandidateUser> listOfUsers) {
		int whenNotification = 4;
		JobCharacteristic jobCharacteristic = product.getJobCharacteristic();
		
		for(CandidateUser u: listOfUsers) {
			int matchs = this.countMatchs(u.getJobCharacteristic(), jobCharacteristic);
			if(matchs>whenNotification) {
				sendMail.sendNotification("seekandbuyorganization@gmail.com",
						"12345678organization", u.getEmail(), "Seek and Buy", "Hey there! \n\n We have a new job for you =]");
			}
		}
		
	}
	
	private int countMatchs(JobCharacteristic charaUser, JobCharacteristic charaJob) {
		int equal = 0;
		
		if(charaUser.getEscolaridade().equals(charaJob.getEscolaridade()))
			equal += 1;
		if(charaUser.getArea().equals(charaJob.getArea()))
			equal += 1;
		if(charaUser.getCargo().equals(charaJob.getCargo()))
			equal += 1;
		if(charaUser.getIdioma().equals(charaJob.getIdioma()))
			equal += 1;
		
		return equal;
	}

}