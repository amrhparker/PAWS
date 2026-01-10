package controller;

import dao.RegisterDao;
import model.AdopterBean;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
/**
 *
 * @author amira
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private RegisterDao registerDao;

    @Override
    public void init() throws ServletException {
        super.init();
        registerDao = new RegisterDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to registration page
        response.sendRedirect("AdopterSignin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            registerAdopter(request, response);
        } else {
            response.sendRedirect("AdopterSignin.jsp");
        }
    }

    private void registerAdopter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String ic = request.getParameter("ic");
        String phoneStr = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Basic validation - check for null/empty values
        if (isEmpty(fname) || isEmpty(lname) || isEmpty(ic) || isEmpty(phoneStr)
                || isEmpty(email) || isEmpty(address) || isEmpty(username) || isEmpty(password)) {

            setErrorAndForward(request, response, "All fields are required",
                    fname, lname, ic, phoneStr, email, address, username);
            return;
        }

        // Trim all inputs
        fname = fname.trim();
        lname = lname.trim();
        ic = ic.trim();
        phoneStr = phoneStr.trim();
        email = email.trim();
        address = address.trim();
        username = username.trim();
        password = password.trim();

        try {
            // Validate phone number format
            if (!registerDao.isValidPhoneNumber(phoneStr)) {
                setErrorAndForward(request, response, "Invalid phone number. Must be 10 digits.",
                        fname, lname, ic, "", email, address, username);
                return;
            }

            // Parse phone number
            int phone;
            try {
                phone = Integer.parseInt(phoneStr);
                if (phone <= 0) {
                    setErrorAndForward(request, response, "Invalid phone number",
                            fname, lname, ic, "", email, address, username);
                    return;
                }
            } catch (NumberFormatException e) {
                setErrorAndForward(request, response, "Invalid phone number format. Please enter digits only.",
                        fname, lname, ic, "", email, address, username);
                return;
            }

            // Check for duplicate IC
            if (registerDao.checkICExists(ic)) {
                setErrorAndForward(request, response, "IC number already registered.",
                        fname, lname, "", phoneStr, email, address, username);
                return;
            }

            // Check for duplicate username
            if (registerDao.checkUsernameExists(username)) {
                setErrorAndForward(request, response, "Username already exists. Please choose another.",
                        fname, lname, ic, phoneStr, email, address, "");
                return;
            }

            // Validate email format (basic validation)
            if (!isValidEmail(email)) {
                setErrorAndForward(request, response, "Invalid email format.",
                        fname, lname, ic, phoneStr, "", address, username);
                return;
            }

            // Validate password strength (minimum 6 characters)
            if (password.length() < 6) {
                setErrorAndForward(request, response, "Password must be at least 6 characters long.",
                        fname, lname, ic, phoneStr, email, address, username);
                return;
            }

            // Create adopter object
            AdopterBean newAdopter = new AdopterBean();
            newAdopter.setAdoptFName(fname);
            newAdopter.setAdoptLName(lname);
            newAdopter.setAdoptIC(ic);
            newAdopter.setAdoptPhoneNum(phone);
            newAdopter.setAdoptEmail(email);
            newAdopter.setAdoptAddress(address);
            newAdopter.setAdoptUsername(username);
            newAdopter.setAdoptPassword(password); // Note: Consider hashing the password for security

            // Register adopter
            boolean success = registerDao.registerAdopter(newAdopter);
            if (success) {
                // Registration successful
                request.setAttribute("successMessage", "Registration successful! You can now login.");
                request.getRequestDispatcher("AdopterLogin.jsp").forward(request, response);
                // Alternative: response.sendRedirect("AdopterLogin.jsp?success=Registration successful! Please login.");
            } else {
                setErrorAndForward(request, response, "Registration failed. Please try again.",
                        fname, lname, ic, phoneStr, email, address, username);
            }

        } catch (Exception e) {
            e.printStackTrace();
            setErrorAndForward(request, response, "System error: Please try again later.",
                    fname, lname, ic, phoneStr, email, address, username);
        }
    }

    // Helper method to check if string is null or empty
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }

    // Helper method to set error and forward to registration page
    private void setErrorAndForward(HttpServletRequest request, HttpServletResponse response,
            String errorMessage, String fname, String lname,
            String ic, String phone, String email,
            String address, String username)
            throws ServletException, IOException {

        request.setAttribute("errorMessage", errorMessage);
        setFormAttributes(request, fname, lname, ic, phone, email, address, username);
        request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
    }

    // Helper method to set form attributes
    private void setFormAttributes(HttpServletRequest request,
            String fname, String lname, String ic,
            String phone, String email, String address,
            String username) {
        request.setAttribute("fname", fname != null ? fname : "");
        request.setAttribute("lname", lname != null ? lname : "");
        request.setAttribute("ic", ic != null ? ic : "");
        request.setAttribute("phone", phone != null ? phone : "");
        request.setAttribute("email", email != null ? email : "");
        request.setAttribute("address", address != null ? address : "");
        request.setAttribute("username", username != null ? username : "");
    }

    // Optional: Method to handle AJAX validation requests
/*    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        String value = request.getParameter("value");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            boolean exists = false;
            String message = "";

            if("username".equals(type)){
                exists = registerDao.checkUsernameExists(value);
                message = exists ? "Username already exists" : "Username available";
            }else if ("ic".equals(type)) {
                exists = registerDao.checkICExists(value);
                message = exists ? "IC already registered" : "IC available";
            }

            String json = String.format("{\"exists\": %b, \"message\": \"%s\"}", exists, message);
            response.getWriter().write(json);

        } catch (Exception e) {
            response.getWriter().write("{\"error\": \"Validation error\"}");
        }
    }*/
}
