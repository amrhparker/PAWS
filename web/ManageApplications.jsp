<%-- 
    Document   : ManageApplications
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    /*
     FLOW (SAMA MACAM RECORDS & REPORTS):

     1. User RUN ManageApplications.jsp
     2. JSP check → ada data "applications" atau belum
     3. Kalau belum → redirect ApplicationController
     4. Controller ambil data DAO
     5. Controller forward balik ke JSP
     6. JSP DISPLAY DATA
    */

    if (request.getAttribute("applications") == null) {
        response.sendRedirect("ApplicationController?action=manage");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Adoption Applications</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <style>
        body{
            font-family:'Poppins', sans-serif;
            background:#f6f7fb;
            margin:0;
        }

        .container{
            width:90%;
            margin:40px auto;
        }

        h2{
            margin-bottom:25px;
            font-weight:600;
        }

        table{
            width:100%;
            border-collapse:collapse;
            background:white;
            border-radius:14px;
            overflow:hidden;
            box-shadow:0 8px 20px rgba(0,0,0,0.08);
        }

        th{
            background:#f2f3f7;
            padding:16px;
            text-align:left;
            font-size:15px;
        }

        td{
            padding:16px;
            border-top:1px solid #eee;
            font-size:14px;
        }

        .status{
            padding:6px 12px;
            border-radius:20px;
            font-size:13px;
            font-weight:500;
            display:inline-block;
        }

        .Pending{
            background:#fff3cd;
            color:#856404;
        }

        .Approved{
            background:#d4edda;
            color:#155724;
        }

        .Rejected{
            background:#f8d7da;
            color:#721c24;
        }

        .actions{
            display:flex;
            gap:8px;
        }

        .btn{
            padding:7px 14px;
            border-radius:6px;
            font-size:13px;
            border:none;
            cursor:pointer;
            font-weight:500;
        }

        .btn-view{
            background:#0d6efd;
            color:white;
        }

        .btn-approve{
            background:#198754;
            color:white;
        }

        .btn-reject{
            background:#dc3545;
            color:white;
        }

        .btn:hover{
            opacity:0.9;
        }

        .empty{
            text-align:center;
            color:gray;
            padding:30px;
        }
    </style>
</head>

<body>
<c:if test="${param.email eq 'sent'}">
    <script>
        alert("✅ Email successfully sent to adopter!");
    </script>
</c:if>

<!-- ===== NAVBAR (KEKAL PAWS STYLE) ===== -->
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
            <a href="ManageApplications.jsp" class="active">Applications</a>
            <a href="ActivityLog.jsp">Logs</a>
        </div>
    </div>

    <div class="navbar-right">
        <a href="LogoutServlet" class="logout">Log Out</a>
    </div>
</div>

<!-- ===== CONTENT ===== -->
<div class="container">

    <h2>Manage Adoption Applications</h2>

    <table>
        <tr>
            <th>Application ID</th>
            <th>Adopter</th>
            <th>Pet</th>
            <th>Date</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <c:forEach var="a" items="${applications}">
            <tr>
                <td>APP${a.appId}</td>

                <td>
                    ${a.adopter.adoptFName} ${a.adopter.adoptLName}
                </td>

                <td>${a.pet.petName}</td>

                <td>${a.appDate}</td>

                <td>
                    <span class="status ${a.appStatus}">
                        ${a.appStatus}
                    </span>
                </td>

                <td>
                    <div class="actions">

        <button class="btn btn-view"
            onclick="location.href='ApplicationController?action=view&appId=${a.appId}'">
            View
        </button>


        <c:if test="${a.appStatus eq 'Pending'}">

            <!-- APPROVE FORM -->
            <form action="ApplicationController" method="post" style="display:inline;">
                <input type="hidden" name="action" value="updateStatus">
                <input type="hidden" name="appId" value="${a.appId}">
                <input type="hidden" name="status" value="Approved">
                <input type="hidden" name="eligibility" value="Approved">
                <button type="submit" class="btn btn-approve">Approve</button>
            </form>

            <!-- REJECT FORM -->
            <form action="ApplicationController" method="post" style="display:inline;">
                <input type="hidden" name="action" value="updateStatus">
                <input type="hidden" name="appId" value="${a.appId}">
                <input type="hidden" name="status" value="Rejected">
                <input type="hidden" name="eligibility" value="Rejected">
                <button type="submit" class="btn btn-reject">Reject</button>
            </form>

        </c:if>

    </div>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty applications}">
            <tr>
                <td colspan="6" class="empty">
                    No adoption applications found
                </td>
            </tr>
        </c:if>

    </table>

</div>

</body>
</html>
