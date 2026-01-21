<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<%
    if (session.getAttribute("staff") == null) {
        response.sendRedirect("LogInStaff.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Application Details</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        body{
            font-family:'Poppins', sans-serif;
            background:#f6f7fb;
            margin:0;
        }

        .page-container{
            width:80%;
            margin:10px auto 50px;
        }

        h1{
            font-size:32px;
            margin-bottom:20px;
        }

        .info{
            font-size:18px;
            margin-bottom:30px;
        }

        .row{
            display:flex;
            gap: 35px;
            margin-bottom:35px;
        }

        .box{
            flex:1;
            background:#b7ebed;
            border-radius:16px;
            padding:22px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08), 0 10px 25px rgba(0, 0, 0, 0.12);
        }

        .box h3{
            margin-bottom:15px;
            font-size:20px;
        }

        .line{
            margin-bottom:8px;
            font-size:15px;
        }

        .label{
            font-weight:600;
        }

        .actions{
            display:flex;
            justify-content:flex-end;
            gap:15px;
            margin-top:30px;
        }

        .btn{
            padding:10px 22px;
            border-radius:8px;
            border:none;
            cursor:pointer;
            font-size:14px;
            font-weight:500;
        }

        .btn-approve{
            background:#22c55e;
            color:white;
        }

        .btn-approve:hover{
            background:#16a34a;
        }

        .btn-reject{
            background:#ef4444;
            color:white;
        }

        .btn-reject:hover{
            background:#dc2626;
        }

        .btn-back{
            background:#e5e7eb;
            color:#111;
        }

        .btn-back:hover{
            background:#d1d5db;
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
            <a href="ReportController">Reports</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>
    
    <div class="page-header">
        <h2>Application #${application.appId} Details</h2>
    </div>

<div class="page-container">
    
    <div class="line2"></div>

    <div class="info">
        <p><b>Status:</b> ${application.appStatus}</p>
        <p><b>Managed By:</b> ${application.staffName}</p>
    </div>
    
    <div class="line2"></div>

    <div class="row">

        <div class="box">
            <h3>👤 Adopter Information</h3>
            <div class="line"><span class="label">𖤓 Name:</span>
                ${application.adopter.adoptFName} ${application.adopter.adoptLName}</div>
            <div class="line"><span class="label">𖤓 Phone:</span>
                ${application.adopter.adoptPhoneNum}</div>
            <div class="line"><span class="label">𖤓 Occupation:</span>
                ${application.adopter.adoptOccupation}</div>
            <div class="line"><span class="label">𖤓 Income:</span>
                ${application.adopter.adoptIncome}</div>
            <div class="line"><span class="label">𖤓 Email:</span>
                ${application.adopter.adoptEmail}</div>
            <div class="line"><span class="label">𖤓 Address:</span>
                ${application.adopter.adoptAddress}</div>
        </div>

        <div class="box">
            <h3>🐶🐱 Pet Information</h3>
            <div class="line"><span class="label">𖤓 Pet Name:</span>
                ${application.pet.petName}</div>
            <div class="line"><span class="label">𖤓 Description:</span>
                ${application.pet.petDesc}</div>
            <div class="line"><span class="label">𖤓 Species:</span>
                ${application.pet.petSpecies}</div>
            <div class="line"><span class="label">𖤓 Gender:</span>
                ${application.pet.petGender}</div>
            <div class="line"><span class="label">𖤓 Breed:</span>
                ${application.pet.petBreed}</div>
            <div class="line"><span class="label">𖤓 Age:</span>
                ${application.pet.petAge}</div>
            <div class="line"><span class="label">𖤓 Health Status:</span>
                ${application.pet.petHealthStatus}</div>
        </div>
    </div>

    <div class="box">
        <h3>✅ Eligibility Review</h3>
        <div class="line"><span class="label">𖤓 Owned a pet before:</span> ${application.hasOwnedPet}</div>
        <div class="line"><span class="label">𖤓 Caretaker:</span> ${application.caretakerInfo}</div>
        <div class="line"><span class="label">𖤓 Pet Environment:</span> ${application.petEnvironment}</div>
        <div class="line"><span class="label">𖤓 Medical Expenses Ready:</span> ${application.medicalReady}</div>
        <div class="line"><span class="label">𖤓 Adoption Reason:</span> ${application.adoptionReason}</div>
    </div>

    <div class="actions">

            <c:if test="${not empty application.appStatus 
             and fn:toLowerCase(application.appStatus) eq 'pending'}">


            <form action="ApplicationController" method="post">
                <input type="hidden" name="action" value="updateStatus">
                <input type="hidden" name="appId" value="${application.appId}">
                <input type="hidden" name="status" value="Approved">
                <input type="hidden" name="eligibility" value="Eligible">
                <button class="btn btn-approve">Approve</button>
            </form>

            <form action="ApplicationController" method="post">
                <input type="hidden" name="action" value="updateStatus">
                <input type="hidden" name="appId" value="${application.appId}">
                <input type="hidden" name="status" value="Rejected">
                <input type="hidden" name="eligibility" value="Not Eligible">
                <button class="btn btn-reject">Reject</button>
            </form>

        </c:if>

        <a href="ApplicationController?action=manage" class="bttn">
            Back
        </a>

    </div>

</div>
<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System
</div>
</body>
</html>
