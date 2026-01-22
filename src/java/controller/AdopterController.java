package controller;

import dao.AdopterDao;
import dao.ApplicationDao;
import dao.RecordDao;
import model.AdopterBean;
import model.ApplicationBean;
import model.RecordBean;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdopterController extends HttpServlet {

    private AdopterDao adopterDao;
    private ApplicationDao applicationDao;
    private RecordDao recordDao;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            adopterDao = new AdopterDao();
            applicationDao = new ApplicationDao();
            recordDao = new RecordDao();
            log("AdopterController initialized successfully"); 
        } catch (Exception e) {
            log("Failed to initialize AdopterController: " + e.getMessage(), e);
            throw new ServletException("Failed to initialize controller", e);
        }
    }

    @Override
    //use to view pages
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //retrieve action parameter from the url
        String action = request.getParameter("action");

        if (action == null) {
            action = "dashboard";
        }
        
        //menu of actions and related method to call
        try {
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
                case "changePasswordForm":
                    showChangePasswordForm(request, response);
                    break;
                default:
                    showDashboard(request, response);
                    break;
            }
        } catch (Exception e) {
            System.err.println("ERROR in AdopterController GET: " + action);
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    //use for submitting data
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        //menu of actions and related method to call
        try {
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
        } catch (Exception e) {
            System.err.println("ERROR in AdopterController GET: " + action);
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    //check if if session exists or if adopter is currently logged in 
    private boolean isAuthenticated(HttpSession session) {
        return session != null && session.getAttribute("adopterId") != null;
    }
    
    //retrieve adopterId from session memory
    private int getAdopterId(HttpSession session) {
        return (int) session.getAttribute("adopterId");
    }
    
    //redirect adopter to dashboard
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        //validate session
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int adopterId = getAdopterId(session);

        //retrieve data needed for dashboard
        try {
            List<ApplicationBean> apps = applicationDao.getApplicationsByAdopter(adopterId);
            List<RecordBean> records = recordDao.getAllRecords();

            request.setAttribute("applications", apps);
            request.setAttribute("records", records);
            request.setAttribute("username", session.getAttribute("username"));

            request.getRequestDispatcher("DashboardA.jsp").forward(request, response);
        } catch (SQLException e) {
            
            log("Database error loading dashboard for adopter: " + adopterId, e);
            throw e;
        }
    }

    //redirect adopter to their profile
    private void showProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        //validate session
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int adopterId = getAdopterId(session);

        try {
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);
            if (adopter != null) {
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
            } else {
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
            }
        } catch (SQLException e) {
            log("Database error loading profile for adopter: " + adopterId, e);
            throw e;
        }
    }

    //redirect adopter to edit their profile
    private void showEditProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        //validate session
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        try {
            int adopterId = getAdopterId(session);
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);

            if (adopter != null) {
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
            } else {
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
            }
        } catch (SQLException e) {
            log("Database error loading edit profile", e);
            throw e;
        }
    }

    //redirect adopter to change their password form
    private void showChangePasswordForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //validate session
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
    }

    //handles profile update
    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        //validate session
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int adopterId = getAdopterId(session);

        //make sure every required field is filled
        String[] requiredFields = {"fname", "lname", "ic", "phone", "email", "address", "username"};
        for (String field : requiredFields) {
            if (request.getParameter(field) == null || request.getParameter(field).trim().isEmpty()) {
                request.setAttribute("errorMessage", field.toUpperCase() + " is required");
                forwardToEditProfileWithData(request, response, adopterId);
                return;
            }
        }

        //get parameters
        String fname = request.getParameter("fname").trim();
        String lname = request.getParameter("lname").trim();
        String ic = request.getParameter("ic").trim();
        String phone = request.getParameter("phone").trim();
        String email = request.getParameter("email").trim();
        String address = request.getParameter("address").trim();
        String occupation = request.getParameter("occupation");
        String incomeStr = request.getParameter("income");
        String username = request.getParameter("username").trim();

        //validate email format using isVlalidEmail()
        if (!isValidEmail(email)) { 
            request.setAttribute("errorMessage", "Invalid email format");
            forwardToEditProfileWithData(request, response, adopterId);
            return;
        }

        try {
            //check if user logged in (in session) still exists in the database
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);
            if (adopter == null) { 
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
                return;
            }

            //check username uniqueness 
            if (!username.equals(adopter.getAdoptUsername())) {
                if (adopterDao.checkUsernameExists(username)) {
                    request.setAttribute("errorMessage", "Username already exists. Please choose another.");
                    forwardToEditProfileWithData(request, response, adopterId);
                    return;
                }
            }

            //convert income datatype from String to Double and validate
            double income = 0.0;
            if (incomeStr != null && !incomeStr.trim().isEmpty()) {
                try {
                    income = Double.parseDouble(incomeStr);
                    if (income < 0) {
                        request.setAttribute("errorMessage", "Income cannot be negative");
                        forwardToEditProfileWithData(request, response, adopterId);
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid income format");
                    forwardToEditProfileWithData(request, response, adopterId);
                    return;
                }
            }

            //update adopter bean
            adopter.setAdoptFName(fname);
            adopter.setAdoptLName(lname);
            adopter.setAdoptIC(ic);
            adopter.setAdoptPhoneNum(phone);
            adopter.setAdoptEmail(email);
            adopter.setAdoptAddress(address);
            adopter.setAdoptOccupation(occupation != null ? occupation : "");
            adopter.setAdoptIncome(income);
            adopter.setAdoptUsername(username);

            boolean success = adopterDao.updateAdopter(adopter);

            if (success) {
                
                session.setAttribute("username", username);
                session.setAttribute("fullName", fname + " " + lname);
                session.setAttribute("adopter", adopter);

                log("Profile updated successfully for adopter ID: " + adopterId);
                request.setAttribute("successMessage", "Profile updated successfully!");
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to update profile. Please try again.");
                forwardToEditProfileWithData(request, response, adopterId);
            }
        } catch (SQLException e) {
            log("Database error updating profile for adopter: " + adopterId, e);
            throw e;
        }
    }
    
    //handles password changing
    private void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        //validate session
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int adopterId = getAdopterId(session);

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        //make sure every required field is filled
        if (currentPassword == null || currentPassword.trim().isEmpty()
                || newPassword == null || newPassword.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All password fields are required");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            return;
        }

        currentPassword = currentPassword.trim();
        newPassword = newPassword.trim();
        confirmPassword = confirmPassword.trim();

        //ensure the new password and the confirmation password match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "New passwords do not match");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            return;
        }

        //password strength validation
        if (newPassword.length() < 8) {
            request.setAttribute("errorMessage", "New password must be at least 8 characters long");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            return;
        }

        //check if new password is same as current
        if (currentPassword.equals(newPassword)) {
            request.setAttribute("errorMessage", "New password must be different from current password");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            return;
        }

        try {

            AdopterBean adopterById = adopterDao.getAdopterById(adopterId);

            if (adopterById == null) {
                System.out.println("ERROR: Could not find adopter with ID: " + adopterId);
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
                return;
            }

            String username = adopterById.getAdoptUsername();
            //check if adopter exists in the database
            AdopterBean validatedAdopter = adopterDao.validateAdopter(username, currentPassword);

            //check current password
            if (validatedAdopter == null) {
                request.setAttribute("errorMessage", "Current password is incorrect");
                request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
                return;
            }

            //update password
            adopterById.setAdoptPassword(newPassword);
            boolean success = adopterDao.updateAdopter(adopterById);

            if (success) {
                log("Password changed successfully for adopter ID: " + adopterId);
                request.setAttribute("successMessage", "Password changed successfully!");
                request.setAttribute("adopter", adopterById);
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to change password. Database update failed.");
                request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            log("Database error changing password for adopter: " + adopterId, e);
            request.setAttribute("errorMessage", "Database error occurred. Please try again.");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
        }
    }

    //reload edit page with filled in data
    private void forwardToEditProfileWithData(HttpServletRequest request, HttpServletResponse response, int adopterId) 
            throws ServletException, IOException, SQLException {
        AdopterBean adopter = adopterDao.getAdopterById(adopterId);
        request.setAttribute("adopter", adopter);
        request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
    }

    //email validation
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }
}
