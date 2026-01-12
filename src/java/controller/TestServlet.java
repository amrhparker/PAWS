package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🔥🔥🔥 TEST SERVLET HIT 🔥🔥🔥");
        response.getWriter().println("TEST OK");
    }
}
