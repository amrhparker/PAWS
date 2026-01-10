package controller;

import dao.ActivityLogDao;
import model.ActivityLogBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

//@WebServlet("/ActivityLogController")
public class ActivityLogController extends HttpServlet {

    private ActivityLogDao dao;

    @Override
    public void init() {
        dao = new ActivityLogDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ActivityLogBean> logs = dao.getAllLogs();
        request.setAttribute("logs", logs);
        request.getRequestDispatcher("ActivityLog.jsp").forward(request, response);
    }
}
