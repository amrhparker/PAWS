<%-- 
    Document   : ManageReports
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    /*
     FLOW:
     1. User buka ManageReports.jsp
     2. Kalau "reports" belum ada → redirect Controller
     3. Controller fetch data → forward balik JSP
     4. JSP display data
    */
    if (request.getAttribute("reports") == null) {
        response.sendRedirect("ReportController?action=list");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Adoption Reports</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        body{
            font-family:'Poppins', sans-serif;
            background:#f6f7fb;
            margin:0;
        }

        .content{
            width:90%;
            margin:40px auto;
        }

        h2{
            margin-bottom:18px;
            font-weight:600;
        }

        /* FILTER */
        .filter-bar{
            margin-bottom:20px;
        }

        .filter-bar form{
            display:flex;
            gap:10px;
            align-items:center;
        }

        .filter-bar input{
            padding:8px 12px;
            border-radius:8px;
            border:1px solid #ddd;
            font-size:14px;
        }

        .filter-bar button{
            padding:8px 18px;
            border:none;
            border-radius:8px;
            background:#4a90e2;
            color:white;
            font-size:14px;
            cursor:pointer;
        }

        .filter-bar button:hover{
            background:#357bd8;
        }

        /* CARD */
        .report-card{
            background:white;
            border-radius:14px;
            padding:18px 22px;
            margin-bottom:14px;
            box-shadow:0 6px 16px rgba(0,0,0,0.08);
            display:flex;
            justify-content:space-between;
            align-items:center;
        }

        .report-info{
            display:flex;
            gap:25px;
            font-size:15px;
        }

        .label{
            font-weight:500;
            color:#444;
        }

        .actions a{
            text-decoration:none;
            padding:8px 16px;
            border-radius:8px;
            font-size:14px;
            font-weight:500;
            margin-left:8px;
        }

        .view-btn{
            background:#0d6efd;
            color:white;
        }

        .delete-btn{
            background:#dc3545;
            color:white;
        }

        .view-btn:hover{ background:#0b5ed7; }
        .delete-btn:hover{ background:#bb2d3b; }

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

<!-- ===== NAVBAR (KEKAL ORIGINAL) ===== -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>

        <div class="navbar-links">
            <a href="StaffDashboard.jsp">Dashboard</a>
            <a href="ManagePets.jsp">Pets</a>
            <a href="ManageRecords.jsp">Records</a>
            <a href="ManageReports.jsp" class="active">Reports</a>
            <a href="ManageApplications.jsp">Applications</a>
            <a href="ActivityLog.jsp">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>

<!-- ===== CONTENT ===== -->
<div class="content">

    <h2>Adoption Reports</h2>

    <!-- FILTER -->
    <div class="filter-bar">
        <form action="ReportController" method="get">
            <input type="hidden" name="action" value="list">

            <input type="text" name="recordId" placeholder="Record ID">
            <input type="date" name="reportDate">

            <button type="submit">Filter</button>
        </form>
    </div>

    <!-- REPORT LIST -->
    <c:forEach var="r" items="${reports}">
        <div class="report-card">

            <div class="report-info">
                <div>
                    <span class="label">Report:</span> ${r.reportId}
                </div>
                <div>
                    <span class="label">Record:</span> ${r.recordId}
                </div>
                <div>
                    <span class="label">Date:</span> ${r.reportDate}
                </div>
            </div>

            <div class="actions">
                <a class="view-btn"
                   href="ViewReports.jsp?reportId=${r.reportId}">
                   View
                </a>

                <a class="delete-btn"
                   href="ReportController?action=delete&reportId=${r.reportId}"
                   onclick="return confirm('Delete this report?');">
                   Delete
                </a>
            </div>

        </div>
    </c:forEach>

    <c:if test="${empty reports}">
        <div class="empty">
            No adoption reports found
        </div>
    </c:if>

</div>

</body>
</html>
