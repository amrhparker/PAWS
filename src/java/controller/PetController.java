package controller;

import dao.PetDao;
import model.PetBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
    
    /* ================= VIEW PET DETAILS ================= */
    private void viewPetDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int petId = Integer.parseInt(request.getParameter("petId"));
        PetBean pet = petDao.getPetById(petId);

        request.setAttribute("pet", pet);
        request.getRequestDispatcher("PetDetails.jsp").forward(request, response);
    }


    /* ================= LIST ================= */
    private void listPets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<PetBean> petList = petDao.getAllPets();
        request.setAttribute("petList", petList);
        request.getRequestDispatcher("ManagePets.jsp").forward(request, response);
    }

    /* ================= ADD ================= */
    private void addPet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PetBean pet = extractPetFromRequest(request);
        petDao.addPet(pet);
        response.sendRedirect("PetServlet");
    }

    /* ================= EDIT (LOAD FORM) ================= */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int petId = Integer.parseInt(request.getParameter("petId"));
        PetBean pet = petDao.getPetById(petId);

        request.setAttribute("pet", pet);
        request.getRequestDispatcher("EditPets.jsp").forward(request, response);
    }

    /* ================= UPDATE ================= */
    private void updatePet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PetBean pet = extractPetFromRequest(request);
        pet.setPetId(Integer.parseInt(request.getParameter("petId")));

        petDao.updatePet(pet);
        response.sendRedirect("PetServlet");
    }

    /* ================= DELETE ================= */
    private void deletePet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int petId = Integer.parseInt(request.getParameter("petId"));
        petDao.deletePet(petId);
        response.sendRedirect("PetServlet");
    }

    /* ================= HELPER ================= */
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
