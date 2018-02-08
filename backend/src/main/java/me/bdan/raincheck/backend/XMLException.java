package me.bdan.raincheck.backend;

public class XMLException extends Exception {
	private static final long serialVersionUID = 4396014039129354259L;

	public XMLException(String msg) {
		super(msg);
	}

	public XMLException(String msg, Throwable e) {
		super(msg, e);
	}

}