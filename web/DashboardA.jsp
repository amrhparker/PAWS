<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    if (session.getAttribute("adopterId") == null) {
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

        h3 {
            color: #237176;
            text-shadow: 1px 1px 2px rgba(255,255,255,0.6);
        }

        .dashboard-container {
            display: flex;
            padding: 20px 80px;
            gap: 40px;
        }

        .left-section {
            flex: 1;
        }

        .section-title {
            font-size: 22px;
            font-weight: 600;
            margin: 5px 0 15px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.25);
        }

        .card-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 30px;
        }

        .data-card {
            margin-bottom: 25px;
            background: #ede0ca;
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

        .status-approved { color: #37b139; font-weight: bold; }
        .status-pending { color: #d252ff; font-weight: bold; }
        .status-rejected { color: red; font-weight: bold; }
        .status-completed { color: #5170ff; font-weight: bold; }

        .popup-overlay {
            position: fixed;
            inset: 0;
            background: rgba(0,0,0,0.4);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }

        .popup-box {
            background: #644d4d;
            color: white;
            padding: 22px;
            border-radius: 16px;
            width: 320px;
            text-align: center;
            box-shadow: 0 10px 25px rgba(0,0,0,0.35);
        }

        .popup-box h3 {
            margin-bottom: 10px;
        }

        .popup-box button {
            margin-top: 15px;
            padding: 6px 20px;
            border: none;
            border-radius: 20px;
            background: #f2d4c2;
            color: #644d4d;
            cursor: pointer;
        }
    </style>
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

<div class="dashboard-container">

    <div class="left-section">

        <div class="section-title">Submitted Applications</div>

        <div class="card-grid">
            <c:choose>
                <c:when test="${not empty applications}">
                    <c:forEach var="app" items="${applications}">
                        <a href="ApplicationController?action=viewAdopter&appId=${app.appId}" class="data-card">

                            <h3>#${app.appId} ${app.pet.petName}</h3>

                            <p><span class="data-label">Applicant:</span>
                                ${app.adopter.adoptFName} ${app.adopter.adoptLName}
                            </p>

                            <p><span class="data-label">Status:</span>
                                <span class="status-${app.appStatus.toLowerCase()}">
                                    ${app.appStatus}
                                </span>
                            </p>

                            <p><span class="data-label">Date:</span>
                                <fmt:formatDate value="${app.appDate}" pattern="dd MMM yyyy"/>
                            </p>

                            <p><span class="data-label">Managed By:</span>
                                ${app.staffName}
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

        <div class="section-title">Adoption Records</div>

        <div class="card-grid">
            <c:choose>
                <c:when test="${not empty records}">
                    <c:forEach var="r" items="${records}">
                        <a href="RecordController?action=viewAdopter&recordId=${r.recordId}" class="data-card">

                            <h3>#${r.recordId} ${r.petName}</h3>

                            <p><span class="data-label">Adopter:</span>
                                ${r.adopterName}
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
            <button class="rehome-btn"><strong>Rehome Our Pet ➜</strong></button>
        </a>
    </div>
</div>

<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System
</div>

<div id="customPopup" class="popup-overlay">
    <div class="popup-box">
        <h3 id="popupTitle"></h3>
        <p id="popupMessage"></p>
        <button onclick="closePopup()">OK</button>
    </div>
</div>

<script>
    function showPopup(title, message) {
        document.getElementById("popupTitle").innerText = title;
        document.getElementById("popupMessage").innerText = message;
        document.getElementById("customPopup").style.display = "flex";
    }

    function closePopup() {
        document.getElementById("customPopup").style.display = "none";
    }
</script>

<c:if test="${param.msg == 'deleted'}">
    <script>
        window.addEventListener("DOMContentLoaded", function () {
            showPopup("Success", "Application deleted successfully.");
        });
    </script>
</c:if>

<%
    String loginSuccess = (String) session.getAttribute("loginSuccess");
    if ("adopter".equals(loginSuccess)) {
        session.removeAttribute("loginSuccess");
%>
<script>
    window.addEventListener("DOMContentLoaded", function () {
        showPopup("Welcome", "Adopter logged in successfully!");
    });
</script>
<%
    }
%>

</body>
</html>
