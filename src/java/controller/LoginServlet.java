package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.AdopterBean;
import model.StaffBean;
import dao.LoginDao;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ Get form input
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String page = request.getParameter("page"); // hidden input in JSP

        LoginDao dao = new LoginDao();
        HttpSession session = request.getSession();

        // 2️⃣ Check which table to query based on page
        if ("adopter".equals(page)) {

            AdopterBean adopter = dao.getAdopter(username, password);

            if (adopter != null) {
                session.setAttribute("user", adopter);
                response.sendRedirect("adopter/home.jsp");
            } else {
                response.sendRedirect("adopterLogin.jsp?error=invalid");
            }

        } else if ("staff".equals(page)) {

            StaffBean staff = dao.getStaff(username, password);

            if (staff != null) {
                session.setAttribute("user", staff);
                response.sendRedirect("staff/dashboard.jsp");
            } else {
                response.sendRedirect("staffLogin.jsp?error=invalid");
            }

        } else {
            // Invalid submission
            response.sendRedirect("login.jsp?error=invalid");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
