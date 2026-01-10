/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StaffDao;
import model.StaffBean;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 *
 * @author amira
 */
@WebServlet(name = "StaffController", urlPatterns = {"/StaffController"})
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
            //default to dashboard
            showDashboard(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("LogInStaff.jsp");
    }

    // Show dashboard (just forward to JSP)
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //check if staff is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staffId") == null) {
            response.sendRedirect("LogInStaff.jsp");
            return;
        }

        //forward to the existing StaffDashboard.jsp
        request.getRequestDispatcher("StaffDashboard.jsp").forward(request, response);
    }
}
