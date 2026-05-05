package Model.DAO.Clases.Via;

import Model.DAO.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ViaDAO<T> {

    // Cada subclasse diu de quina taula és
    protected abstract String getTaula();

    // Cada subclasse sap com convertir una fila en el seu objecte
    protected abstract T mapRow(ResultSet rs) throws SQLException;

    // Cada subclasse sap com fer el INSERT i l'UPDATE
    protected abstract void setInserirParams(PreparedStatement ps, T via) throws SQLException;
    protected abstract void setModificarParams(PreparedStatement ps, T via) throws SQLException;
    protected abstract String getSqlInserir();
    protected abstract String getSqlModificar();
    protected abstract void setIdGenerat(T via, int id);

    // ── CREATE ──
    public void inserir(T via) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(getSqlInserir(), Statement.RETURN_GENERATED_KEYS);
        setInserirParams(ps, via);
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) setIdGenerat(via, keys.getInt(1));
        ps.close();
    }

    // ── READ ONE ──
    public T cercarPerId(int id) throws SQLException {
        String sql = "SELECT * FROM " + getTaula() + " WHERE id_via = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        T via = rs.next() ? mapRow(rs) : null;
        ps.close();
        return via;
    }

    // ── READ ALL ──
    public List<T> llistarTotes() throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() + " ORDER BY nom";
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) llista.add(mapRow(rs));
        st.close();
        return llista;
    }

    // ── READ per sector ──
    public List<T> llistarPerSector(int idSector) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() + " WHERE id_sector = ? ORDER BY nom";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idSector);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    // ── READ per estat ──
    public List<T> llistarPerEstat(String estat) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() + " WHERE estat = ? ORDER BY nom";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, estat);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    // ── READ vies aptes d'una escola ──
    public List<T> llistarAptesPerEscola(int idEscola) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT v.* FROM " + getTaula() + " v " +
                "JOIN sector s ON v.id_sector = s.id_sector " +
                "WHERE s.id_escola = ? AND v.estat = 'apte' ORDER BY v.nom";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idEscola);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    // ── READ per rang de grau ──
    public List<T> llistarPerRangGrau(String grauMin, String grauMax) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() + " WHERE grau >= ? AND grau <= ? ORDER BY grau";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, grauMin);
        ps.setString(2, grauMax);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    // ── READ vies recentment aptes ──
    public List<T> llistarRecentmentAptes() throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() +
                " WHERE estat = 'apte'" +
                "   AND data_fi_no_apte IS NOT NULL" +
                "   AND data_fi_no_apte >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)" +
                " ORDER BY data_fi_no_apte DESC";
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) llista.add(mapRow(rs));
        st.close();
        return llista;
    }

    // ── READ vies més llargues d'una escola ──
    public List<T> llistarMesLlarguesPerEscola(int idEscola) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT v.* FROM " + getTaula() + " v " +
                "JOIN sector s ON v.id_sector = s.id_sector " +
                "WHERE s.id_escola = ? ORDER BY v.llargada_total DESC";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idEscola);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    // ── UPDATE ──
    public void modificar(T via) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(getSqlModificar());
        setModificarParams(ps, via);
        ps.executeUpdate();
        ps.close();
    }

    // ── UPDATE estat ──
    public void actualitzarEstat(int idVia, String nouEstat,
                                 Date dataInici, Date dataFi) throws SQLException {
        String sql = "UPDATE " + getTaula() +
                " SET estat = ?, data_inici_no_apte = ?, data_fi_no_apte = ?" +
                " WHERE id_via = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nouEstat);
        ps.setDate(2, dataInici);
        ps.setDate(3, dataFi);
        ps.setInt(4, idVia);
        ps.executeUpdate();
        ps.close();
    }

    // ── DELETE ──
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM " + getTaula() + " WHERE id_via = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    // ── Utilitat per llegir nullables de MySQL ──
    protected Integer getIntOrNull(ResultSet rs, String col) throws SQLException {
        int val = rs.getInt(col);
        return rs.wasNull() ? null : val;
    }

    protected java.time.LocalDate getDateOrNull(ResultSet rs, String col) throws SQLException {
        Date d = rs.getDate(col);
        return d != null ? d.toLocalDate() : null;
    }
}