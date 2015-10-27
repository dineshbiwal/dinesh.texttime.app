package tt.app.texttime.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFunctions {

	 public boolean isEmailValid(String email) { 
	        boolean isValid = false;
	        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	        CharSequence inputStr = email;
	        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(inputStr);
	        if (matcher.matches()) {
	               isValid = true;
	        }
	        return isValid;
	    }
	 public boolean isPhoneNumberValid(String phonenumber)
	 {
		 boolean isValid = false;
		 String expression = "^[1-9][0-9]{9}$" ;
		 CharSequence phone = phonenumber;
		 Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
		 Matcher matcher = pattern.matcher(phone);
		 if(matcher.matches())
			 isValid = true;
		 return isValid;
	 }
	 public boolean isUsernameValid(String username)
	 {
		 boolean isValid = false;
		 String exp = "^[a-z0-9_]{3,30}$";
		 Pattern pattern = Pattern.compile(exp,Pattern.CASE_INSENSITIVE);
		 Matcher matcher = pattern.matcher(username);
		 if(matcher.matches())
			 isValid = true;
		 return isValid;
	 }
}
