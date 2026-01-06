<%-- 
    Document   : ManageApplications
    Created on : Jan 6, 2026, 9:04:09 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Applications</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        #main-title{
            font-size:32px;
            font-weight:bold;
            margin:40px auto 20px auto;
            width:95%;
            text-align:left;
        }

        #view{
            width:95%;
            margin-left:auto;
            margin-right:auto;
            border-collapse:collapse;
        }

        #view th, #view td{
            border:1px solid black;
            padding:15px;
            text-align:center;
            font-size:18px;
        }

        .action-column{
            padding:10px;
            vertical-align:middle;
        }

        .action-wrapper{
            display:flex;
            flex-direction:column;
            gap:10px;
            align-items:center;
        }

        .action-btn{
            padding:8px 12px;
            border:1px solid black;
            background:white;
            border-radius:6px;
            cursor:pointer;
            font-size:14px;
            width:110px;
        }

        .action-btn:hover{
            background:#e6e6e6;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>

        <div class="navbar-links">
            <a href="StaffDashboard.html">Dashboard</a>
            <a href="ManagePets.html">Pets</a>
            <a href="ManageRecords.html">Records</a>
            <a href="ManageReports.html">Reports</a>
            <a href="ManageApplications.jsp" class="active">Applications</a>
            <a href="ActivityLog.html">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="Logout.html" class="logout">Log Out</a>
    </div>
</div>

<div id="main-title">Manage Adoption Applications</div>

<table id="view">
    <tr>
        <th>Application ID</th>
        <th>Adopter</th>
        <th>Pet</th>
        <th>Date</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

<%
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        conn = DriverManager.getConnection(
            "jdbc:derby://localhost:1527/PAWSdb",
            "app",
            "app"
        );

        String sql =
            "SELECT a.APP_ID, a.APP_DATE, a.APP_STATUS, " +
            "ad.ADOPT_FNAME, ad.ADOPT_LNAME, p.PET_NAME " +
            "FROM APPLICATION a " +
            "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
            "JOIN PET p ON a.PET_ID = p.PET_ID " +
            "ORDER BY a.APP_DATE DESC";

        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            int appId = rs.getInt("APP_ID");
            String adopterName = rs.getString("ADOPT_FNAME") + " " + rs.getString("ADOPT_LNAME");
            String petName = rs.getString("PET_NAME");
            Date appDate = rs.getDate("APP_DATE");
            String status = rs.getString("APP_STATUS");
%>

    <tr>
        <td>APP<%= String.format("%03d", appId) %></td>
        <td><%= adopterName %></td>
        <td><%= petName %></td>
        <td><%= appDate %></td>
        <td><%= status %></td>

        <td class="action-column">
            <div class="action-wrapper">

                <button class="action-btn"
                        onclick="location.href='ViewApplication.jsp?appId=<%=appId%>'">
                    View
                </button>

                <% if ("Pending".equalsIgnoreCase(status)) { %>

                    <button class="action-btn"
                            onclick="location.href='UpdateApplicationStatusServlet?appId=<%=appId%>&status=Approved'">
                        Approve
                    </button>

                    <button class="action-btn"
                            onclick="location.href='UpdateApplicationStatusServlet?appId=<%=appId%>&status=Rejected'">
                        Reject
                    </button>

                <% } %>
            </div>
        </td>
    </tr>

<%
        }
    } catch (Exception e) {
%>
    <tr>
        <td colspan="6" style="color:red;">
            Error loading applications: <%= e.getMessage() %>
        </td>
    </tr>
<%
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (conn != null) conn.close();
    }
%>

</table>

</body>
</html>
