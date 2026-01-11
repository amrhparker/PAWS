<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
            width:90%;
            margin:40px auto;
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
            gap:25px;
            margin-bottom:30px;
        }

        .box{
            flex:1;
            background:white;
            border-radius:16px;
            padding:22px;
            box-shadow:0 6px 16px rgba(0,0,0,0.08);
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

<!-- NAVBAR -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html"><img src="pawsS.png" alt="PAWS Staff"></a>

        <div class="navbar-links">
            <a href="StaffDashboard.jsp">Dashboard</a>
            <a href="ManagePets.jsp">Pets</a>
            <a href="ManageRecords.jsp">Records</a>
            <a href="ManageReports.jsp">Reports</a>
            <a href="ApplicationController?action=manage" class="active">Applications</a>
            <a href="ActivityLog.jsp">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>

<div class="page-container">

    <h1>Application Details</h1>

    <div class="info">
        <p><b>Application ID:</b> #${application.appId}</p>
        <p><b>Status:</b> ${application.appStatus}</p>
    </div>

    <div class="row">

        <!-- ADOPTER -->
        <div class="box">
            <h3>Adopter Information</h3>
            <div class="line"><span class="label">Name:</span>
                ${application.adopter.adoptFName} ${application.adopter.adoptLName}</div>
            <div class="line"><span class="label">Phone:</span>
                ${application.adopter.adoptPhoneNum}</div>
            <div class="line"><span class="label">Address:</span>
                ${application.adopter.adoptAddress}</div>
        </div>

        <!-- PET -->
        <div class="box">
            <h3>Pet Information</h3>
            <div class="line"><span class="label">Pet Name:</span>
                ${application.pet.petName}</div>
            <div class="line"><span class="label">Species:</span>
                ${application.pet.petSpecies}</div>
            <div class="line"><span class="label">Breed:</span>
                ${application.pet.petBreed}</div>
            <div class="line"><span class="label">Age:</span>
                ${application.pet.petAge}</div>
        </div>
    </div>

    <!-- ELIGIBILITY -->
    <div class="box">
        <h3>Eligibility Review</h3>
        <div class="line"><span class="label">Owned pet before:</span> ${application.hasOwnedPet}</div>
        <div class="line"><span class="label">Caretaker:</span> ${application.caretakerInfo}</div>
        <div class="line"><span class="label">Environment:</span> ${application.petEnvironment}</div>
        <div class="line"><span class="label">Medical Ready:</span> ${application.medicalReady}</div>
        <div class="line"><span class="label">Reason:</span> ${application.adoptionReason}</div>
    </div>

    <!-- ACTION BUTTONS -->
    <div class="actions">

        <!-- ONLY SHOW IF PENDING -->
        <c:if test="${application.appStatus eq 'Pending'}">

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

        <a href="ApplicationController?action=manage">
            <button class="btn btn-back" type="button">Back</button>
        </a>

    </div>

</div>

</body>
</html>
