package com.masantes.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.masantes.shared.model.ContactForm;

public interface EmailServiceAsync {
	void sendEmail(ContactForm contactForm, AsyncCallback<Void> callback);
}
