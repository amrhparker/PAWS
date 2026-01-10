<%-- 
    Document   : ManageRecords
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Adoption Records</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        body{
            font-family:'Poppins', sans-serif;
            background:#f6f7fb;
            margin:0;
        }

        /* CONTENT */
        .content{
            width:90%;
            margin:40px auto;
        }

        h2{
            margin-bottom:25px;
            font-weight:600;
        }

        /* RECORD CARD */
        .record-card{
            background:white;
            border-radius:14px;
            padding:22px 28px;
            margin-bottom:18px;
            box-shadow:0 8px 20px rgba(0,0,0,0.08);
            display:flex;
            justify-content:space-between;
            align-items:center;
        }

        .record-title{
            font-size:18px;
            font-weight:500;
        }

        /* BUTTONS */
        .actions a{
            text-decoration:none;
            padding:9px 16px;
            border-radius:8px;
            font-size:14px;
            font-weight:500;
            margin-left:10px;
            display:inline-block;
        }

        .view-btn{
            background:#0d6efd;
            color:white;
        }

        .delete-btn{
            background:#dc3545;
            color:white;
        }

        .view-btn:hover{ background:#0b5ed7; }
        .delete-btn:hover{ background:#bb2d3b; }

        /* EMPTY */
        .empty{
            background:white;
            padding:30px;
            border-radius:14px;
            text-align:center;
            color:gray;
            box-shadow:0 6px 16px rgba(0,0,0,0.06);
        }
    </style>
</head>

<body>

<!-- ===== PAWS ORIGINAL NAVBAR (KEKAL) ===== -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>

        <div class="navbar-links">
            <a href="StaffDashboard.jsp">Dashboard</a>
            <a href="ManagePets.jsp">Pets</a>
            <a href="ManageRecords.jsp">Records</a>
            <a href="ManageReports.jsp">Reports</a>
            <a href="ManageApplications.jsp">Applications</a>
            <a href="ActivityLog.jsp">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="Logout.html" class="logout">Log Out</a>
    </div>
</div>

<!-- ===== CONTENT ===== -->
<div class="content">

    <h2>Adoption Records</h2>

    <c:forEach var="r" items="${records}">
        <div class="record-card">

            <div class="record-title">
                Adoption ${r.recordId}
            </div>

            <div class="actions">
                <a class="view-btn"
                   href="ViewRecords.jsp?recordId=${r.recordId}">
                   View
                </a>

                <a class="delete-btn"
                   href="RecordController?action=delete&recordId=${r.recordId}"
                   onclick="return confirm('Delete this record?');">
                   Delete
                </a>
            </div>

        </div>
    </c:forEach>

    <c:if test="${empty records}">
        <div class="empty">
            No adoption records found
        </div>
    </c:if>

</div>

</body>
</html>
