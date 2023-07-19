package ntt.com.example.demo_SpringBoot_Web.exception;


public class DuplicateEmailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuplicateEmailException(String message) {
        super(message);
    }
	
	

}
