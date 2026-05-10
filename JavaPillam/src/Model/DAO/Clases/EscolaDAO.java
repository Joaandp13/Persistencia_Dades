// Defineix el paquet per a la persistència de dades de l'Escola.
package Model.DAO.Clases;

import Model.DAO.DBConnection;
import Model.Objectes.Escola;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EscolaDAO: Gestiona el cicle de vida de les dades de les Escoles d'escalada.
 * A part del CRUD bàsic, inclou lògica per comptabilitzar vies i filtrar per restriccions.
 */
public class EscolaDAO {

    /**
     * Mètode privat per convertir un registre de la base de dades en un objecte Escola.
     */
    private Escola mapRow(ResultSet rs) throws SQLException {
        return new Escola(
                rs.getInt("id_escola"),
                rs.getString("nom"),
                rs.getString("poblacio"),
                rs.getString("aproximacio"),
                rs.getString("popularitat")
        );
    }

    // ── CREATE (Inserir) ──
    /**
     * Guarda una nova escola. Recupera l'ID generat pel motor de la BDD
     * i l'assigna a l'objecte Java.
     */
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

            // Obtenim l'ID autoincremental i el guardem al model.
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) e.setIdEscola(keys.getInt(1));
        }
    }

    // ── READ (Consultes) ──

    /** Cerca una escola pel seu ID (Clau Primària). */
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

    /** Cerca una escola pel seu nom (útil per a cercadors o validacions de duplicats). */
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

    /** Retorna totes les escoles ordenades alfabèticament. */
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

    // ── CONSULTES AVANÇADES ──

    /**
     * Calcula el número total de vies d'una escola sumant les esportives, 
     * clàssiques i de gel de tots els seus sectors.
     */
    public int comptarVies(int idEscola) throws SQLException {
        // Fem tres subconsultes que uneixen (JOIN) les vies amb els sectors de l'escola indicada.
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

    /**
     * Retorna les escoles que tenen sectors amb algun tipus de restricció activa.
     * Útil per a avisos de seguretat o protecció del medi ambient.
     */
    public List<Escola> llistarAmbRestriccions() throws SQLException {
        List<Escola> llista = new ArrayList<>();
        // SELECT DISTINCT per evitar que una escola surti repetida si té diversos sectors restringits.
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

    // ── UPDATE (Modificació) ──
    /**
     * Actualitza les dades d'una escola existent.
     */
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

    // ── DELETE (Eliminació) ──
    /**
     * Elimina una escola pel seu ID. 
     * Nota: Pot fallar si hi ha sectors associats (per restriccions d'integritat referencial).
     */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM escola WHERE id_escola = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
