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

        HttpSession session = request.getSession();
        int adoptId = (int) session.getAttribute("adoptId");

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
    private void showApplicationForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(false);
        int adoptId = (int) session.getAttribute("adoptId");
        int petId = Integer.parseInt(request.getParameter("petId"));

        AdopterBean adopter = adopterDao.getAdopterById(adoptId);

        request.setAttribute("adopter", adopter);
        request.setAttribute("petId", petId);

        request.getRequestDispatcher("applicationform.jsp").forward(request, response);
    }

    // Insert application
private void insertApplication(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException {

    int adoptId = Integer.parseInt(request.getParameter("adoptId"));
    int petId = Integer.parseInt(request.getParameter("petId"));

    // 1️⃣ Insert application
    ApplicationBean app = new ApplicationBean();
    app.setAdoptId(adoptId);
    app.setPetId(petId);
    app.setAppStatus("Pending");
    app.setAppEligibility("Pending");
    app.setHasOwnedPet(request.getParameter("hasOwnedPet"));
    app.setCaretakerInfo(request.getParameter("caretakerInfo"));
    app.setPetEnvironment(request.getParameter("petEnvironment"));
    app.setMedicalReady(request.getParameter("medicalReady"));
    app.setAdoptionReason(request.getParameter("adoptionReason"));

    dao.insertApplication(app);

    // 2️⃣ Update Adopter table with new details
    AdopterBean updatedAdopter = new AdopterBean();
    updatedAdopter.setAdoptId(adoptId);
    updatedAdopter.setAdoptFName(request.getParameter("adoptFName"));
    updatedAdopter.setAdoptLName(request.getParameter("adoptLName"));
    updatedAdopter.setAdoptIC(request.getParameter("adoptIC"));
    updatedAdopter.setAdoptPhoneNum(request.getParameter("adoptPhoneNum"));
    updatedAdopter.setAdoptAddress(request.getParameter("adoptAddress"));
    updatedAdopter.setAdoptOccupation(request.getParameter("adoptOccupation"));
    updatedAdopter.setAdoptIncome(Double.parseDouble(request.getParameter("adoptIncome")));

    adopterDao.updateAdopter(updatedAdopter);

    // 3️⃣ Refresh session
    HttpSession session = request.getSession();
    session.setAttribute("adopter", adopterDao.getAdopterById(adoptId));

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
}
