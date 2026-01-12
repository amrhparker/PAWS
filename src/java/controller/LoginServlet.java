package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.AdopterBean;
import model.StaffBean;
import dao.LoginDao;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String page = request.getParameter("page"); 

        LoginDao dao = new LoginDao();
        HttpSession session = request.getSession();

        if ("adopter".equals(page)) {

            AdopterBean adopter = dao.getAdopter(username, password);

            if (adopter != null) {
                session.setAttribute("adopter", adopter);
                session.setAttribute("adoptId", adopter.getAdoptId());

                session.setAttribute("loginSuccess", "adopter");

                response.sendRedirect("ApplicationController?action=dashboardA");
            } else {
                 response.sendRedirect("AdopterLogin.jsp?error=invalid");
            }


        } else if ("staff".equals(page)) {

            StaffBean staff = dao.getStaff(username, password);

            if (staff != null) {
                session.setAttribute("staff", staff);

                session.setAttribute("loginSuccess", "staff");

                response.sendRedirect("StaffDashboard.jsp");
            } else {
                response.sendRedirect("LogInStaff.jsp?error=invalid");
            }


        } else {
            response.sendRedirect("Home.html?error=invalid");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Home.html");
    }
}
