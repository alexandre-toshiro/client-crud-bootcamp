package com.alexandretoshiro.clientcrud.services;

public class ClientNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ClientNotFoundException(String msg){
		super(msg);
	}

}
