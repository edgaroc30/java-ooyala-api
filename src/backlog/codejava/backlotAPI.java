package backlog.codejava;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.URLEncoder;
import java.util.Base64;



public class backlotAPI {
	private String APIKey = "";
	private String APISecret = "";
	public String baseUrl = "api.ooyala.com";
	public long expirationWindowSeconds = 15L;
	public long roundUpTimeSeconds = 300L;
	
	backlotAPI(String key,String secret){
		this.APIKey = key;
		this.APISecret = secret;
	}
	backlotAPI(String key,String secret,String url){
		this.APIKey = key;
		this.APISecret = secret;
		this.baseUrl = url;
	}
	
	private String concatenateParams(HashMap<String, String> parameters, String separator) {
        Vector<String> keys = new Vector<String>(parameters.keySet());
        Collections.sort(keys);
        
        String string = ""; 
        for (Enumeration<String> e = keys.elements(); e.hasMoreElements();) {
            String key    = (String)e.nextElement();
            String value  = (String)parameters.get(key);
            if (!string.isEmpty())
                string += separator;
            string += key + "=" + value;
        }
        return string;
    }
	
    public String generateSignature(String HTTPMethod, String requestPath, HashMap<String, String> parameters, String   requestBody,String expires) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String stringToSign = this.APISecret + HTTPMethod + requestPath+"api_key="+this.APIKey+"expires="+expires;
        stringToSign += concatenateParams(parameters, "");
        stringToSign += requestBody;
        System.out.println(stringToSign);
        MessageDigest digestProvider = MessageDigest.getInstance("SHA-256");
        digestProvider.reset();
        byte[] digest = digestProvider.digest(stringToSign.getBytes());
        String signedInput = Base64.getEncoder().encodeToString(digest);
        System.out.println(URLEncoder.encode(signedInput.substring(0, 43), "US-ASCII"));
        return URLEncoder.encode(signedInput.substring(0, 43), "US-ASCII");
    }
    
    public String generateUrl(String HTTPMethod, String requestPath, HashMap<String, String> parameters, String   requestBody,String expires) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	return "http://"+this.baseUrl+requestPath+"?"+concatenateParams(parameters, "&")+"&api_key="+this.APIKey+"&expires="+expires
    			+"&signature="+generateSignature(HTTPMethod,requestPath,parameters,requestBody,expires);
    }
    
    public long getExpiration() {
        long nowPlusWindow = System.currentTimeMillis() / 1000 + expirationWindowSeconds;
        long roundUp = roundUpTimeSeconds - (nowPlusWindow % roundUpTimeSeconds);
        return (nowPlusWindow + roundUp);
    }

}
