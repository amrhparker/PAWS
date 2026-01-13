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
            }

            .profile-left img {
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

            .btn {
                display: inline-block;
                width: 120px;
                margin: 20px 10px 0;
                padding: 10px;
                border: none;
                border-radius: 20px;
                cursor: pointer;
                font-size: 16px;
                color: black;
                text-align: center;
                background-color: #f0f0f0;
            }
            
            .btn:hover {
                background: #999;
                color: white;
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
            <a href="AboutUs.html">About</a>
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
                        <button type="button" class="btn change-password-btn">Change Password</button>
                    </a>
                </div>
            </div>

            <div class="profile-info">
                <form method="post" action="AdopterController">
                    <input type="hidden" name="action" value="updateProfile">

                    <div class="form-row">
                        <div class="form-group">
                            <label for="fname">First Name:</label>
                            <input type="text" id="fname" name="fname" value="${adopter.adoptFName}" required>
                        </div>
                        <div class="form-group">
                            <label for="lname">Last Name:</label>
                            <input type="text" id="lname" name="lname" value="${adopter.adoptLName}" required>
                        </div>
                    </div>

                    <label for="ic">IC Number:</label>
                    <input type="text" id="ic" name="ic" value="${adopter.adoptIC}" required>

                    <label for="phone">Phone Number:</label>
                    <input type="text" id="phone" name="phone" value="${adopter.adoptPhoneNum}" required>

                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${adopter.adoptEmail}" required>

                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address" value="${adopter.adoptAddress}" required>

                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="${adopter.adoptUsername}" required>

                    <div class="btn-group">
                        <button type="submit" class="btn">Save</button>
                        <a href="AdopterController?action=profile">
                            <button type="button" class="btn">Cancel</button>
                        </a>
                    </div>
                </form>
            </div>

        </div>

        <div class="footer">
            © 2025 PAWS Pet Adoption Welfare System — All Rights Reserved
        </div>

    </body>
</html>
