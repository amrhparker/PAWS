<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.AdopterBean" %>

<%
    if (session.getAttribute("adopter") == null) {
        response.sendRedirect("AdopterLogin.jsp");
        return;
    }
%>

<%
    if (request.getAttribute("adopter") == null) {
        response.sendRedirect("ApplicationController");
        return;
    }

    AdopterBean adopter = (AdopterBean) request.getAttribute("adopter");

    // petId MUST come from attribute (forward), not parameter
    Integer petIdObj = (Integer) request.getAttribute("petId");
    int petId = petIdObj != null ? petIdObj : 0;

    int adoptId = adopter.getAdoptId();

    String fName = adopter.getAdoptFName() != null ? adopter.getAdoptFName() : "";
    String lName = adopter.getAdoptLName() != null ? adopter.getAdoptLName() : "";
    String phone = adopter.getAdoptPhoneNum() != null ? adopter.getAdoptPhoneNum() : "";
    String ic = adopter.getAdoptIC() != null ? adopter.getAdoptIC() : "";
    String address = adopter.getAdoptAddress() != null ? adopter.getAdoptAddress() : "";

    // Editable adopter fields (EMPTY first time, filled later)
    String occupation = adopter.getAdoptOccupation() != null ? adopter.getAdoptOccupation() : "";
    String income = adopter.getAdoptIncome() > 0 ? String.valueOf(adopter.getAdoptIncome()) : "";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Application Form</title>
        <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; display: flex; flex-direction: column; min-height: 100vh; }
        .main-content { flex: 1; }
        .navbar { padding: 15px 30px; border-bottom: 1px solid #ccc; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { text-decoration: none; font-weight: bold; margin-right: 20px; color: black; }
        .page-title { text-align: center; font-size: 28px; font-weight: bold; margin-top: 20px; }
        .form-wrapper { display: flex; justify-content: center; gap: 40px; padding: 30px 40px; flex-wrap: wrap; }
        .form-section { background: #d0e6c7; width: 40%; min-width: 300px; padding: 25px; border-radius: 20px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        .form-section h2 { margin-top: 0; font-size: 22px; color: #333; }
        label { display: block; margin: 12px 0 5px; font-weight: bold; color: #333; }
        input[type="text"], input[type="number"], input[type="tel"], textarea, select { width: 90%; padding: 10px; border-radius: 10px; border: 1px solid #ccc; margin-bottom: 10px; font-size: 15px; font-family: 'Poppins', sans-serif; }
        textarea { height: 80px; resize: none; }
        .radio-row { display: flex; align-items: center; gap: 15px; margin: 5px 0 10px; }
        .radio-row input[type="radio"] { width: auto; }
        .checkboxes { margin-top: 10px; }
        .checkboxes label { font-weight: normal; display: block; margin-bottom: 5px; }
        .checkboxes input[type="checkbox"] { width: auto; margin-right: 8px; }
        .required { color: red; }
        .final-submit-container { text-align: center; margin-top: 30px; padding-top: 20px; border-top: 2px dashed #ccc; }
        .submit-btn { padding: 10px 30px; font-size: 18px; border-radius: 20px; border: none; cursor: pointer; font-weight: bold; background: #4CAF50; color: white; transition: all 0.3s; }
        .submit-btn:hover { background: #45a049; transform: translateY(-3px); }
        .pet-info { background: #f2dfd1; padding: 15px; border-radius: 15px; margin: 20px auto; width: 80%; max-width: 500px; text-align: center; }
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

<div class="main-content">
    <h1 class="page-title">Application Form</h1>

    <% if (petId > 0) { %>
    <div class="pet-info">
        <strong>Applying for Pet:</strong> <%= petId %>
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
                <input type="text" name="adoptFName" value="${adopter.adoptFName}" readonly>

                <label>Last Name *</label>
                <input type="text" name="adoptLName" value="${adopter.adoptLName}" readonly />

                <label>Phone Number *</label>
                <input type="tel" name="adoptPhoneNum" value="<%= phone %>" required>

                <label>IC / Passport *</label>
                <input type="text" name="adoptIC" value="<%= ic %>" required>

                <label>Address *</label>
                <textarea name="adoptAddress" required><%= address %></textarea>

                <label>Occupation *</label>
                <input type="text" name="adoptOccupation" value="${adopter.adoptOccupation != null ? adopter.adoptOccupation : ''}" />

                <label>Income (RM) *</label>
                <input type="number" step="0.01" name="adoptIncome" value="${adopter.adoptIncome != null ? adopter.adoptIncome : ''}" />
            </div>

            <!-- ELIGIBILITY -->
            <div class="form-section">
                <h2>Eligibility Review</h2>

                <label>Have you owned a pet before?</label>
                <input type="radio" name="hasOwnedPet" value="Yes" required> Yes
                <input type="radio" name="hasOwnedPet" value="No"> No

                <label>Who will be responsible for feeding, grooming, vet visits?</label>
                <input type="text" name="caretakerInfo" required>

                <label>Will the pet be kept indoors/outdoors?</label>
                <select name="petEnvironment" required>
                    <option value="">Select</option>
                    <option value="Indoor">Indoor</option>
                    <option value="Outdoor">Outdoor</option>
                    <option value="Both">Both</option>
                </select>

                <label>Are you prepared for medical expenses?</label>
                <input type="radio" name="medicalReady" value="Yes" required> Yes
                <input type="radio" name="medicalReady" value="No"> No

                <label>Why do you want to adopt this pet?</label>
                <input type="text" name="adoptionReason" required>

        <div class="checkboxes">
            <label><input type="checkbox"> I confirm that all information provided is true and accurate.</label>
            <label><input type="checkbox"> I understand and accept full responsibility for the pet.</label>
            <label><input type="checkbox"> I am financially able to provide care and medical attention.</label>
        </div>

                <div class="final-submit-container"> <button type="submit" class="submit-btn">Submit Application</button> </div>
            </div>
        </div>
    </form>
</div>

<div class="footer">
    © 2026 PAWS Pet Adoption Welfare System
</div>

</body>
</html>
