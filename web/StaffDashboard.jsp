<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    if (session.getAttribute("staff") == null) {
        response.sendRedirect("LogInStaff.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Staff Dashboard</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        /* ===== POPUP ===== */
        .popup-overlay {
            position: fixed;
            inset: 0;
            background: rgba(0,0,0,0.4);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }

        .popup-box {
            background: #644d4d;
            color: white;
            padding: 22px;
            border-radius: 16px;
            width: 320px;
            text-align: center;
            box-shadow: 0 10px 25px rgba(0,0,0,0.35);
        }

        .popup-box h3 {
            margin-bottom: 10px;
        }

        .popup-box button {
            margin-top: 15px;
            padding: 6px 20px;
            border: none;
            border-radius: 20px;
            background: #f2d4c2;
            color: #644d4d;
            cursor: pointer;
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
            <a href="StaffDashboard.jsp" class="active">Dashboard</a>
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
    <h2>Staff Dashboard</h2>
</div>

<div class="manage-grid">

    <div class="manage-card">
        <a href="PetController?action=staffList">
            <img src="managepets.jpg" alt="Manage Pets" class="card-img">
            <h3>Manage Pets</h3>
            <p class="sub">Add, view, edit, or remove pets in our system</p>
        </a>
    </div>

    <div class="manage-card">
        <a href="ManageApplications.jsp">
            <img src="manageapplication.jpg" alt="Manage Applications" class="card-img">
            <h3>Manage Applications</h3>
            <p class="sub">View adoption applications submitted by customers</p>
        </a>
    </div>

    <div class="manage-card">
        <a href="RecordController?action=list">
            <img src="managerecord.jpg" alt="Manage Records" class="card-img">
            <h3>Manage Records</h3>
            <p class="sub">View records of pet adoptions</p>
        </a>
    </div>

    <div class="manage-card">
        <a href="ReportController">
            <img src="managereport.jpg" alt="Manage Reports" class="card-img">
            <h3>Manage Reports</h3>
            <p class="sub">Generate and view reports throughout the year</p>
        </a>
    </div>

</div>

<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System
</div>

<div id="customPopup" class="popup-overlay">
    <div class="popup-box">
        <h3 id="popupTitle">Message</h3>
        <p id="popupMessage"></p>
        <button onclick="closePopup()">OK</button>
    </div>
</div>

<script>
function showPopup(title, message) {
    document.getElementById("popupTitle").innerText = title;
    document.getElementById("popupMessage").innerText = message;
    document.getElementById("customPopup").style.display = "flex";
}

function closePopup() {
    document.getElementById("customPopup").style.display = "none";
}
</script>

<%
    String loginSuccess = (String) session.getAttribute("loginSuccess");
    if ("staff".equals(loginSuccess)) {
        session.removeAttribute("loginSuccess");
%>
<script>
    window.addEventListener("DOMContentLoaded", function () {
        showPopup("Welcome", "Staff logged in successfully!");
    });
</script>
<%
    }
%>

</body>
</html>
