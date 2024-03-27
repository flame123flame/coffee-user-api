package coffee.backoffice.rebate.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import coffee.backoffice.promotion.vo.res.PromotionDatatableRes;
import coffee.backoffice.rebate.vo.res.RebateHistoryDatatableRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.DatatableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.rebate.vo.res.RebateHistoryRes;
import framework.utils.CommonJdbcTemplate;
import framework.utils.ConvertDateUtils;

@Repository
public class RebateHistoryDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<RebateHistoryRes> listRebateHistoryRes() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" select rh.*,gl.group_name from rebate_history rh ");
		sqlBuilder.append(" left join (select * from group_level ) gl ");
		sqlBuilder.append(" on gl.group_code = rh.group_code ");
		List<RebateHistoryRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				rowMapperRebateHistoryList);
		return dataRes;
	}

	private RowMapper<RebateHistoryRes> rowMapperRebateHistoryList = new RowMapper<RebateHistoryRes>() {
		@Override
		public RebateHistoryRes mapRow(ResultSet rs, int arg1) throws SQLException {
			RebateHistoryRes vo = new RebateHistoryRes();
			vo.setId(rs.getLong("id"));
			vo.setRebateHistoryCode(rs.getString("rebate_history_code"));
			vo.setRebateCode(rs.getString("rebate_code"));
			vo.setRebateTitle(rs.getString("rebate_title"));
			vo.setUsername(rs.getString("username"));
			vo.setGroupCode(rs.getString("group_code"));
			vo.setGroupName(rs.getString("group_name"));
			vo.setIsAutoRebate(rs.getBoolean("is_auto_rebate"));
			vo.setValidBets(rs.getBigDecimal("valid_bets"));
			vo.setOriginalRebate(rs.getBigDecimal("original_rebate"));
			vo.setActualRebate(rs.getBigDecimal("actual_rebate"));
			vo.setRemark(rs.getString("remark"));
			vo.setStatus(rs.getBoolean("status"));
			vo.setCreatedBy(rs.getString("created_by"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(ConvertDateUtils.formatDateToStringEn(tempCD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			return vo;
		}
	};

	public DataTableResponse<RebateHistoryDatatableRes> paginate(DatatableRequest req) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT rh.* from rebate_history rh");
		DataTableResponse<RebateHistoryDatatableRes> dataTable = new DataTableResponse<RebateHistoryDatatableRes>();
		String sqlCount = DatatableUtils.countForDatatable(stringBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(stringBuilder.toString(), req.getPage(), req.getLength(), req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<RebateHistoryDatatableRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(), BeanPropertyRowMapper.newInstance(RebateHistoryDatatableRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}
}
