package controller;

import model.ApplicationBean;
import dao.ApplicationDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ApplicationController")
public class ApplicationController extends HttpServlet {

    private ApplicationDao dao;

    @Override
    public void init() {
        dao = new ApplicationDao();
    }

    /* ================= GET ================= */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listApplications(request, response);

            } else if (action.equals("edit")) {
                showEditForm(request, response);

            } else if (action.equals("delete")) {
                deleteApplication(request, response);

            } else {
                listApplications(request, response);
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

            } else if ("update".equals(action)) {
                updateApplication(request, response);

            } else if ("updateStatus".equals(action)) {
                updateStatus(request, response);

            } else {
                response.sendRedirect("ApplicationController?action=list");
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /* ================= METHODS ================= */

    // ===== CREATE =====
    private void insertApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        ApplicationBean app = new ApplicationBean();

        app.setAdoptId(Integer.parseInt(request.getParameter("adoptId")));
        app.setPetId(Integer.parseInt(request.getParameter("petId")));
        app.setStaffId(0); // Not assigned yet

        app.setAppStatus("Pending");
        app.setAppEligibility("Pending");

        app.setHasOwnedPet(request.getParameter("hasOwnedPet"));
        app.setCaretakerInfo(request.getParameter("caretakerInfo"));
        app.setPetEnvironment(request.getParameter("petEnvironment"));
        app.setMedicalReady(request.getParameter("medicalReady"));
        app.setAdoptionReason(request.getParameter("adoptionReason"));

        dao.insertApplication(app);
        response.sendRedirect("ApplicationController?action=list");
    }

    // ===== READ ALL =====
    private void listApplications(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<ApplicationBean> list = dao.getAllApplications();
        request.setAttribute("applications", list);
        request.getRequestDispatcher("applicationList.jsp").forward(request, response);
    }

    // ===== READ BY ID (EDIT FORM) =====
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        ApplicationBean app = dao.getApplicationById(appId);

        request.setAttribute("application", app);
        request.getRequestDispatcher("applicationEdit.jsp").forward(request, response);
    }

    // ===== UPDATE FULL =====
    private void updateApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        ApplicationBean app = new ApplicationBean();

        app.setAppId(Integer.parseInt(request.getParameter("appId")));
        app.setAdoptId(Integer.parseInt(request.getParameter("adoptId")));
        app.setPetId(Integer.parseInt(request.getParameter("petId")));

        String staffId = request.getParameter("staffId");
        if (staffId == null || staffId.isEmpty())
            app.setStaffId(0);
        else
            app.setStaffId(Integer.parseInt(staffId));

        app.setAppStatus(request.getParameter("appStatus"));
        app.setAppEligibility(request.getParameter("appEligibility"));

        app.setHasOwnedPet(request.getParameter("hasOwnedPet"));
        app.setCaretakerInfo(request.getParameter("caretakerInfo"));
        app.setPetEnvironment(request.getParameter("petEnvironment"));
        app.setMedicalReady(request.getParameter("medicalReady"));
        app.setAdoptionReason(request.getParameter("adoptionReason"));

        dao.updateApplication(app);
        response.sendRedirect("ApplicationController?action=list");
    }

    // ===== UPDATE STATUS ONLY =====
    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        String status = request.getParameter("status");
        String eligibility = request.getParameter("eligibility");

        dao.updateStatus(appId, status, eligibility);
        response.sendRedirect("ApplicationController?action=list");
    }

    // ===== DELETE =====
    private void deleteApplication(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int appId = Integer.parseInt(request.getParameter("appId"));
        dao.deleteApplication(appId);
        response.sendRedirect("ApplicationController?action=list");
    }
}
