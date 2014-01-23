package com.example.testplateautortuga;

@SuppressWarnings("serial")
public class CaseNonLibreException extends Exception {
	public CaseNonLibreException() {
		super("La case est deja utilisee");
	}
}
