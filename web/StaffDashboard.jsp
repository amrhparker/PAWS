<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    if (session.getAttribute("staff") == null) {
        response.sendRedirect("LogInStaff.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Staff Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
    
<%
    String loginSuccess = (String) session.getAttribute("loginSuccess");
    if ("staff".equals(loginSuccess)) {
%>
    <script>
        alert("Staff logged in successfully!");
    </script>
<%
        session.removeAttribute("loginSuccess"); 
    }
%>

<div class="navbar">
    <div class="navbar-left">
        <a href="Home.jsp">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>
    </div>

    <div class="navbar-links">
        <a href="StaffDashboard.jsp">Dashboard</a>
        <a href="PetController">Pets</a>
        <a href="RecordController?action=list">Records</a>
        <a href="ManageReports.jsp">Reports</a>
        <a href="ManageApplications.jsp">Applications</a>
        <a href="ActivityLog.jsp">Logs</a>
        <a href="LogoutServlet" class="logout">LOG OUT</a>
    </div>
</div>

<div class="page-header">
    <h2>Staff Dashboard</h2>
</div>

<div class="manage-grid">

    <div class="manage-card">
        <a href="PetController">
            <h3>Manage Pets</h3>
            <p class="sub">Add, see, edit, or remove pets in our system</p>
        </a>
    </div>

    <div class="manage-card">
        <a href="RecordController?action=list">
            <h3>Manage Records</h3>
            <p class="sub">View records, trend and graph of adoptions</p>
        </a>
    </div>

    <div class="manage-card">
        <a href="ManageReports.jsp">
            <h3>Manage Reports</h3>
            <p class="sub">View reports throughout the year</p>
        </a>
    </div>

    <div class="manage-card">
        <a href="ManageApplications.jsp">
            <h3>Manage Applications</h3>
            <p class="sub">View adoption applications submitted by customers</p>
        </a>
    </div>

</div>

</body>
</html>
