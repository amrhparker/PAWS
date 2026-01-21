package controller;

import dao.RecordDao;
import model.RecordBean;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class RecordController extends HttpServlet {

    private RecordDao dao;

    @Override
    public void init() {
        dao = new RecordDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            listRecords(request, response);

        } else if (action.equals("view")) {
            // Staff's
            viewRecord(request, response);

        } else if (action.equals("viewAdopter")) {
            // Adopter's
            viewRecordAdopter(request, response);

        } else if (action.equals("delete")) {
            deleteRecord(request, response);

        } else if (action.equals("complete")) {
            completeRecord(request, response);

        } else {
            listRecords(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            insertRecord(request, response);
        } else {
            response.sendRedirect("RecordController?action=list");
        }
    }


    // Create
    private void insertRecord(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        RecordBean record = new RecordBean();
        record.setAppId(Integer.parseInt(request.getParameter("appId")));

        dao.insertRecord(record);
        response.sendRedirect("RecordController?action=list");
    }

    // Display
    private void listRecords(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<RecordBean> records = dao.getAllRecords();
        request.setAttribute("records", records);
        request.getRequestDispatcher("ManageRecords.jsp").forward(request, response);
    }

    // Display by ID for Staff
    private void viewRecord(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int recordId = Integer.parseInt(request.getParameter("recordId"));
        RecordBean record = dao.getRecordById(recordId);

        request.setAttribute("record", record);
        request.getRequestDispatcher("ViewRecords.jsp").forward(request, response);
    }

    // Display by ID for Adopter
    private void viewRecordAdopter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int recordId = Integer.parseInt(request.getParameter("recordId"));
        RecordBean record = dao.getRecordById(recordId);

        request.setAttribute("record", record);
        request.getRequestDispatcher("AdoptionRecord.jsp").forward(request, response);
    }

    // Delete
    private void deleteRecord(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int recordId = Integer.parseInt(request.getParameter("recordId"));
        dao.deleteRecord(recordId);
        response.sendRedirect("RecordController?action=list");
    }

    // Update Record Status
    private void completeRecord(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int recordId = Integer.parseInt(request.getParameter("recordId"));
        dao.updateRecordStatus(recordId, "Completed");

        response.sendRedirect(
    "RecordController?action=view&recordId=" + recordId + "&completed=success"
);

    }
}
