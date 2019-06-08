/*package com.egerton.bugs.components;package com.egerton.bugs.components;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;


@Component
public class AuthenticatedUser {
	
	//Access authenticated user/principal details
		@SuppressWarnings({ "unchecked" })		
		public Map<String,String> getUser(Principal principal){		    
			    
				if (principal != null) {
			        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
			        Authentication authentication = oAuth2Authentication.getUserAuthentication();
			        
			        Map<String, String> details = new LinkedHashMap<>();
			        details = (Map<String, String>) authentication.getDetails();     
			     
			        return details;
			    }
			    return null;
	  }

}
*/