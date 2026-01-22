package dao;

import model.PetBean;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDao {

    // Create
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

    public List<PetBean> getAllPets() {
        List<PetBean> pets = new ArrayList<>();
        String sql = "SELECT * FROM APP.PET " +
                     "WHERE PET_ADOPTIONSTATUS <> 'Archived' " +
                     "ORDER BY PET_ID";

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
    
    public List<PetBean> getAllPetsStaff() {
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

    // Update
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

    // Update Status
    public void updateAdoptionStatus(int petId, String status) {
        String sql = "UPDATE APP.PET SET PET_ADOPTIONSTATUS=? WHERE PET_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, petId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public String deletePet(int petId) {

        String archiveSql =
            "UPDATE APP.PET SET PET_ADOPTIONSTATUS = 'Archived' WHERE PET_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(archiveSql)) {

            ps.setInt(1, petId);
            ps.executeUpdate();
            return "archived";

        } catch (SQLException e) {
            e.printStackTrace();
        }

    return "error";
}

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
    
    public boolean isPetAdopted(int petId) {
        String sql = "SELECT PET_ADOPTIONSTATUS FROM APP.PET WHERE PET_ID = ?";
        boolean adopted = false;

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                adopted = "Adopted".equalsIgnoreCase(rs.getString("PET_ADOPTIONSTATUS"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adopted;
    }
    
    public boolean hasApplication(int petId) {

    String sql = "SELECT COUNT(*) FROM APP.APPLICATION WHERE PET_ID = ?";
    boolean exists = false;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, petId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            exists = rs.getInt(1) > 0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return exists;
}


}
