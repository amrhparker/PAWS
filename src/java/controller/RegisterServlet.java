package controller;

import dao.RegisterDao;
import model.AdopterBean;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

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
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String occupation = request.getParameter("occupation");
        String incomeStr = request.getParameter("income");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (fname != null) {
            fname = fname.trim();
        }
        if (lname != null) {
            lname = lname.trim();
        }
        if (ic != null) {
            ic = ic.trim();
        }
        if (phone != null) {
            phone = phone.trim();
        }
        if (email != null) {
            email = email.trim();
        }
        if (address != null) {
            address = address.trim();
        }
        if (occupation != null) {
            occupation = occupation.trim();
        }
        if (incomeStr != null) {
            incomeStr = incomeStr.trim();
        }
        if (username != null) {
            username = username.trim();
        }
        if (password != null) {
            password = password.trim();
        }

        if (fname == null || fname.isEmpty()
                || lname == null || lname.isEmpty()
                || ic == null || ic.isEmpty()
                || phone == null || phone.isEmpty()
                || email == null || email.isEmpty()
                || address == null || address.isEmpty()
                || username == null || username.isEmpty()
                || password == null || password.isEmpty()) {

            request.setAttribute("errorMessage", "All required fields are required");
            // Set form values to preserve input
            request.setAttribute("fname", fname != null ? fname : "");
            request.setAttribute("lname", lname != null ? lname : "");
            request.setAttribute("ic", ic != null ? ic : "");
            request.setAttribute("phone", phone != null ? phone : "");
            request.setAttribute("email", email != null ? email : "");
            request.setAttribute("address", address != null ? address : "");
            request.setAttribute("occupation", occupation != null ? occupation : "");
            request.setAttribute("income", incomeStr != null ? incomeStr : "");
            request.setAttribute("username", username != null ? username : "");

            request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
            return;
        }

        try {
            // Phone validation
            if (!phone.matches("\\d{8,15}")) {
                request.setAttribute("errorMessage", "Phone number should be 8-15 digits");
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", "");
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("occupation", occupation);
                request.setAttribute("income", incomeStr);
                request.setAttribute("username", username);
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                return;
            }

            Double income = null;
            if (incomeStr != null && !incomeStr.trim().isEmpty()) {
                try {
                    income = Double.parseDouble(incomeStr);
                    if (income < 0) {
                        request.setAttribute("errorMessage", "Income cannot be negative");
                        request.setAttribute("fname", fname);
                        request.setAttribute("lname", lname);
                        request.setAttribute("ic", ic);
                        request.setAttribute("phone", phone);
                        request.setAttribute("email", email);
                        request.setAttribute("address", address);
                        request.setAttribute("occupation", occupation);
                        request.setAttribute("income", "");
                        request.setAttribute("username", username);
                        request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid income format. Please enter a valid number (e.g., 3500.50)");
                    request.setAttribute("fname", fname);
                    request.setAttribute("lname", lname);
                    request.setAttribute("ic", ic);
                    request.setAttribute("phone", phone);
                    request.setAttribute("email", email);
                    request.setAttribute("address", address);
                    request.setAttribute("occupation", occupation);
                    request.setAttribute("income", "");
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                    return;
                }
            }

            // Check for duplicate IC
            if (registerDao.checkICExists(ic)) {
                request.setAttribute("errorMessage", "IC number already registered.");
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", "");
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("occupation", occupation);
                request.setAttribute("income", incomeStr);
                request.setAttribute("username", username);
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                return;
            }

            // Check for duplicate username
            if (registerDao.checkUsernameExists(username)) {
                request.setAttribute("errorMessage", "Username already exists. Please choose another.");
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("occupation", occupation);
                request.setAttribute("income", incomeStr);
                request.setAttribute("username", "");
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                return;
            }

            // Validate email
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (!email.matches(emailRegex)) {
                request.setAttribute("errorMessage", "Invalid email format.");
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", phone);
                request.setAttribute("email", "");
                request.setAttribute("address", address);
                request.setAttribute("occupation", occupation);
                request.setAttribute("income", incomeStr);
                request.setAttribute("username", username);
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
                return;
            }

            // Validate password
            if (password.length() < 6) {
                request.setAttribute("errorMessage", "Password must be at least 6 characters long.");
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("occupation", occupation);
                request.setAttribute("income", incomeStr);
                request.setAttribute("username", username);
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
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
            newAdopter.setAdoptOccupation(occupation);
            newAdopter.setAdoptIncome(income);
            newAdopter.setAdoptUsername(username);
            newAdopter.setAdoptPassword(password);

            // Register adopter
            boolean success = registerDao.registerAdopter(newAdopter);
            if (success) {
                request.setAttribute("successMessage", "Registration successful! You can now login.");
                request.getRequestDispatcher("AdopterLogin.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.setAttribute("fname", fname);
                request.setAttribute("lname", lname);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("occupation", occupation);
                request.setAttribute("income", incomeStr);
                request.setAttribute("username", username);
                request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "System error: Please try again later.");
            request.setAttribute("fname", fname);
            request.setAttribute("lname", lname);
            request.setAttribute("ic", ic);
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            request.setAttribute("address", address);
            request.setAttribute("occupation", occupation);
            request.setAttribute("income", incomeStr);
            request.setAttribute("username", username);
            request.getRequestDispatcher("AdopterSignin.jsp").forward(request, response);
        }
    }
    
    // Check if string is null or empty
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }

}
