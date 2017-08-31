package com.iggroup.universityworkshopmw.domain.exceptions;

public class NoAvailableDataException extends Exception {

   public NoAvailableDataException (String message, Throwable cause) { super(message, cause); }
   public NoAvailableDataException (String message) { super(message); }

}
