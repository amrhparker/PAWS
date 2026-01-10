<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Log In Staff</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/style.css">

<style>	
    .content-wrapper{
        min-height:calc(100vh - 140px);
        display:flex;
        flex-direction:column;
        align-items:center;
        justify-content:center;
    }

    #heading{
        font-size:28px;
        margin-bottom:18px;
        font-weight:bold;
        text-align:center;
    }

    .form-box{
        width:420px;
        background:#b4c0f6;
        padding:25px 35px;
        border-radius:18px;
        border:1px solid #e0e0e0;
        box-shadow:0 2px 8px rgba(0,0,0,0.06);
    }

    label{
        display:block;
        font-size:14px;
        margin-bottom:4px;
        margin-left:5%;
    }

    input{
        width:90%;
        padding:9px;
        border-radius:7px;
        border:1px solid #ccc;
        margin:0 auto 15px auto;
        font-size:14px;
        display:block;
    }

    .right-link{
        width:90%;
        margin:0 auto 12px auto;
        text-align:right;
        font-size:13px;
    }

    .btn{
        display:block;
        margin:10px auto 0;
        padding:9px 20px;
        border:none;
        border-radius:8px;
        background:#00bf63;
        color:white;
        font-size:15px;
        cursor:pointer;
        width:110px;
        text-align:center;
    }

    .btn:hover{
        background:#999;
    }

    .error-msg{
        color:red;
        text-align:center;
        margin-bottom:10px;
        font-size:14px;
    }
</style>
</head>

<body>

<!-- ================= NAVBAR ================= -->
<div class="navbar">
    <div class="navbar-left">
        <a href="Home.html">
            <img src="pawsS.png" alt="PAWS Staff">
        </a>

        <div class="navbar-links">
            <a href="Home.html">Home</a>
            <a href="AboutUs.html">About</a>
        </div>

        <div class="navbar-right">
            <a href="StaffLogin.jsp">LOG IN</a>
        </div>
    </div>
</div>

<!-- ================= CONTENT ================= -->
<div class="content-wrapper">

    <div id="heading">Staff Log In</div>

    <div class="form-box">

        <!-- Error message from servlet -->
        <c:if test="${not empty error}">
            <div class="error-msg">${error}</div>
        </c:if>

        <!-- LOGIN FORM -->
        <form action="StaffController" method="post">
            <input type="hidden" name="action" value="login">

            <label>Username</label>
            <input type="text" name="username" required>

            <label>Password</label>
            <input type="password" name="password" required>

            <div class="right-link">
                <a href="#">Forgot Password?</a>
            </div>
            <input type="hidden" name="page" value="staff">
            <button type="submit" class="btn">LOG IN</button>

        </form>
    </div>
</div>

</body>
</html>
