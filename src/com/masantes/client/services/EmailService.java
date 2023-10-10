package com.masantes.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.masantes.shared.model.ContactForm;

@RemoteServiceRelativePath("email")
public interface EmailService extends RemoteService{
	void sendEmail(ContactForm contactForm);
}
