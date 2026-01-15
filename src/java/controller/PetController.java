package controller;

import dao.PetDao;
import model.PetBean;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class PetController extends HttpServlet {

    private PetDao petDao;

    @Override
    public void init() {
        petDao = new PetDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "viewDetails":
                viewPetDetails(request, response);
                break;

            case "edit":
                showEditForm(request, response);
                break;

            case "delete":
                deletePet(request, response);
                break;

            default:
                listPets(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addPet(request, response);
                break;

            case "edit":
                updatePet(request, response);
                break;

            default:
                response.sendRedirect("ManagePets.jsp");
                break;
        }
    }
    
    private void viewPetDetails(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    int petId = Integer.parseInt(request.getParameter("petId"));
    PetBean pet = petDao.getPetById(petId);

    String petImage = generateRandomPetImage(pet);

    request.setAttribute("pet", pet);
    request.setAttribute("petImage", petImage);
    request.getRequestDispatcher("PetDetails.jsp").forward(request, response);
}

private String generateRandomPetImage(PetBean pet) {

    int imageCount = 10;
    String image = "default.jpg";

    if (pet != null && pet.getPetSpecies() != null) {
        int randomIndex = (int) (Math.random() * imageCount) + 1;

        if ("cat".equalsIgnoreCase(pet.getPetSpecies())) {
            image = "cat" + randomIndex + ".jpg";
        } else if ("dog".equalsIgnoreCase(pet.getPetSpecies())) {
            image = "dog" + randomIndex + ".jpg";
        }
    }
    return image;
}

    // Display
    private void listPets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<PetBean> petList = petDao.getAllPets();
        request.setAttribute("petList", petList);
        request.getRequestDispatcher("ManagePets.jsp").forward(request, response);
    }

    // Create
    private void addPet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PetBean pet = extractPetFromRequest(request);
        petDao.addPet(pet);
        response.sendRedirect("PetController");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int petId = Integer.parseInt(request.getParameter("petId"));
        PetBean pet = petDao.getPetById(petId);

        request.setAttribute("pet", pet);
        request.getRequestDispatcher("EditPets.jsp").forward(request, response);
    }

    // Update
    private void updatePet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PetBean pet = extractPetFromRequest(request);
        pet.setPetId(Integer.parseInt(request.getParameter("petId")));

        petDao.updatePet(pet);
        response.sendRedirect("PetController");
    }

    // Delete
    private void deletePet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int petId = Integer.parseInt(request.getParameter("petId"));
        petDao.deletePet(petId);
        response.sendRedirect("PetController");
    }

    private PetBean extractPetFromRequest(HttpServletRequest request) {

        PetBean pet = new PetBean();
        pet.setPetName(request.getParameter("petName"));
        pet.setPetDesc(request.getParameter("petDesc"));
        pet.setPetSpecies(request.getParameter("petSpecies"));
        pet.setPetGender(request.getParameter("petGender"));
        pet.setPetBreed(request.getParameter("petBreed"));
        pet.setPetAge(Integer.parseInt(request.getParameter("petAge")));
        pet.setPetHealthStatus(request.getParameter("petHealthStatus"));
        pet.setPetAdoptionStatus(request.getParameter("petAdoptionStatus"));

        return pet;
    }
}
