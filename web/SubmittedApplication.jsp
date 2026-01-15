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
        width: 40%;
        margin: 0px auto 40px;
        background: #ede0ca;
        padding: 35px;
        border-radius: 20px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08), 0 10px 25px rgba(0, 0, 0, 0.12);
    }
    .info-row { margin-bottom: 15px; }
    .info-row p{ margin-bottom: 15px; }
    .label { font-weight:bold; }

    .btn-container {
        margin-top: 30px;
        display:flex;
        gap:20px;
        justify-content: flex-end;
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
    
<c:if test="${param.msg == 'approved'}">
    <script>
        showPopup(
            "Action Not Allowed",
            "Application cannot be deleted as it has been approved."
        );
    </script>
</c:if>

<div class="navbar">
    <div class="navbar-left">
        <a href="Home.jsp">
            <img src="pawsA.png" alt="PAWS">
        </a>

        <div class="navbar-links">
            <a href="Home.jsp">Home</a>
            <a href="AboutUs.jsp">About Us</a>
            <a href="ApplicationController?action=dashboardA" class="active">Dashboard</a>
            <a href="Rehome.jsp">Rehome Pet</a>
        </div>
    </div>

    <div class="navbar-profile">
        <a href="AdopterController?action=profile">
            <img src="ProfileIcon.png" alt="Profile" class="profile-icon">
        </a>
        <a href="LogoutServlet" class="logout">LOG OUT</a>
    </div>
</div>
</div>

<c:if test="${empty application}">
    <c:redirect url="ApplicationController?action=dashboardA"/>
</c:if>

<div class="page-header">
    <h2>Submitted Application Details</h2>
</div>
    
<div class="content-box">

    <div class="info-row">
        <p>
            <span class="label">𖤓 Applicant Name:</span>
            ${application.adopter.adoptFName}
            ${application.adopter.adoptLName}
        </p>

        <p>
            <span class="label">𖤓 Pet Name:</span>
            ${application.pet.petName}
        </p>

        <p>
            <span class="label">𖤓 Status:</span>
            ${application.appStatus}
        </p>

        <p>
            <span class="label">𖤓 Have you owned a pet before?</span><br>
            ➛ ${application.hasOwnedPet}
        </p>
        
        <p>
            <span class="label">𖤓 Who will be responsible for feeding, grooming, vet visits?</span><br>
            ➛ ${application.caretakerInfo}
        </p>
        
        <p>
            <span class="label">𖤓 Where the pet will be kept at?</span><br>
            ➛ ${application.petEnvironment}
        </p>
        
        <p>
            <span class="label">𖤓 Medical Expenses Ready?</span><br>
            ➛ ${application.medicalReady}
        </p>
        
        <p>
            <span class="label">𖤓 Adoption Reason:</span>
            ${application.adoptionReason}
        </p>
        
        <p>
            <span class="label">𖤓 Eligibility:</span>
            ${application.appEligibility}
        </p>
        
        <p>
            <span class="label">📅 Date Submitted:</span>
            <fmt:formatDate value="${application.appDate}" pattern="dd MMM yyyy"/>
        </p>
    </div>

    <div class="btn-container">
    <a href="ApplicationController?action=dashboardA">
        <button class="back-btn">Back</button>
    </a>

<c:choose>
    <c:when test="${application.appStatus == 'Approved'}">
        <button class="back-btn" style="background:#ccc;" disabled>
            Delete
        </button>
    </c:when>

    <c:otherwise>
        <button type="button"
                class="back-btn"
                style="background:#ff6b6b;"
                onclick="openDeleteConfirm(${application.appId});">
            Delete
        </button>
    </c:otherwise>
</c:choose>


</div>

</div>

<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System 
</div>

<div id="customPopup" class="popup-overlay" style="display:none;">
    <div class="popup-box">
        <h3 id="popupTitle"></h3>
        <p id="popupMessage"></p>
        <button onclick="closePopup()">OK</button>
    </div>
</div>

<div id="deleteConfirmPopup" class="popup-overlay" style="display:none;">
    <div class="popup-box">
        <h3 id="popupTitle">Confirm Deletion</h3>
        <p id="popupMessage">
            Are you sure you want to delete this application?
        </p>

        <button onclick="confirmDelete()">Yes, Delete</button>
        <button onclick="closeDeleteConfirm()" style="margin-left:10px;">
            Cancel
        </button>
    </div>
</div>

<form id="deleteForm" action="ApplicationController" method="post" style="display:none;">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" name="appId" id="deleteAppId">
</form>

<script>
    function openDeleteConfirm(appId) {
        document.getElementById("deleteAppId").value = appId;
        document.getElementById("deleteConfirmPopup").style.display = "flex";
    }

    function closeDeleteConfirm() {
        document.getElementById("deleteConfirmPopup").style.display = "none";
    }

    function confirmDelete() {
        document.getElementById("deleteForm").submit();
    }
</script>
        
</body>
</html>
