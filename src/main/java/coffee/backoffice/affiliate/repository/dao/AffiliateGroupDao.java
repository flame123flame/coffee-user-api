package coffee.backoffice.affiliate.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.affiliate.vo.res.AffiliateGroupRes;
import framework.utils.CommonJdbcTemplate;
import framework.utils.ConvertDateUtils;

@Repository
public class AffiliateGroupDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public AffiliateGroupRes findAffiliateGroupByCode(String code) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select * from ");
		sqlBuilder.append(" ");
		sqlBuilder.append(" ");
		sqlBuilder.append(" ");
		sqlBuilder.append(" ");
		sqlBuilder.append(" ");
		sqlBuilder.append(" ");
		sqlBuilder.append(" ");
		sqlBuilder.append(" ");
		sqlBuilder.append(" ");
		sqlBuilder.append(" ");
		AffiliateGroupRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new Object[] { code }, rowMapperAffiliateGroup);
		return dataRes;
	}

	public RowMapper<AffiliateGroupRes> rowMapperAffiliateGroup = new RowMapper<AffiliateGroupRes>() {
		@Override
		public AffiliateGroupRes mapRow(ResultSet rs, int arg1) throws SQLException {
			AffiliateGroupRes vo = new AffiliateGroupRes();
			vo.setId(rs.getLong("id"));
			return vo;
		}
	};

	public List<AffiliateGroupRes> listFindAffiliateGroup() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" select * from affiliate_group ag ");
		sqlBuilder.append(" where enable = 1 ");
//		sqlBuilder.append(" ");
//		sqlBuilder.append(" ");
//		sqlBuilder.append(" ");
//		sqlBuilder.append(" ");
		List<AffiliateGroupRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				rowMapperAffiliateGroupList);
		return dataRes;
	}

	private RowMapper<AffiliateGroupRes> rowMapperAffiliateGroupList = new RowMapper<AffiliateGroupRes>() {
		@Override
		public AffiliateGroupRes mapRow(ResultSet rs, int arg1) throws SQLException {
			AffiliateGroupRes vo = new AffiliateGroupRes();
			vo.setId(rs.getLong("id"));
			vo.setAffiliateGroupCode(rs.getString("affiliate_group_code"));
			vo.setGroupName(rs.getString("group_name"));
			vo.setDescription(rs.getString("description"));
			vo.setWithdrawCondition(rs.getString("withdraw_condition"));
			vo.setMinTotalBets(rs.getBigDecimal("min_total_bets"));
			vo.setMinAffiliateCount(rs.getBigDecimal("min_affiliate_count"));
			vo.setMinTotalIncome(rs.getBigDecimal("min_total_income"));
			vo.setCreatedDate(
					ConvertDateUtils.formatDateToStringEn(rs.getDate("created_date"), ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setCreatedBy(rs.getString("created_by"));
			vo.setUpdatedDate(
					ConvertDateUtils.formatDateToStringEn(rs.getDate("updated_date"), ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setUpdatedBy(rs.getString("updated_by"));
			vo.setEnable(rs.getBoolean("enable"));
			return vo;
		}
	};
}
