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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "dashboard";
        }

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
                case "logout":
                    logout(request, response);
                    break;
                case "changePasswordForm":
                    showChangePasswordForm(request, response);
                    break;
                default:
                    showDashboard(request, response);
                    break;
            }
        } catch (Exception e) {
            handleException(request, response, e, "GET action: " + action);
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
            handleException(request, response, e, "POST action: " + action);
        }
    }

    private boolean isAuthenticated(HttpSession session) {
        return session != null && session.getAttribute("adopterId") != null;
    }

    private int getAdopterId(HttpSession session) {
        return (int) session.getAttribute("adopterId");
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int adopterId = getAdopterId(session);

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

    private void showProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
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

    private void showEditProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
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

    private void showChangePasswordForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            log("Adopter logged out successfully");
        }
        response.sendRedirect("Home.html");
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int adopterId = getAdopterId(session);

        String[] requiredFields = {"fname", "lname", "ic", "phone", "email", "address", "username"};
        for (String field : requiredFields) {
            if (request.getParameter(field) == null || request.getParameter(field).trim().isEmpty()) {
                request.setAttribute("errorMessage", field.toUpperCase() + " is required");
                forwardToEditProfileWithData(request, response, adopterId);
                return;
            }
        }

        // Get parameters
        String fname = request.getParameter("fname").trim();
        String lname = request.getParameter("lname").trim();
        String ic = request.getParameter("ic").trim();
        String phone = request.getParameter("phone").trim();
        String email = request.getParameter("email").trim();
        String address = request.getParameter("address").trim();
        String occupation = request.getParameter("occupation");
        String incomeStr = request.getParameter("income");
        String username = request.getParameter("username").trim();

        // Validate email format
        if (!isValidEmail(email)) {
            request.setAttribute("errorMessage", "Invalid email format");
            forwardToEditProfileWithData(request, response, adopterId);
            return;
        }

        try {
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);

            if (adopter == null) {
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
                return;
            }

            // Check username uniqueness 
            if (!username.equals(adopter.getAdoptUsername())) {
                if (adopterDao.checkUsernameExists(username)) {
                    request.setAttribute("errorMessage", "Username already exists. Please choose another.");
                    forwardToEditProfileWithData(request, response, adopterId);
                    return;
                }
            }

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

            // Update adopter bean
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

    private void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession(false);
        if (!isAuthenticated(session)) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int adopterId = getAdopterId(session);

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validation
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

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "New passwords do not match");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            return;
        }

        // Password strength validation
        if (newPassword.length() < 8) {
            request.setAttribute("errorMessage", "New password must be at least 8 characters long");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            return;
        }

        // Check if new password is same as current
        if (currentPassword.equals(newPassword)) {
            request.setAttribute("errorMessage", "New password must be different from current password");
            request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
            return;
        }

        try {
            System.out.println("***DEBUG PASSWORD CHANGE***");
            System.out.println("Adopter ID: " + adopterId);
            System.out.println("Current password entered: '" + currentPassword + "'");
            System.out.println("Length of entered password: " + currentPassword.length());

            AdopterBean adopterById = adopterDao.getAdopterById(adopterId);

            if (adopterById == null) {
                System.out.println("ERROR: Could not find adopter with ID: " + adopterId);
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
                return;
            }

            System.out.println("Adopter username from DB: '" + adopterById.getAdoptUsername() + "'");
            System.out.println("Password from DB: '" + adopterById.getAdoptPassword() + "'");
            System.out.println("Length of DB password: " + adopterById.getAdoptPassword().length());

            // Check if passwords match directly
            boolean directMatch = currentPassword.equals(adopterById.getAdoptPassword());
            System.out.println("Direct comparison result: " + directMatch);

            String username = adopterById.getAdoptUsername();
            System.out.println("Trying validateAdopter with username: '" + username + "'");
            AdopterBean validatedAdopter = adopterDao.validateAdopter(username, currentPassword);

            System.out.println("ValidateAdopter returned: " + (validatedAdopter != null ? "Adopter found" : "null"));

            // Check current password
            if (validatedAdopter == null) {
                request.setAttribute("errorMessage", "Current password is incorrect");
                request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
                return;
            }

            // Update password
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

    private void forwardToEditProfileWithData(HttpServletRequest request,
            HttpServletResponse response,
            int adopterId)
            throws ServletException, IOException, SQLException {
        AdopterBean adopter = adopterDao.getAdopterById(adopterId);
        request.setAttribute("adopter", adopter);
        request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

    private void handleException(HttpServletRequest request,
            HttpServletResponse response,
            Exception e,
            String context)
            throws ServletException, IOException {

        System.err.println("ERROR in AdopterController: " + context);
        e.printStackTrace();

        request.setAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        request.setAttribute("errorDetails", e.getMessage());
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }
}
