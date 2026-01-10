<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.AdopterBean" %>

<%
    if (request.getAttribute("adopter") == null) {
        response.sendRedirect("ApplicationController");
        return;
    }

    AdopterBean adopter = (AdopterBean) request.getAttribute("adopter");

    int petId = 0;
    String petIdParam = request.getParameter("petId");
    if (petIdParam != null && !petIdParam.isEmpty()) {
        try { petId = Integer.parseInt(petIdParam); }
        catch (NumberFormatException e) { petId = 0; }
    }

    int adoptId = adopter.getAdoptId();
    String fName = adopter.getAdoptFName() != null ? adopter.getAdoptFName() : "";
    String lName = adopter.getAdoptLName() != null ? adopter.getAdoptLName() : "";
    String phone = adopter.getAdoptPhoneNum() != null ? adopter.getAdoptPhoneNum() : "";
    String ic = adopter.getAdoptIC() != null ? adopter.getAdoptIC() : "";
    String address = adopter.getAdoptAddress() != null ? adopter.getAdoptAddress() : "";
    String occupation = adopter.getAdoptOccupation() != null ? adopter.getAdoptOccupation() : "";
    String income = adopter.getAdoptIncome() > 0 ? String.valueOf(adopter.getAdoptIncome()) : "";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Application Form</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html"><img src="PAWS.png" alt="PAWS Logo"></a>
        <a href="Home.html">Home</a>
        <a href="AboutUs.jsp">About</a>
        <a href="DashboardA.jsp">Dashboard</a>
        <a href="Rehome.jsp">Rehome Pet</a>
    </div>

    <div class="navbar-right">
        <a href="Profile.jsp">
            <img src="ProfileIcon.png" alt="Profile" class="profile-icon">
        </a>
        <a href="LogoutServlet" class="logout">LOG OUT</a>
    </div>
</div>

<div class="main-content">
    <h1 class="page-title">Application Form</h1>

    <% if (petId > 0) { %>
    <div class="pet-info">
        <strong>Applying for Pet ID:</strong> <%= petId %>
    </div>
    <% } %>

    <form action="ApplicationController" method="POST">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="petId" value="<%= petId %>">
        <input type="hidden" name="adoptId" value="<%= adoptId %>">

        <div class="form-wrapper">

            <!-- PERSONAL DETAILS -->
            <div class="form-section">
                <h2>Personal Details *</h2>

                <label>First Name *</label>
                <input type="text" name="adoptFName" value="<%= fName %>" required>

                <label>Last Name *</label>
                <input type="text" name="adoptLName" value="<%= lName %>" required>

                <label>Phone Number *</label>
                <input type="tel" name="adoptPhoneNum" value="<%= phone %>" required>

                <label>IC / Passport *</label>
                <input type="text" name="adoptIC" value="<%= ic %>" required>

                <label>Address *</label>
                <textarea name="adoptAddress" required><%= address %></textarea>

                <label>Occupation *</label>
                <input type="text" name="adoptOccupation" value="<%= occupation %>" required>

                <label>Income (RM) *</label>
                <input type="number" step="0.01" name="adoptIncome" value="<%= income %>" required>
            </div>

            <!-- ELIGIBILITY -->
            <div class="form-section">
                <h2>Eligibility Review *</h2>

                <label>Owned a pet before? *</label>
                <input type="radio" name="hasOwnedPet" value="Yes" required> Yes
                <input type="radio" name="hasOwnedPet" value="No"> No

                <label>Pet caretaker *</label>
                <input type="text" name="caretakerInfo" required>

                <label>Pet environment *</label>
                <select name="petEnvironment" required>
                    <option value="">Select</option>
                    <option value="Indoor">Indoor</option>
                    <option value="Outdoor">Outdoor</option>
                    <option value="Both">Both</option>
                </select>

                <label>Medical expenses ready? *</label>
                <input type="radio" name="medicalReady" value="Yes" required> Yes
                <input type="radio" name="medicalReady" value="No"> No

                <label>Reason for adoption *</label>
                <input type="text" name="adoptionReason" required>

                <label>
                    <input type="checkbox" name="confirmInfo" required>
                    Information provided is true *
                </label>

                <button type="submit" class="submit-btn">Submit Application</button>
            </div>
        </div>
    </form>
</div>

<div class="footer">
    © 2026 PAWS Pet Adoption Welfare System
</div>

</body>
</html>
