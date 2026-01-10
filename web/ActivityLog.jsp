<%-- 
    Document   : ActivityLog
    Created on : Jan 10, 2026, 2:42:38 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Activity Log</title>

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

        #logs{
            width:80%;
            margin:20px auto;
            border-collapse:collapse;
        }

        #logs th{
            font-size:18px;
            padding:15px;
            background:#cccccc;
        }

        #logs td{
            padding:12px;
            text-align:center;
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
            <a href="ReportController?action=list">Reports</a>
            <a href="ApplicationController?action=list">Applications</a>
            <a href="ActivityLogController?action=list" class="active">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="Logout.html" class="logout">Log Out</a>
    </div>
</div>

<!-- PAGE TITLE -->
<table id="heading">
    <tr>
        <th style="text-align:left;">Activity Logs</th>
    </tr>
</table>

<!-- LOG TABLE -->
<table id="logs" border="1">
    <tr>
        <th>Action</th>
        <th>Timestamp</th>
        <th>User</th>
        <th>Entity</th>
    </tr>

    <!-- DYNAMIC LOGS -->
    <c:forEach var="log" items="${logs}">
        <tr>
            <td>${log.action}</td>
            <td>${log.timestamp}</td>
            <td>${log.user}</td>
            <td>${log.entity}</td>
        </tr>
    </c:forEach>

    <!-- EMPTY STATE -->
    <c:if test="${empty logs}">
        <tr>
            <td colspan="4" style="text-align:center; color:gray;">
                No activity logs found
            </td>
        </tr>
    </c:if>
</table>

</body>
</html>
