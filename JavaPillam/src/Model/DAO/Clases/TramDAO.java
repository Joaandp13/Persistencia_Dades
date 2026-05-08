package Model.DAO.Clases;

import Model.DAO.DBConnection;
import Model.Objectes.Tram;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TramDAO {

    private Tram mapRow(ResultSet rs) throws SQLException {
        int vc = rs.getInt("id_via_classica"); Integer idVc = rs.wasNull() ? null : vc;
        int ve = rs.getInt("id_via_esportiva"); Integer idVe = rs.wasNull() ? null : ve;
        int vg = rs.getInt("id_via_gel");       Integer idVg = rs.wasNull() ? null : vg;
        return new Tram(
            rs.getInt("id_tram"), idVc, idVe, idVg,
            rs.getInt("num_tram"), rs.getInt("llargada"), rs.getString("grau")
        );
    }

    // ── CREATE ──
    public void inserir(Tram t) throws SQLException {
        String sql = """
                INSERT INTO trams (id_via_classica, id_via_esportiva, id_via_gel, num_tram, llargada, grau)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        if (t.getIdViaClasica()    != null) ps.setInt(1, t.getIdViaClasica());    else ps.setNull(1, Types.INTEGER);
        if (t.getIdViaEsportiva()  != null) ps.setInt(2, t.getIdViaEsportiva());  else ps.setNull(2, Types.INTEGER);
        if (t.getIdViaGel()        != null) ps.setInt(3, t.getIdViaGel());        else ps.setNull(3, Types.INTEGER);
        ps.setInt(4, t.getNumTram());
        ps.setInt(5, t.getLlargada());
        ps.setString(6, t.getGrau());
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) t.setIdTram(keys.getInt(1));
        ps.close();
    }

    // ── READ per via esportiva ──
    public List<Tram> llistarPerViaEsportiva(int idVia) throws SQLException {
        return llistarPerVia("id_via_esportiva", idVia);
    }

    // ── READ per via classica ──
    public List<Tram> llistarPerViaClassica(int idVia) throws SQLException {
        return llistarPerVia("id_via_classica", idVia);
    }

    // ── READ per via gel ──
    public List<Tram> llistarPerViaGel(int idVia) throws SQLException {
        return llistarPerVia("id_via_gel", idVia);
    }

    private List<Tram> llistarPerVia(String columna, int idVia) throws SQLException {
        List<Tram> llista = new ArrayList<>();
        String sql = "SELECT * FROM trams WHERE " + columna + " = ? ORDER BY num_tram";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idVia);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    // ── DELETE ──
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM trams WHERE id_tram = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public void eliminarPerViaEsportiva(int idVia) throws SQLException { eliminarPerVia("id_via_esportiva", idVia); }
    public void eliminarPerViaClassica(int idVia)  throws SQLException { eliminarPerVia("id_via_classica",  idVia); }
    public void eliminarPerViaGel(int idVia)       throws SQLException { eliminarPerVia("id_via_gel",       idVia); }

    private void eliminarPerVia(String columna, int idVia) throws SQLException {
        String sql = "DELETE FROM trams WHERE " + columna + " = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idVia);
        ps.executeUpdate();
        ps.close();
    }
}
