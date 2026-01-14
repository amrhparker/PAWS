<%@ page import="dao.PetDao, model.PetBean, model.AdopterBean, model.StaffBean, java.util.List, java.util.Random" %>
<%
    AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");
    StaffBean staff = (StaffBean) session.getAttribute("staff");
%>
<!DOCTYPE html>
<html>
<head>
    <title>About Us</title>

    <style>
        body{
            background-color:#FAF9F6;
            font-family:banschrift, sans-serif;
            margin:0;
            padding:0;
        }

        #content{
            width:90%;
            margin:auto;
            padding-top:30px;
        }

        h1{
            font-size:12px;
            margin-bottom:10px;
        }

        h2{
            text-align:center;
            font-size:28px;
            margin-top:40px;
        }
        
        .about-logo {
            height: 100px;
            margin-bottom: 20px;
            transition: transform 0.3s ease, filter 0.3s ease;
            cursor: pointer;
        }

        .about-logo:hover {
            transform: scale(1.08) translateY(-4px);
            filter: drop-shadow(0 8px 15px rgba(0,0,0,0.25));
        }


        .desc{
            text-align:center;
            font-size:20px;
            width:80%;
            margin:auto;
            line-height:1.6;
            margin-bottom:40px;
        }
    </style>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<% if (adopter != null) { %>

    <div class="navbar">
        <div class="navbar-left">
            <a href="Home.jsp">
                <img src="pawsA.png" alt="PAWS">
            </a>

            <div class="navbar-links">
                <a href="Home.jsp">Home</a>
                <a href="AboutUs.jsp" class="active">About</a>
                <a href="ApplicationController?action=dashboardA">Dashboard</a>
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

<% } else if (staff != null) { %>

    <div class="navbar">
        <div class="navbar-left">
            <a href="Home.jsp">
                <img src="pawsS.png" alt="PAWS Staff">
            </a>

            <div class="navbar-links">
                <a href="Home.jsp">Home</a>
                <a href="AboutUs.jsp" class="active">About</a>
                <a href="StaffDashboard.jsp">Dashboard</a>
                <a href="ManagePets.jsp">Pets</a>
                <a href="ManageApplications.jsp">Applications</a>
                <a href="ManageRecords.jsp">Records</a>
                <a href="ReportController">Reports</a>
            </div>
        </div>

        <div class="navbar-right">
            <a href="LogoutServlet" class="logout">Log Out</a>
        </div>
    </div>

<% } else { %>

    <div class="navbar">
        <div class="navbar-left">
            <a href="Home.jsp">
                <img src="PAWS.png" alt="PAWS Logo">
            </a>
        </div>

        <div class="navbar-right">
            <a href="Home.jsp">Home</a>
            <a href="AboutUs.jsp" class="active">About Us</a>
            <a href="Rehome.jsp">Rehome</a>
            <a href="AdopterSignin.jsp">Adopter</a>
            <a href="LogInStaff.jsp">Staff</a>
        </div>
    </div>

<% } %>

<section class="about-hero">
    <img src="PAWS.png" alt="PAWS" class="about-logo">
    <p>
        A platform dedicated to connect loving individuals
        with pets who deserve a second chance.
    </p>
</section>

<section class="about-section">
    <h2 class="section-title3">What is PAWS?</h2>

    <div class="about-cards">
        <div class="about-card">
            <h3>Adoption System</h3>
            <p>
                PAWS is a digital pet adoption management system that simplifies
                the adoption process from application to approval.
            </p>
        </div>

        <div class="about-card">
            <h3>Smart Management</h3>
            <p>
                The system helps staff manage pets, applications, and reports
                efficiently in one centralized platform.
            </p>
        </div>

        <div class="about-card">
            <h3>Trusted Homes</h3>
            <p>
                Our goal is to ensure every pet finds a responsible and loving
                home through a transparent process.
            </p>
        </div>
    </div>
</section>

<section class="about-section">
    <h2 class="section-title">Our Mission</h2>

    <div class="mission-box">
        <p>
            We aim to promote responsible pet adoption by providing a
            secure and transparent platform for adopters, staff, and shelters.
            Through proper documentation, application reviews, and follow-up
            records, PAWS ensures every adopted pet receives lifelong care,
            safety, and love.
        </p>
    </div>
</section>

<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System
</div>

</body>
</html>
