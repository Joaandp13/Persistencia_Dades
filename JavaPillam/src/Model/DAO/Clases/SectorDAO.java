package Model.DAO.Clases;

import Model.DAO.DBConnection;
import Model.Objectes.Sector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectorDAO {

    private Sector mapRow(ResultSet rs) throws SQLException {
        Sector s = new Sector();
        s.setIdSector(rs.getInt("id_sector"));
        s.setNom(rs.getString("nom"));
        s.setIdEscola(rs.getInt("id_escola"));
        s.setLatitud(rs.getDouble("latitud"));
        s.setLongitud(rs.getDouble("longitud"));
        s.setAproximacio(rs.getString("aproximacio"));
        s.setPopularitat(rs.getString("popularitat"));
        s.setRestriccions(rs.getString("restriccions"));
        s.setTipusVies(rs.getString("tipus_vies"));
        return s;
    }

    // ── CREATE ──
    public void inserir(Sector s) throws SQLException {
        String sql = """
                INSERT INTO sector (nom, id_escola, latitud, longitud, aproximacio, popularitat, restriccions, tipus_vies)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getNom());
            ps.setInt(2, s.getIdEscola());
            ps.setDouble(3, s.getLatitud());
            ps.setDouble(4, s.getLongitud());
            ps.setString(5, s.getAproximacio());
            ps.setString(6, s.getPopularitat());
            ps.setString(7, s.getRestriccions());
            ps.setString(8, s.getTipusVies());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) s.setIdSector(keys.getInt(1));
        }
    }

    // ── READ ONE ──
    public Sector cercarPerId(int id) throws SQLException {
        String sql = "SELECT * FROM sector WHERE id_sector = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    // ── READ ALL ──
    public List<Sector> llistarTots() throws SQLException {
        List<Sector> llista = new ArrayList<>();
        String sql = "SELECT * FROM sector ORDER BY nom";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) llista.add(mapRow(rs));
        }
        return llista;
    }

    // ── READ per escola ──
    public List<Sector> llistarPerEscola(int idEscola) throws SQLException {
        List<Sector> llista = new ArrayList<>();
        String sql = "SELECT * FROM sector WHERE id_escola = ? ORDER BY nom";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEscola);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) llista.add(mapRow(rs));
        }
        return llista;
    }

    // ── READ sectors amb més de X vies disponibles ──
    public List<Sector> llistarAmbMesDeXVies(int x) throws SQLException {
        List<Sector> llista = new ArrayList<>();
        String sql = """
                SELECT s.* FROM sector s
                WHERE (
                    (SELECT COUNT(*) FROM via_esportiva ve WHERE ve.id_sector = s.id_sector AND ve.estat = 'apte')
                  + (SELECT COUNT(*) FROM via_classica  vc WHERE vc.id_sector = s.id_sector AND vc.estat = 'apte')
                  + (SELECT COUNT(*) FROM via_gel        vg WHERE vg.id_sector = s.id_sector AND vg.estat = 'apte')
                ) > ?
                ORDER BY s.nom
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, x);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) llista.add(mapRow(rs));
        }
        return llista;
    }

    // ── UPDATE ──
    public void modificar(Sector s) throws SQLException {
        String sql = """
                UPDATE sector SET nom=?, id_escola=?, latitud=?, longitud=?,
                    aproximacio=?, popularitat=?, restriccions=?, tipus_vies=?
                WHERE id_sector=?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNom());
            ps.setInt(2, s.getIdEscola());
            ps.setDouble(3, s.getLatitud());
            ps.setDouble(4, s.getLongitud());
            ps.setString(5, s.getAproximacio());
            ps.setString(6, s.getPopularitat());
            ps.setString(7, s.getRestriccions());
            ps.setString(8, s.getTipusVies());
            ps.setInt(9, s.getIdSector());
            ps.executeUpdate();
        }
    }

    // ── DELETE ──
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM sector WHERE id_sector = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
