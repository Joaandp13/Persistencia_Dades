package Model.DAO.Clases.Via;

import Model.Objectes.ViaEsportiva;
import java.sql.*;

public class ViaEsportivaDAO extends ViaDAO<ViaEsportiva> {

    @Override
    protected String getTaula() { return "via_esportiva"; }

    @Override
    protected ViaEsportiva mapRow(ResultSet rs) throws SQLException {
        ViaEsportiva v = new ViaEsportiva();
        v.setIdVia(rs.getInt("id_via"));
        v.setNom(rs.getString("nom"));
        v.setIdSector(rs.getInt("id_sector"));
        v.setLlargadaTotal(rs.getInt("llargada_total"));
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
               INSERT INTO via_esportiva
                   (nom, id_sector, llargada_total, grau, orientacio, estat,
                    data_inici_no_apte, data_fi_no_apte, ancoratge, tipus_roca, id_creador, restriccions)
               VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
               """;
    }

    @Override
    protected String getSqlModificar() {
        return """
               UPDATE via_esportiva SET
                   nom=?, id_sector=?, llargada_total=?, grau=?, orientacio=?, estat=?,
                   data_inici_no_apte=?, data_fi_no_apte=?,
                   ancoratge=?, tipus_roca=?, id_creador=?, restriccions=?
               WHERE id_via=?
               """;
    }

    @Override
    protected void setInserirParams(PreparedStatement ps, ViaEsportiva v) throws SQLException {
        ps.setString(1, v.getNom());
        ps.setInt(2, v.getIdSector());
        ps.setInt(3, v.getLlargadaTotal());
        ps.setString(4, v.getGrau());
        ps.setString(5, v.getOrientacio());
        ps.setString(6, v.getEstat());
        ps.setDate(7, v.getDataIniciNoApte() != null ? Date.valueOf(v.getDataIniciNoApte()) : null);
        ps.setDate(8, v.getDataFiNoApte()    != null ? Date.valueOf(v.getDataFiNoApte())    : null);
        ps.setString(9, v.getAncoratge());
        ps.setString(10, v.getTipusRoca());
        if (v.getIdCreador() != null) ps.setInt(11, v.getIdCreador());
        else ps.setNull(11, Types.INTEGER);
        ps.setString(12, v.getRestriccions());
    }

    @Override
    protected void setModificarParams(PreparedStatement ps, ViaEsportiva v) throws SQLException {
        setInserirParams(ps, v);      // els primers 12 paràmetres són iguals
        ps.setInt(13, v.getIdVia()); // afegim el WHERE
    }

    @Override
    protected void setIdGenerat(ViaEsportiva v, int id) { v.setIdVia(id); }
}