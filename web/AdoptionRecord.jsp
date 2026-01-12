<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    if (session.getAttribute("adopter") == null) {
        response.sendRedirect("AdopterLogin.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Adoption Record Details</title>

<style>
    body { font-family: Arial, sans-serif; margin:0; padding:0; background:#f8f8f8; }

    .navbar {
        padding: 15px 30px;
        border-bottom: 1px solid #ccc;
    }

    .content-box {
        width: 70%;
        margin: 40px auto;
        background: #fff;
        padding: 30px;
        border-radius: 20px;
        box-shadow: 0 3px 8px rgba(0,0,0,0.1);
    }

    h2 { margin-top:0; }

    .info-row { margin-bottom: 15px; }
    .label { font-weight:bold; }

    .btn-container {
        margin-top: 30px;
        display:flex;
        gap:20px;
    }

    .back-btn {
        padding: 12px 26px;
        border:none;
        border-radius: 12px;
        cursor:pointer;
        font-size:16px;
        font-weight:bold;
        background:#6fc4ff;
    }

    @media print {
        body * { visibility:hidden; }
        .content-box, .content-box * { visibility:visible; }
        .content-box { position:absolute; top:0; left:0; width:100%; }
    }
</style>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="navbar">
    <div class="navbar-left">
        <a href="Home.jsp">
            <img src="pawsA.png" alt="PAWS Logo">
        </a>

        <div class="navbar-links">
            <a href="Home.jsp">Home</a>
            <a href="AboutUs.jsp">About</a>
            <a href="ApplicationController?action=dashboardA">Dashboard</a>
            <a href="Rehome.jsp">Rehome Pet</a>
        </div>
    </div>

    <div class="navbar-profile">
        <a href="Profile.jsp">
            <img src="ProfileIcon.png" alt="Profile" class="profile-icon">
        </a>
        <a href="LogoutServlet" class="logout">LOG OUT</a>
    </div>
</div>

<!-- ================= CONTENT ================= -->
<div class="content-box" id="recordBox">
    <h2>Adoption Record Details</h2>

    <div class="info-row">
        <span class="label">Adopter Name:</span>
        ${record.application.adopter.adoptFName}
        ${record.application.adopter.adoptLName}
    </div>

    <div class="info-row">
        <span class="label">Pet Name:</span>
        ${record.application.pet.petName}
    </div>

    <div class="info-row">
        <span class="label">Adoption Date:</span>
        ${record.application.appDate}
    </div>

    <div class="info-row">
        <span class="label">Contact Number:</span>
        ${record.application.adopter.adoptPhoneNum}
    </div>

    <div class="info-row">
        <span class="label">Home Address:</span>
        ${record.application.adopter.adoptAddress}
    </div>

    <div class="btn-container">
        <a href="ApplicationController?action=dashboardA">
            <button class="back-btn">Back</button>
        </a>
    </div>
</div>

<!-- ================= FOOTER ================= -->
<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System — All Rights Reserved
</div>

</body>
</html>
