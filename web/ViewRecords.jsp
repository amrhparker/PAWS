<%-- 
    Document   : ViewRecords
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // FLOW CONTROL (JANGAN BUANG)
    if (request.getAttribute("record") == null) {
        String recordId = request.getParameter("recordId");

        if (recordId != null && !recordId.equals("")) {
            response.sendRedirect(
                "RecordController?action=view&recordId=" + recordId
            );
            return;
        } else {
            response.sendRedirect("RecordController?action=list");
            return;
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Adoption Record</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        body{
            font-family:'Poppins', sans-serif;
            background:#f6f7fb;
            margin:0;
        }

        /* PAGE TITLE */
        .page-title{
            width:90%;
            margin:35px auto 20px;
            font-size:28px;
            font-weight:600;
        }

        /* CARD */
        .record-wrapper{
            width:90%;
            margin:auto;
            display:flex;
            justify-content:center;
        }

        .record-card{
            width:100%;
            max-width:700px;
            background:white;
            padding:35px 40px;
            border-radius:16px;
            box-shadow:0 10px 25px rgba(0,0,0,0.08);
        }

        .record-card h3{
            margin-top:0;
            font-weight:600;
        }

        .divider{
            height:1px;
            background:#e0e0e0;
            margin:15px 0 25px;
        }

        .field{
            margin-bottom:22px;
        }

        .label{
            font-size:14px;
            font-weight:500;
            color:#666;
            margin-bottom:6px;
        }

        .value{
            font-size:16px;
            font-weight:500;
            color:#222;
            background:#f5f6fa;
            padding:12px 14px;
            border-radius:10px;
        }

        /* BUTTON */
        .actions{
            margin-top:30px;
        }

        .back-btn{
            display:inline-block;
            padding:12px 26px;
            background:#333;
            color:white;
            border-radius:10px;
            text-decoration:none;
            font-size:14px;
            font-weight:500;
            transition:0.2s;
        }

        .back-btn:hover{
            background:#555;
        }
    </style>
</head>

<body>

<!-- ===== PAWS NAVBAR (KEKAL) ===== -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>

        <div class="navbar-links">
            <a href="StaffDashboard.jsp">Dashboard</a>
            <a href="ManagePets.jsp">Pets</a>
            <a href="ManageRecords.jsp">Records</a>
            <a href="ManageReports.jsp">Reports</a>
            <a href="ManageApplications.jsp">Applications</a>
            <a href="ActivityLog.jsp">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>

<!-- ===== PAGE TITLE ===== -->
<div class="page-title">
    Adoption Record
</div>

<!-- ===== RECORD CARD ===== -->
<div class="record-wrapper">
    <div class="record-card">

        <h3>Adoption ${record.recordId}</h3>
        <div class="divider"></div>

        <div class="field">
            <div class="label">Application ID</div>
            <div class="value">${record.appId}</div>
        </div>

        <div class="field">
            <div class="label">Record Date</div>
            <div class="value">${record.recordDate}</div>
        </div>

        <div class="actions">
            <a href="RecordController?action=list" class="back-btn">
                Back to Records
            </a>
        </div>

    </div>
</div>

</body>
</html>
