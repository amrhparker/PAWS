package controller;

import dao.ReportDao;
import model.RecordBean;
import model.ReportBean;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ReportController extends HttpServlet {

    private ReportDao dao;

    @Override
    public void init() {
        dao = new ReportDao();
    }

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");

    // View
    if ("view".equals(action)) {

        int reportId = Integer.parseInt(request.getParameter("reportId"));

        ReportBean report = dao.getReportById(reportId);
        List<RecordBean> records = dao.getReportDetails(reportId);

        request.setAttribute("report", report);
        request.setAttribute("records", records);

        request.getRequestDispatcher("ViewReports.jsp")
               .forward(request, response);
        return;
    }
    
    // Delete
    if ("delete".equals(action)) {
        int reportId = Integer.parseInt(request.getParameter("reportId"));

        dao.deleteReport(reportId);  

        response.sendRedirect("ReportController"); 
        return;
    }
    
        List<ReportBean> reports = dao.getAllReports();
        request.setAttribute("reports", reports);

        request.getRequestDispatcher("ManageReports.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reportType = request.getParameter("reportType");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String petType = request.getParameter("petType");

        List<RecordBean> records =
                dao.getRecordsByFilter(reportType, fromDate, toDate, petType);

        dao.createReport(reportType, records);

        response.sendRedirect("ReportController");
    }
}
