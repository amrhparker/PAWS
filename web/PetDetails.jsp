<%-- 
    Document   : PetDetails
    Created on : Jan 5, 2026, 7:23:05 PM
    Author     : amira
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Pet Details</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background: #ffffff;
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

            .profile-container {
                display: flex;
                flex-direction: column;
                align-items: center;
                position: relative;
                top: 30px;
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

            .menu-links a,
            .nav-right a {
                text-decoration: none;
                font-weight: bold;
                margin-right: 20px;
                color: black;
            }

            .details-container {
                display: flex;
                align-items: flex-start;
                gap: 40px;
                padding: 50px 50px;
            }

            .pet-image-box {
                text-align: center;
            }

            .pet-image-frame {
                border: 3px solid black;
                padding: 10px;
                border-radius: 15px;
                display: inline-block;
            }

            .pet-image-frame img {
                width: 230px;
                height: 300px;
                object-fit: cover;
                border-radius: 10px;
            }

            .pet-name {
                font-size: 20px;
                font-weight: bold;
                margin-top: 10px;
            }

            .pet-details-box {
                background: #f2e4cf;
                width: 65%;
                padding: 35px;
                border-radius: 20px;
                font-size: 16px;
            }

            .pet-details-box p {
                margin: 8px 0;
            }

            .description-title {
                margin-top: 25px;
                font-size: 20px;
                font-weight: bold;
            }

            .rehoming-section {
                display: flex;
                justify-content: flex-end;
                padding: 30px 60px 60px;
            }

            .rehome-btn {
                padding: 12px 35px;
                border-radius: 20px;
                border: none;
                cursor: pointer;
                font-size: 18px;
                font-weight: bold;
                background-color: #ff66c4;
                color: white;
            }
        
            .rehome-btn:hover {
                background-color: #e055b3;
            }
        
            .footer {
                text-align: center;
                padding: 20px;
                border-top: 1px solid #ccc;
                margin-top: 20px;
            }
        
            @media (max-width: 768px) {
                .details-container {
                    flex-direction: column;
                    align-items: center;
                    padding: 20px;
                }
            
                .pet-details-box {
                    width: 90%;
                }
            
                .navbar-right {
                    flex-wrap: wrap;
                }
            
                .rehoming-section {
                    justify-content: center;
                    padding: 20px;
                }
            }
        </style>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <div class="navbar">
            <div class="navbar-left">
                <a href="Home.html">
                    <img src="pawsA.png" alt="PAWS Logo">
                </a>
            </div>

            <div class="navbar-right">
                <a href="Home.html">Home</a>
                <a href="AboutUs.html">About</a>
                <a href="AdopterController?action=dashboard">Dashboard</a>
                <a href="Rehome.jsp">Rehome Pet</a>
            </div>
        </div>

        <div class="details-container">
            <div class="pet-image-box">
                <div class="pet-image-frame">
                    <img src="cat1.jpg" alt="Pet Image">
                </div>
                <div class="pet-name">${pet.petName}</div>
            </div>

                <div class="pet-details-box">
                    <p><strong>Age:</strong> ${pet.petAge} years old</p>
                    <p><strong>Breed:</strong> ${pet.petBreed}</p>
                    <p><strong>Gender:</strong> ${pet.petGender}</p>
                    <p><strong>Species:</strong> ${pet.petSpecies}</p>
                    <p><strong>Health Status:</strong> ${pet.petHealthStatus}</p>
                    <p><strong>Adoption Status:</strong> ${pet.petAdoptionStatus}</p>
                </div>
        </div>

        <div class="rehoming-section">
            <c:if test="${not empty pet.petId}">
                <form action="AdopterController" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="applyForAdoption">
                    <input type="hidden" name="petId" value="${pet.petId}">
                    <button type="submit" class="rehome-btn">Apply for Adoption</button>
                </form>
            </c:if>

            <!-- Show message if pet data is not loaded -->
            <c:if test="${empty pet}">
                <p>Pet information not available.</p>
            </c:if>
        </div>

        <div class="footer">
            © 2025 PAWS Pet Adoption Welfare System — All Rights Reserved
        </div>

    </body>
</html>