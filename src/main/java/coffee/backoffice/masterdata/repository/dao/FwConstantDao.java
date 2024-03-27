package coffee.backoffice.masterdata.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.masterdata.vo.res.ConstantRes;

@Repository
public class FwConstantDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public List<ConstantRes> findAllByConstantKey(String constantKey){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sqlBuilder = new StringBuilder(" select * from  fw_constant ");
		if(StringUtils.isNotEmpty(constantKey)) {
			sqlBuilder.append("  where constant_key like ? ");
			params.add("%"+constantKey.trim()+"%");
		}
		sqlBuilder.append(" order by create_date desc ");
		List<ConstantRes> dataRes = jdbcTemplate.query(sqlBuilder.toString(),params.toArray(), rowMapper);
		return dataRes;
	}
	
	private RowMapper<ConstantRes> rowMapper = new RowMapper<ConstantRes>() {
		@Override
		public ConstantRes mapRow(ResultSet rs, int arg1) throws SQLException {
			ConstantRes vo = new ConstantRes();
			vo.setFwConstantId(rs.getLong("fw_constant_id"));
			vo.setConstantKey(rs.getString("constant_key"));
			vo.setConstantValue(rs.getString("constant_value"));
			vo.setCreateBy(rs.getString("create_by"));
			vo.setCreateDate(rs.getString("create_date"));
			vo.setUpdateBy(rs.getString("update_by"));
			vo.setUpdateDate(rs.getString("update_date"));
			return vo;
		}
	};
}
