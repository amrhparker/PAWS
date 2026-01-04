<%-- 
    Document   : AdopterSignin
    Created on : Jan 4, 2026, 3:34:55 PM
    Author     : amira
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adopter Sign In</title>
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

                    .menu-links {
                            margin-top: 10px;
                    }

                    .menu-links a, .nav-right a {
                            text-decoration: none;
                            font-weight: bold;
                            margin-right: 20px;
                    }
            table{
                width:100%;
                border-collapse:collapse;
            }

            .menu{
                background-color:#cccccc;
            }

            .header-row{
                border:none;
            }

            .header-cell{
                padding:20px 40px;
                border:none;
                white-space:nowrap;
            }

            .header-cell:hover{
                color:#848484;
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

            /* PAGE TITLE */
            #heading{
                font-size:25px;
                margin:40px auto 30px auto;
                font-weight:bold;
                text-align:center;
            }

            .form-box{
                max-width:1100px;
                width:80%;
                margin:0 auto 40px auto;      
                background:#d0e6c7;
                padding:40px 70px 40px 50px;   
                border-radius:25px;
                border:1px solid #e0e0e0;
            }

            .form-grid{
                display:grid;
                grid-template-columns: 1fr 1fr;   
                column-gap:40px;
                row-gap:20px;
            }

            .field label{
                display:block;
                font-size:15px;
                margin-bottom:5px;
            }

            .field input{
                width:100%;
                padding:12px;
                border-radius:8px;
                border:1px solid #ccc;
            }

            /* GREY BUTTON */
            .btn{
                display:block;
                margin:40px auto 0;
                padding:15px 40px;
                border:none;
                border-radius:10px;
                background:#b3b3b3;
                color:white;
                font-size:18px;
                cursor:pointer;
            }
            .btn{
                display:block;
                margin:25px auto 0;
                padding:10px 28px;        
                border:none;
                border-radius:8px;        
                background:#ff66c4;
                color:white;
                font-size:16px;           
                cursor:pointer;
            }
            .btn:hover{
                background:#999;
            }
        </style>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="navbar">
            <div class="navbar-left">
                <a href="Home.html">
                    <img src="PAWS.png" alt="PAWS Logo">
                </a>
            </div>

            <div class="navbar-right">
                <a href="Home.html">Home</a>
                <a href="AboutUs.html">About</a>
                <a href="Rehome.html">Rehome</a>
                <a href="AdopterSignin.html">Sign In</a>
                <a href="AdopterLogin.html">Log In</a>
            </div>
        </div>

        <div id="heading">Adopter Sign In</div>

        <div class="form-box">
            <form method="post" action="AdopterProfileServlet">
                <div class="form-grid">
                    <!-- Row 1 -->
                    <div class="field">
                        <label>First Name</label>
                        <input type="text">
                    </div>
                    <div class="field">
                        <label>Last Name</label>
                        <input type="text">
                    </div>

                    <!-- Row 2 -->
                    <div class="field">
                        <label>IC Number</label>
                        <input type="text">
                    </div>
                    <div class="field">
                        <label>Phone Number</label>
                        <input type="text">
                    </div>

                    <!-- Row 3 -->
                    <div class="field">
                        <label>Email</label>
                        <input type="email">
                    </div>
                    <div class="field">
                        <label>Address</label>
                        <input type="text">
                    </div>

                    <!-- Row 4 -->
                    <div class="field">
                        <label>Username</label>
                        <input type="text">
                    </div>
                    <div class="field">
                        <label>Password</label>
                        <input type="password">
                    </div>
                </div>
                <input type="submit" class="btn" value="SIGN IN">
            </form>
                
        </div>
        <div class="footer">
            © 2025 PAWS Pet Adoption Welfare System -- All Rights Reserved
        </div>
    </body>
</html>
