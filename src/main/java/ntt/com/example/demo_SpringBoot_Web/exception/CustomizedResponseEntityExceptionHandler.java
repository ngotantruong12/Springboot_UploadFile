package ntt.com.example.demo_SpringBoot_Web.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override //"name" : aaaaaaaa,// thieu dau nhay'
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		 List<String> details = new ArrayList<String>();
         details.add(ex.getMessage());
         ErrorDetails errorDetails = new ErrorDetails(
             LocalDateTime.now(),
             "Malformed JSON request" ,
             details);
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
    @Override // check param
//    @ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    	List<String> details = new ArrayList<String>();
        details.add(ex.getParameterName() + " parameter is missing");

        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            "Missing Parameters" ,
            details);
        return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	//http://localhost:8080/studentsxsss // them config trong file properties
	// tat ca exception deu bi
	@Override //handleNoHandlerFoundException
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		List<String> details = new ArrayList<String>();
        details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        
        ErrorDetails errorDetails = new ErrorDetails( LocalDateTime.now(),"Method Not Found", details);
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	// validation
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//		System.out.println(ex);
//		Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", new Date());
//        body.put("status", status.value());
//		System.out.println(ex.getBindingResult().getFieldErrors().toString());
		//ex.getBindingResult().toString());
		 List<String> errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Validation Failed",
				errors);
        //Get all fields errors
       

//        body.put("errors", errors);

		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	} 
	 
	
	
//	@Override // neu khac kieu json thi bao loi
//	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
//			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//		List<String> details = new ArrayList<String>();
//        StringBuilder builder = new StringBuilder();
//        builder.append(ex.getContentType());
//        builder.append(" media type is not supported. Supported media types are ");
//        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));     
//        details.add(builder.toString());
//        
//        ErrorDetails errorDetails = new ErrorDetails(
//            LocalDateTime.now(), 
//            "Unsupported Media Type" ,
//            details);
//        
//		return new ResponseEntity<Object>(errorDetails,HttpStatus.UNSUPPORTED_MEDIA_TYPE);
//	}
	
	// check type id tren url, type int but nhap string ???
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException ex,
        WebRequest request) {
        
        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
      
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),            
            "Type Mismatch" ,
            details);
        
        return new ResponseEntity<Object>(errorDetails,HttpStatus.BAD_REQUEST ); 
    }

	// check type id tren url > 0 // chua fix dc
	  @ExceptionHandler(ConstraintViolationException.class)
      public ResponseEntity<?> handleConstraintViolationException(
          Exception ex, 
          WebRequest request) {
          
          List<String> details = new ArrayList<String>();
          details.add(ex.getMessage());
          
          ErrorDetails errorDetails = new ErrorDetails(
              LocalDateTime.now(),             
              "Constraint Violations" ,
              details);          
          return new ResponseEntity<Object>(errorDetails,HttpStatus.BAD_REQUEST );
      }
//	found
	 @ExceptionHandler(NotFoundException.class)
	  public final ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	 
	 // all
	 @ExceptionHandler(Exception.class)
	 public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
	   ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
	       request.getDescription(false));
	   return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	 
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
//        List<String> details = new ArrayList<String>();
//        details.add(ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            "Duplicate Values",
            ex.getMessage());
//		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
