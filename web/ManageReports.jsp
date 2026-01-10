<%-- 
    Document   : ManageReports
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    /*
     FLOW:
     1. User buka JSP
     2. Kalau "reports" belum ada → redirect Controller
     3. Controller fetch data & forward balik
     4. JSP display list
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

        .page-title{
            width:90%;
            margin:35px auto 20px;
            font-size:28px;
            font-weight:600;
        }

        .container{
            width:90%;
            margin:auto;
        }

        .card{
            background:white;
            border-radius:18px;
            padding:25px;
            box-shadow:0 8px 22px rgba(0,0,0,0.08);
        }

        table{
            width:100%;
            border-collapse:collapse;
        }

        th{
            text-align:left;
            padding:14px;
            font-size:15px;
            background:#f2f3f7;
        }

        td{
            padding:14px;
            border-top:1px solid #eee;
            font-size:14px;
        }

        .btn{
            padding:8px 16px;
            border-radius:8px;
            text-decoration:none;
            font-size:13px;
            font-weight:500;
            margin-right:6px;
            display:inline-block;
        }

        .btn-view{
            background:#4a90e2;
            color:white;
        }

        .btn-view:hover{
            background:#357bd8;
        }

        .btn-delete{
            background:#e74c3c;
            color:white;
        }

        .btn-delete:hover{
            background:#c0392b;
        }

        .empty{
            text-align:center;
            color:#888;
            padding:30px;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>

        <div class="navbar-links">
            <a href="StaffDashboard.html">Dashboard</a>
            <a href="ManagePets.html">Pets</a>
            <a href="RecordController?action=list">Records</a>
            <a href="ManageReports.jsp" class="active">Reports</a>
            <a href="ApplicationController?action=list">Applications</a>
            <a href="ActivityLog.html">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="Logout.html" class="logout">Log Out</a>
    </div>
</div>

<!-- TITLE -->
<div class="page-title">Adoption Reports</div>

<!-- REPORT LIST -->
<div class="container">
    <div class="card">

        <table>
            <tr>
                <th>Report ID</th>
                <th>Record ID</th>
                <th>Report Date</th>
                <th>Action</th>
            </tr>

            <c:forEach var="r" items="${reports}">
                <tr>
                    <td>Report ${r.reportId}</td>
                    <td>${r.recordId}</td>
                    <td>${r.reportDate}</td>
                    <td>
                        <a class="btn btn-view"
                           href="ViewReports.jsp?reportId=${r.reportId}">
                            View
                        </a>

                        <a class="btn btn-delete"
                           href="ReportController?action=delete&reportId=${r.reportId}"
                           onclick="return confirm('Are you sure you want to delete this report?');">
                            Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty reports}">
                <tr>
                    <td colspan="4" class="empty">
                        No reports available
                    </td>
                </tr>
            </c:if>

        </table>

    </div>
</div>

</body>
</html>
