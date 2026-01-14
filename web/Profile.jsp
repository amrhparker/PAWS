
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
        margin-top: 20px;
        background: #d0e6c7;
        width: 260px;
        padding: 20px;
        border: 1px solid #ccc;
        text-align: center;
        border-radius: 10px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08), 0 10px 25px rgba(0, 0, 0, 0.12);
    }

    .profile-left img {
        margin-top: 100px;
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }

    .profile-info {
        margin-top: 20px;
        background: #d0e6c7;
        width: 550px;
        padding: 25px;
        border: 1px solid #ccc;
        border-radius: 10px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08), 0 10px 25px rgba(0, 0, 0, 0.12);
    }

    .profile-info p {
        margin: 10px 0;
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
        </div>

        <div class="profile-info">

            <p><strong>Name:</strong> ${adopter.adoptFName} ${adopter.adoptLName}</p>
            <p><strong>IC Number:</strong> ${adopter.adoptIC}</p>
            <p><strong>Phone Number:</strong> ${adopter.adoptPhoneNum}</p>
            <p><strong>Email:</strong> ${adopter.adoptEmail}</p>
            <p><strong>Address:</strong> ${adopter.adoptAddress}</p>
            <p><strong>Occupation:</strong>
                <c:out value="${adopter.adoptOccupation}" default="-" />
            </p>
            <p><strong>Income:</strong>
                <c:choose>
                <c:when test="${adopter.adoptIncome > 0}">
                    RM ${adopter.adoptIncome}
                </c:when>
                <c:otherwise>-</c:otherwise>
                </c:choose>
            </p>
            <p><strong>Username:</strong> ${adopter.adoptUsername}</p>
            <p><strong>Password:</strong> ********</p>

        <div class="btn-group">
            <a href="AdopterController?action=editProfile">
                <button class="edit-btn">Edit</button>
            </a>

            <a href="AdopterController?action=changePasswordForm">
                <button class="edit-btn">Change Password</button>
            </a>
        </div>

        </div>


    </div>

    <div class="footer">
        © 2025 PAWS Pet Adoption Welfare System
    </div>
        
    <c:if test="${not empty successMessage}">
    <script>
        alert("${successMessage}");
    </script>
    </c:if>

    <c:if test="${not empty errorMessage}">
    <script>
        alert("${errorMessage}");
    </script>
    </c:if>

</body>
</html>
