
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
/**
 *
 * @author amira
 */
@WebServlet(name = "AdopterController", urlPatterns = {"/AdopterController"})
public class AdopterController extends HttpServlet {
    
    private AdopterDao adopterDao;
    private ApplicationDao applicationDao;
    private RecordDao recordDao;
    //initialize
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

// Remove the entire signin() method from AdopterController
      
    //admin authentication
    private boolean isStaffLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("staffId") != null;
    }
    //dashboard
    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //check user's type
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("adopterId")==null){
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        int adopterId = (int) session.getAttribute("adopterId");
        try{
            
            ApplicationDao appDao = new ApplicationDao();
            RecordDao recordsDao = new RecordDao();
            
            //fetch data for dashboard
            //TODO: add getApplicationsByAdopter(adopterId) and getRecordsByAdopter(adopterId)
            List<ApplicationBean> apps = appDao.getAllApplications();
            List<RecordBean> records = recordsDao.getAllRecords();
            
            //set attributes
            request.setAttribute("applications", apps);
            request.setAttribute("records", records);
            request.setAttribute("username", session.getAttribute("username"));
            
            //forward data to DashboardA.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("DashboardA.jsp");
            dispatcher.forward(request, response);
        }catch(SQLException e){
            request.setAttribute("error", "Error loading dashboard"+ e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }catch(Exception e){
            request.setAttribute("error", "Error loading dashboard");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    //profile
    private void showProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("adopterId")==null){
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        int adopterId = (int) session.getAttribute("adopterId");
        
        try{
            AdopterDao adoptDao = new AdopterDao();
            
            //fetch adopter profile's data
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);
            if(adopter !=null){
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
            } else {
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
            }
        }catch(IOException | ServletException e){
            request.setAttribute("error", "Error loading profile" + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    //edit profile
    private void showEditProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("adopterId")==null){
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        try{
            int adopterId = (int) session.getAttribute("adopterId");
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);
            if(adopter!=null){
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request,response);
            }else{
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
            }
        }catch(Exception e){
            request.setAttribute("error", "Error loading edit profile page");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }
        
    //logout
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        response.sendRedirect("Home.html");
    }
        
    //update profile
    private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("adopterId")==null){
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        int adopterId = (int) session.getAttribute("adopterId");
        
        //get parameters
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String ic = request.getParameter("ic");
        String phoneStr = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String username = request.getParameter("username");
        
        //validate
        if (fname == null || lname == null || ic == null || phoneStr == null || email == null || address == null || username == null) {

            request.setAttribute("errorMessage", "All fields are required");
            request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
            return;

        }
        
        try{
            //retrieve data
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);
            
            if(adopter == null){
                session.invalidate();
                response.sendRedirect("AdopterLogin.jsp");
                return;
            }
            //check if username changed and already exists for other users
            if(!username.equals(adopter.getAdoptUsername())){
                if(adopterDao.checkUsernameExists(username)){
                    request.setAttribute("errorMessage", "Username already exists. Please choose another.");
                    request.setAttribute("adopter", adopter);
                    request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
                    return;
                }
            }
            
            adopter.setAdoptFName(fname);
            adopter.setAdoptLName(lname);
            adopter.setAdoptIC(ic);
            adopter.setAdoptPhoneNum(Integer.parseInt(phoneStr));
            adopter.setAdoptEmail(email);
            adopter.setAdoptAddress(address);
            adopter.setAdoptUsername(username);
            
            boolean success = adopterDao.updateAdopter(adopter);
            
            if(success){
                //update session
                session.setAttribute("username", username);
                session.setAttribute("fullName", fname + " " + lname);
                session.setAttribute("adopter", adopter);
                
                request.setAttribute("successMessage", "Profile updated successfully!");
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
            }else{
                request.setAttribute("errorMessage", "Failed to update profile :(");
                request.setAttribute("adopter", adopter);
                request.getRequestDispatcher("EditAdopterProfile.jsp").forward(request, response);
            }
        }catch(NumberFormatException e){
            request.setAttribute("errorMessage", "Invalid phone number format");
            request.getRequestDispatcher("EditProfileA.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            request.setAttribute("errorMessage", "Error updating profile");
            request.getRequestDispatcher("EditProfileA.jsp").forward(request, response);
        }
    }
        
        
    //change password
    private void changePassword(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopterId") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int adopterId = (int) session.getAttribute("adopterId");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        //validate
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
            //verify current password
            String username = (String) session.getAttribute("username");
            AdopterBean adopter = adopterDao.validateAdopter(username, currentPassword);

            if (adopter == null) {
                request.setAttribute("errorMessage", "Current password is incorrect");
                request.getRequestDispatcher("ChangeAdopterPassword.jsp").forward(request, response);
                return;
            }

            //update password
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
