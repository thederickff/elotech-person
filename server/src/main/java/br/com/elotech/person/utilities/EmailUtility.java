package br.com.elotech.person.utilities;

public class EmailUtility {
  
  private EmailUtility()
  {
  }

  public static boolean isValid(String email) {
    String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    return email.matches(regex);
  }
}
