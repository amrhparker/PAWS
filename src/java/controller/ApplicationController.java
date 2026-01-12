package controller;

import dao.ApplicationDao;
import dao.AdopterDao;
import dao.RecordDao;
import model.RecordBean;
import model.ApplicationBean;
import model.AdopterBean;
import util.EmailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ApplicationController extends HttpServlet {

    private ApplicationDao applicationDao;
    private AdopterDao adopterDao;
    private RecordDao recordDao;

    @Override
    public void init() {
        applicationDao = new ApplicationDao();
        adopterDao = new AdopterDao();
        recordDao = new RecordDao();
    }

    /* ================= GET ================= */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("dashboardA")) {
                showAdopterDashboard(request, response);

            } else if (action.equals("form")) {
                showApplicationForm(request, response);

            } else if (action.equals("view")) {
                viewApplication(request, response);

            } else if (action.equals("viewAdopter")) {
                viewApplicationAdopter(request, response);

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

    /* ================= POST ================= */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                insertApplication(request, response);

            } else if ("updateStatus".equals(action)) {
                updateStatus(request, response);

            } else if ("delete".equals(action)) {
                deleteApplication(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /* ================= DASHBOARD (ADOPTER) ================= */
    private void showAdopterDashboard(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp?error=loginRequired");
            return;
        }

        AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");
        List<ApplicationBean> applications =
                applicationDao.getApplicationsByAdopter(adopter.getAdoptId());
        List<RecordBean> records =
                recordDao.getRecordsByAdopter(adopter.getAdoptId());
        request.setAttribute("applications", applications);
        request.setAttribute("records", records);
        request.getRequestDispatcher("DashboardA.jsp").forward(request, response);
    }

    /* ================= VIEW APPLICATION ================= */
    private void viewApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        ApplicationBean application = applicationDao.getApplicationById(appId);

        request.setAttribute("application", application);
        request.getRequestDispatcher("ViewApplication.jsp").forward(request, response);
    }

    private void viewApplicationAdopter(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        ApplicationBean application = applicationDao.getApplicationById(appId);

        request.setAttribute("application", application);
        request.getRequestDispatcher("SubmittedApplication.jsp").forward(request, response);
    }

    /* ================= SHOW FORM ================= */
    private void showApplicationForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        AdopterBean adopter =
                adopterDao.getAdopterById(
                        ((AdopterBean) session.getAttribute("adopter")).getAdoptId()
                );

        session.setAttribute("adopter", adopter);
        request.setAttribute("adopter", adopter);
        request.setAttribute("petId",
                Integer.parseInt(request.getParameter("petId")));

        request.getRequestDispatcher("ApplicationForm.jsp").forward(request, response);
    }

    /* ================= INSERT APPLICATION ================= */
    private void insertApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");

        ApplicationBean application = new ApplicationBean();
        application.setAdopter(adopter);
        application.setPetId(Integer.parseInt(request.getParameter("petId")));
        application.setAppStatus("Pending");
        application.setAppEligibility("Pending");
        application.setHasOwnedPet(request.getParameter("hasOwnedPet"));
        application.setCaretakerInfo(request.getParameter("caretakerInfo"));
        application.setPetEnvironment(request.getParameter("petEnvironment"));
        application.setMedicalReady(request.getParameter("medicalReady"));
        application.setAdoptionReason(request.getParameter("adoptionReason"));

        applicationDao.insertApplication(application);

        response.sendRedirect("ApplicationController?action=dashboardA");
    }

    /* ================= UPDATE STATUS (STAFF) ================= */
    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException {

    int appId = Integer.parseInt(request.getParameter("appId"));
    String status = request.getParameter("status");
    String eligibility = request.getParameter("eligibility");

    // 1️⃣ Update application
    applicationDao.updateStatus(appId, status, eligibility);

    // 2️⃣ If APPROVED → create record + send email
    if ("Approved".equalsIgnoreCase(status)) {

        // CREATE RECORD
        RecordBean record = new RecordBean();
        record.setAppId(appId);
        record.setRecordStatus("Pending");
        recordDao.insertRecord(record);

        // GET EMAIL INFO
        String[] emailInfo = applicationDao.getAdopterEmailInfo(appId);

        if (emailInfo != null) {
            System.out.println("Sending email to: " + emailInfo[0]);

            EmailUtil.sendAdoptionApprovalEmail(
                emailInfo[0], // adopter email
                emailInfo[1], // adopter name
                emailInfo[2]  // pet name
            );
        } else {
            System.out.println("Email info is NULL for appId=" + appId);
        }
    }

    // 3️⃣ Redirect AFTER everything
    response.sendRedirect("ApplicationController?action=manage&email=sent");
}


    /* ================= DELETE ================= */
    private void deleteApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        applicationDao.deleteApplication(
                Integer.parseInt(request.getParameter("appId"))
        );
        response.sendRedirect("ApplicationController?action=dashboardA");
    }

    /* ================= MANAGE ================= */
    private void manageApplications(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        request.setAttribute(
                "applications",
                applicationDao.getAllApplications()
        );
        request.getRequestDispatcher("ManageApplications.jsp").forward(request, response);
    }
}
