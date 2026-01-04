
package controller;

//import dao.ApplicationDAO;
//import dao.AdoptionRecordDAO;
import model.AdopterBean;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
/**
 *
 * @author amira
 */
@WebServlet(name = "AdopterController", urlPatterns = {"/AdopterController"})
public class AdopterController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if("dashboard".equals(action)){
            showDashboard(request, response);
        }else if ("profile".equals(action)){
            showProfile(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //crud methods
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("adopterId")==null){
            response.sendRedirect("AdopterLogin.jsp");
            return;
        }
        
        int adopterId = (int) session.getAttribute("adopterId");
        try{
            Connection connection = getConnectionFromPool();
            ApplicationDao appDao = new ApplicationDao(connection);
            RecordsDao recordsDao = new RecordsDao(connection);
            
            //fetch data for dashboard
            List<Application> apps = appDao.getApplicationsByAdopter(adopterId);
            List<Records> records = recordsDao.getRecordsByAdopter(adopterId);
            
            //set attributes
            request.setAttribute("applications", applications);
            request.setAttribute("records", records);
            request.setAttribute("username", session.getAttribute("username"));
            
            //forward data to DashboardA.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("DashboardA.jsp");
            dispatcher.forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute("error", "Error loading dashboard");
            request.getRequestDispatcher("error.jsp".forward(request, response));
        }
    }
    

}
