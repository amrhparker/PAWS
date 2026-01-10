<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Get petId from URL parameter if available
    String petIdParam = request.getParameter("petId");
    int petId = 0;
    if (petIdParam != null && !petIdParam.isEmpty()) {
        try {
            petId = Integer.parseInt(petIdParam);
        } catch (NumberFormatException e) {
            petId = 0;
        }
    }

    // For demo purposes - you should get this from session
    int adoptId = 1; // This should come from logged-in user's session
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Application Form</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }

            .main-content {
                flex: 1;
            }

            .navbar {
                padding: 15px 30px;
                border-bottom: 1px solid #ccc;
            }

            .nav-top {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }

            .nav-left {
                display: flex;
                align-items: center;
                gap: 20px;
            }

            .nav-right {
                display: flex;
                align-items: center;
            }

            .profile-container {
                display: flex;
                flex-direction: column;
                align-items: center;
                position: relative;
                top: 30px;
            }

            .profile-icon {
                height: 32px;
                display: block;
                margin-bottom: 3px;
            }

            .logout-text {
                font-weight: bold;
                text-decoration: none;
                margin: 0;
            }

            .menu-links {
                margin-top: 10px;
            }

            .menu-links a,
            .nav-right a {
                text-decoration: none;
                font-weight: bold;
                margin-right: 20px;
                color: black;
            }

            .page-title {
                text-align: center;
                font-size: 28px;
                font-weight: bold;
                margin-top: 20px;
            }

            .submit-btn-container {
                text-align: right;
                padding: 0 60px 20px;
                margin-top: -40px;
            }

            .submit-btn {
                padding: 10px 30px;
                font-size: 18px;
                border-radius: 20px;
                border: none;
                cursor: pointer;
                font-weight: bold;
                background: #4CAF50;
                color: white;
                transition: all 0.3s;
            }

            .submit-btn:hover {
                background: #45a049;
                transform: translateY(-3px);
            }

            .form-wrapper {
                display: flex;
                justify-content: center;
                gap: 40px;
                padding: 30px 40px;
                flex-wrap: wrap;
            }

            .form-section {
                background: #d0e6c7;
                width: 40%;
                min-width: 300px;
                padding: 25px;
                border-radius: 20px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }

            .form-section h2 {
                margin-top: 0;
                font-size: 22px;
                color: #333;
            }

            label {
                display: block;
                margin: 12px 0 5px;
                font-weight: bold;
                color: #333;
            }

            input[type="text"],
            input[type="number"],
            input[type="tel"],
            textarea,
            select {
                width: 90%;
                padding: 10px;
                border-radius: 10px;
                border: 1px solid #ccc;
                margin-bottom: 10px;
                font-size: 15px;
                font-family: 'Poppins', sans-serif;
            }

            textarea {
                height: 80px;
                resize: none;
            }

            .radio-row {
                display: flex;
                align-items: center;
                gap: 15px;
                margin: 5px 0 10px;
            }

            .radio-row input[type="radio"] {
                width: auto;
            }

            .checkboxes {
                margin-top: 10px;
            }

            .checkboxes label {
                font-weight: normal;
                display: block;
                margin-bottom: 5px;
            }

            .checkboxes input[type="checkbox"] {
                width: auto;
                margin-right: 8px;
            }

            .required {
                color: red;
            }

            .pet-info {
                background: #f2dfd1;
                padding: 15px;
                border-radius: 15px;
                margin: 20px auto;
                width: 80%;
                max-width: 500px;
                text-align: center;
            }
            .final-submit-container {
                text-align: center;
                margin-top: 30px;
                padding-top: 20px;
                border-top: 2px dashed #ccc;
            }
        </style>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>

        <div class="navbar">
            <div class="navbar-left">
                <a href="Home.jsp">
                    <img src="PAWS.png" alt="PAWS Logo">
                </a>
                <div class="navbar-links">
                    <a href="Home.jsp">Home</a>
                    <a href="AboutUs.jsp">About</a>
                    <a href="DashboardA.jsp">Dashboard</a>
                    <a href="Rehome.jsp">Rehome Pet</a>
                </div>
            </div>
            <div class="navbar-profile">
                <a href="Profile.jsp">
                    <img src="ProfileIcon.png" alt="Profile" class="profile-icon">
                </a>
                <a href="Logout.jsp" class="logout">LOG OUT</a>
            </div>
        </div>

        <div class="main-content">
            <div class="page-title">Application Form</div>

            <% if (petId > 0) {%>
            <div class="pet-info">
                <strong>Applying for Pet ID:</strong> <%= petId%><br>
                <em>Please fill out all required fields below</em>
            </div>
            <% }%>

            <form action="ApplicationController" method="POST">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="petId" value="<%= petId%>">
                <input type="hidden" name="adoptId" value="<%= adoptId%>">                

                <div class="form-wrapper">
                    <div class="form-section">
                        <h2>Personal Details <span class="required">*</span></h2>

                        <label>Full Name: <span class="required">*</span></label>
                        <input type="text" name="fullName" required>

                        <label>Phone Number: <span class="required">*</span></label>
                        <input type="tel" name="phoneNumber" required>

                        <label>Gender: <span class="required">*</span></label>
                        <select name="gender" required>
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>

                        <label>IC / Passport: <span class="required">*</span></label>
                        <input type="text" name="icPassport" required>

                        <label>Address: <span class="required">*</span></label>
                        <textarea name="address" required></textarea>

                        <label>Occupation: <span class="required">*</span></label>
                        <input type="text" name="occupation" required>

                        <label>Income (RM): <span class="required">*</span></label>
                        <input type="number" name="income" placeholder="RM" required>
                    </div>

                    <div class="form-section">
                        <h2>Eligibility Review <span class="required">*</span></h2>

                        <label>Have you owned a pet before? <span class="required">*</span></label>
                        <div class="radio-row">
                            <input type="radio" name="hasOwnedPet" value="Yes" required> Yes
                            <input type="radio" name="hasOwnedPet" value="No"> No
                        </div>

                        <label>Who will be responsible for feeding, grooming, vet visits? <span class="required">*</span></label>
                        <input type="text" name="caretakerInfo" required>

                        <label>Will the pet be kept indoors/outdoors? <span class="required">*</span></label>
                        <select name="petEnvironment" required>
                            <option value="">Select Location</option>
                            <option value="Indoor">Indoor</option>
                            <option value="Outdoor">Outdoor</option>
                            <option value="Both">Both</option>
                        </select>

                        <label>Are you prepared for medical expenses? <span class="required">*</span></label>
                        <div class="radio-row">
                            <input type="radio" name="medicalReady" value="Yes" required> Yes
                            <input type="radio" name="medicalReady" value="No"> No
                        </div>

                        <label>Why do you want to adopt this pet? <span class="required">*</span></label>
                        <input type="text" name="adoptionReason" required>

                        <div class="checkboxes">
                            <label><input type="checkbox" name="confirmInfo" required> I confirm that all information provided is true and accurate. <span class="required">*</span></label>
                            <label><input type="checkbox" name="acceptResponsibility" required> I understand and accept full responsibility for the pet. <span class="required">*</span></label>
                            <label><input type="checkbox" name="financialAbility" required> I am financially able to provide care and medical attention. <span class="required">*</span></label>
                        </div>

                        <!-- Submit button moved here -->
                        <div class="final-submit-container">
                            <button type="submit" class="submit-btn">Submit Application</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <div class="footer">
            © 2025 PAWS Pet Adoption Welfare System — All Rights Reserved
        </div>

    </body>
</html>