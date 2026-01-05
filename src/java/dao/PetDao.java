package dao;

import model.PetBean;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDao {

    /* ================= CREATE ================= */
    public void addPet(PetBean pet) {

        String sql = "INSERT INTO APP.PET " +
                "(PET_NAME, PET_DESC, PET_SPECIES, PET_GENDER, PET_BREED, PET_AGE, PET_HEALTHSTATUS, PET_ADOPTIONSTATUS) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pet.getPetName());
            ps.setString(2, pet.getPetDesc());
            ps.setString(3, pet.getPetSpecies());
            ps.setString(4, pet.getPetGender());
            ps.setString(5, pet.getPetBreed());
            ps.setInt(6, pet.getPetAge());
            ps.setString(7, pet.getPetHealthStatus());
            ps.setString(8, pet.getPetAdoptionStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ================= READ ALL ================= */
    public List<PetBean> getAllPets() {

        List<PetBean> pets = new ArrayList<>();
        String sql = "SELECT * FROM APP.PET ORDER BY PET_ID";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pets.add(mapRowToPet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pets;
    }

    /* ================= READ ONE ================= */
    public PetBean getPetById(int petId) {

        String sql = "SELECT * FROM APP.PET WHERE PET_ID = ?";
        PetBean pet = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pet = mapRowToPet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pet;
    }

    /* ================= UPDATE ================= */
    public void updatePet(PetBean pet) {

        String sql = "UPDATE APP.PET SET " +
                "PET_NAME=?, PET_DESC=?, PET_SPECIES=?, PET_GENDER=?, " +
                "PET_BREED=?, PET_AGE=?, PET_HEALTHSTATUS=?, PET_ADOPTIONSTATUS=? " +
                "WHERE PET_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pet.getPetName());
            ps.setString(2, pet.getPetDesc());
            ps.setString(3, pet.getPetSpecies());
            ps.setString(4, pet.getPetGender());
            ps.setString(5, pet.getPetBreed());
            ps.setInt(6, pet.getPetAge());
            ps.setString(7, pet.getPetHealthStatus());
            ps.setString(8, pet.getPetAdoptionStatus());
            ps.setInt(9, pet.getPetId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ================= DELETE ================= */
    public void deletePet(int petId) {

        String sql = "DELETE FROM APP.PET WHERE PET_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ================= MAPPER ================= */
    private PetBean mapRowToPet(ResultSet rs) throws SQLException {

        PetBean pet = new PetBean();
        pet.setPetId(rs.getInt("PET_ID"));
        pet.setPetName(rs.getString("PET_NAME"));
        pet.setPetDesc(rs.getString("PET_DESC"));
        pet.setPetSpecies(rs.getString("PET_SPECIES"));
        pet.setPetGender(rs.getString("PET_GENDER"));
        pet.setPetBreed(rs.getString("PET_BREED"));
        pet.setPetAge(rs.getInt("PET_AGE"));
        pet.setPetHealthStatus(rs.getString("PET_HEALTHSTATUS"));
        pet.setPetAdoptionStatus(rs.getString("PET_ADOPTIONSTATUS"));

        return pet;
    }
}
