<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.PetDao, model.PetBean, model.AdopterBean, java.util.List" %>

<%
    AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");
%>

<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Rehome Pet</title>

    <style>
        h2 {
            text-align: center;
            margin-top: 40px;
            font-size: 28px;
            font-weight: bold;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.25);
        }

        .pets-container {
            display: flex;
            justify-content: center;
            gap: 40px;
            padding: 40px 20px;
            flex-wrap: wrap;
        }

        .pet-card {
            width: 220px;
            text-align: center;
        }

        .pet-img-box {
            border: 3px solid black;
            padding: 10px;
            border-radius: 15px;
        }

        .pet-img-box img {
            width: 100%;
            height: 230px;
            object-fit: cover;
            border-radius: 10px;
        }

        .pet-info {
            background: #f2dfd1;
            padding: 15px;
            border-radius: 15px;
            margin-top: 10px;
            font-size: 14px;
        }

        .no-pet {
            background: #f2dfd1;
            padding: 15px;
            border-radius: 15px;
            font-size: 20px;
            text-align: center;
            margin: 20px auto;
            width: 20%;
        }

        .more-details {
            display: block;
            margin-top: 8px;
            color: black;
            font-weight: bold;
        }

        .rehoming-btn {
            margin-top: 10px;
            padding: 10px 25px;
            border-radius: 20px;
            border: none;
            cursor: pointer;
            font-weight: bold;
            background: #4CAF50;
            color: white;
        }

        .rehoming-btn:hover {
            background: #45a049;
            transform: translateY(-3px);
        }
    </style>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="main-content">

<% if (adopter != null) { %>

    <div class="navbar">
        <div class="navbar-left">
            <a href="Home.jsp">
                <img src="pawsA.png" alt="PAWS">
            </a>

            <div class="navbar-links">
                <a href="Home.jsp">Home</a>
                <a href="AboutUs.jsp">About Us</a>
                <a href="ApplicationController?action=dashboardA">Dashboard</a>
                <a href="Rehome.jsp" class="active">Rehome Pet</a>
            </div>
        </div>

        <div class="navbar-profile">
            <a href="AdopterController?action=profile">
                <img src="ProfileIcon.png" alt="Profile" class="profile-icon">
            </a>
            <a href="LogoutServlet" class="logout">LOG OUT</a>
        </div>
    </div>

<% } else { %>

    <div class="navbar">
        <div class="navbar-left">
            <a href="Home.jsp">
                <img src="PAWS.png" alt="PAWS Logo">
            </a>
        </div>

        <div class="navbar-right">
            <a href="Home.jsp">Home</a>
            <a href="AboutUs.jso">About Us</a>
            <a href="Rehome.jsp" class="active">Rehome</a>
            <a href="AdopterSignin.jsp">Adopter</a>
            <a href="LogInStaff.jsp">Staff</a>
        </div>
    </div>

<% } %>

    </div>

    <h2>Meet Our Pets</h2>

    <div class="pets-container">
        <%
            PetDao petDao = new PetDao();
            List<PetBean> pets = petDao.getAllPets();

            if (pets != null && !pets.isEmpty()) {
                for (PetBean pet : pets) {

                    int imageCount = 10; 
                    String imageName = "default.jpg";

                    if (pet.getPetSpecies() != null) {
                        int randomIndex = (int) (Math.random() * imageCount) + 1;

                        if ("cat".equalsIgnoreCase(pet.getPetSpecies())) {
                            imageName = "cat" + randomIndex + ".jpg";
                        } else if ("dog".equalsIgnoreCase(pet.getPetSpecies())) {
                            imageName = "dog" + randomIndex + ".jpg";
                        }
                    }
        %>

        <div class="pet-card">
            <div class="pet-img-box">
                <img src="images/<%= imageName %>" alt="<%= pet.getPetName() %>">
            </div>

            <div class="pet-info">
                <strong>Name:</strong> <%= pet.getPetName() %><br>
                <strong>Age:</strong> <%= pet.getPetAge() %> years old<br>
                <strong>Breed:</strong> <%= pet.getPetBreed() != null ? pet.getPetBreed() : "Unknown" %><br>
                <a href="PetController?action=viewDetails&petId=<%= pet.getPetId() %>" class="more-details">
                    More Details
                </a>
            </div>

            <%
                boolean loggedIn = (session != null && session.getAttribute("adopter") != null);
            %>

            <% if (loggedIn) { %>
                <form action="ApplicationController" method="get">
                    <input type="hidden" name="action" value="form">
                    <input type="hidden" name="petId" value="<%= pet.getPetId() %>">
                    <button type="submit" class="rehoming-btn">Rehome 🐾</button>
                </form>
            <% } else { %>
                <a href="AdopterLogin.jsp">
                    <button type="button" class="rehoming-btn">Login to Rehome 🐾</button>
                </a>
            <% } %>
        </div>

        <%
                }
            } else {
        %>

        <div class="no-pet">
            Fortunately all the pets have their own home now!
        </div>

        <%
            }
        %>
    </div>
</div>

<div class="footer">
    © 2025 PAWS Pet Adoption Welfare System
</div>

<script>
    const params = new URLSearchParams(window.location.search);

    if (params.get("error") === "petAdopted") {
        alert("Sorry, this pet has already been adopted.");
    }

    if (params.get("error") === "alreadyApplied") {
        alert("You have already submitted an application for this pet.");
    }
</script>


</body>
</html>
