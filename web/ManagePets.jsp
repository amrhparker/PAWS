<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    if (session.getAttribute("staff") == null) {
        response.sendRedirect("LogInStaff.jsp");
        return;
    }
%>
<c:if test="${empty petList}">
    <c:redirect url="PetController"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Pets</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
    
<c:if test="${param.msg == 'deleted'}">
    <script>
        alert('Pet deleted successfully.');
    </script>
</c:if>

<c:if test="${param.msg == 'archived'}">
    <script>
        alert('Pet is archived.');
    </script>
</c:if>

<c:if test="${param.msg == 'error'}">
    <script>
        alert('An error occurred. Please try again.');
    </script>
</c:if>


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
    <h2>Pets Management</h2><br>
    <a href="AddPets.jsp" class="bttn">Add Pets</a>
</div>

<div class="info-stack">

    <c:forEach var="pet" items="${petList}">
        <div class="info-card">

            <h3>${pet.petName}</h3>

            <p><strong>Species:</strong> ${pet.petSpecies}</p>
            <p><strong>Breed:</strong> ${pet.petBreed}</p>
            <p><strong>Age:</strong> ${pet.petAge} years old</p>
            <p><strong>Gender:</strong> ${pet.petGender}</p>
            <p><strong>Health Status:</strong> ${pet.petHealthStatus}</p>
            <p><strong>Adoption Status:</strong> ${pet.petAdoptionStatus}</p>

            <div class="card-actions">
                <a href="PetController?action=edit&petId=${pet.petId}" class="bttn">Edit</a>
                <a href="javascript:void(0);" class="bttn danger"
                    onclick="openDeleteModal(${pet.petId});">
                    Delete
                </a>

            </div>
        </div>
    </c:forEach>

</div>
<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System
</div>
    
<div id="deleteModal" class="modal-overlay" style="display:none;">
    <div class="modal-box">
        <h3>Confirm Deletion</h3>
        <p>Are you sure you want to delete this pet?</p>

        <div class="modal-actions">
            <button class="bttn cancel" onclick="closeDeleteModal()">Cancel</button>
            <a id="confirmDeleteBtn" class="bttn danger">Yes, Delete</a>
        </div>
    </div>
</div>

<script>
    function openDeleteModal(petId) {
        const modal = document.getElementById("deleteModal");
        const confirmBtn = document.getElementById("confirmDeleteBtn");

        confirmBtn.href = "PetController?action=delete&petId=" + petId;
        modal.style.display = "flex";   
    }

    function closeDeleteModal() {
        document.getElementById("deleteModal").style.display = "none";
    }
</script>

    
</body>
</html>
