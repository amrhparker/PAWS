<%-- 
    Document   : ManageReports
    Created on : Jan 7, 2026, 2:39:34 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reports</title>

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
            border-collapse:collapse;
        }

        #view th{
            border:1px solid black;
            font-size:20px;
            padding:15px;
        }

        #view td{
            padding:10px;
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
        <th style="text-align:left;">Reports Management</th>
    </tr>
</table>

<!-- REPORT LIST -->
<table>
    <tr>
        <td>
            <table id="view" border="1">
                <tr>
                    <th>Reports</th>
                </tr>

                <!-- DYNAMIC REPORTS -->
                <c:forEach var="r" items="${reports}">
                    <tr>
                        <td>
                            <a href="ReportController?action=view&reportId=${r.reportId}">
                                Report ${r.reportId} — ${r.reportDate}
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <!-- EMPTY STATE -->
                <c:if test="${empty reports}">
                    <tr>
                        <td style="text-align:center; color:gray;">
                            No reports available
                        </td>
                    </tr>
                </c:if>

            </table>
        </td>
    </tr>
</table>

</body>
</html>

