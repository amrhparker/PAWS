package controller;

import dao.ApplicationDao;
import dao.AdopterDao;
import dao.RecordDao;
import dao.PetDao;
import model.RecordBean;
import model.ApplicationBean;
import model.AdopterBean;
import model.StaffBean;
import model.PetBean;
import util.EmailUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ApplicationController extends HttpServlet {

    private ApplicationDao applicationDao;
    private AdopterDao adopterDao;
    private RecordDao recordDao;
    private PetDao petDao;

    @Override
    public void init() {
        applicationDao = new ApplicationDao();
        adopterDao = new AdopterDao();
        recordDao = new RecordDao();
        petDao = new PetDao();
    }

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

    private void showAdopterDashboard(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp?error=loginRequired");
            return;
        }

        AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");

        request.setAttribute(
                "applications",
                applicationDao.getApplicationsByAdopter(adopter.getAdoptId())
        );

        request.setAttribute(
                "records",
                recordDao.getRecordsByAdopter(adopter.getAdoptId())
        );

        request.getRequestDispatcher("DashboardA.jsp").forward(request, response);
    }

    private void viewApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        request.setAttribute("application", applicationDao.getApplicationById(appId));
        request.getRequestDispatcher("ViewApplication.jsp").forward(request, response);
    }

    private void viewApplicationAdopter(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        request.setAttribute("application", applicationDao.getApplicationById(appId));
        request.getRequestDispatcher("SubmittedApplication.jsp").forward(request, response);
    }

    private void showApplicationForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        int petId = Integer.parseInt(request.getParameter("petId"));
        AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");
        PetBean pet = petDao.getPetById(petId);

        if (petDao.isPetAdopted(petId)) {
            response.sendRedirect("Rehome.jsp?error=petAdopted");
            return;
        }
        
        if (applicationDao.hasApplied(adopter.getAdoptId(), petId)) {
            response.sendRedirect("Rehome.jsp?error=alreadyApplied");
            return;
        }

        request.setAttribute("adopter", adopter);
        request.setAttribute("pet", pet);
        request.setAttribute("petId", petId);
        request.getRequestDispatcher("ApplicationForm.jsp").forward(request, response);
    }

    // Create
    private void insertApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adopter") == null) {
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }

        AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");
        int petId = Integer.parseInt(request.getParameter("petId"));

        // Duplication Check
        if (applicationDao.hasApplied(adopter.getAdoptId(), petId)) {
            response.sendRedirect("Rehome.jsp?error=alreadyApplied");
            return;
        }

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

        applicationDao.insertApplication(application);

        response.sendRedirect("ApplicationController?action=dashboardA");
    }

    // Update Application Status
    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staff") == null) {
            response.sendRedirect("LogInStaff.jsp");
            return;
    }

    StaffBean staff = (StaffBean) session.getAttribute("staff");
    int staffId = staff.getStaffId();

        int appId = Integer.parseInt(request.getParameter("appId"));
        String status = request.getParameter("status");
        String eligibility = request.getParameter("eligibility");

        int petId = applicationDao.getPetIdByApplication(appId);

        if ("Approved".equalsIgnoreCase(status)
                && applicationDao.isPetAlreadyApproved(petId)) {

            response.sendRedirect("ApplicationController?action=manage&error=alreadyApproved");
            return;
        }

        applicationDao.updateStatus(appId, status, eligibility, staffId);

        if ("Approved".equalsIgnoreCase(status)) {

            applicationDao.rejectOtherApplications(petId, appId);

            petDao.updateAdoptionStatus(petId, "Adopted");

            // Create Record
            RecordBean record = new RecordBean();
            record.setAppId(appId);
            record.setStaffId(staffId);
            record.setRecordStatus("Pending");
            recordDao.insertRecord(record);

            String[] emailInfo = applicationDao.getAdopterEmailInfo(appId);
            if (emailInfo != null) {
                EmailUtil.sendAdoptionApprovalEmail(
                        emailInfo[0],
                        emailInfo[1],
                        emailInfo[2]
                );
            }
        }

        response.sendRedirect("ApplicationController?action=manage");
    }

    // Delete
    private void deleteApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        applicationDao.deleteApplication(
                Integer.parseInt(request.getParameter("appId"))
        );
        response.sendRedirect("ApplicationController?action=dashboardA");
    }

    // Manage
    private void manageApplications(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        request.setAttribute(
                "applications",
                applicationDao.getAllApplications()
        );
        request.getRequestDispatcher("ManageApplications.jsp").forward(request, response);
    }
}
