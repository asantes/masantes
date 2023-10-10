package com.masantes.shared.validation;

import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;
import com.masantes.shared.model.ContactForm;

public class MyValidatorFactory extends AbstractGwtValidatorFactory {

	@GwtValidation({ContactForm.class})
	public interface GWTValidator extends Validator{
	}
	
	@Override
	public AbstractGwtValidator createValidator() {
		return GWT.create(GWTValidator.class);
	}
}
