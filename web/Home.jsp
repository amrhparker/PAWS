<%@ page import="dao.PetDao, model.PetBean, model.AdopterBean, model.StaffBean, java.util.List, java.util.Random" %>
<%
    AdopterBean adopter = (AdopterBean) session.getAttribute("adopter");
    StaffBean staff = (StaffBean) session.getAttribute("staff");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PAWS Home Page</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/home.css">

</head>

<body>

<% if (adopter != null) { %>

    <div class="navbar">
        <div class="navbar-left">
            <a href="Home.jsp">
                <img src="pawsA.png" alt="PAWS">
            </a>

            <div class="navbar-links">
                <a href="Home.jsp" class="active">Home</a>
                <a href="AboutUs.jsp">About Us</a>
                <a href="ApplicationController?action=dashboardA">Dashboard</a>
                <a href="Rehome.jsp">Rehome Pet</a>
            </div>
        </div>

        <div class="navbar-profile">
            <a href="AdopterController?action=profile">
                <img src="ProfileIcon.png" alt="Profile" class="profile-icon">
            </a>
            <a href="LogoutServlet" class="logout">LOG OUT</a>
        </div>
    </div>

<% } else if (staff != null) { %>

    <div class="navbar">
        <div class="navbar-left">
            <a href="Home.jsp">
                <img src="pawsS.png" alt="PAWS Staff">
            </a>

            <div class="navbar-links">
                <a href="StaffDashboard.jsp">Dashboard</a>
                <a href="ManagePets.jsp">Pets</a>
                <a href="ManageApplications.jsp">Applications</a>
                <a href="ManageRecords.jsp">Records</a>
                <a href="ReportController">Reports</a>
            </div>
        </div>

        <div class="navbar-right">
            <a href="LogoutServlet" class="logout">Log Out</a>
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
            <a href="Home.jsp" class="active">Home</a>
            <a href="AboutUs.jsp">About Us</a>
            <a href="Rehome.jsp">Rehome</a>
            <a href="AdopterSignin.jsp">Adopter</a>
            <a href="LogInStaff.jsp">Staff</a>
        </div>
    </div>

<% } %>

    <div class="hero">
        <img src="pawsHome.png">
        
        <div class="button-row">
            <a href="AdopterSignin.jsp"><button class="home-btn">Join Our Adopter Community</button></a>
            <a href="Rehome.jsp"><button class="home-btn">Rehome Our Pets</button></a>
            <a href="LogInStaff.jsp"><button class="home-btn">Staff Access</button></a>
        </div>
    </div>

    <div class="section mission-section">
        <div class="line"></div>
    <h2>Our Mission</h2>
    <p>
        PAWS is committed to rescuing animals, providing them with shelter, care, and a second chance.
        Every adoption helps us continue our mission of giving hope, love, and forever homes.
    </p>
    <div class="line"></div>
    </div>

    <div class="section light">
        <h2>Why Adopt From Us?</h2>

        <div class="why-grid">
            <div class="why-card">
                <img src="healthypets.jpg">
                <h3>Healthy & Vaccinated Pets</h3>
                <p>All animals receive proper medical care, vaccinations, and sterilization before adoption.</p>
            </div>

            <div class="why-card">
                <img src="loving.jpg">
                <h3>Trusted & Loving Shelter</h3>
                <p>We ensure each pet is cared for with compassion and comfort while waiting for a new home.</p>
            </div>

            <div class="why-card">
                <img src="easy.jpg">
                <h3>Easy Adoption Process</h3>
                <p>Our step-by-step adoption process is simple, guided, and stress-free.</p>
            </div>
        </div>
    </div>
    
    <div class="section">
    <h2>Our Lovely Pets</h2>

    <div class="pet-grid">
        <%
            PetDao petDao = new PetDao();
            List<PetBean> pets = petDao.getAllPets();
            Random rand = new Random();

            if (pets != null && !pets.isEmpty()) {

                int limit = Math.min(pets.size(), 4); 

                for (int i = 0; i < limit; i++) {
                    PetBean pet = pets.get(i);

                    String imageName = "default.jpg";

                    if (pet.getPetSpecies() != null) {
                        if ("cat".equalsIgnoreCase(pet.getPetSpecies())) {
                            imageName = "cat" + (rand.nextInt(3) + 1) + ".jpg";
                        } else if ("dog".equalsIgnoreCase(pet.getPetSpecies())) {
                            imageName = "dog" + (rand.nextInt(3) + 1) + ".jpg";
                        }
                    }
        %>

        <div class="pet-card">
            <img src="images/<%= imageName %>" alt="<%= pet.getPetName() %>">
            <h4><%= pet.getPetName() %> ~ <%= pet.getPetAge() %> years</h4>
            <p>
                Breed:
                <%= pet.getPetBreed() != null ? pet.getPetBreed() : "Unknown" %>
            </p>
        </div>

        <%
                }
            } else {
        %>

        <p style="text-align:center;">No pets available at the moment.</p>

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
    if (params.get("logout") === "success") {
        alert("Logout Successful");
    }
    </script>

</body>
</html>
