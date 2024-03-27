package coffee.backoffice.rebate.repository.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import framework.utils.CommonJdbcTemplate;

@Repository
public class CalculateRebateDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public BigDecimal totalBet(String startDate, String endDate, String username, String provider,
			String[] gameCodeExclude) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT SUM(tg.bet) FROM transaction_game tg");
		sqlBuilder.append(" where created_date BETWEEN ? ");
		params.add(startDate);
		sqlBuilder.append(" and ? ");
		params.add(endDate);

		sqlBuilder.append(" and username = ? ");
		params.add(username);

		sqlBuilder.append(" and game_provider = ? ");
		params.add(provider);
		if (gameCodeExclude.length > 0) {
			sqlBuilder.append(" and game_code NOT IN ( ");
			for (int i = 0; i < gameCodeExclude.length; i++) {
				if (i == 0) {
					sqlBuilder.append(" ? ");
					params.add(gameCodeExclude[i]);
				} else {
					sqlBuilder.append(" , ? ");
					params.add(gameCodeExclude[i]);
				}
			}
			sqlBuilder.append(" )");
		}

		BigDecimal sumTotalBet = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(),
				BigDecimal.class);
		return sumTotalBet;
	}
}
