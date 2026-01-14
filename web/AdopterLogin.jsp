<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Adopter Log In</title>
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
        </style>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="navbar">
            <div class="navbar-left">
                <a href="Home.jsp">
                    <img src="pawsA.png" alt="PAWS Logo">
                </a>
            </div>

            <div class="navbar-right">
                <a href="Home.jsp">Home</a>
                <a href="AboutUs.jsp">About Us</a>        
                <a href="AdopterSignin.jsp">Sign In</a>
                <a href="AdopterLogin.jsp">Log In</a>
            </div>
        </div>
        <div class="content-wrapper">
            <div id="heading">Adopter Log In</div>
            <form method="post" action="${pageContext.request.contextPath}/LoginServlet">
                <div class="form-box">
                    <label>Username</label>
                    <input type="text" name="username" required>

                    <label>Password</label>
                    <input type="password" name="password" required>

                    <div class="right-link">
                        <a href="#">Forgot Password?</a>
                    </div>
                    <input type="hidden" name="page" value="adopter">
                    <input type="submit" class="btn" value="LOG IN">
                </div>
            </form>
        </div>
        <div class="footer">
            © 2025 PAWS Pet Adoption Welfare System
        </div>
    </body>
</html>
