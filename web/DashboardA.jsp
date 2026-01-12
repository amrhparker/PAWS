<%-- 
    Document   : DashboardA
    Created on : Jan 4, 2026
    Author     : amira
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    if (session.getAttribute("adoptId") == null) {
        response.sendRedirect("AdopterLogin.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Adopter Dashboard</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            margin: 0;
            background: #f8f8f8;
        }

        .dashboard-container {
            display: flex;
            padding: 40px 50px;
            gap: 40px;
        }

        .left-section {
            flex: 1;
        }

        .section-title {
            font-size: 22px;
            font-weight: 600;
            margin: 25px 0 15px;
        }

        .card-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
            gap: 20px;
        }

        .data-card {
            background: white;
            border-radius: 18px;
            padding: 18px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            text-decoration: none;
            color: inherit;
            transition: .25s;
        }

        .data-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 16px rgba(0,0,0,.15);
        }

        .data-card p {
            margin: 6px 0;
            font-size: 14px;
        }

        .data-label {
            font-weight: 600;
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
            border-radius: 18px;
        }

        .rehome-btn {
            margin-top: 15px;
            width: 100%;
            padding: 14px;
            border-radius: 20px;
            font-size: 16px;
            background: #f5a0c8;
            border: none;
            cursor: pointer;
        }

        .status-approved { color: green; font-weight: bold; }
        .status-pending { color: orange; font-weight: bold; }
        .status-rejected { color: red; font-weight: bold; }

    </style>
</head>

<body>
<%
    String loginSuccess = (String) session.getAttribute("loginSuccess");
    if ("adopter".equals(loginSuccess)) {
%>
    <script>
        alert("Adopter logged in successfully!");
    </script>
<%
        session.removeAttribute("loginSuccess"); 
    }
%>

<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsA.png" alt="PAWS">
        </a>

        <div class="navbar-links">
            <a href="Home.html">Home</a>
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

<!-- ===== CONTENT ===== -->
<div class="dashboard-container">

    <div class="left-section">

        <!-- ================= SUBMITTED APPLICATIONS ================= -->
        <div class="section-title">Submitted Applications</div>

        <div class="card-grid">
            <c:choose>
                <c:when test="${not empty applications}">
                    <c:forEach var="app" items="${applications}">
                        <a href="ApplicationController?action=viewAdopter&appId=${app.appId}"
                           class="data-card">

                            <strong>( ${app.appId} )</strong>

                            <p><span class="data-label">Applicant:</span>
                                ${app.adopter.adoptFName} ${app.adopter.adoptLName}
                            </p>

                            <p><span class="data-label">Pet:</span> ${app.pet.petName}</p>

                            <p>
                            <span class="data-label">Status:</span>
                            <c:choose>
                                <c:when test="${app.appStatus == 'Approved'}">
                                    <span class="status-approved">${app.appStatus}</span>
                                </c:when>
                                <c:when test="${app.appStatus == 'Pending'}">
                                    <span class="status-pending">${app.appStatus}</span>
                                </c:when>
                                <c:when test="${app.appStatus == 'Rejected'}">
                                    <span class="status-rejected">${app.appStatus}</span>
                                </c:when>
                                <c:otherwise>
                                    <span>${app.appStatus}</span>
                                </c:otherwise>
                            </c:choose>
                            </p>

                            <p><span class="data-label">Date:</span>
                                <fmt:formatDate value="${app.appDate}" pattern="dd MMM yyyy"/>
                            </p>
                        </a>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <div class="data-card">
                        <p>No submitted applications found.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- ================= ADOPTION RECORDS ================= -->
        <div class="section-title">Adoption Records</div>

        <div class="card-grid">
            <c:choose>
                <c:when test="${not empty records}">
                    <c:forEach var="r" items="${records}">
                        <a href="RecordController?action=viewAdopter&recordId=${r.recordId}"
                           class="data-card">

                            <p><span class="data-label">Adopter:</span>
                                ${r.adopterName}
                            </p>

                            <p><span class="data-label">Pet:</span>
                                ${r.petName}
                            </p>

                            <p><span class="data-label">Status:</span>
                                <span class="${r.recordStatus == 'Completed' ? 'status-completed' : 'status-pending'}">
                                    ${r.recordStatus}
                                </span>
                            </p>

                            <p><span class="data-label">Adoption Date:</span>
                                <fmt:formatDate value="${r.recordDate}" pattern="dd MMM yyyy"/>
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
        <img src="rehome.png" alt="Rehome">
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
