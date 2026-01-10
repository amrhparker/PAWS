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
public class StaffController extends HttpServlet{
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
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("LogInStaff.jsp");
            return;
        }

        if ("login".equals(action)) {
            login(request, response);
        } else {
            response.sendRedirect("LogInStaff.jsp");
        }
    }

    // Show dashboard (just forward to JSP)
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if staff is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staffId") == null) {
            response.sendRedirect("LogInStaff.jsp");
            return;
        }

        // Forward to the existing StaffDashboard.jsp
        request.getRequestDispatcher("StaffDashboard.jsp").forward(request, response);
    }

    //login
    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("LogInStaff.jsp").forward(request, response);
            return;
        }

        try {
            StaffBean staff = staffDao.authenticateStaff(username, password);

            if (staff != null && staff.getStaffId() > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("staffId", staff.getStaffId());
                session.setAttribute("staffUsername", staff.getStaffUsername());
                session.setAttribute("staffFullName", staff.getStaffFname() + " " + staff.getStaffLname());
                session.setAttribute("staff", staff);
                session.setAttribute("isAdmin", true); // All staff are admin

                session.setMaxInactiveInterval(30 * 60); // 30 minutes

                // Redirect to dashboard
                response.sendRedirect("StaffController?action=dashboard");
            } else {
                request.setAttribute("error", "Invalid staff credentials");
                request.getRequestDispatcher("LogInStaff.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Login failed: " + e.getMessage());
            request.getRequestDispatcher("LogInStaff.jsp").forward(request, response);
        }
    }
}

