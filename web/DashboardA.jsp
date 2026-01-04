<%-- 
    Document   : DashboardA
    Created on : Jan 4, 2026, 4:09:21 PM
    Author     : amira
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%
    // Get current user from session (example implementation)
    String currentUser = (String) session.getAttribute("username");
    if (currentUser == null) {
        // Redirect to login if not authenticated
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Sample data - Replace with database/service calls
    List<Application> submittedApplications = new ArrayList<>();
    List<AdoptionRecord> adoptionRecords = new ArrayList<>();
    
    // Populate sample data (Remove this in production)
    submittedApplications.add(new Application("John Tan", "Buddy", "Pending Review", "12 Jan 2025"));
    submittedApplications.add(new Application("Mira Alya", "Snowy", "Under Screening", "08 Jan 2025"));
    submittedApplications.add(new Application("Farid Hakim", "Oreo", "Pending Home Visit", "03 Jan 2025"));
    
    adoptionRecords.add(new AdoptionRecord("Sarah Lim", "Milo", "Completed", "22 Dec 2024"));
    adoptionRecords.add(new AdoptionRecord("Amir Hafiz", "Coco", "Completed", "10 Nov 2024"));
    adoptionRecords.add(new AdoptionRecord("Wendy Ng", "Luna", "Completed", "02 Oct 2024"));
    
    // Set attributes for JSTL/EL access (if using JSTL)
    request.setAttribute("submittedApps", submittedApplications);
    request.setAttribute("adoptionRecs", adoptionRecords);
%>

<%!
    // Inner classes for demonstration - Replace with your actual model classes
    public class Application {
        private String applicantName;
        private String petName;
        private String status;
        private String dateSubmitted;
        
        public Application(String applicantName, String petName, String status, String dateSubmitted) {
            this.applicantName = applicantName;
            this.petName = petName;
            this.status = status;
            this.dateSubmitted = dateSubmitted;
        }
        
        public String getApplicantName() { return applicantName; }
        public String getPetName() { return petName; }
        public String getStatus() { return status; }
        public String getDateSubmitted() { return dateSubmitted; }
    }
    
    public class AdoptionRecord {
        private String adopterName;
        private String petName;
        private String status;
        private String adoptionDate;
        
        public AdoptionRecord(String adopterName, String petName, String status, String adoptionDate) {
            this.adopterName = adopterName;
            this.petName = petName;
            this.status = status;
            this.adoptionDate = adoptionDate;
        }
        
        public String getAdopterName() { return adopterName; }
        public String getPetName() { return petName; }
        public String getStatus() { return status; }
        public String getAdoptionDate() { return adoptionDate; }
    }
%>

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

        /* NEW: grid instead of slider */
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
        }

        .data-card:hover {
            transform: translateY(-5px);
            box-shadow: 0px 6px 12px rgba(0,0,0,0.15);
        }

        .data-card p {
            margin: 6px 0;
            font-size: 14px;
        }

        .data-label {
            font-weight: bold;
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
            <a href="Home.jsp">
                <img src="${pageContext.request.contextPath}/images/pawsA.png" alt="PAWS Logo">
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
                <img src="${pageContext.request.contextPath}/images/ProfileIcon.png" alt="Profile" class="profile-icon">
            </a>
            <a href="LogoutServlet" class="logout">LOG OUT</a>
        </div>
    </div>

    <div class="dashboard-container">
        <div class="left-section">
            <div class="section-title">Submitted Application</div>

            <div class="card-grid">
                <% for (Application app : submittedApplications) { %>
                <div class="data-card">
                    <a href="SubmittedApplication.jsp?id=<%= java.net.URLEncoder.encode(app.getApplicantName(), "UTF-8") %>">
                        <p><span class="data-label">Applicant Name:</span> <%= app.getApplicantName() %></p>
                        <p><span class="data-label">Pet Name:</span> <%= app.getPetName() %></p>
                        <p><span class="data-label">Status:</span> <%= app.getStatus() %></p>
                        <p><span class="data-label">Date Submitted:</span> <%= app.getDateSubmitted() %></p>
                    </a>
                </div>
                <% } %>
                
                <% if (submittedApplications.isEmpty()) { %>
                <div class="data-card">
                    <p>No submitted applications found.</p>
                </div>
                <% } %>
            </div>

            <div class="section-title">Adoption Record</div>

            <div class="card-grid">
                <% for (AdoptionRecord record : adoptionRecords) { %>
                <div class="data-card">
                    <a href="AdoptionRecord.jsp?id=<%= java.net.URLEncoder.encode(record.getAdopterName(), "UTF-8") %>">
                        <p><span class="data-label">Adopter Name:</span> <%= record.getAdopterName() %></p>
                        <p><span class="data-label">Pet Name:</span> <%= record.getPetName() %></p>
                        <p><span class="data-label">Status:</span> <%= record.getStatus() %></p>
                        <p><span class="data-label">Adoption Date:</span> <%= record.getAdoptionDate() %></p>
                    </a>
                </div>
                <% } %>
                
                <% if (adoptionRecords.isEmpty()) { %>
                <div class="data-card">
                    <p>No adoption records found.</p>
                </div>
                <% } %>
            </div>
        </div>

        <div class="divider"></div>

        <div class="right-section">
            <img src="${pageContext.request.contextPath}/images/rehome.png" alt="pet">
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
