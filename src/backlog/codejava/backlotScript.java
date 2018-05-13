package backlog.codejava;
import java.util.HashMap;

public class backlotScript {

	public static void main(String[] args) throws Exception {
		String method ="GET";//GET, PATCH, POST, DELETE
		String path ="/v3/analytics/reports/";//i.e. /v2/assets/{embed)code}/metadata
		String expires ="1580569200";//set a mnaual expiration time or use getExpiration()
		String requestBody =""; //the JSON body should be like this one {\"created_at\":\"should works\"}
		backlotAPI sign = new backlotAPI("ZzZGMxOo_dNZEF6BH2MfXVqweXLU.4918i","");
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("report_type", "performance");
		//parameters.put("dimensions", "asset");
		parameters.put("start_date", "2017-03-25");
		//parameters.put("new","parameter");
			String url = sign.generateUrl(method, path, parameters, requestBody, expires);
			System.out.println(url);
		}

}
