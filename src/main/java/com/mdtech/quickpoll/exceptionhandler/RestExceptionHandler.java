package com.mdtech.quickpoll.exceptionhandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mdtech.quickpoll.dto.error.ErrorDetails;
import com.mdtech.quickpoll.dto.error.ValidationError;
import com.mdtech.quickpoll.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(
			ResourceNotFoundException rnfe, HttpServletRequest request
			) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(new Date().getTime());
		errorDetails.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetails.setDetail(rnfe.getMessage());
		errorDetails.setTitle("Resource Not Found");
		errorDetails.setDeveloperMessage(rnfe.getClass().getName());
		return new ResponseEntity<> (errorDetails, null, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDetails handleValidationError(MethodArgumentNotValidException manve, HttpServletRequest request) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(new Date().getTime());
		errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
		
		String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestPath == null) {
			requestPath = request.getRequestURI();
		}
		
		errorDetails.setDetail("Validation Failed");
		errorDetails.setTitle("Input validation failed");
		errorDetails.setDeveloperMessage(manve.getClass().getName());
		
		//Create validation error instance
		List<FieldError> fieldError = manve.getBindingResult().getFieldErrors();
		for (FieldError fe : fieldError) {
			List<ValidationError> validationErrorList = errorDetails.getErrors().get(fe.getField());
			if (validationErrorList == null) {
				validationErrorList = new ArrayList<ValidationError>();
				errorDetails.getErrors().put(fe.getField(), validationErrorList);
			}
			ValidationError validationError = new ValidationError();
			validationError.setCode(fe.getCode());
			validationError.setMessage(messageSource.getMessage(fe, null));
			validationErrorList.add(validationError);
		}
		return errorDetails;
	}
	
}
