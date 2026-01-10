<%-- 
    Document   : Rehome
    Created on : Jan 10, 2026, 1:45:15 PM
    Author     : amira
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.PetDao, model.PetBean, java.util.List" %>

<%
 session = request.getSession(false);
if (session == null || session.getAttribute("adopter") == null) {
    response.sendRedirect("AdopterLogin.jsp");
    return;
}
%>

<!DOCTYPE html>
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

            .no-pet{
                background: #f2dfd1;
                padding: 15px;
                border-radius: 15px;
                margin-top: 10px;
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
                transition: all 0.3s;
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
            <div class="navbar">
                <div class="navbar-left">
                    <a href="Home.html">
                        <img src="PAWS.png" alt="PAWS Logo">
                    </a>
                </div>

                <div class="navbar-right">
                    <a href="Home.html">Home</a>
                    <a href="AboutUs.jsp">About</a>
                    <a href="DashboardA.jsp">Dashboard</a>
                    <a href="Rehome.jsp">Rehome Pet</a>
                </div>
            </div>

            <h2>Meet Our Pets</h2>

            <div class="pets-container">
                <%
                    PetDao petDao = new PetDao();
                    List<PetBean> pets = petDao.getAllPets();

                    if (pets != null && !pets.isEmpty()) {
                        for (PetBean pet : pets) {
                            // Determine image based on species and ID
                            String imageName = "default.jpg";
                            if (pet.getPetSpecies() != null) {
                                if ("cat".equalsIgnoreCase(pet.getPetSpecies())) {
                                    imageName = "cat" + (pet.getPetId() % 2 + 1) + ".jpg";
                                } else if ("dog".equalsIgnoreCase(pet.getPetSpecies())) {
                                    imageName = "dog" + (pet.getPetId() % 2 + 1) + ".jpg";
                                }
                            }
                %>

                <div class="pet-card">
                    <div class="pet-img-box">
                        <!-- UNCOMMENT THIS LINE TO SHOW IMAGES -->
                        <img src="images/<%= imageName%>" alt="<%= pet.getPetName()%>">
                    </div>
                    <div class="pet-info">
                        <strong>Name:</strong> <%= pet.getPetName()%><br>
                        <strong>Age:</strong> <%= pet.getPetAge()%> years old<br>
                        <strong>Breed:</strong> <%= pet.getPetBreed() != null ? pet.getPetBreed() : "Unknown"%><br>
                        <a href="PetServlet?action=viewDetails&petId=<%= pet.getPetId()%>" class="more-details">More Details</a>
                    </div>
                    <a href="ApplicationController?action=form&petId=<%= pet.getPetId() %>">
                        <button class="rehoming-btn">Rehome 🐾</button>
                    </a>
                </div>

                <%
                    }
                } else {
                    // Show static pet cards if no database pets (for testing)
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
            © 2025 PAWS Pet Adoption Welfare System — All Rights Reserved
        </div>

    </body>
</html>