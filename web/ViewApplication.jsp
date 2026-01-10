<%-- 
    Document   : ViewApplication
    Created on : Jan 10, 2026, 2:37:23 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Application Details</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        .page-container{
            width:95%;
            margin:auto;
            margin-top:25px;
        }

        #heading-title{
            font-size:32px;
            font-weight:bold;
            margin-bottom:20px;
            text-align:left;
        }

        .basic-info{
            font-size:20px;
            margin-bottom:25px;
        }

        .flex-row{
            display:flex;
            gap:30px;
            width:100%;
            margin-bottom:30px;
        }

        .section-box{
            flex:1;
            padding:20px;
            border:1px solid black;
            border-radius:15px;
            background-color:#e6e6e6;
        }

        .section-title{
            font-size:24px;
            font-weight:bold;
            margin-bottom:12px;
        }

        .info-line{
            font-size:18px;
            margin-bottom:8px;
        }

        .label{
            font-weight:bold;
        }

        #eligibility{
            width:100%;
            padding:20px;
            border:1px solid black;
            border-radius:15px;
            background-color:#e6e6e6;
            margin-bottom:40px;
        }

        .action-buttons{
            display:flex;
            justify-content:flex-end;
            gap:20px;
            margin-bottom:50px;
        }

        .btn{
            padding:10px 25px;
            font-size:16px;
            border:1px solid black;
            border-radius:8px;
            background:white;
            cursor:pointer;
            text-decoration:none;
            color:black;
        }

        .btn:hover{
            background:#d9d9d9;
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
            <a href="ReportController?action=list">Reports</a>
            <a href="ApplicationController?action=list" class="active">Applications</a>
            <a href="ActivityLog.html">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="Logout.html" class="logout">Log Out</a>
    </div>
</div>

<div class="page-container">

    <!-- TITLE -->
    <div id="heading-title">Application Details</div>

    <!-- BASIC INFO -->
    <div class="basic-info">
        <p><b>Application ID:</b> APP${application.appId}</p>
        <p><b>Date Submitted:</b> ${application.appDate}</p>
        <p><b>Current Status:</b> ${application.appStatus}</p>
    </div>

    <!-- ADOPTER + PET -->
    <div class="flex-row">

        <!-- ADOPTER -->
        <div class="section-box">
            <div class="section-title">Adopter Information</div>

            <div class="info-line"><span class="label">Adopter ID:</span> ${application.adoptId}</div>
            <div class="info-line"><span class="label">Name:</span> ${application.adopterName}</div>
            <div class="info-line"><span class="label">Email:</span> ${application.adopter.adoptEmail}</div>
            <div class="info-line"><span class="label">Phone:</span> ${application.adopter.adoptPhonenum}</div>
            <div class="info-line"><span class="label">Address:</span> ${application.adopter.adoptAddress}</div>
        </div>

        <!-- PET -->
        <div class="section-box">
            <div class="section-title">Pet Information</div>

            <div class="info-line"><span class="label">Pet ID:</span> ${application.petId}</div>
            <div class="info-line"><span class="label">Pet Name:</span> ${application.petName}</div>
            <div class="info-line"><span class="label">Species:</span> ${application.pet.petSpecies}</div>
            <div class="info-line"><span class="label">Breed:</span> ${application.pet.petBreed}</div>
            <div class="info-line"><span class="label">Age:</span> ${application.pet.petAge}</div>
            <div class="info-line"><span class="label">Health Status:</span> ${application.pet.petHealthstatus}</div>
        </div>

    </div>

    <!-- ELIGIBILITY -->
    <div id="eligibility">
        <div class="section-title">Eligibility Review</div>

        <div class="info-line"><span class="label">Owned a pet before?</span> ${application.hasOwnedPet}</div>
        <div class="info-line"><span class="label">Caretaker:</span> ${application.caretakerInfo}</div>
        <div class="info-line"><span class="label">Pet Environment:</span> ${application.petEnvironment}</div>
        <div class="info-line"><span class="label">Prepared for medical expenses?</span> ${application.medicalReady}</div>
        <div class="info-line"><span class="label">Reason to adopt:</span> ${application.adoptionReason}</div>
    </div>

    <!-- ACTION BUTTONS -->
    <div class="action-buttons">

        <c:if test="${application.appStatus == 'Pending'}">
            <form action="ApplicationController" method="post">
                <input type="hidden" name="action" value="updateStatus">
                <input type="hidden" name="appId" value="${application.appId}">
                <input type="hidden" name="status" value="Approved">
                <input type="hidden" name="eligibility" value="Eligible">
                <button type="submit" class="btn">Approve</button>
            </form>

            <form action="ApplicationController" method="post">
                <input type="hidden" name="action" value="updateStatus">
                <input type="hidden" name="appId" value="${application.appId}">
                <input type="hidden" name="status" value="Rejected">
                <input type="hidden" name="eligibility" value="Not Eligible">
                <button type="submit" class="btn">Reject</button>
            </form>
        </c:if>

        <a href="ApplicationController?action=list" class="btn">Back</a>
    </div>

</div>

</body>
</html>
