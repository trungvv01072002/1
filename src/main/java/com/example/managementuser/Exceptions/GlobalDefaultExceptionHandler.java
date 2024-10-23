//package com.example.managementuser.Exceptions;//package com.example.managementuser.Exceptions;
////
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.ControllerAdvice;
////import org.springframework.web.bind.annotation.ExceptionHandler;
////import org.springframework.web.bind.annotation.ResponseStatus;
////import org.springframework.web.bind.annotation.RestControllerAdvice;
////
////import java.security.SignatureException;
////// GlobalDefaultExceptionHandler.java
////@RestControllerAdvice
////class GlobalDefaultExceptionHandler {
////
////    @ExceptionHandler(Exception.class)
////    public ResponseEntity<?> handleException(Exception e) {
////        return ResponseEntity.badRequest().body(e.getMessage());
////    }
////
////    @ExceptionHandler(SignatureException.class)
////    @ResponseStatus(code = HttpStatus.FORBIDDEN)
////    public ResponseEntity<?> handleSignatureException(SignatureException e) {
////        return new ResponseEntity<>(new CustomException(e.getMessage(), HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
////    }
////
////    @ExceptionHandler(JwtValidationException.class)
////    public ResponseEntity<String> handleJwtValidationException(JwtValidationException e) {
////        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
////    }
////}
//
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalDefaultExceptionHandler {
////    @ExceptionHandler({ Exception.class})  // Có thể bắt nhiều loại exception
////    public ResponseEntity<String> handleExceptionA(Exception e) {
////        return ResponseEntity.status(432).body(e.getMessage());
////    }
//
//    // Có thêm các @ExceptionHandler khác...
//
//    // Nên bắt cả Exception.class
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleUnwantedException(Exception e) {
//        // Log lỗi ra và ẩn đi message thực sự (xem phần 3.2)
//        e.printStackTrace();  // Thực tế người ta dùng logger
//        return ResponseEntity.status(500).body("Unknow error");
//    }
//
//    @ExceptionHandler(JwtValidationException.class)
//    public ResponseEntity<String> handleJwtValidationException(JwtValidationException e) {
//        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
//    }
//}


package com.example.managementuser.Exceptions;

import io.jsonwebtoken.MalformedJwtException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.security.SignatureException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(Exception e) {
        return ResponseEntity.badRequest().body("e.getMessage()");
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleSignatureException(SignatureException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }


}