package coffee.backoffice.player.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.vo.res.GroupLevelRes;
import framework.utils.CommonJdbcTemplate;

@Repository
public class GroupLevelDao {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	public String findById() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),new BeanPropertyRowMapper<>(GroupLevel.class));
		return sqlBuilder.toString();
	}
	
	public List<GroupLevelRes> findCustomerByGroupName() {
		StringBuilder sqlBuilder = new StringBuilder();
		
		List<Object> params = new ArrayList<>();
		sqlBuilder.append("  select * , (");
		sqlBuilder.append("  SELECT COUNT(ctm.id) as SS");
		sqlBuilder.append("  FROM customer ctm");
		sqlBuilder.append("  RIGHT JOIN group_level gl ON ctm.group_code = gl.group_code");
		sqlBuilder.append("  WHERE  gl.group_code = grl.group_code");
		sqlBuilder.append("  GROUP BY gl.group_code");
		sqlBuilder.append("  ) as count from group_level  grl");
		
		List<GroupLevelRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), rowMapperCustomerList);
//		System.out.print(dataRes);
		return dataRes;
	}

	private RowMapper<GroupLevelRes> rowMapperCustomerList = new RowMapper<GroupLevelRes>() {
		@Override
		public GroupLevelRes mapRow(ResultSet dataGet, int arg1) throws SQLException {
			GroupLevelRes groupLevel = new GroupLevelRes();
//			groupLevel.setId(rs.getLong("id"));

			groupLevel.setId(dataGet.getLong("id"));
			groupLevel.setGroupCode(dataGet.getString("group_code"));
			groupLevel.setCountPlayer(dataGet.getInt("count"));
			groupLevel.setGroupName(dataGet.getString("group_name"));
			groupLevel.setStatus(dataGet.getString("status"));
			groupLevel.setDescription(dataGet.getString("description"));
			groupLevel.setDefaultGroup(dataGet.getBoolean("default_group"));
			groupLevel.setMinDepositAmt(dataGet.getBigDecimal("min_deposit_amt"));
			groupLevel.setMaxDepositAmt(dataGet.getBigDecimal("max_deposit_amt"));
			groupLevel.setMinWithdrawalAmt(dataGet.getBigDecimal("min_withdrawal_amt"));
			groupLevel.setMaxWithdrawalAmt(dataGet.getBigDecimal("max_withdrawal_amt"));
			groupLevel.setDailyMaxWDAmount(dataGet.getBigDecimal("daily_max_wd_amount"));
			groupLevel.setDailyMaxWDCount(dataGet.getInt("daily_max_wd_count"));
			groupLevel.setUpdateOn(dataGet.getDate("update_on"));
			groupLevel.setUpdateBy(dataGet.getString("update_by"));
			groupLevel.setGroupMobilePhone(dataGet.getString("group_mobile_phone"));
			groupLevel.setGroupLinkLine(dataGet.getString("group_link_line"));
			groupLevel.setGroupIconImg(dataGet.getNString("group_icon_img"));
			return groupLevel;
		}
	};
}
