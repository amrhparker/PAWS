package controller;

import dao.ApplicationDao;
import dao.AdopterDao;
import model.ApplicationBean;
import model.AdopterBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ApplicationController")
public class ApplicationController extends HttpServlet {

    private ApplicationDao applicationDao;
    private AdopterDao adopterDao;

    @Override
    public void init() {
        applicationDao = new ApplicationDao();
        adopterDao = new AdopterDao();
    }

    // =========================
    // GET REQUESTS
    // =========================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp?error=loginRequired");
            return;
        }


        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("dashboardA")) {
                showAdopterDashboard(request, response);

            } else if (action.equals("form")) {
                showApplicationForm(request, response);

            } else if (action.equals("view")) {
                viewApplication(request, response);

            } else if (action.equals("delete")) {
                deleteApplication(request, response);

            } else if (action.equals("manage")) {
                manageApplications(request, response);

            } else {
                showAdopterDashboard(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // =========================
    // POST REQUESTS
    // =========================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                insertApplication(request, response);

            } else if ("updateStatus".equals(action)) {
                updateStatus(request, response);
            }else if ("delete".equals(action)) {
                deleteApplication(request, response);
            }


        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // =========================
    // DASHBOARD (ADOPTER)
    // =========================
    private void showAdopterDashboard(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");
        int adoptId = adopter.getAdoptId();

        List<ApplicationBean> applications =
                applicationDao.getApplicationsByAdopter(adoptId);

        request.setAttribute("applications", applications);
        request.getRequestDispatcher("DashboardA.jsp").forward(request, response);
    }

    // =========================
    // VIEW SUBMITTED APPLICATION
    // =========================
    private void viewApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        ApplicationBean application = applicationDao.getApplicationById(appId);

        request.setAttribute("application", application);
        request.getRequestDispatcher("SubmittedApplication.jsp").forward(request, response);
    }

    // =========================
    // SHOW APPLICATION FORM (FIXED PREFILL)
    // =========================
    private void showApplicationForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        // 1️⃣ Get adopter from session
        AdopterBean sessionAdopter = (AdopterBean) session.getAttribute("adopter");

        // 2️⃣ Fetch full adopter info from DB (ensures prefill)
        AdopterBean fullAdopter = adopterDao.getAdopterById(sessionAdopter.getAdoptId());

        // 3️⃣ Update session with full info
        session.setAttribute("adopter", fullAdopter);

        // 4️⃣ Get petId from request
        int petId = Integer.parseInt(request.getParameter("petId"));

        // 5️⃣ Set attributes for JSP
        request.setAttribute("adopter", fullAdopter);
        request.setAttribute("petId", petId);

        // 6️⃣ Forward to form
        request.getRequestDispatcher("ApplicationForm.jsp").forward(request, response);
    }

    // =========================
    // INSERT APPLICATION
    // =========================
    private void insertApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        // 1️⃣ Get adopter from session (FULL data)
        AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");

        int petId = Integer.parseInt(request.getParameter("petId"));

        // 2️⃣ Build application bean
        ApplicationBean application = new ApplicationBean();
        application.setAdopter(adopter);
        application.setPetId(petId);
        application.setAppStatus("Pending");
        application.setAppEligibility("Pending");
        application.setHasOwnedPet(request.getParameter("hasOwnedPet"));
        application.setCaretakerInfo(request.getParameter("caretakerInfo"));
        application.setPetEnvironment(request.getParameter("petEnvironment"));
        application.setMedicalReady(request.getParameter("medicalReady"));
        application.setAdoptionReason(request.getParameter("adoptionReason"));

        // 3️⃣ Insert application (ALWAYS new record)
        applicationDao.insertApplication(application);

        // 4️⃣ Update adopter editable fields (FIRST application or later)
        String occupation = request.getParameter("adoptOccupation");
        String incomeStr = request.getParameter("adoptIncome");

        if (occupation != null && !occupation.isEmpty()) {
            adopter.setAdoptOccupation(occupation);
        }

        if (incomeStr != null && !incomeStr.isEmpty()) {
            adopter.setAdoptIncome(Double.parseDouble(incomeStr));
        }

        adopterDao.updateAdopterApplicationInfo(adopter);

        // 5️⃣ Refresh adopter session
        AdopterBean refreshedAdopter =
                adopterDao.getAdopterById(adopter.getAdoptId());

        session.setAttribute("adopter", refreshedAdopter);

        // 6️⃣ Redirect
        response.sendRedirect("ApplicationController?action=dashboardA");
    }

    // =========================
    // UPDATE STATUS (STAFF)
    // =========================
    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        String status = request.getParameter("status");
        String eligibility = request.getParameter("eligibility");

        applicationDao.updateStatus(appId, status, eligibility);
        response.sendRedirect("ApplicationController?action=dashboardA");
    }

    // =========================
    // DELETE APPLICATION
    // =========================
    private void deleteApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        applicationDao.deleteApplication(appId);
        response.sendRedirect("ApplicationController?action=dashboardA");
    }

    // =========================
    // MANAGE APPLICATIONS (STAFF)
    // =========================
    private void manageApplications(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<ApplicationBean> applications =
                applicationDao.getAllApplications();

        request.setAttribute("applications", applications);
        request.getRequestDispatcher("ManageApplications.jsp").forward(request, response);
    }
}
