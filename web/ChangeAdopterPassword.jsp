<%-- 
    Document   : ChangeAdopterPassword
    Created on : Jan 5, 2026, 5:06:22 PM
    Author     : amira
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Change Password</title>
        <style>
            body{
                background-color:#FAF9F6;
                font-family:banschrift, sans-serif;
                margin:0;
                padding:0;
            }

            .navbar {
                padding: 15px 30px;
                border-bottom: 1px solid #ccc;
            }

            .nav-top {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }

            .nav-left {
                display: flex;
                align-items: center;
                gap: 20px;
            }

            .nav-right {
                display: flex;
                align-items: center;
            }
            
            .profile-icon {
                height: 32px;
                display: block;
                margin-bottom: 3px;
            }

            .logout-text {
                font-weight: bold;
                text-decoration: none;
                margin: 0;
            }

            .menu-links {
                margin-top: 10px;
            }

            .menu-links a, .nav-right a {
                text-decoration: none;
                font-weight: bold;
                margin-right: 20px;
            }

            #paws{
                text-align:left;
                font-size:40px;
                background-color:#FAF9F6;
            }

            a{
                color:black;
                text-decoration:none;
            }
            a:hover{
                color:#848484;
            }

            .content-wrapper{
                min-height:calc(100vh - 140px);
                display:flex;
                flex-direction:column;
                align-items:center;
                justify-content:center;
                padding: 20px 0;
            }

            #heading{
                font-size:28px;
                margin-bottom:18px;
                font-weight:bold;
                text-align:center;
            }

            .form-box{
                width:420px;            
                background:#d0e6c7;
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
                background:#ff66c4;
                color:white;
                font-size:15px;
                cursor:pointer;
                width:110px;
                text-align:center;
            }
            .btn:hover{
                background:#999;
            }

            .footer {
                text-align: center;
                padding: 20px;
                border-top: 1px solid #ccc;
                font-size: 14px;
                color: #666;
            }

            /* NEW CSS - Minimal additions */
            .message {
                width: 420px;
                margin-bottom: 15px;
                padding: 12px;
                border-radius: 7px;
                text-align: center;
                font-size: 14px;
            }
            
            .error-message {
                background-color: #ffe6e6;
                color: #ff3333;
                border: 1px solid #ffcccc;
            }
            
            .success-message {
                background-color: #e6ffe6;
                color: #28a745;
                border: 1px solid #c3e6cb;
            }
            
            .btn-secondary {
                background: #6c757d;
                margin-top: 10px;
            }
            
            .btn-secondary:hover {
                background: #5a6268;
            }
            
            .password-note {
                width: 90%;
                margin: 0 auto 15px;
                font-size: 12px;
                color: #666;
                text-align: center;
            }
        </style>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
   </head>
    <body>
        <div class="navbar">
            <div class="navbar-left">
                <a href="Home.html">
                    <img src="pawsA.png" alt="PAWS Logo">
                </a>
            </div>
            <div class="navbar-profile">
                <a href="Profile.jsp">
                    <img src="ProfileIcon.png" alt="Profile" class="profile-icon">
                </a>
                <a href="Logout.jsp" class="logout">LOG OUT</a>
            </div>
        </div>
        
        <div class="content-wrapper">
            <div id="heading">Change Password</div>
            
            <c:if test="${not empty errorMessage}">
                <div class="message error-message">
                    ${errorMessage}
                </div>
            </c:if>
            
            <c:if test="${not empty successMessage}">
                <div class="message success-message">
                    ${successMessage}
                </div>
            </c:if>
            
            <form method="post" action="AdopterController">
                <input type="hidden" name="action" value="changePassword">
                <div class="form-box">
                    <label>Current Password</label>
                    <input type="password" name="currentPassword" required>
                    
                    <label>New Password</label>
                    <input type="password" name="newPassword" required>
                    
                    <label>Confirm New Password</label>
                    <input type="password" name="confirmPassword" required>
                    
                    <div class="password-note">
                        Make sure your new password is strong and secure.
                    </div>
                                       
                    <input type="submit" class="btn" value="UPDATE">
                    <a href="AdopterController?action=profile" class="btn btn-secondary">CANCEL</a>
                </div>
            </form>
        </div>
        
        <div class="footer">
            © 2025 PAWS Pet Adoption Welfare System — All Rights Reserved
        </div>
    </body>
</html>