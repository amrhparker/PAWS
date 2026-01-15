package controller;

import dao.StaffDao;
import model.StaffBean;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class StaffController extends HttpServlet {

    private StaffDao staffDao;

    @Override
    public void init() throws ServletException {
        super.init();
        staffDao = new StaffDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "dashboard";
        }

        if ("dashboard".equals(action)) {
            showDashboard(request, response);
        } else {
            
            showDashboard(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("LogInStaff.jsp");
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staffId") == null) {
            response.sendRedirect("LogInStaff.jsp");
            return;
        }

        
        request.getRequestDispatcher("StaffDashboard.jsp").forward(request, response);
    }
}
