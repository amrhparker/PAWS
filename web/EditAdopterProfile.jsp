<%-- 
    Document   : EditAdopterProfile
    Created on : Jan 5, 2026, 6:50:49 PM
    Author     : amira
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Adopter Profile</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
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
                margin-right: 10px;
            }

            .container {
                display: flex;
                justify-content: center;
                gap: 40px;
                padding: 40px 20px;
            }

            .profile-left {
                background: #d0e6c7;
                width: 260px;
                padding: 20px;
                border: 1px solid #ccc;
                text-align: center;
                border-radius: 10px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08), 0 10px 25px rgba(0, 0, 0, 0.12);
            }

            .profile-left img {
                margin-top: 220px;
                width: 120px;
                height: 120px;
                border-radius: 50%;
            }

            .profile-info {
                background: #d0e6c7;
                width: 550px;
                padding: 25px;
                border: 1px solid #ccc;
                border-radius: 10px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08), 0 10px 25px rgba(0, 0, 0, 0.12);
            }

            .profile-info label {
                display: block;
                margin: 10px 0 5px;
                font-weight: bold;
            }

            .profile-info input {
                width: 100%;
                padding: 8px;
                margin-bottom: 15px;
                border-radius: 5px;
                border: 1px solid #ccc;
            }

            .edit-btn {
                margin-top: 20px;
                margin-right: 10px;
                padding: 10px 25px;
                border-radius: 20px;
                border: none;
                cursor: pointer;
                background: #4CAF50;
                color: white;
                font-size: 15px;
                justify-content: flex-end;
            }

            .edit-btn:hover {
                background: #45a049;
                transform: translateY(-3px);
            }
            a{
                text-decoration: none;
            }
            .btn-group {
                display: flex;
                justify-content: center;
                gap: 20px;
            }
            .change-password-container {
                text-align: center;
            }
            .change-password-btn {
                margin-top: 15px;
                width:200px;
            }

            @media (max-width: 700px) {
                .container {
                    flex-direction: column;
                    align-items: center;
                }

                .profile-info,
                .profile-left {
                    width: 90%;
                }

                .menu-links a {
                    display: inline-block;
                    margin: 6px 10px 6px 0;
                }
            }
        </style>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body>
        <c:if test="${empty sessionScope.adopterId}">
            <% response.sendRedirect("AdopterLogin.jsp"); %>
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

        <div class="container">

            <div class="profile-left">
                <img src="ProfileIcon.png" alt="Profile Icon">
                <h3>${adopter.adoptFName} ${adopter.adoptLName}</h3>
                <!-- Change Password button centered below -->
                <div class="change-password-container">
                    <a href="AdopterController?action=changePassword">
                        <button type="button" class="edit-btn">Change Password</button>
                    </a>
                </div>
            </div>

            <div class="profile-info">
                <form method="post" action="AdopterController">
                    <input type="hidden" name="action" value="updateProfile">

                    <div class="form-row">
                    <div class="form-group">
                        <label>First Name</label>
                        <input type="text" name="fname" value="${adopter.adoptFName}" required>
                    </div>
                    <div class="form-group">
                        <label>Last Name</label>
                        <input type="text" name="lname" value="${adopter.adoptLName}" required>
                    </div>
                    </div>
                    
                        <label>IC Number</label>
                        <input type="text" name="ic" value="${adopter.adoptIC}" required>
                        
                        <label>Phone Number</label>
                        <input type="text" name="phone" value="${adopter.adoptPhoneNum}" required>

                        <label>Email</label>
                        <input type="email" name="email" value="${adopter.adoptEmail}" required>

                        <label>Address</label>
                        <input type="text" name="address" value="${adopter.adoptAddress}" required>

                        <label>Occupation</label>
                        <input type="text" name="occupation" value="${adopter.adoptOccupation}">

                        <label>Income</label>
                        <input type="number" step="0.01" name="income" value="${adopter.adoptIncome}">

                        <label>Username</label>
                        <input type="text" name="username" value="${adopter.adoptUsername}" required>

                    <div class="btn-group">
                        <button type="submit" class="edit-btn">Save</button>
                        <a href="AdopterController?action=profile">
                            <button type="button" class="edit-btn">Cancel</button>
                        </a>
                    </div>

                </form>
            </div>

        </div>

        <div class="footer">
            © 2025 PAWS Pet Adoption Welfare System
        </div>

    </body>
</html>
