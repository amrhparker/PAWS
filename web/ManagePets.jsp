<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty petList}">
    <c:redirect url="PetServlet"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Pets</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>
    </div>

    <div class="navbar-links">
        <a href="StaffDashboard.jsp">Dashboard</a>
        <a href="ManagePets.jsp">Pets</a>
        <a href="ManageRecords.jsp">Records</a>
        <a href="ManageReports.jsp">Reports</a>
        <a href="ManageApplications.jsp">Applications</a>
        <a href="ActivityLog.jsp">Logs</a>
        <a href="LogoutServlet" class="logout">LOG OUT</a>
    </div>
</div>

<div class="page-header">
    <h2>Pets Management</h2>
    <a href="AddPets.jsp" class="bttn">Add Pets</a>
</div>

<div class="info-stack">

    <c:if test="${empty petList}">
        <p style="text-align:center;">No pets available.</p>
    </c:if>

    <c:forEach var="pet" items="${petList}">
        <div class="info-card">

            <h3>${pet.petName}</h3>

            <p><strong>Species:</strong> ${pet.petSpecies}</p>
            <p><strong>Breed:</strong> ${pet.petBreed}</p>
            <p><strong>Age:</strong> ${pet.petAge} year(s)</p>
            <p><strong>Gender:</strong> ${pet.petGender}</p>
            <p><strong>Health Status:</strong> ${pet.petHealthStatus}</p>
            <p><strong>Adoption Status:</strong> ${pet.petAdoptionStatus}</p>

            <div class="card-actions">
                <a href="PetServlet?action=edit&petId=${pet.petId}" class="bttn">Edit</a>
                <a href="PetServlet?action=delete&petId=${pet.petId}"
                   class="bttn"
                   onclick="return confirm('Are you sure you want to delete this pet?');">
                    Delete
                </a>
            </div>
        </div>
    </c:forEach>

</div>

</body>
</html>
