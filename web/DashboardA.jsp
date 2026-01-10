<%-- 
    Document   : DashboardA
    Created on : Jan 4, 2026, 4:09:21 PM
    Author     : amira
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Adopter Dashboard</title>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: #f8f8f8;
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
        }
        
        .dashboard-container {
            display: flex;
            padding: 40px 50px;
            gap: 40px;
            align-items: flex-start;
        }
        
        .left-section {
            flex: 1;
        }
        
        .section-title {
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 15px;
            margin-top: 20px;
        }
        
        .card-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }
        
        .data-card {
            background: #ffffff;
            border-radius: 20px;
            padding: 20px;
            box-shadow: 0px 3px 6px rgba(0,0,0,0.1);
            transition: 0.25s ease;
            text-decoration: none; /*remove line under links*/
            color: inherit;
            display: block;
        }
        
        .data-card:hover {
            transform: translateY(-5px);
            box-shadow: 0px 6px 12px rgba(0,0,0,0.15);
            text-decoration: none;
        }
        
        .data-card p {
            margin: 6px 0;
            font-size: 14px;
        }
        
        .data-label {
            font-weight: bold;
            color: #333;
        }
        
        .divider {
            width: 2px;
            background: #666;
            height: 520px;
        }
        
        .right-section {
            width: 320px;
            text-align: center;
        }
        
        .right-section img {
            width: 100%;
            border-radius: 20px;
        }
        
        .rehome-btn {
            margin-top: 15px;
            width: 100%;
            padding: 15px;
            border-radius: 20px;
            font-size: 18px;
            border: none;
            cursor: pointer;
            background: #f5a0c8;
            transition: 0.25s ease;
        }
        
        .rehome-btn:hover {
            transform: translateY(-5px);
            background: #e890b8;
        }
        
        @media (max-width: 900px) {
            .dashboard-container {
                padding: 20px;
                gap: 20px;
                flex-direction: column;
            }
            
            .divider {
                display: none;
                height: auto;
                width: 100%;
            }
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
        <a href="Logout.jsp" class="logout">LOG OUT</a>
    </div>

    </div>

    <div class="dashboard-container">
        <div class="left-section">
            <div class="section-title">Submitted Applications</div>
            
            <div class="card-grid">
                <c:choose>
                    <c:when test="${not empty applications}">
                        <c:forEach var="app" items="${applications}">
                            <a href="ApplicationController?action=view&appId=${app.appId}" class="data-card">
                                <p><span class="data-label">Applicant Name:</span> ${app.applicantName}</p>
                                <p><span class="data-label">Pet Name:</span> ${app.petName}</p>
                                <p><span class="data-label">Status:</span> 
                                    <span style="color: 
                                        ${app.status == 'Completed' ? 'green' : 
                                          app.status == 'Pending Review' ? 'orange' : 
                                          app.status == 'Under Screening' ? 'blue' : 'red'}">
                                        ${app.status}
                                    </span>
                                </p>
                                <p><span class="data-label">Date Submitted:</span> 
                                    <fmt:formatDate value="${app.dateSubmitted}" pattern="dd MMM yyyy"/>
                                </p>
                            </a>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="data-card">
                            <p>No submitted applications found.</p>
                            <p><a href="adopter?action=apply">Apply for adoption now!</a></p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="section-title">Adoption Records</div>
            
            <div class="card-grid">
                <c:choose>
                    <c:when test="${not empty records}">
                        <c:forEach var="app" items="${applications}">
                           <a href="ApplicationController?action=view&appId=${app.appId}" class="data-card">

                           <p><span class="data-label">Applicant Name:</span> 
                               ${app.adopter.adoptFName} ${app.adopter.adoptLName}
                           </p>
                                

                           <p><span class="data-label">Pet Name:</span> ${app.pet.petName}</p>

                            <p><span class="data-label">Status:</span>
                                ${app.appStatus}
                            </p>

                            <p><span class="data-label">Eligibility:</span>
                                ${app.appEligibility}
                            </p>
                            </a>
                        </c:forEach>

                    </c:when>
                    <c:otherwise>
                        <div class="data-card">
                            <p>No adoption records found.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="divider"></div>

        <div class="right-section">
            <img src="rehome.png" alt="pet">
            <a href="Rehome.jsp">
                <button class="rehome-btn">Rehome Our Pet ➜</button>
            </a>
        </div>
    </div>

    <div class="footer">
        © 2025 PAWS Pet Adoption Welfare System — All Rights Reserved
    </div>
</body>
</html>