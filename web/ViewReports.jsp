<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>View Report</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: #f7f9fb;
        }

        .container {
            width: 95%;
            margin: 30px auto;
        }

        .section {
            background: white;
            padding: 20px 25px;
            margin-bottom: 25px;
            border-radius: 12px;
            box-shadow: 0 6px 15px rgba(0,0,0,0.08);
        }

        .section h2 {
            margin-bottom: 15px;
        }

        .info-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            row-gap: 10px;
            column-gap: 60px;
            font-size: 14px;
            text-align: left; /* 🔑 ini penting */
        }

        .info-grid span {
            font-weight: 500;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        thead {
            background: #5ecf9b;
            color: white;
        }

        th, td {
            padding: 12px;
            text-align: left;
            font-size: 14px;
            border-bottom: 1px solid #eee;
        }

        tr:hover {
            background: #f7fdfb;
        }

        .tag {
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
        }

        .pending { background: #fff3cd; color: #856404; }
        .completed { background: #d1ecf1; color: #0c5460; }

        .back-link {
            margin-top: 20px;
            display: inline-block;
            color: #5ecf9b;
            font-weight: 500;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }
        
    </style>
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="navbar-left">

        <a href="StaffDashboard.jsp">
            <img src="pawsS.png">

        <a href="Home.jsp">
            <img src="pawsS.png" alt="PAWS Staff">

        </a>

        <div class="navbar-links">
            <a href="StaffDashboard.jsp">Dashboard</a>
            <a href="ManagePets.jsp">Pets</a>
            <a href="ManageRecords.jsp">Records</a>
            <a href="ReportController" class="active">Reports</a>
            <a href="ManageApplications.jsp">Applications</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>

<div class="container">

    <!-- REPORT SUMMARY -->
    <div class="section">

        <div class="info-grid">
            <div><span>Report ID:</span> ${report.reportId}</div>
            <div><span>Report Type:</span> ${report.reportType}</div>
            <div><span>Generated Date:</span> ${report.reportDate}</div>
            <div><span>Total Records:</span> ${report.totalCount}</div>
        </div>
    </div>

    <!-- RECORD DETAILS -->
    <div class="section">
        <h2>Adoption / Application Details</h2>

        <table>
            <thead>
                <tr>
                    <th>Record ID</th>
                    <th>Status</th>
                    <th>Record Date</th>
                    <th>Application Date</th>
                    <th>Adopter</th>
                    <th>Phone</th>
                    <th>Pet</th>
                    <th>Species</th>
                    <th>Breed</th>
                    <th>Age</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="r" items="${records}">
                    <tr>
                        <td>${r.recordId}</td>

                        <td>
                            <span class="tag ${r.recordStatus == 'Pending' ? 'pending' : 'completed'}">
                                ${r.recordStatus}
                            </span>
                        </td>

                        <td>${r.recordDate}</td>
                        <td>${r.appDate}</td>
                        <td>${r.adopterName}</td>
                        <td>${r.adopterPhone}</td>
                        <td>${r.petName}</td>
                        <td>${r.petSpecies}</td>
                        <td>${r.petBreed}</td>
                        <td>${r.petAge}</td>
                    </tr>
                </c:forEach>

                <c:if test="${empty records}">
                    <tr>
                        <td colspan="10" style="text-align:center;">
                            No records found for this report.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <a class="back-link"
       href="${pageContext.request.contextPath}/ReportController">
        ← Back to Reports
    </a>

</div>

</body>
</html>
