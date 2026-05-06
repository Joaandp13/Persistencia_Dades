package Model.DAO.Clases;

import Model.DAO.DBConnection;
import Model.Objectes.Escola;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscolaDAO {

    private Escola mapRow(ResultSet rs) throws SQLException {
        return new Escola(
                rs.getInt("id_escola"),
                rs.getString("nom"),
                rs.getString("poblacio"),
                rs.getString("aproximacio"),
                rs.getString("popularitat")
        );
    }

    // ── CREATE ──
    public void inserir(Escola e) throws SQLException {
        String sql = """
                INSERT INTO escola (nom, poblacio, aproximacio, popularitat)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getPoblacio());
            ps.setString(3, e.getAproximacio());
            ps.setString(4, e.getPopularitat());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) e.setIdEscola(keys.getInt(1));
        }
    }

    // ── READ ONE per id ──
    public Escola cercarPerId(int id) throws SQLException {
        String sql = "SELECT * FROM escola WHERE id_escola = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    // ── READ ONE per nom ──
    public Escola cercarPerNom(String nom) throws SQLException {
        String sql = "SELECT * FROM escola WHERE nom = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    // ── READ ALL ──
    public List<Escola> llistarTotes() throws SQLException {
        List<Escola> llista = new ArrayList<>();
        String sql = "SELECT * FROM escola ORDER BY nom";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) llista.add(mapRow(rs));
        }
        return llista;
    }

    // ── Compta el total de vies de totes les taules d'una escola ──
    public int comptarVies(int idEscola) throws SQLException {
        String sql = """
            SELECT
              (SELECT COUNT(*) FROM via_esportiva ve JOIN sector s ON ve.id_sector = s.id_sector WHERE s.id_escola = ?)
            + (SELECT COUNT(*) FROM via_classica  vc JOIN sector s ON vc.id_sector = s.id_sector WHERE s.id_escola = ?)
            + (SELECT COUNT(*) FROM via_gel        vg JOIN sector s ON vg.id_sector = s.id_sector WHERE s.id_escola = ?)
            AS total
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEscola);
            ps.setInt(2, idEscola);
            ps.setInt(3, idEscola);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");
            return 0;
        }
    }

    // ── Escoles que tenen algun sector amb restriccions ──
    public List<Escola> llistarAmbRestriccions() throws SQLException {
        List<Escola> llista = new ArrayList<>();
        String sql = """
            SELECT DISTINCT e.* FROM escola e
            JOIN sector s ON s.id_escola = e.id_escola
            WHERE s.restriccions IS NOT NULL AND s.restriccions <> ''
            ORDER BY e.nom
            """;
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) llista.add(mapRow(rs));
        }
        return llista;
    }

    // ── UPDATE ──
    public void modificar(Escola e) throws SQLException {
        String sql = """
                UPDATE escola SET nom = ?, poblacio = ?, aproximacio = ?, popularitat = ?
                WHERE id_escola = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getPoblacio());
            ps.setString(3, e.getAproximacio());
            ps.setString(4, e.getPopularitat());
            ps.setInt(5, e.getIdEscola());
            ps.executeUpdate();
        }
    }

    // ── DELETE ──
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM escola WHERE id_escola = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
