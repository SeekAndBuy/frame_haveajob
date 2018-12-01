package com.seekandbuy.haveabeer.notification;

import java.util.List;

import com.seekandbuy.haveabeer.domain.Job;
import com.seekandbuy.haveabeer.domain.JobCharacteristic;
import com.seekandbuy.haveabeer.domain.CandidateUser;

public class NotificationJob extends Notification<CandidateUser, Job>{

	@Override
	public void sendNotification(Job product, List<CandidateUser> listOfUsers) {
		int whenNotification = 2; //Se o produto tiver mais de dois pontos de interesse, o usuario sera notificado.
		JobCharacteristic jobCharacteristic = product.getJobCharacteristic();
		
		for(CandidateUser u: listOfUsers) {
			int matchs = this.countMatchs(u.getJobCharacteristic(), jobCharacteristic);
			if(matchs>whenNotification) {
				sendMail.sendNotification("seekandbuyorganization@gmail.com",
						"12345678organization", u.getEmail(), "Seek and Buy", "Hey there! \n\n We have a new product for you =]");
			}
		}
		
	}
	
	private int countMatchs(JobCharacteristic charaUser, JobCharacteristic charaJob) {
		int equal = 0;
		
		if(charaUser.getEscolaridade().equals(charaJob.getEscolaridade()))
			equal += 2;
		if(charaJob.getSalario() <= charaUser.getSalario())
			equal++;
		
		System.out.println(charaJob.getEscolaridade());
		System.out.println(charaJob.getSalario());
		System.out.println(equal);
		System.out.println();
		
		return equal;
	}

}
