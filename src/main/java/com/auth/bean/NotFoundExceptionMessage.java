package com.auth.bean;

public class NotFoundExceptionMessage extends RuntimeException {

	private static final long serialVersionUID=1L;


	public NotFoundExceptionMessage() {
      super();
	}

	public NotFoundExceptionMessage(String msg) {
		super(msg);
	}

}
