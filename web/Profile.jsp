<%-- 
    Document   : Profile
    Created on : Jan 5, 2026, 6:36:54 PM
    Author     : amira
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <title>Adopter Profile</title>
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
        width: 550px;
        padding: 25px;
        border: 1px solid #ccc;
        border-radius: 10px;
    }

    .profile-info p {
        margin: 10px 0;
    }

    .edit-btn {
        display: block;
        width: 120px;
        margin: 30px auto 0;
        padding: 10px;
        border: none;
        border-radius: 20px;
        cursor: pointer;
        font-size: 16px;
    }
    .btn{
        text-decoration: none;
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

    <c:if test="${not empty successMessage}">
        <div class="message success-message">
            ${successMessage}
        </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="message error-message">
            ${errorMessage}
        </div>
    </c:if>
    
    <div class="container">

        <div class="profile-left">
            <img src="ProfileIcon.png" alt="Profile Icon">
            <h3>${adopter.adoptFName} ${adopter.adoptLName}</h3>
        </div>

        <div class="profile-info">
            <p><strong>Email:</strong> ${adopter.adoptEmail}</p>
            <p><strong>Username:</strong> ${adopter.adoptUsername}</p>
            <p><strong>Phone Number:</strong> ${adopter.adoptPhoneNum}</p>
            <p><strong>Address:</strong> ${adopter.adoptAddress}</p>
            <p><strong>IC Number:</strong> ${adopter.adoptIC}</p>
            <p><strong>Password:</strong> ********</p>

            <a href="AdopterController?action=editProfile" class="btn"><button class="edit-btn">Edit</button></a>
            <a href="AdopterController?action=changePassword" class="btn"><button class="edit-btn">Change Password</button></a>
        </div>

    </div>

    <div class="footer">
        © 2025 PAWS Pet Adoption Welfare System — All Rights Reserved
    </div>
    
</body>
</html>
