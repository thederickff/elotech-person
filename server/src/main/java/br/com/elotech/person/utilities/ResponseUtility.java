package br.com.elotech.person.utilities;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ResponseUtility {
  
  private ResponseUtility()
  {
  }

  public static ResponseEntity<MessageResponse> ok()
  {
    return ResponseEntity.ok(new MessageResponse("Ok", 200));
  }

  public static ResponseEntity<MessageResponse> exception(Exception e)
  {
    return ResponseEntity.status(400).body(new MessageResponse(e.getMessage(), 400));
  }

  @Data
  @AllArgsConstructor
  public static class MessageResponse {
    private String message;
    private int status;
  }
}
