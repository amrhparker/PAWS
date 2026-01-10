package controller;

import dao.ApplicationDao;
import dao.AdopterDao;
import model.ApplicationBean;
import model.AdopterBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ApplicationController")
public class ApplicationController extends HttpServlet {

    private ApplicationDao dao;
    private AdopterDao adopterDao;

    @Override
    public void init() {
        dao = new ApplicationDao();
        adopterDao = new AdopterDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("dashboardA")) {
                showAdopterDashboard(request, response);

            } else if (action.equals("view")) {
                viewApplication(request, response);

            } else if (action.equals("delete")) {
                deleteApplication(request, response);

            } else if (action.equals("form")) {
                showApplicationForm(request, response);
                
            } else if (action.equals("manage")) {
                manageApplications(request, response);

            } else {
                showAdopterDashboard(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                insertApplication(request, response);

            } else if ("updateStatus".equals(action)) {
                updateStatus(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // Dashboard
    private void showAdopterDashboard(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");
        int adoptId = adopter.getAdoptId();

        List<ApplicationBean> apps = dao.getApplicationsByAdopter(adoptId);

        request.setAttribute("applications", apps);
        request.getRequestDispatcher("DashboardA.jsp").forward(request, response);

    }

    // View application
    private void viewApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        ApplicationBean app = dao.getApplicationById(appId);

        request.setAttribute("application", app);
        request.getRequestDispatcher("SubmittedApplication.jsp").forward(request, response);
    }

    // Show application form (pre-fill adopter info)
// Show application form (pre-fill adopter info safely)
private void showApplicationForm(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {

    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("adopter") == null) {
        response.sendRedirect("AdopterLogin.jsp");
        return;
    }

    // Use session adopter (already has all fields)
    AdopterBean sessionAdopter = (AdopterBean) session.getAttribute("adopter");

    int petId = Integer.parseInt(request.getParameter("petId"));

    request.setAttribute("adopter", sessionAdopter); // safe
    request.setAttribute("petId", petId);

    request.getRequestDispatcher("ApplicationForm.jsp").forward(request, response);
}

    // Insert application
// Insert application (safe, won't overwrite email/username/password)
private void insertApplication(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException {

    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("adopter") == null) {
        response.sendRedirect("AdopterLogin.jsp");
        return;
    }

    // 1️⃣ Get the session adopter (full info including email/username/password)
    AdopterBean sessionAdopter = (AdopterBean) session.getAttribute("adopter");

    int petId = Integer.parseInt(request.getParameter("petId"));

    // 2️⃣ Create application bean
    ApplicationBean app = new ApplicationBean();
    app.setAdopter(sessionAdopter);  // Keep all adopter fields intact
    app.setPetId(petId);
    app.setAppStatus("Pending");
    app.setAppEligibility("Pending");
    app.setHasOwnedPet(request.getParameter("hasOwnedPet"));
    app.setCaretakerInfo(request.getParameter("caretakerInfo"));
    app.setPetEnvironment(request.getParameter("petEnvironment"));
    app.setMedicalReady(request.getParameter("medicalReady"));
    app.setAdoptionReason(request.getParameter("adoptionReason"));

    // 3️⃣ Insert into DB
    dao.insertApplication(app);

    // 4️⃣ Optional: update any editable adopter fields from form (if you allow editing)
    //    Only update fields present in the form; leave email/username/password untouched
    boolean adopterUpdated = false;
    if (request.getParameter("adoptPhoneNum") != null) {
        sessionAdopter.setAdoptPhoneNum(request.getParameter("adoptPhoneNum"));
        adopterUpdated = true;
    }
    if (request.getParameter("adoptAddress") != null) {
        sessionAdopter.setAdoptAddress(request.getParameter("adoptAddress"));
        adopterUpdated = true;
    }
    if (request.getParameter("adoptOccupation") != null) {
        sessionAdopter.setAdoptOccupation(request.getParameter("adoptOccupation"));
        adopterUpdated = true;
    }
    if (request.getParameter("adoptIncome") != null) {
        sessionAdopter.setAdoptIncome(Double.parseDouble(request.getParameter("adoptIncome")));
        adopterUpdated = true;
    }

    if (adopterUpdated) {
        adopterDao.updateAdopter(sessionAdopter); // only updates editable fields
    }

    // 5️⃣ Refresh session with latest adopter info
    session.setAttribute("adopter", adopterDao.getAdopterById(sessionAdopter.getAdoptId()));

    // 6️⃣ Redirect to dashboard
    response.sendRedirect("ApplicationController?action=dashboardA");
}

    // Update status
    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        String status = request.getParameter("status");
        String eligibility = request.getParameter("eligibility");

        dao.updateStatus(appId, status, eligibility);
        response.sendRedirect("ApplicationController?action=dashboardA");
    }

    // Delete
    private void deleteApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        dao.deleteApplication(appId);
        response.sendRedirect("ApplicationController?action=dashboardA");
    }
    
    // Manage Applications
    private void manageApplications(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {

    List<ApplicationBean> apps = dao.getAllApplications(); // STAFF VIEW ALL

    request.setAttribute("applications", apps);
    request.getRequestDispatcher("ManageApplications.jsp").forward(request, response);
}

}
