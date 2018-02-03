package call;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import com.plivo.helper.api.client.*;
import com.plivo.helper.api.response.call.Call;
import com.plivo.helper.exception.PlivoException;
/**
 * Servlet implementation class MakeAcall
 */
public class MakeAcall extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeAcall() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String auth_id = "MAZGI2YTKXN2NMNDGWOD";
        String auth_token = "ODkyNDZmMGU5YzhlMjhhMzk2NmVjMmVkZTFiYTM1";
        RestAPI api = new RestAPI(auth_id, auth_token, "v1");

        LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
        parameters.put("to","919216411835"); // The phone numer to which the all has to be placed
        parameters.put("from","1111111111"); // The phone number to be used as the caller id

        // answer_url is the URL invoked by Plivo when the outbound call is answered
        // and contains instructions telling Plivo what to do with the call
        parameters.put("answer_url","https://s3.amazonaws.com/static.plivo.com/answer.xml");
        parameters.put("answer_method","GET"); // method to invoke the answer_url

        // Example for asynchronous request
        // callback_url is the URL to which the API response is sent.
        // parameters.put("callback_url","http://myvoiceapp.com/callback/");
        // parameters.put("callback_method","GET"); // The method used to notify the callback_url.
        try {
            // Make an outbound call and print the response
            Call resp = api.makeCall(parameters);
            System.out.println(resp);
            response.getWriter().append(resp.toString());
        } catch (PlivoException e) {
            System.out.println(e.getLocalizedMessage());
            response.getWriter().append(e.getLocalizedMessage());
        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
