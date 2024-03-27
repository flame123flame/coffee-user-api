package coffee.backoffice.affiliate.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.affiliate.vo.res.AffiliateChannelRes;
import framework.utils.CommonJdbcTemplate;
import framework.utils.ConvertDateUtils;

@Repository
public class AffiliateChannelDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public AffiliateChannelRes findAffiliateChannelByCode(String code) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("");
		sqlBuilder.append("");
		sqlBuilder.append("");
		sqlBuilder.append("");
		sqlBuilder.append("");
		sqlBuilder.append("");
		sqlBuilder.append("");
		sqlBuilder.append("");
		sqlBuilder.append("");
		sqlBuilder.append("");
		sqlBuilder.append("");
		AffiliateChannelRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new Object[] { code }, rowMapperAffiliateChannel);
		return dataRes;
	}
	public RowMapper<AffiliateChannelRes> rowMapperAffiliateChannel = new RowMapper<AffiliateChannelRes>() {
		@Override
		public AffiliateChannelRes mapRow(ResultSet rs,int arg1) throws SQLException {
			AffiliateChannelRes vo = new AffiliateChannelRes();
			vo.setId(rs.getLong("id"));
			return vo;
		}
	};

	public List<AffiliateChannelRes> listFindAffiliateChannelByCode(String code) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" select * from affiliate_channel ac");
		sqlBuilder.append(" where affiliate_group_code = ?");
		params.add(code);
		sqlBuilder.append(" and enable = 1 ");
//		sqlBuilder.append("");
//		sqlBuilder.append("");
//		sqlBuilder.append("");
//		sqlBuilder.append("");
//		sqlBuilder.append("");
//		sqlBuilder.append("");
//		sqlBuilder.append("");
//		sqlBuilder.append("");
		List<AffiliateChannelRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				rowMapperAffiliateChannelList);
		return dataRes;
	}

	private RowMapper<AffiliateChannelRes> rowMapperAffiliateChannelList = new RowMapper<AffiliateChannelRes>() {
		@Override
		public AffiliateChannelRes mapRow(ResultSet rs, int arg1) throws SQLException {
			AffiliateChannelRes vo = new AffiliateChannelRes();
			vo.setId(rs.getLong("id"));
			vo.setAffiliateChannelCode(rs.getString("affiliate_channel_code"));
			vo.setAffiliateGroupCode(rs.getString("affiliate_group_code"));
			vo.setChannelName(rs.getString("channel_name"));
			vo.setProductTypeCode(rs.getString("product_type_code"));
			vo.setShareRateOne(rs.getBigDecimal("share_rate_one"));
			vo.setShareRateTwo(rs.getBigDecimal("share_rate_two"));
			vo.setRemark(rs.getString("remark"));
			vo.setCreatedBy(rs.getString("created_by"));
			vo.setCreatedDate(
					ConvertDateUtils.formatDateToStringEn(rs.getDate("created_date"), ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setUpdatedBy(rs.getString("updated_by"));
			vo.setUpdatedDate(
					ConvertDateUtils.formatDateToStringEn(rs.getDate("updated_date"), ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setEnable(rs.getBoolean("enable"));
			return vo;
		}
	};
	
	
	

}
