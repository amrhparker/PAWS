<%-- 
    Document   : ManageRecords
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (session.getAttribute("staff") == null) {
        response.sendRedirect("LogInStaff.jsp");
        return;
    }
%>

<%
    /*
     FLOW (LECTURER STYLE):
     1. User run JSP
     2. Kalau "records" belum ada → JSP redirect controller
     3. Controller ambil data
     4. Controller forward balik ke JSP
     5. JSP display data
    */

    if (request.getAttribute("records") == null) {
        response.sendRedirect("RecordController?action=list");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Records</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        body{
            font-family:'Poppins', sans-serif;
            background:#f6f7fb;
            margin:0;
        }

        .content{
            width:80%;
            margin:10px auto;
        }

        .record-card{
            background:#b4c0f6;;
            border-radius:15px;
            padding:22px 28px;
            margin-bottom:18px;
            box-shadow:0 8px 20px rgba(0,0,0,0.08);
            display:flex;
            justify-content:space-between;
            align-items:center;
        }

        .record-title{
            font-size:18px;
            font-weight:500;
        }

        .view-btn {
            padding: 6px 16px;
            background: #ff66c4;
            color: white;
            border-radius: 20px;
            text-decoration: none;
            font-size: 16px;
            font-weight: 500;
        }

        .view-btn:hover {
            background: #4abf8a;
        }

        .delete-btn {
            padding: 6px 16px;
            margin-left: 10px;
            background: red;
            color: white;
            border-radius: 20px;
            text-decoration: none;
            font-size: 16px;
            font-weight: 500;
        }

        .delete-btn:hover {
            background: #4abf8a;
        }


        .empty{
            background:white;
            padding:30px;
            border-radius:14px;
            text-align:center;
            color:gray;
            box-shadow:0 6px 16px rgba(0,0,0,0.06);
        }
    </style>
</head>

<body>

<div class="navbar">
    <div class="navbar-left">
        <a href="Home.jsp">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>
        <div class="navbar-links">
            <a href="StaffDashboard.jsp">Dashboard</a>
            <a href="ManagePets.jsp">Pets</a>
            <a href="ManageApplications.jsp">Applications</a>
            <a href="ManageRecords.jsp">Records</a>
            <a href="ReportController">Reports</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>

<div class="page-header">
    <h2>Adoption Records</h2>
</div>
    
<div class="content">

    <c:forEach var="r" items="${records}">
        <div class="record-card">

            <div class="record-title">
                Adoption ${r.recordId}
            </div>

            <div class="actions">
                <a class="view-btn"
                   href="ViewRecords.jsp?recordId=${r.recordId}">
                   View
                </a>

                <a class="delete-btn"
                   href="RecordController?action=delete&recordId=${r.recordId}"
                   onclick="return confirm('Delete this record?');">
                   Delete
                </a>
            </div>

        </div>
    </c:forEach>

    <c:if test="${empty records}">
        <div class="empty">
            No adoption records found
        </div>
    </c:if>

</div>

</body>
</html>
