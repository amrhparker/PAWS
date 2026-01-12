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
    <title>Add Pets</title>
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

<!-- ================= ADD PET FORM ================= -->
<div class="form-wrapper">
    <div class="form-card">
        <h2>Add Pets</h2>

        <form action="${pageContext.request.contextPath}/PetServlet" method="post">
            <!-- Hidden field to tell servlet which action -->
            <input type="hidden" name="action" value="add">

            <label>Name</label>
            <input type="text" name="petName" required>

            <label>Species</label>
            <div class="radio-group">
                <input type="radio" name="petSpecies" value="Cat" required> Cat
                <input type="radio" name="petSpecies" value="Dog"> Dog
            </div>

            <label>Breed</label>
            <input type="text" name="petBreed" required>

            <label>Age</label>
            <input type="number" name="petAge" min="0" required>

            <label>Gender</label>
            <div class="radio-group">
                <input type="radio" name="petGender" value="Male" required> Male
                <input type="radio" name="petGender" value="Female"> Female
            </div>

            <label>Colour / Description</label>
            <input type="text" name="petDesc" required>

            <label>Health Status</label>
            <input type="text" name="petHealthStatus">

            <label>Adoption Status</label>
            <input type="text" name="petAdoptionStatus" value="Available">

            <button type="submit" class="bttn">Add Pet</button>
        </form>

    </div>
</div>

</body>
</html>
