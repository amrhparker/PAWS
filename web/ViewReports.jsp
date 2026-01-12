<%-- 
    Document   : ViewReports
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("staff") == null) {
        response.sendRedirect("LogInStaff.jsp");
        return;
    }
%>

<%
    /*
     FLOW:
     1. JSP dibuka
     2. Kalau "report" belum ada → redirect Controller
     3. Controller fetch data & forward balik
     4. JSP display
    */

    if (request.getAttribute("report") == null) {

        String reportId = request.getParameter("reportId");

        if (reportId != null && !reportId.equals("")) {
            response.sendRedirect(
                "ReportController?action=view&reportId=" + reportId
            );
            return;
        } else {
            response.sendRedirect("ReportController?action=list");
            return;
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Adoption Report</title>

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

        .card{
            width:90%;
            max-width:700px;
            margin:auto;
            background:white;
            border-radius:16px;
            padding:35px;
            box-shadow:0 10px 25px rgba(0,0,0,0.08);
        }

        .field{
            margin-bottom:22px;
        }

        .label{
            font-size:14px;
            color:#666;
            margin-bottom:6px;
            font-weight:500;
        }

        .value{
            background:#f5f6fa;
            padding:12px 14px;
            border-radius:10px;
            font-size:15px;
            font-weight:500;
        }

        .back-btn{
            display:inline-block;
            margin-top:25px;
            padding:12px 26px;
            background:#333;
            color:white;
            border-radius:10px;
            text-decoration:none;
            font-size:14px;
        }

        .back-btn:hover{
            background:#555;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.jsp">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>

        <div class="navbar-links">
            <a href="StaffDashboard.html">Dashboard</a>
            <a href="ManagePets.html">Pets</a>
            <a href="RecordController?action=list">Records</a>
            <a href="ReportController?action=list" class="active">Reports</a>
            <a href="ApplicationController?action=list">Applications</a>
            <a href="ActivityLog.html">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>

<!-- TITLE -->
<div class="page-title">Adoption Report</div>

<!-- REPORT DETAILS -->
<div class="card">

    <div class="field">
        <div class="label">Report ID</div>
        <div class="value">Report ${report.reportId}</div>
    </div>

    <div class="field">
        <div class="label">Record ID</div>
        <div class="value">${report.recordId}</div>
    </div>

    <div class="field">
        <div class="label">Staff ID</div>
        <div class="value">${report.staffId}</div>
    </div>

    <div class="field">
        <div class="label">Report Type</div>
        <div class="value">${report.reportType}</div>
    </div>

    <div class="field">
        <div class="label">Report Date</div>
        <div class="value">${report.reportDate}</div>
    </div>

    <a href="ReportController?action=list" class="back-btn">
        Back to Reports
    </a>

</div>

</body>
</html>
