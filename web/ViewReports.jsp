<%-- 
    Document   : ViewReports
    Created on : Jan 10, 2026, 2:33:32 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>View Report</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        #heading{
            font-size:30px;
            padding-left:60px;
            padding-top:20px;
            width:95%;
            margin:auto;
        }

        #view{
            width:95%;
            padding:10px;
            margin:auto;
        }

        #view th{
            font-size:20px;
            padding:15px;
            text-align:left;
        }

        #view td{
            padding:15px;
            text-align:left;
        }

        table{
            font-family:banschrift, sans-serif;
            width:100%;
            border-collapse:collapse;
        }

        a{
            text-decoration:none;
            color:black;
        }

        a:hover{
            color:#848484;
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
            <a href="RecordController?action=list">Records</a>
            <a href="ReportController?action=list" class="active">Reports</a>
            <a href="ApplicationController?action=list">Applications</a>
            <a href="ActivityLog.html">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="Logout.html" class="logout">Log Out</a>
    </div>
</div>

<!-- PAGE TITLE -->
<table id="heading">
    <tr>
        <th style="text-align:left;">Adoption Report</th>
    </tr>
</table>

<!-- REPORT DETAILS -->
<table>
    <tr>
        <td>
            <table id="view" border="1">
                <tr>
                    <th colspan="2">
                        Report ${report.reportId}
                    </th>
                </tr>

                <tr>
                    <td><strong>Record ID</strong></td>
                    <td>${report.recordId}</td>
                </tr>

                <tr>
                    <td><strong>Staff ID</strong></td>
                    <td>${report.staffId}</td>
                </tr>

                <tr>
                    <td><strong>Report Type</strong></td>
                    <td>${report.reportType}</td>
                </tr>

                <tr>
                    <td><strong>Report Date</strong></td>
                    <td>${report.reportDate}</td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align:center;">
                        <a href="ReportController?action=list">← Back to Reports</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

</body>
</html>

