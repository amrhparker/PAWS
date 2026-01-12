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
    <title>Edit Pets</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<!-- ================= NAVBAR ================= -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>
        <div class="navbar-links">
            <a href="StaffDashboard.jsp">Dashboard</a>
            <a href="ManagePets.jsp">Pets</a>
            <a href="ManageRecords.jsp">Records</a>
            <a href="ManageReports.jsp">Reports</a>
            <a href="ManageApplications.jsp">Applications</a>
            <a href="ActivityLog.jsp">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>

<!-- ================= EDIT PET FORM ================= -->
<div class="form-wrapper">
    <div class="form-card">
        <h2>Edit Pet Details</h2>

        <form action="${pageContext.request.contextPath}/PetServlet" method="post">
            <!-- Hidden fields to indicate action and which pet -->
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="petId" value="${pet.petId}">

            <label>Name</label>
            <input type="text" name="petName" value="${pet.petName}" required>

            <label>Species</label>
            <div class="radio-group">
                <label>
                    <input type="radio" name="petSpecies" value="Cat" 
                        <c:if test="${pet.petSpecies == 'Cat'}">checked</c:if> > Cat
                </label>
                <label>
                    <input type="radio" name="petSpecies" value="Dog" 
                        <c:if test="${pet.petSpecies == 'Dog'}">checked</c:if> > Dog
                </label>
            </div>

            <label>Breed</label>
            <input type="text" name="petBreed" value="${pet.petBreed}" required>

            <label>Age</label>
            <input type="number" name="petAge" value="${pet.petAge}" min="0" required>

            <label>Gender</label>
            <div class="radio-group">
                <label>
                    <input type="radio" name="petGender" value="Male" 
                        <c:if test="${pet.petGender == 'Male'}">checked</c:if> > Male
                </label>
                <label>
                    <input type="radio" name="petGender" value="Female" 
                        <c:if test="${pet.petGender == 'Female'}">checked</c:if> > Female
                </label>
            </div>

            <label>Colour / Description</label>
            <input type="text" name="petDesc" value="${pet.petDesc}" required>

            <label>Health Status</label>
            <input type="text" name="petHealthStatus" value="${pet.petHealthStatus}">

            <label>Adoption Status</label>
            <input type="text" name="petAdoptionStatus" value="${pet.petAdoptionStatus}">

            <button type="submit" class="bttn">Update Pet</button>
        </form>

    </div>
</div>

</body>
</html>
