<%-- 
    Document   : ViewRecords1
    Created on : Jan 6, 2026, 9:30:32 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Adoption Record</title>

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
            padding:10px;
            text-align:center;
            vertical-align:middle;
            height:300px;
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
            <a href="ManageReports.html">Reports</a>
            <a href="ApplicationController?action=list">Applications</a>
            <a href="ActivityLog.html">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="Logout.html" class="logout">Log Out</a>
    </div>
</div>

<!-- PAGE HEADING -->
<table id="heading">
    <tr>
        <th style="text-align:left;">Adoption</th>
    </tr>
</table>

<!-- RECORD DETAILS -->
<table>
    <tr>
        <td>
            <table id="view" border="1">
                <tr>
                    <th>
                        <table id="heading">
                            <tr>
                                <th style="text-align:left;">
                                    Adoption ${record.recordId}
                                </th>
                            </tr>
                        </table>
                    </th>
                </tr>

                <tr>
                    <td>
                        <strong>Application ID:</strong> ${record.appId} <br><br>
                        <strong>Record Date:</strong> ${record.recordDate}
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

</body>
</html>
