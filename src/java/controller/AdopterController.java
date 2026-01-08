
package controller;

//import dao.ApplicationDAO;
//import dao.AdoptionRecordDAO;
import dao.AdopterDao;
import dao.ApplicationDao;
import model.AdopterBean;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
/**
 *
 * @author amira
 */
@WebServlet(name = "AdopterController", urlPatterns = {"/AdopterController"})
public class AdopterController extends HttpServlet {
    
    private AdopterDao adopterDao;
    //initialize
    @Override
    public void init() throws ServletException {
        super.init();
        adopterDao = new AdopterDao();
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
            case "list":
                listAdopters(request, response);
                break;
            case "delete":
                deleteAdopter(request, response);
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
        //crud methods
        switch (action) {
            case "login":
                login(request, response);
                break;
            case "signin":
                signin(request, response);
                break;
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
            
            Connection conn = getConnectionFromPool(); //TUKAR
            ApplicationDao appDao = new ApplicationDao();
            RecordsDao recordsDao = new RecordsDao();
            
            //fetch data for dashboard
            List<Application> apps = appDao.getApplicationsByAdopter(adopterId);
            List<Records> records = recordsDao.getRecordsByAdopter(adopterId);
            
            //set attributes
            request.setAttribute("applications", apps);
            request.setAttribute("records", records);
            request.setAttribute("username", session.getAttribute("username"));
            
            //forward data to DashboardA.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("DashboardA.jsp");
            dispatcher.forward(request, response);
        }catch(IOException | ServletException e){
            request.setAttribute("error", "Error loading dashboard");
            request.getRequestDispatcher("error.jsp".forward(request, response));
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
            Connection conn = getConnectionFromPool();
            AdopterDao adoptDao = new AdopterDao(conn);
            
            //fetch adopter profile's data
            AdopterBean adopter = adopterDao.getAdopterById(adopterId);
        }catch(Exception e){
            request.setAttribute("error", "Error loading profile");
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
    
    //display adopters list
    private void listAdopters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //admin authentication here!!
        
        try {
            List<AdopterBean> adopters = adopterDao.getAllAdopters();
            request.setAttribute("adopters", adopters);
            request.getRequestDispatcher("AdopterList.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            request.setAttribute("error", "Error loading adopter list");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    //delete adopter
    private void deleteAdopter(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Add admin authentication check here
        
        String idParam = request.getParameter("id");
        if (idParam == null ) {
            response.sendRedirect("AdopterController?action=list");
            return;
        }
        
        try {
            int adopterId = Integer.parseInt(idParam);
            boolean success = adopterDao.deleteAdopter(adopterId);
            
            if (success) {
                response.sendRedirect("AdopterController?action=list&success=true");
            } else {
                response.sendRedirect("AdopterController?action=list&error=true");
            }
        } catch (NumberFormatException | IOException e) {
            response.sendRedirect("AdopterController?action=list&error=true");
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
            
    //login
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String un = request.getParameter("username");
        String pw = request.getParameter("password");
        
        if(un == null || pw == null){
            request.setAttribute("errorMessage", "Username and password are required");
            request.getRequestDispatcher("AdopterLogin.jsp").forward(request, response);
            return;
        }
        
        try{
            AdopterBean adopter = adopterDao.validateAdopter(un, pw);
            
            if (adopter != null) {
                //create session
                HttpSession session = request.getSession();
                session.setAttribute("adopterId", adopter.getAdoptId());
                session.setAttribute("username", adopter.getAdoptUsername());
                session.setAttribute("fullName", adopter.getAdoptFName() + " " + adopter.getAdoptLName());
                session.setAttribute("adopter", adopter);
                
                //set session timeout (30 minutes)
                session.setMaxInactiveInterval(30 * 60);
                
                //go to dashboard
                response.sendRedirect("AdopterController?action=dashboard");
            } else {
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("AdopterLogin.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            request.setAttribute("errorMessage", "Login failed. Please try again.");
            request.getRequestDispatcher("AdopterLogin.jsp").forward(request, response);

        }
    }
    
    //ergister
    private void signin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get parameters
        try{
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String ic = request.getParameter("ic");
            String phoneStr = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            //validate
            if (fname == null || lname == null || ic == null || phoneStr == null || email == null || address == null || username == null || password == null) {

                request.setAttribute("errorMessage", "All fields are required");
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", phoneStr);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("username", username);
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                return;
            }

            //validate phone number
            try {
                //parse phone number to int
                int phone = Integer.parseInt(phoneStr);

                //validate phoneNum length
                if (phoneStr.length() != 10) {
                    request.setAttribute("errorMessage", "Phone number must be 10 digits");
                    //set each attribute individually
                    request.setAttribute("fname", fname);
                    request.setAttribute("lname", lname);
                    request.setAttribute("ic", ic);
                    request.setAttribute("phone", ""); //clear phone text field
                    request.setAttribute("email", email);
                    request.setAttribute("address", address);
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                    return;
                }
                //ensuring phone number entered is not all zeros
                if (phone <= 0) {
                    request.setAttribute("errorMessage", "Invalid phone number");
                    //set each attribute individually
                    request.setAttribute("fname", fname);
                    request.setAttribute("lname", lname);
                    request.setAttribute("ic", ic);
                    request.setAttribute("phone", ""); //clear phone text field
                    request.setAttribute("email", email);
                    request.setAttribute("address", address);
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                    return;
                }

            }catch(NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid phone number format. Please enter digits only.");
                //set each attribute individually
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", ""); // Clear phone field
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("username", username);
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                return;
            }

            //prevent duplicate username
            if (adopterDao.checkUsernameExists(username)) {
                request.setAttribute("errorMessage", "Username already exists. Please choose another.");
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", phoneStr);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("username", username);
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                return;
            }

            //create adopter object
            AdopterBean temp = new AdopterBean();
            temp.setAdoptFName(fname);
            temp.setAdoptLName(lname);
            temp.setAdoptIC(ic);
            temp.setAdoptPhoneNum(Integer.parseInt(phoneStr));
            temp.setAdoptEmail(email);
            temp.setAdoptAddress(address);
            temp.setAdoptUsername(username);
            temp.setAdoptPassword(password);

            boolean success = adopterDao.insertAdopter(temp);
            if(success){
                response.sendRedirect("AdopterLogin.jsp?success=Registration succesful! Please login.");
            }else{
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", phoneStr);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("username", username);
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
            }
        }catch(IOException | NumberFormatException | ServletException e) {
            request.setAttribute("errorMessage", "Registration error: " + e.getMessage());
            //preserve form data in case of unexpected error.
            request.setAttribute("fname", request.getParameter("fname"));
            request.setAttribute("lname", request.getParameter("lname"));
            request.setAttribute("ic", request.getParameter("ic"));
            request.setAttribute("phone", request.getParameter("phone"));
            request.setAttribute("email", request.getParameter("email"));
            request.setAttribute("address", request.getParameter("address"));
            request.setAttribute("username", request.getParameter("username"));
            request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
        }
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
        if (fname == null || lname == null || ic == null || phoneStr == null || email == null || address == null || username == null || password == null) {

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
