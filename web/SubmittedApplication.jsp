<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
<title>Submitted Application Details</title>

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
</style>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsA.png" alt="PAWS Logo">
        </a>

        <div class="navbar-links">
            <a href="Home.html">Home</a>
            <a href="AboutUs.html">About</a>
            <a href="DashboardA.jsp">Dashboard</a>
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

<c:if test="${empty application}">
    <c:redirect url="ApplicationController?action=dashboardA"/>
</c:if>
    
<div class="content-box">
    <h2>Submitted Application Details</h2>

    <div class="info-row">
        <p>
            <span class="label">Applicant Name:</span>
            ${application.adopter.adoptFName}
            ${application.adopter.adoptLName}
        </p>

        <p>
            <span class="label">Pet Name:</span>
            ${application.pet.petName}
        </p>

        <p>
            <span class="label">Status:</span>
            ${application.appStatus}
        </p>

        <p>
            <span class="label">Eligibility:</span>
            ${application.appEligibility}
        </p>

        <p>
            <span class="label">Date Submitted:</span>
            <fmt:formatDate value="${application.appDate}" pattern="dd MMM yyyy"/>
        </p>
    </div>

    <div class="btn-container">
    <a href="ApplicationController?action=dashboardA">
        <button class="back-btn">Back</button>
    </a>

    <form action="ApplicationController" method="post"
          onsubmit="return confirm('Are you sure you want to delete this application?');">

        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="appId" value="${application.appId}">

        <button type="submit" class="back-btn" style="background:#ff6b6b;">
            Delete
        </button>
    </form>
</div>

</div>

<!-- ================= FOOTER ================= -->
<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System — All Rights Reserved
</div>

</body>
</html>
