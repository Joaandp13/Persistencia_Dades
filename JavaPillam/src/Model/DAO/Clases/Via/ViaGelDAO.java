package Model.DAO.Clases.Via;

import Model.DAO.DBConnection;
import Model.Objectes.ViaGel;
import java.sql.*;

public class ViaGelDAO extends ViaDAO<ViaGel> {

    @Override
    protected String getTaula() { return "via_gel"; }

    @Override
    protected ViaGel mapRow(ResultSet rs) throws SQLException {
        ViaGel v = new ViaGel();
        v.setIdVia(rs.getInt("id_via"));
        v.setNom(rs.getString("nom"));
        v.setIdSector(rs.getInt("id_sector"));
        v.setLlargadaTotal(getIntOrNull(rs, "llargada_total"));
        v.setGrau(rs.getString("grau"));
        v.setOrientacio(rs.getString("orientacio"));
        v.setDataIniciNoApte(getDateOrNull(rs, "data_inici_no_apte"));
        v.setDataFiNoApte(getDateOrNull(rs, "data_fi_no_apte"));
        v.setEstat(rs.getString("estat"));
        v.setAncoratge(rs.getString("ancoratge"));
        v.setTipusRoca(rs.getString("tipus_roca"));
        v.setIdCreador(getIntOrNull(rs, "id_creador"));
        v.setRestriccions(rs.getString("restriccions"));
        return v;
    }

    @Override
    protected String getSqlInserir() {
        return """
               INSERT INTO via_gel
                   (nom, id_sector, grau, orientacio, estat,
                    data_inici_no_apte, data_fi_no_apte, ancoratge, tipus_roca, id_creador, restriccions)
               VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
               """;
    }

    @Override
    protected String getSqlModificar() {
        return """
               UPDATE via_gel SET
                   nom=?, id_sector=?, grau=?, orientacio=?, estat=?,
                   data_inici_no_apte=?, data_fi_no_apte=?,
                   ancoratge=?, tipus_roca=?, id_creador=?, restriccions=?
               WHERE id_via=?
               """;
    }

    @Override
    protected void setInserirParams(PreparedStatement ps, ViaGel v) throws SQLException {
        ps.setString(1, v.getNom());
        ps.setInt(2, v.getIdSector());
        ps.setString(3, v.getGrau());
        ps.setString(4, v.getOrientacio());
        ps.setString(5, v.getEstat());
        ps.setDate(6, v.getDataIniciNoApte() != null ? Date.valueOf(v.getDataIniciNoApte()) : null);
        ps.setDate(7, v.getDataFiNoApte()    != null ? Date.valueOf(v.getDataFiNoApte())    : null);
        ps.setString(8, v.getAncoratge());
        ps.setString(9, v.getTipusRoca());
        if (v.getIdCreador() != null) ps.setInt(10, v.getIdCreador());
        else ps.setNull(10, Types.INTEGER);
        ps.setString(11, v.getRestriccions());
    }

    @Override
    protected void setModificarParams(PreparedStatement ps, ViaGel v) throws SQLException {
        setInserirParams(ps, v);
        ps.setInt(12, v.getIdVia());
    }

    @Override
    protected void setIdGenerat(ViaGel v, int id) { v.setIdVia(id); }

    // ── Específic de gel: recalcular llargada sumant trams ──
    public void actualitzarLlargadaTotal(int idVia) throws SQLException {
        String sql = """
                UPDATE via_gel SET llargada_total = (
                    SELECT SUM(llargada) FROM trams WHERE id_via_gel = ?
                ) WHERE id_via = ?
                """;
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idVia);
        ps.setInt(2, idVia);
        ps.executeUpdate();
        ps.close();
    }
}