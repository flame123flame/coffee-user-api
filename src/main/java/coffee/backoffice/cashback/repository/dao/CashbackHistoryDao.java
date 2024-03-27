package coffee.backoffice.cashback.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.cashback.vo.res.CashbackHistoryRes;

import org.springframework.beans.factory.annotation.Autowired;

import framework.utils.CommonJdbcTemplate;
import framework.utils.ConvertDateUtils;

@Repository
public class CashbackHistoryDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<CashbackHistoryRes> listRebateHistoryRes() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" select rh.*,gl.group_name from cashback_history rh ");
		sqlBuilder.append(" left join (select * from group_level ) gl ");
		sqlBuilder.append(" on gl.group_code = rh.group_code ");
		List<CashbackHistoryRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				rowMapperCashbackHistoryList);
		return dataRes;
	}

	private RowMapper<CashbackHistoryRes> rowMapperCashbackHistoryList = new RowMapper<CashbackHistoryRes>() {
		@Override
		public CashbackHistoryRes mapRow(ResultSet rs, int arg1) throws SQLException {
			CashbackHistoryRes vo = new CashbackHistoryRes();
			vo.setId(rs.getLong("id"));
			vo.setCashbackHistoryCode(rs.getString("cashback_history_code"));
			vo.setCashbackCode(rs.getString("cashback_code"));
			vo.setCashbackTitle(rs.getString("cashback_title"));
			vo.setUsername(rs.getString("username"));
			vo.setGroupCode(rs.getString("group_code"));
			vo.setGroupName(rs.getString("group_name"));
			vo.setIsAutoCashback(rs.getBoolean("is_auto_cashback"));
			vo.setTotalLoss(rs.getBigDecimal("total_loss"));
			vo.setOriginalCashback(rs.getBigDecimal("original_cashback"));
			vo.setActualCashback(rs.getBigDecimal("actual_cashback"));
			vo.setRemark(rs.getString("remark"));
			vo.setStatus(rs.getBoolean("status"));
			vo.setCreatedBy(rs.getString("created_by"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(ConvertDateUtils.formatDateToStringEn(tempCD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			return vo;
		}
	};
}
