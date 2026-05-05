package Model.DAO.Clases;

import Model.DAO.DBConnection;
import Model.Objectes.Escalador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscaladorDAO {

    // ── Mapeig ResultSet -> Escalador ──────────────────────────────────────
    // Mètode privat reutilitzat per tots els mètodes de lectura
    private Escalador mapRow(ResultSet rs) throws SQLException {
        return new Escalador(
                rs.getInt("id_escalador"),
                rs.getString("nom"),
                rs.getString("alias"),
                rs.getInt("edat"),
                rs.getString("nivell"),
                rs.getString("via_max_nivell"),
                rs.getString("estil_preferit"),
                rs.getString("historial")
        );
    }

    // ── CREATE ─────────────────────────────────────────────────────────────
    public void inserir(Escalador e) throws SQLException {
        String sql = """
                INSERT INTO escalador (nom, alias, edat, nivell, via_max_nivell, estil_preferit, historial)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getNom());
            ps.setString(2, e.getAlias());
            ps.setInt   (3, e.getEdat());
            ps.setString(4, e.getNivell());
            ps.setString(5, e.getViaMaxNivell());
            ps.setString(6, e.getEstilPreferit());
            ps.setString(7, e.getHistorial());
            ps.executeUpdate();

            // Recupera l'id generat per MySQL i l'assigna a l'objecte
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                e.setIdEscalador(keys.getInt(1));
            }
        }
    }

    // ── READ ONE per id ────────────────────────────────────────────────────
    public Escalador cercarPerId(int id) throws SQLException {
        String sql = "SELECT * FROM escalador WHERE id_escalador = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapRow(rs);
            return null; // no trobat
        }
    }

    // ── READ ONE per nom ───────────────────────────────────────────────────
    // Útil per comprovar si el creador d'una via existeix
    public Escalador cercarPerNom(String nom) throws SQLException {
        String sql = "SELECT * FROM escalador WHERE nom = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    // ── READ ALL ───────────────────────────────────────────────────────────
    public List<Escalador> llistarTots() throws SQLException {
        List<Escalador> llista = new ArrayList<>();
        String sql = "SELECT * FROM escalador";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                llista.add(mapRow(rs));
            }
        }
        return llista;
    }

    // ── READ ALL amb mateix nivell màxim ───────────────────────────────────
    // Cas específic de l'enunciat
    public List<Escalador> llistarPerNivell(String nivell) throws SQLException {
        List<Escalador> llista = new ArrayList<>();
        String sql = "SELECT * FROM escalador WHERE nivell = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nivell);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                llista.add(mapRow(rs));
            }
        }
        return llista;
    }

    // ── UPDATE ─────────────────────────────────────────────────────────────
    public void modificar(Escalador e) throws SQLException {
        String sql = """
                UPDATE escalador
                SET nom = ?, alias = ?, edat = ?, nivell = ?,
                    via_max_nivell = ?, estil_preferit = ?, historial = ?
                WHERE id_escalador = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNom());
            ps.setString(2, e.getAlias());
            ps.setInt   (3, e.getEdat());
            ps.setString(4, e.getNivell());
            ps.setString(5, e.getViaMaxNivell());
            ps.setString(6, e.getEstilPreferit());
            ps.setString(7, e.getHistorial());
            ps.setInt   (8, e.getIdEscalador());
            ps.executeUpdate();
        }
    }

    // ── DELETE ─────────────────────────────────────────────────────────────
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM escalador WHERE id_escalador = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}