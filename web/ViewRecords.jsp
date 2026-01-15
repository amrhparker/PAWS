<%-- 
    Document   : ViewRecords
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
    // ===== FLOW CONTROL (LECTURER STYLE) =====
    if (request.getAttribute("record") == null) {
        String recordId = request.getParameter("recordId");

        if (recordId != null && !recordId.isEmpty()) {
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
    <title>Adoption Record Details</title>

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

        .record-wrapper{
            width:90%;
            margin:auto;
            display:flex;
            justify-content:center;
        }

        .record-card{
            width:100%;
            max-width:750px;
            background:#edb4f6;
            padding:35px 40px;
            border-radius:20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08), 0 10px 25px rgba(0, 0, 0, 0.12);
            margin-bottom: 70px;
        }

        .record-card h3{
            margin-top:0;
            font-weight:600;
        }

        .divider{
            height:1px;
            background:black;
            margin:18px 0 25px;
        }

        .field{
            margin-bottom:20px;
        }

        .label{
            font-size:14px;
            font-weight:500;
            color:black;
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

        .status{
            display:inline-block;
            padding:6px 14px;
            border-radius:20px;
            font-size:13px;
            font-weight:600;
            color:white;
            background:#ffc107;
        }

        .status.completed{
            background:#28a745;
        }

        .actions{
            margin-top:30px;
            display:flex;
            gap:12px;
            flex-wrap:wrap;
        }
        .back-btn{
            margin-top: 20px;
            padding: 6px 16px;
            background: #ff66c4;
            color: white;
            border-radius: 20px;
            text-decoration: none;
            font-size: 15px;
            font-weight: 500;
            display:flex;
            justify-content: center;
        }

        .back-btn:hover{
            background:#4abf8a;
            transform: translateY(-5px);
        }

        .complete-btn{
            background:#28a745;
        }

        .complete-btn:hover{
            background:#218838;
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
            <a href="PetController?action=staffList">Pets</a>
            <a href="ManageApplications.jsp">Applications</a>
            <a href="ManageRecords.jsp">Records</a>
            <a href="ReportController">Reports</a>        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>

<div class="page-header">
    <h2>Adoption Record Details</h2>
</div>

<!-- ===== RECORD DETAILS ===== -->
<div class="record-wrapper">
    <div class="record-card">

        <h3>Adoption Record #${record.recordId}</h3>
        <div class="divider"></div>

        <div class="field">
            <div class="label">Adopter Name</div>
            <div class="value">${record.application.adopter.adoptFName} ${record.application.adopter.adoptLName}</div>
        </div>

        <div class="field">
            <div class="label">Pet Name</div>
            <div class="value">${record.application.pet.petName}</div>
        </div>

        <div class="field">
            <div class="label">Contact Number</div>
            <div class="value">${record.application.adopter.adoptPhoneNum}</div>
        </div>

        <div class="field">
            <div class="label">Home Address</div>
            <div class="value">${record.application.adopter.adoptAddress}</div>
        </div>

        <div class="field">
            <div class="label">Adoption Date</div>
            <div class="value">${record.recordDate}</div>
        </div>

        <div class="field">
            <div class="label">Record Status</div>
            <span class="status ${record.recordStatus == 'Completed' ? 'completed' : ''}">
                ${record.recordStatus}
            </span>
        </div>

        
        <div class="actions">
            <c:if test="${record.recordStatus == 'Pending'}">
                <a href="RecordController?action=complete&recordId=${record.recordId}"
                   class="back-btn complete-btn"
                   onclick="return confirm('Mark this adoption as completed?');">
                    Mark as Completed
                </a>
            </c:if>

        </div>
            
        
            <a href="RecordController?action=list" class="back-btn">
                Back to Records
            </a>
        

    </div>
</div>
<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System
</div>
</body>
</html>
