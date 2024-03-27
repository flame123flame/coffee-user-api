package coffee.backoffice.finance.repository.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.vo.res.PlayerValidBetProductRes;
import coffee.backoffice.finance.vo.res.PlayerValidBetRes;
import framework.utils.CommonJdbcTemplate;

@Repository
public class PlayerValidBetDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<PlayerValidBetRes> findTotalValidBet(String sd, String ed, String username) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT tg.game_provider, sum(tg.bet) as validBet ");
		sqlBuilder.append(" FROM transaction_game tg ");
		sqlBuilder.append(" WHERE ");
		sqlBuilder.append(" tg.created_date BETWEEN ? and ? ");
		sqlBuilder.append(" AND tg.username = ? ");
		sqlBuilder.append(" group by tg.game_provider ");
		params.add(sd);
		params.add(ed);
		params.add(username);
		List<PlayerValidBetRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				rowMapperPlayerValidBetRes);
		return dataRes;
	}

	private RowMapper<PlayerValidBetRes> rowMapperPlayerValidBetRes = new RowMapper<PlayerValidBetRes>() {
		@Override
		public PlayerValidBetRes mapRow(ResultSet rs, int arg1) throws SQLException {
			PlayerValidBetRes vo = new PlayerValidBetRes();
			vo.setGameProvider(rs.getString("game_provider"));
			if (rs.getBigDecimal("validBet") != null) {
				vo.setValidBet(rs.getBigDecimal("validBet"));
			} else {
				vo.setValidBet(BigDecimal.ZERO);
			}

			return vo;
		}
	};
	public List<PlayerValidBetProductRes> findTotalValidBetProduct(String sd, String ed, String username) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT name_en,name_th,code,SUM(tgm.validBet) as validBet FROM  game_product_type gpt ");
		sqlBuilder.append(" left join (select product_code,provider_code  from game_product_type_mapping_provider) ");
		sqlBuilder.append(" pdmp on pdmp.product_code = gpt.code ");
		sqlBuilder.append(" INNER join (select tg.game_provider, sum(tg.bet) as validBet  FROM transaction_game tg ");
		sqlBuilder.append(" WHERE tg.created_date BETWEEN ? and ? AND tg.username = ? group by tg.game_provider)  ");
		sqlBuilder.append(" tgm on tgm.game_provider = pdmp.provider_code ");
		sqlBuilder.append(" GROUP by name_en,name_th,code ");
		params.add(sd);
		params.add(ed);
		params.add(username);
		List<PlayerValidBetProductRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				rowMapperPlayerValidBetProductRes);
		return dataRes;
	}
	
	private RowMapper<PlayerValidBetProductRes> rowMapperPlayerValidBetProductRes = new RowMapper<PlayerValidBetProductRes>() {
		@Override
		public PlayerValidBetProductRes mapRow(ResultSet rs, int arg1) throws SQLException {
			PlayerValidBetProductRes vo = new PlayerValidBetProductRes();
			vo.setProductNameEn(rs.getString("name_en"));
			vo.setProductNameTh(rs.getString("name_th"));
			vo.setProductCode(rs.getString("code"));
			if (rs.getBigDecimal("validBet") != null) {
				vo.setValidBet(rs.getBigDecimal("validBet"));
			} else {
				vo.setValidBet(BigDecimal.ZERO);
			}
			
			return vo;
		}
	};
}
