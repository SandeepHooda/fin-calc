package email;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;

/**
 * Servlet implementation class SendEmail
 */
public class SendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendEmail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Sendgrid mail = new Sendgrid("sonu.hooda","Sandeep@1234");
		mail.use_headers = false;
		mail.setTo("sonu.hooda@gmail.com")
	    .setFrom("sonu.hooda@gmail.com")
	    .setSubject("Fin cal email")
	    .setText(" Fin cal email")
	    .setHtml("<strong>Fin cal email</strong>");
	

	try {
		mail.send();
		response.getWriter().append("email Sent: ").append(request.getContextPath());
	} catch (JSONException e) {
		response.getWriter().append("Exception "+e.getMessage()).append(request.getContextPath());
		e.printStackTrace();
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
