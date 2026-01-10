package controller;

import dao.ReportDao;
import model.ReportBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ReportController")
public class ReportController extends HttpServlet {

    private ReportDao dao;

    @Override
    public void init() {
        dao = new ReportDao();
    }

    //GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            listReports(request, response);

        } else if (action.equals("view")) {
            viewReport(request, response);

        } else {
            listReports(request, response);
        }
    }

    //POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            insertReport(request, response);
        } else {
            response.sendRedirect("ReportController?action=list");
        }
    }

    /* ================= METHODS ================= */

    // CREATE
    private void insertReport(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ReportBean report = new ReportBean();

        report.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        report.setStaffId(Integer.parseInt(request.getParameter("staffId")));
        report.setReportType(request.getParameter("reportType"));

        dao.insertReport(report);
        response.sendRedirect("ReportController?action=list");
    }

    // READ ALL
    private void listReports(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ReportBean> reports = dao.getAllReports();
        request.setAttribute("reports", reports);
        request.getRequestDispatcher("ManageReports.jsp").forward(request, response);
    }

    // READ BY ID 
    private void viewReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int reportId = Integer.parseInt(request.getParameter("reportId"));
        ReportBean report = dao.getReportById(reportId);

        request.setAttribute("report", report);
        request.getRequestDispatcher("ViewReports.jsp").forward(request, response);
    }
}
