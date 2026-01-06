<%-- 
    Document   : ManageRecords
    Created on : Jan 6, 2026, 9:34:16 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Adoption</title>

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
            <a href="RecordController?action=list" class="active">Records</a>
            <a href="ManageReports.html">Reports</a>
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
        <th style="text-align:left;">Adoption Records</th>
    </tr>
</table>

<!-- RECORD LIST -->
<table>
    <tr>
        <td>
            <table id="view" border="1">
                <tr>
                    <th>Records</th>
                </tr>

                <!-- DYNAMIC RECORDS -->
                <c:forEach var="r" items="${records}">
                    <tr>
                        <td>
                            <a href="RecordController?action=view&recordId=${r.recordId}">
                                Adoption ${r.recordId}
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <!-- EMPTY STATE -->
                <c:if test="${empty records}">
                    <tr>
                        <td style="text-align:center; color:gray;">
                            No adoption records found
                        </td>
                    </tr>
                </c:if>

            </table>
        </td>
    </tr>
</table>

</body>
</html>

