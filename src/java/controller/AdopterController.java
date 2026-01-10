package controller;

import dao.AdopterDao;
import dao.ApplicationDao;
import dao.RecordDao;
import model.AdopterBean;
import model.ApplicationBean;
import model.RecordBean;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AdopterController", urlPatterns = {"/AdopterController"})
public class AdopterController extends HttpServlet {
    
    private AdopterDao adopterDao;
    private ApplicationDao applicationDao;
    private RecordDao recordDao;
    
    @Override
    public void init() throws ServletException {
        super.init();
        adopterDao = new AdopterDao();
        applicationDao = new ApplicationDao();
        recordDao = new RecordDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "dashboard";
        }        
        
        switch (action) {
            case "dashboard":
                showDashboard(request, response);
                break;
            case "profile":
                showProfile(request, response);
                break;
            case "editProfile":
                showEditProfile(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            default:
                showDashboard(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        switch (action) {
            case "updateProfile":
                updateProfile(request, response);
                break;
            case "changePassword":
                changePassword(request, response);
                break;
            default:
                response.sendRedirect("AdopterLogin.jsp");
                break;
        }
    }
    
    // Dashboard method
    private void showDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("adopterId") == null){
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        int adopterId = (int) session.getAttribute("adopterId");
        try {
            ApplicationDao appDao = new ApplicationDao();
            RecordDao recordsDao = new RecordDao();
            
            List<ApplicationBean> apps = appDao.getAllApplications();
            List<RecordBean> records = recordsDao.getAllRecords();
            
            request.setAttribute("applications", apps);
            request.setAttribute("records", records);
            request.setAttribute("username", session.getAttribute("username"));
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("DashboardA.jsp");
            dispatcher.forward(request, response);
        } catch(Exception e) {
            request.setAttribute("error", "Error loading dashboard");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    // Profile method
    private void showProfile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("adopterId") == null){
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        int adopterId = (int) session.getAttribute("adopterId");
        
        try {
            AdopterDao adoptDao = new AdopterDao();
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);
            if(adopter != null) {
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
            } else {
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
            }
        } catch(IOException | ServletException e) {
            request.setAttribute("error", "Error loading profile" + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    // Edit profile method
    private void showEditProfile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("adopterId") == null){
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        try {
            int adopterId = (int) session.getAttribute("adopterId");
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);
            if(adopter != null) {
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request,response);
            } else {
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
            }
        } catch(Exception e) {
            request.setAttribute("error", "Error loading edit profile page");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }
    
    // Logout method
    private void logout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        response.sendRedirect("Home.html");
    }
    
    // Update profile method
    private void updateProfile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("adopterId") == null){
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        int adopterId = (int) session.getAttribute("adopterId");
        
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String ic = request.getParameter("ic");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String occupation = request.getParameter("occupation");
        String incomeStr = request.getParameter("income");
        String username = request.getParameter("username");
        
        if (fname == null || lname == null || ic == null || phone == null || 
            email == null || address == null || username == null) {
            request.setAttribute("errorMessage", "All fields are required");
            request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
            return;
        }
        
        try {
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);
            
            if(adopter == null) {
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
                return;
            }
            
            if(!username.equals(adopter.getAdoptUsername())) {
                if(adopterDao.checkUsernameExists(username)) {
                    request.setAttribute("errorMessage", "Username already exists. Please choose another.");
                    request.setAttribute("adopter", adopter);
                    request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
                    return;
                }
            }
            
            // Parse income if provided
            java.math.BigDecimal income = null;
            if (incomeStr != null && !incomeStr.trim().isEmpty()) {
                try {
                    income = new java.math.BigDecimal(incomeStr);
                    if (income.compareTo(java.math.BigDecimal.ZERO) < 0) {
                        request.setAttribute("errorMessage", "Income cannot be negative");
                        request.setAttribute("adopter", adopter);
                        request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid income format");
                    request.setAttribute("adopter", adopter);
                    request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
                    return;
                }
            }

            // UPDATE ALL FIELDS INCLUDING NEW ONES
            adopter.setAdoptFName(fname);
            adopter.setAdoptLName(lname);
            adopter.setAdoptIC(ic);
            adopter.setAdoptPhoneNum(phone);
            adopter.setAdoptEmail(email);
            adopter.setAdoptAddress(address);
            adopter.setAdoptOccupation(occupation);  
            adopter.setAdoptIncome(income);          
            adopter.setAdoptUsername(username);

            boolean success = adopterDao.updateAdopter(adopter);

            if (success) {
                session.setAttribute("username", username);
                session.setAttribute("fullName", fname + " " + lname);
                session.setAttribute("adopter", adopter);

                request.setAttribute("successMessage", "Profile updated successfully!");
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to update profile :(");
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {  // Remove NumberFormatException catch
            request.setAttribute("errorMessage", "Error updating profile");
            request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
        }
    }
    
    // Change password method
    private void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopterId") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int adopterId = (int) session.getAttribute("adopterId");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (currentPassword == null || newPassword == null || confirmPassword == null) {
            request.setAttribute("errorMessage", "All password fields are required");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "New passwords do not match");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            return;
        }

        try {
            String username = (String) session.getAttribute("username");
            AdopterBean adopter = adopterDao.validateAdopter(username, currentPassword);

            if (adopter == null) {
                request.setAttribute("errorMessage", "Current password is incorrect");
                request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
                return;
            }

            adopter.setAdoptPassword(newPassword);
            boolean success = adopterDao.updateAdopter(adopter);

            if (success) {
                request.setAttribute("successMessage", "Password changed successfully!");
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to change password");
                request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            request.setAttribute("errorMessage", "Error changing password");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
        }
    }
}