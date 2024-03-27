package coffee.backoffice.finance.repository.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.Deposit;
import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.vo.res.AllTransactionRes;
import coffee.backoffice.finance.vo.res.TransactionGameRes;
import coffee.backoffice.finance.vo.res.TransactionLogRes;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.backoffice.report.vo.res.BettingHistoriesRes;
import coffee.backoffice.report.vo.res.PlayerReportRes;
import coffee.website.affiliate.vo.model.PromotionDateList;
import framework.model.DataTableResponse;
import framework.model.DatatableFilter;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.ConvertDateUtils;
import framework.utils.DatatableUtils;

@Repository
public class AllTransactionDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public TransactionGame findTopOne() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT TOP 1 * FROM transaction_game ORDER BY created_date DESC");

		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new BeanPropertyRowMapper<>(TransactionGame.class));
	}

	public List<TransactionGame> findTransactionAfterDate(Date before, Date after) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		params.add(before);
		params.add(after);
		sqlBuilder.append(" SELECT * FROM transaction_game ");
		sqlBuilder.append(" WHERE  created_date BETWEEN ? AND ?");
		sqlBuilder.append(" ORDER BY created_date DESC ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				new BeanPropertyRowMapper<>(TransactionGame.class));
	}

	public BigDecimal sumTransactionByUsernameAndGameCodeAndProvider(String username, List<String> gameCode,
			List<String> providers, Date lastUpdate, Date nowDate, List<PromotionDateList> oldPromotionDateList) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();

		sqlBuilder.append(" SELECT SUM(valid_bet) as total_sum FROM transaction_game ");
		sqlBuilder.append(" WHERE username = ? ");
		params.add(username);

		if (lastUpdate != null) {
			sqlBuilder.append(" AND created_date > ? ");
			params.add(lastUpdate);
		}

		if (nowDate != null) {
			sqlBuilder.append(" AND created_date <= ? ");
			params.add(nowDate);
		}

		if (providers != null && providers.size() > 1) {
			sqlBuilder.append(" AND game_provider IN ");
			sqlBuilder.append(" (  ");
			for (int i = 0; i < providers.size(); i++) {
				if (i == 0) {
					sqlBuilder.append(" '" + providers.get(i) + "' ");
				} else {
					sqlBuilder.append(", '" + providers.get(i) + "' ");
				}
			}
			sqlBuilder.append(" ) ");

			if (gameCode != null && gameCode.size() > 1) {
				sqlBuilder.append(" AND game_code IN ");
				sqlBuilder.append(" (  ");
				for (int i = 0; i < gameCode.size(); i++) {
					if (i == 0) {
						sqlBuilder.append(" '" + gameCode.get(i) + "' ");
					} else {
						sqlBuilder.append(", '" + gameCode.get(i) + "' ");
					}
				}
				sqlBuilder.append(" ) ");
			} else if (gameCode != null && gameCode.size() == 1) {
				sqlBuilder.append(" AND game_code = ? ");
				params.add(gameCode.get(0));
			}
		} else if (providers != null && providers.size() == 1) {
			sqlBuilder.append(" AND game_provider = ? ");
			params.add(providers.get(0));

			if (gameCode != null && gameCode.size() > 1) {
				sqlBuilder.append(" AND game_code IN ");
				sqlBuilder.append(" (  ");
				for (int i = 0; i < gameCode.size(); i++) {
					if (i == 0) {
						sqlBuilder.append(" '" + gameCode.get(i) + "' ");
					} else {
						sqlBuilder.append(", '" + gameCode.get(i) + "' ");
					}
				}
				sqlBuilder.append(" ) ");
			} else if (gameCode != null && gameCode.size() == 1) {
				sqlBuilder.append(" AND game_code = ? ");
				params.add(gameCode.get(0));
			}
		} else {
			if (gameCode != null && gameCode.size() > 1) {
				sqlBuilder.append(" AND game_code IN ");
				sqlBuilder.append(" (  ");
				for (int i = 0; i < gameCode.size(); i++) {
					if (i == 0) {
						sqlBuilder.append(" '" + gameCode.get(i) + "' ");
					} else {
						sqlBuilder.append(", '" + gameCode.get(i) + "' ");
					}
				}
				sqlBuilder.append(" ) ");
			} else if (gameCode != null && gameCode.size() == 1) {
				sqlBuilder.append(" AND game_code = ? ");
				params.add(gameCode.get(0));
			}
		}

		if (oldPromotionDateList != null && oldPromotionDateList.size() > 0) {
			for (PromotionDateList temp : oldPromotionDateList) {
				sqlBuilder.append(" AND created_date NOT BETWEEN ? AND ? ");
				params.add(temp.getStart());
				params.add(temp.getEnd());
			}
		}
//		else {
//			sqlBuilder.append(" AND created_date NOT BETWEEN ? AND ? ");
//			params.add(oldPromotionDateList.get(0).getStart());
//			params.add(oldPromotionDateList.get(0).getEnd());
//		}

		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(), rowMapperBigDecimal);
	}

	private RowMapper<BigDecimal> rowMapperBigDecimal = new RowMapper<BigDecimal>() {
		@Override
		public BigDecimal mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getBigDecimal("total_sum");
		}
	};

	public DataTableResponse<TransactionGameRes> transactionGameResPaginate(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();

//		sqlBuilder.append(" left join ( select * from games ) g ");
//		sqlBuilder.append(" on g.game_code = tb.game_code ");
//		sqlBuilder.append(" left join ( select * from game_group ) gp ");
//		sqlBuilder.append(" on gp.code = g.game_group_code ");
//		sqlBuilder.append(" left join ( select * from game_product_type ) gpt ");
//		sqlBuilder.append(" on gpt.code = gp.game_product_type_code ");
		DataTableResponse<TransactionGameRes> dataTable = new DataTableResponse<TransactionGameRes>();
		String sqlCount = DatatableUtils.countForDatatable("transaction_game", req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable("transaction_game", req.getPage(), req.getLength(),
				req.getSort(), req.getFilter(), sqlBuilder.toString());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<TransactionGameRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				BeanPropertyRowMapper.newInstance(TransactionGameRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	public DataTableResponse<TransactionLogRes> transactionLogResPaginate(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		DataTableResponse<TransactionLogRes> dataTable = new DataTableResponse<TransactionLogRes>();
		String sqlCount = DatatableUtils.countForDatatable("transaction_list", req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable("transaction_list", req.getPage(), req.getLength(),
				req.getSort(), req.getFilter(), sqlBuilder.toString());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<TransactionLogRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				BeanPropertyRowMapper.newInstance(TransactionLogRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	public TransactionGame findTopOneProviderCreatedDateDesc(String providerCode) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		params.add(providerCode);
		sqlBuilder.append("SELECT TOP 1 * FROM transaction_game where game_provider = ? ORDER BY created_date DESC");

		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(),
				new BeanPropertyRowMapper<>(TransactionGame.class));
	}

	public TransactionGame findTopOneProviderUpdatedDateDesc(String providerCode) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		params.add(providerCode);
		sqlBuilder.append("SELECT TOP 1 * FROM transaction_game where game_provider = ? ORDER BY updated_date DESC");

		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(),
				new BeanPropertyRowMapper<>(TransactionGame.class));
	}

	public List<TransactionGame> findLastMinuteProvider(String providerCode, Date start, Date end) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		params.add(providerCode);
		params.add(start);
		params.add(end);
		sqlBuilder.append(
				"SELECT * FROM transaction_game where game_provider = ? and created_date between ? and ? ORDER BY created_date DESC");

		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				new BeanPropertyRowMapper<>(TransactionGame.class));
	}

	public DataTableResponse<BettingHistoriesRes.DataListRes> getBettingHistoriesResPaginate(DatatableRequest req) {
		DataTableResponse<BettingHistoriesRes.DataListRes> dataTable = new DataTableResponse<BettingHistoriesRes.DataListRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" tg.created_date,tg.game_session_id,tg.username, ");
		sqlBuilder.append(" g.provider_code,gp.name_en as provider_name, ");
		sqlBuilder.append(" g.game_product_type_code, gpt.name_en as product_name, ");
		sqlBuilder.append(" g.game_group_code,gg.name_en as group_name, g.game_code ,g.display_name, ");
		sqlBuilder.append(" tg.bet,tg.valid_bet,tg.win_loss ");
		sqlBuilder.append(" from games g ");
		sqlBuilder.append(" inner join game_provider gp on gp.code = g.provider_code ");
		sqlBuilder.append(" inner join game_product_type gpt on gpt.code = g.game_product_type_code ");
		sqlBuilder.append(" inner join game_group gg on gg.code = g.game_group_code ");
		sqlBuilder.append(" left join ( ");
		sqlBuilder.append(" select created_date, username, game_code, game_session_id, bet, win_loss, valid_bet  ");
		sqlBuilder.append(" from transaction_game) tg on tg.game_code = g.game_code ");
		String groupBy = "GROUP BY tg.created_date,tg.game_session_id,tg.username, "
				+ "g.provider_code,gp.name_en, g.game_product_type_code, gpt.name_en, "
				+ "g.game_group_code,gg.name_en , g.game_code ,g.display_name,tg.bet,tg.valid_bet,tg.win_loss";
		String sqlCount = DatatableUtils.countForDatatableGroupBy(sqlBuilder.toString(), req.getFilter(), groupBy);
		String sqlData = DatatableUtils.limitForDataTableGroupBy(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter(), groupBy);
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<BettingHistoriesRes.DataListRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				rowMapperDataListResList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<BettingHistoriesRes.DataListRes> rowMapperDataListResList = new RowMapper<BettingHistoriesRes.DataListRes>() {
		@Override
		public BettingHistoriesRes.DataListRes mapRow(ResultSet rs, int arg1) throws SQLException {
			BettingHistoriesRes.DataListRes vo = new BettingHistoriesRes.DataListRes();
			Date tempCD = rs.getTimestamp("created_date");
			vo.setBetTime(tempCD);
			vo.setResultTime(tempCD);
			vo.setTicketId(rs.getString("game_session_id"));
			vo.setProvidercode(rs.getString("provider_code"));
			vo.setProvidername(rs.getString("provider_name"));
			vo.setProducttypecode(rs.getString("game_product_type_code"));
			vo.setProducttypename(rs.getString("product_name"));
			vo.setGroupcode(rs.getString("game_group_code"));
			vo.setGroupname(rs.getString("group_name"));
			vo.setGamecode(rs.getString("game_code"));
			vo.setGamename(rs.getString("display_name"));
			vo.setPlayerId(rs.getString("username"));

			if (rs.getBigDecimal("bet") == null)
				vo.setBetAmount(BigDecimal.ZERO);
			else
				vo.setBetAmount(rs.getBigDecimal("bet"));

			if (rs.getBigDecimal("valid_bet") == null)
				vo.setValidBet(BigDecimal.ZERO);
			else
				vo.setValidBet(rs.getBigDecimal("valid_bet"));

			if (rs.getBigDecimal("win_loss") == null)
				vo.setWinLoss(BigDecimal.ZERO);
			else
				vo.setWinLoss(rs.getBigDecimal("win_loss"));

			return vo;
		}
	};

	public BettingHistoriesRes.DataSummaryRes getBettingHistoriesTotalRes(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" SUM(tb.bet) as totalBet, ");
		sqlBuilder.append(" SUM(tb.valid_bet) as totalValidBet, ");
		sqlBuilder.append(" SUM(tb.win_loss) as winLoss ");
		sqlBuilder.append(" from ( ");
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" tg.created_date,tg.game_session_id,tg.username, ");
		sqlBuilder.append(" g.provider_code,gp.name_en as provider_name, ");
		sqlBuilder.append(" g.game_product_type_code, gpt.name_en as product_name, ");
		sqlBuilder.append(" g.game_group_code,gg.name_en as group_name, g.game_code ,g.display_name, ");
		sqlBuilder.append(" tg.bet,tg.valid_bet,tg.win_loss ");
		sqlBuilder.append(" from games g ");
		sqlBuilder.append(" inner join game_provider gp on gp.code = g.provider_code ");
		sqlBuilder.append(" inner join game_product_type gpt on gpt.code = g.game_product_type_code ");
		sqlBuilder.append(" inner join game_group gg on gg.code = g.game_group_code ");
		sqlBuilder.append(" left join ( ");
		sqlBuilder.append(" select created_date, username, game_code, game_session_id, bet, win_loss, valid_bet  ");
		sqlBuilder.append(" from transaction_game) tg on tg.game_code = g.game_code ");

		sqlBuilder.append("  where");
		int i = 0;
		for (DatatableFilter item : req.getFilter()) {
			if (i > 0) {
				sqlBuilder.append(" and ");
			}
			sqlBuilder.append(" ").append(item.getColumn()).append(" ");
			System.out.println(item.getOp());
			if (item.getOp().equals("startWith") || item.getOp().equals("STARTWITH")) {
				sqlBuilder.append(" LIKE ");
				sqlBuilder.append(" '%").append(item.getValue()).append("' ");
			} else if (item.getOp().equals("endWith") || item.getOp().equals("ENDWITH")) {
				sqlBuilder.append(" LIKE ");
				sqlBuilder.append(" '").append(item.getValue()).append("%' ");
			} else if (item.getOp().equals("contain") || item.getOp().equals("CONTAIN")) {
				sqlBuilder.append(" LIKE ");
				sqlBuilder.append(" '%").append(item.getValue()).append("%' ");
			} else if (item.getOp().equals("between") || item.getOp().equals("BETWEEN")) {
				sqlBuilder.append(" between ");
				sqlBuilder.append(" '").append(item.getValue()).append("' ");
				sqlBuilder.append(" and ");
				sqlBuilder.append(" '").append(item.getValue1()).append("' ");

			} else {
				sqlBuilder.append(item.getOp()).append(" '").append(item.getValue()).append("' ");
			}
			i++;
		}
		sqlBuilder.append(" ");
		sqlBuilder.append("GROUP BY tg.created_date,tg.game_session_id,tg.username,");
		sqlBuilder.append("g.provider_code,gp.name_en, g.game_product_type_code, gpt.name_en,");
		sqlBuilder.append("g.game_group_code,gg.name_en , g.game_code ,g.display_name,tg.bet,tg.valid_bet,tg.win_loss");
		sqlBuilder.append(" ) as tb ");
		BettingHistoriesRes.DataSummaryRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new Object[] {}, rowMapperBettingHistoriesTotalRes);
		return dataRes;
	}

	private RowMapper<BettingHistoriesRes.DataSummaryRes> rowMapperBettingHistoriesTotalRes = new RowMapper<BettingHistoriesRes.DataSummaryRes>() {
		@Override
		public BettingHistoriesRes.DataSummaryRes mapRow(ResultSet rs, int arg1) throws SQLException {
			BettingHistoriesRes.DataSummaryRes vo = new BettingHistoriesRes.DataSummaryRes();
			if (rs.getBigDecimal("totalBet") != null)
				vo.setTotalBetAmount(rs.getBigDecimal("totalBet"));
			else
				vo.setTotalBetAmount(BigDecimal.ZERO);

			if (rs.getBigDecimal("totalValidBet") != null)
				vo.setTotalValidBet(rs.getBigDecimal("totalValidBet"));
			else
				vo.setTotalValidBet(BigDecimal.ZERO);

			if (rs.getBigDecimal("winLoss") != null)
				vo.setTotalWinLoss(rs.getBigDecimal("winLoss"));
			else
				vo.setTotalWinLoss(BigDecimal.ZERO);
			return vo;
		}
	};
}
