<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    if (session.getAttribute("staff") == null) {
        response.sendRedirect("LogInStaff.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Reports</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: #f7f9fb;
        }

        .page-title {
            width: 95%;
            margin: 20px auto 5px;
            font-size: 28px;
            font-weight: 600;
        }
        
        .content {
            width: 85%;
            margin: 20px auto 40px auto;
        }

        .filter-bar {
            width: 100%;
            margin: 0 0 20px 0;
            display: flex;
            gap: 10px;
            align-items: center;
            flex-wrap: wrap;
        }

        .filter-bar select,
        .filter-bar input,
        .filter-bar button {
            padding: 6px 10px;
            font-family: 'Poppins', sans-serif;
        }

        .filter-bar button {
            background: #ff66c4;
            border: none;
            color: white;
            border-radius: 20px;
            padding: 6px 18px;
            cursor: pointer;
        }

        .filter-bar button:hover {
            background: #4abf8a;
        }

        .report-table {
            width: 100%;
            margin: 0;
            border-collapse: collapse;
            background: #ffffff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 6px 15px rgba(0,0,0,0.08);
        }

        .report-table thead {
            background: #b4c0f6;
            color: black;
        }

        .report-table th {
            padding: 14px 16px;
            text-align: left;
            font-size: 16px;
            font-weight: 600;
        }

        .report-table td {
            padding: 14px 16px;
            border-bottom: 1px solid #f0f0f0;
            font-size: 14px;
        }


        .tag {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
            white-space: nowrap;
        }

        .pending { background: #fff3cd; color: #856404; }
        .approved { background: #d4edda; color: #155724; }
        .rejected { background: #f8d7da; color: #721c24; }
        .completed { background: #d1ecf1; color: #0c5460; }

        .view-btn {
            padding: 6px 16px;
            background: #ff66c4;
            color: white;
            border-radius: 20px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 500;
        }

        .view-btn:hover {
            background: #4abf8a;
        }

        .delete-btn {
            padding: 6px 16px;
            margin-left: 10px;
            background: red;
            color: white;
            border-radius: 20px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 500;
        }

        .delete-btn:hover {
            background: #4abf8a;
        }
    </style>
</head>

<body>

<div class="navbar">
    <div class="navbar-left">
        <a href="Home.jsp">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>
        <div class="navbar-links">
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

<div class="page-header">
    <h2>Adoption Reports</h2>
</div>
<div class="content">
<form class="filter-bar"
      action="${pageContext.request.contextPath}/ReportController"
      method="post">

    <select name="reportType" required>
        <option value="">-- Select Report --</option>
        <option value="Pending Applications">Pending Applications</option>
        <option value="Approved Applications">Approved Applications</option>
        <option value="Rejected Applications">Rejected Applications</option>
        <option value="Pending Adoptions">Pending Adoptions</option>
        <option value="Completed Adoptions">Completed Adoptions</option>
    </select>

    From:
    <input type="date" name="fromDate">

    To:
    <input type="date" name="toDate">

    <select name="petType">
        <option value="ALL">All Pets</option>
        <option value="Cat">Cat</option>
        <option value="Dog">Dog</option>
    </select>

    <button type="submit">Generate Report</button>
</form>

<table class="report-table">
    <thead>
        <tr>
            <th>Report ID</th>
            <th>Report Type</th>
            <th>Date</th>
            <th>Total</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>

        <c:forEach var="r" items="${reports}">
            <tr>
                <td>Report <b>#${r.reportId}</b></td>

                <td>
                    <c:choose>
                        <c:when test="${fn:contains(r.reportType,'Pending')}">
                            <span class="tag pending">${r.reportType}</span>
                        </c:when>
                        <c:when test="${fn:contains(r.reportType,'Approved')}">
                            <span class="tag approved">${r.reportType}</span>
                        </c:when>
                        <c:when test="${fn:contains(r.reportType,'Rejected')}">
                            <span class="tag rejected">${r.reportType}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="tag completed">${r.reportType}</span>
                        </c:otherwise>
                    </c:choose>
                </td>

                <td>${r.reportDate}</td>
                <td>${r.totalCount}</td>

                <td>
                    <a class="view-btn"
                       href="${pageContext.request.contextPath}/ReportController?action=view&reportId=${r.reportId}">
                        View
                    </a>

                    <a class="delete-btn"
                        href="ReportController?action=delete&reportId=${r.reportId}"
                        onclick="return confirm('Are you sure you want to delete this report?');">
                        Delete
                    </a>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty reports}">
            <tr>
                <td colspan="5" style="text-align:center; padding:20px;">
                    No reports generated yet.
                </td>
            </tr>
        </c:if>

    </tbody>
</table>

</div>
      
<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System
</div>
</body>
</html>
