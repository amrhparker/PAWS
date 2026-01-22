package controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class StaffController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    //use to view page
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showDashboard(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); //redirect to doGet()
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //validate session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staff") == null) {
            response.sendRedirect("LogInStaff.jsp");
            return;
        }
       
        request.getRequestDispatcher("StaffDashboard.jsp").forward(request, response);
    }
}
