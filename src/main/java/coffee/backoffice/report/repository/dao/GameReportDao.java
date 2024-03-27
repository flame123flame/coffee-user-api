package coffee.backoffice.report.repository.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.report.vo.res.GameReportRes;
import framework.model.DataTableResponse;
import framework.model.DatatableFilter;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class GameReportDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
//	<========================= Daily ===========================================>
	public DataTableResponse<GameReportRes.DataLisrRes> getGameReportDaily(DatatableRequest req) {
		DataTableResponse<GameReportRes.DataLisrRes> dataTable = new DataTableResponse<GameReportRes.DataLisrRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(
				" CAST(tg.created_date as Date) as date_yml,COUNT(game_session_id) as totaltxn,COUNT(DISTINCT username) as playercount, ");
		sqlBuilder.append(" sum(bet) as totalbet, sum(valid_bet) as totalvalidbet, sum(win_loss) as totalwinloss");
		sqlBuilder.append(" from games g ");
		sqlBuilder.append(
				" left join (select created_date,username,game_code,game_session_id,bet,win_loss,valid_bet from transaction_game) ");
		sqlBuilder.append(" tg on tg.game_code = g.game_code ");
		String sqlCount = DatatableUtils.countForDatatableGroup(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTableGroup(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<GameReportRes.DataLisrRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				rowMapperDataListResList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<GameReportRes.DataLisrRes> rowMapperDataListResList = new RowMapper<GameReportRes.DataLisrRes>() {
		@Override
		public GameReportRes.DataLisrRes mapRow(ResultSet rs, int arg1) throws SQLException {
			GameReportRes.DataLisrRes vo = new GameReportRes.DataLisrRes();
			vo.setPlayercount(rs.getInt("playercount"));
			vo.setTotaltxn(rs.getInt("totaltxn"));
			if (rs.getBigDecimal("totalbet") == null) {
				vo.setTotalbet(BigDecimal.ZERO);
			} else {
				vo.setTotalbet(rs.getBigDecimal("totalbet"));
			}

			if (rs.getBigDecimal("totalvalidbet") == null) {
				vo.setValidbet(BigDecimal.ZERO);
			} else {
				vo.setValidbet(rs.getBigDecimal("totalvalidbet"));
			}

			if (rs.getBigDecimal("totalwinloss") == null) {
				vo.setTotalwinloss(BigDecimal.ZERO);
			} else {
				vo.setTotalwinloss(rs.getBigDecimal("totalwinloss"));
			}

			vo.setSummarydate(rs.getString("date_yml"));
			
			vo.setTotalloss(BigDecimal.ZERO);
			vo.setTotalwin(BigDecimal.ZERO);
			vo.setTotalprizewon(BigDecimal.ZERO);
			return vo;
		}
	};

	public GameReportRes.DataSummaryRes getGameReportDailyTotalRes(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" SUM(tb.totaltxn) as totaltxn, SUM(tb.playercount) as playercount, ");
		sqlBuilder.append(" SUM(tb.totalbet) as totalbet, SUM(tb.totalvalidbet) as totalvalidbet, ");
		sqlBuilder.append(" SUM(tb.totalwinloss) as totalwinloss ");
		sqlBuilder.append(" from (");
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(
				" CAST(tg.created_date as Date) as date_yml,COUNT(game_session_id) as totaltxn,COUNT(DISTINCT username) as playercount, ");
		sqlBuilder.append(" sum(bet) as totalbet, sum(valid_bet) as totalvalidbet, sum(win_loss) as totalwinloss");
		sqlBuilder.append(" from games g ");
		sqlBuilder.append(
				" left join (select created_date,username,game_code,game_session_id,bet,win_loss,valid_bet from transaction_game) ");
		sqlBuilder.append(" tg on tg.game_code = g.game_code ");

		int i = 0;
		for (DatatableFilter item : req.getFilter()) {
			if (i == 0)
				sqlBuilder.append("  where");
			// filter index second
			// Column need to send (sql and, or,not,ETC.) Operators
			// sql EX. wordFilter.column = 'and username'
			sqlBuilder.append(" ").append(item.getColumn()).append(" ");
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
			} else if (item.getOp().equals("group by") || item.getOp().equals("GROUP BY")) {
				sqlBuilder.append(" ").append(item.getValue()).append(" ");
			} else {
				sqlBuilder.append(item.getOp()).append(" '").append(item.getValue()).append("' ");
			}
			i++;
		}
		sqlBuilder.append(" ) as tb ");
		GameReportRes.DataSummaryRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new Object[] {}, rowMappergetGameReportDailyTotalRes);
		return dataRes;
	}

	private RowMapper<GameReportRes.DataSummaryRes> rowMappergetGameReportDailyTotalRes = new RowMapper<GameReportRes.DataSummaryRes>() {
		@Override
		public GameReportRes.DataSummaryRes mapRow(ResultSet rs, int arg1) throws SQLException {
			GameReportRes.DataSummaryRes vo = new GameReportRes.DataSummaryRes();
			vo.setTotaltxn(rs.getInt("totaltxn"));
			vo.setPlayercount(rs.getInt("playercount"));
			if (rs.getBigDecimal("totalbet") != null)
				vo.setTotalbet(rs.getBigDecimal("totalbet"));
			else
				vo.setTotalbet(BigDecimal.ZERO);

			if (rs.getBigDecimal("totalvalidbet") != null)
				vo.setValidbet(rs.getBigDecimal("totalvalidbet"));
			else
				vo.setValidbet(BigDecimal.ZERO);

			vo.setTotalloss(BigDecimal.ZERO);
			vo.setTotalprizewon(BigDecimal.ZERO);
			vo.setTotalwin(BigDecimal.ZERO);
			
			if (rs.getBigDecimal("totalwinloss") != null)
				vo.setTotalwinloss(rs.getBigDecimal("totalwinloss"));
			else
				vo.setTotalwinloss(BigDecimal.ZERO);
			
			return vo;
		}
	};
//	<========================= Monthly ===========================================>
	public DataTableResponse<GameReportRes.DataLisrRes> getGameReportMonthly(DatatableRequest req) {
		DataTableResponse<GameReportRes.DataLisrRes> dataTable = new DataTableResponse<GameReportRes.DataLisrRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" format(created_date,'yyyy-MM') AS date_ym , ");
		sqlBuilder.append(" COUNT(game_session_id) as totaltxn,	COUNT(DISTINCT username) as playercount, ");
		sqlBuilder.append(" sum(bet) as totalbet,	sum(valid_bet) as totalvalidbet, ");
		sqlBuilder.append(" sum(win_loss) as totalwinloss ");
		sqlBuilder.append(" from games g ");
		sqlBuilder.append(" left join (	select created_date, username,game_code,game_session_id,bet, ");
		sqlBuilder.append(" win_loss,valid_bet ");
		sqlBuilder.append(" from transaction_game) tg on tg.game_code = g.game_code ");
		String sqlCount = DatatableUtils.countForDatatableGroup(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTableGroup(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<GameReportRes.DataLisrRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				rowMapperGameReportMonthlyList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<GameReportRes.DataLisrRes> rowMapperGameReportMonthlyList = new RowMapper<GameReportRes.DataLisrRes>() {
		@Override
		public GameReportRes.DataLisrRes mapRow(ResultSet rs, int arg1) throws SQLException {
			GameReportRes.DataLisrRes vo = new GameReportRes.DataLisrRes();
			vo.setPlayercount(rs.getInt("playercount"));
			vo.setTotaltxn(rs.getInt("totaltxn"));
			if (rs.getBigDecimal("totalbet") == null) {
				vo.setTotalbet(BigDecimal.ZERO);
			} else {
				vo.setTotalbet(rs.getBigDecimal("totalbet"));
			}

			if (rs.getBigDecimal("totalvalidbet") == null) {
				vo.setValidbet(BigDecimal.ZERO);
			} else {
				vo.setValidbet(rs.getBigDecimal("totalvalidbet"));
			}

			if (rs.getBigDecimal("totalwinloss") == null) {
				vo.setTotalwinloss(BigDecimal.ZERO);
			} else {
				vo.setTotalwinloss(rs.getBigDecimal("totalwinloss"));
			}

			vo.setSummarydate(rs.getString("date_ym"));
			
			vo.setTotalloss(BigDecimal.ZERO);
			vo.setTotalwin(BigDecimal.ZERO);
			vo.setTotalprizewon(BigDecimal.ZERO);
			return vo;
		}
	};
	
	public GameReportRes.DataSummaryRes getGameReportMonthlyTotalRes(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" SUM(tb.totaltxn) as totaltxn, SUM(tb.playercount) as playercount, ");
		sqlBuilder.append(" SUM(tb.totalbet) as totalbet, SUM(tb.totalvalidbet) as totalvalidbet, ");
		sqlBuilder.append(" SUM(tb.totalwinloss) as totalwinloss ");
		sqlBuilder.append(" from (");
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" format(created_date,'yyyy-MM') AS date_ym , ");
		sqlBuilder.append(" COUNT(game_session_id) as totaltxn,	COUNT(DISTINCT username) as playercount, ");
		sqlBuilder.append(" sum(bet) as totalbet,	sum(valid_bet) as totalvalidbet, ");
		sqlBuilder.append(" sum(win_loss) as totalwinloss ");
		sqlBuilder.append(" from games g ");
		sqlBuilder.append(" left join (	select created_date, username,game_code,game_session_id,bet, ");
		sqlBuilder.append(" win_loss,valid_bet ");
		sqlBuilder.append(" from transaction_game) tg on tg.game_code = g.game_code ");

		int i = 0;
		for (DatatableFilter item : req.getFilter()) {
			if (i == 0)
				sqlBuilder.append("  where");
			// filter index second
			// Column need to send (sql and, or,not,ETC.) Operators
			// sql EX. wordFilter.column = 'and username'
			sqlBuilder.append(" ").append(item.getColumn()).append(" ");
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
			} else if (item.getOp().equals("group by") || item.getOp().equals("GROUP BY")) {
				sqlBuilder.append(" ").append(item.getValue()).append(" ");
			} else {
				sqlBuilder.append(item.getOp()).append(" '").append(item.getValue()).append("' ");
			}
			i++;
		}
		sqlBuilder.append(" ) as tb ");
		GameReportRes.DataSummaryRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new Object[] {}, rowMappergetGameReportMonthlyTotalRes);
		return dataRes;
	}

	private RowMapper<GameReportRes.DataSummaryRes> rowMappergetGameReportMonthlyTotalRes = new RowMapper<GameReportRes.DataSummaryRes>() {
		@Override
		public GameReportRes.DataSummaryRes mapRow(ResultSet rs, int arg1) throws SQLException {
			GameReportRes.DataSummaryRes vo = new GameReportRes.DataSummaryRes();
			vo.setTotaltxn(rs.getInt("totaltxn"));
			vo.setPlayercount(rs.getInt("playercount"));
			if (rs.getBigDecimal("totalbet") != null)
				vo.setTotalbet(rs.getBigDecimal("totalbet"));
			else
				vo.setTotalbet(BigDecimal.ZERO);

			if (rs.getBigDecimal("totalvalidbet") != null)
				vo.setValidbet(rs.getBigDecimal("totalvalidbet"));
			else
				vo.setValidbet(BigDecimal.ZERO);

			vo.setTotalloss(BigDecimal.ZERO);
			vo.setTotalprizewon(BigDecimal.ZERO);
			vo.setTotalwin(BigDecimal.ZERO);
			
			if (rs.getBigDecimal("totalwinloss") != null)
				vo.setTotalwinloss(rs.getBigDecimal("totalwinloss"));
			else
				vo.setTotalwinloss(BigDecimal.ZERO);
			
			return vo;
		}
	};
//	<========================= Games ===========================================>
	public DataTableResponse<GameReportRes.DataLisrRes> getGameReportGames(DatatableRequest req) {
		DataTableResponse<GameReportRes.DataLisrRes> dataTable = new DataTableResponse<GameReportRes.DataLisrRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" g.provider_code,gp.name_en as provider_name, ");
		sqlBuilder.append(" g.game_product_type_code, gpt.name_en as product_name, ");
		sqlBuilder.append(" g.game_group_code,gg.name_en as group_name,  ");
		sqlBuilder.append(" g.game_code ,g.display_name, ");
		sqlBuilder.append(" COUNT(game_session_id) as totaltxn, ");
		sqlBuilder.append(" COUNT(DISTINCT username) as playercount, ");
		sqlBuilder.append(" SUM(bet) as totalbet , ");
		sqlBuilder.append(" SUM(valid_bet) as totalvalidbet, ");
		sqlBuilder.append(" SUM(win_loss) as totalwinloss ");
		sqlBuilder.append(" from games g ");
		sqlBuilder.append(" inner join game_provider gp on gp.code = g.provider_code ");
		sqlBuilder.append(" inner join game_product_type gpt on gpt.code = g.game_product_type_code ");
		sqlBuilder.append(" inner join game_group gg on gg.code = g.game_group_code ");
		sqlBuilder.append(" left join ( ");
		sqlBuilder.append(" select created_date, username, game_code, game_session_id, bet, win_loss, valid_bet from transaction_game) ");
		sqlBuilder.append(" tg on tg.game_code = g.game_code ");
		String sqlCount = DatatableUtils.countForDatatableGroup(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTableGroup(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<GameReportRes.DataLisrRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				rowMapperGameReportGamesList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<GameReportRes.DataLisrRes> rowMapperGameReportGamesList = new RowMapper<GameReportRes.DataLisrRes>() {
		@Override
		public GameReportRes.DataLisrRes mapRow(ResultSet rs, int arg1) throws SQLException {
			GameReportRes.DataLisrRes vo = new GameReportRes.DataLisrRes();
			vo.setGameprovidercode(rs.getString("provider_code"));
			vo.setGameprovidername(rs.getString("provider_name"));
			vo.setProducttypecode(rs.getString("game_product_type_code"));
			vo.setProducttypename(rs.getString("product_name"));
			vo.setGamegroupcode(rs.getString("game_group_code"));
			vo.setGamegroupname(rs.getString("group_name"));
			vo.setGamecode(rs.getString("game_code"));
			vo.setGamename(rs.getString("display_name"));
			vo.setPlayercount(rs.getInt("playercount"));
			vo.setTotaltxn(rs.getInt("totaltxn"));
			if (rs.getBigDecimal("totalbet") == null) {
				vo.setTotalbet(BigDecimal.ZERO);
			} else {
				vo.setTotalbet(rs.getBigDecimal("totalbet"));
			}

			if (rs.getBigDecimal("totalvalidbet") == null) {
				vo.setValidbet(BigDecimal.ZERO);
			} else {
				vo.setValidbet(rs.getBigDecimal("totalvalidbet"));
			}

			if (rs.getBigDecimal("totalwinloss") == null) {
				vo.setTotalwinloss(BigDecimal.ZERO);
			} else {
				vo.setTotalwinloss(rs.getBigDecimal("totalwinloss"));
			}

			vo.setSummarydate("");
			
			vo.setTotalloss(BigDecimal.ZERO);
			vo.setTotalwin(BigDecimal.ZERO);
			vo.setTotalprizewon(BigDecimal.ZERO);
			return vo;
		}
	};
	
	public GameReportRes.DataSummaryRes getGameReportGamesTotalRes(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" SUM(tb.totaltxn) as totaltxn, SUM(tb.playercount) as playercount, ");
		sqlBuilder.append(" SUM(tb.totalbet) as totalbet, SUM(tb.totalvalidbet) as totalvalidbet, ");
		sqlBuilder.append(" SUM(tb.totalwinloss) as totalwinloss ");
		sqlBuilder.append(" from (");
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" g.provider_code,gp.name_en as provider_name, ");
		sqlBuilder.append(" g.game_product_type_code, gpt.name_en as product_name, ");
		sqlBuilder.append(" g.game_group_code,gg.name_en as group_name,  ");
		sqlBuilder.append(" g.game_code ,g.display_name, ");
		sqlBuilder.append(" COUNT(game_session_id) as totaltxn, ");
		sqlBuilder.append(" COUNT(DISTINCT username) as playercount, ");
		sqlBuilder.append(" SUM(bet) as totalbet , ");
		sqlBuilder.append(" SUM(valid_bet) as totalvalidbet, ");
		sqlBuilder.append(" SUM(win_loss) as totalwinloss ");
		sqlBuilder.append(" from games g ");
		sqlBuilder.append(" inner join game_provider gp on gp.code = g.provider_code ");
		sqlBuilder.append(" inner join game_product_type gpt on gpt.code = g.game_product_type_code ");
		sqlBuilder.append(" inner join game_group gg on gg.code = g.game_group_code ");
		sqlBuilder.append(" left join ( ");
		sqlBuilder.append(" select created_date, username, game_code, game_session_id, bet, win_loss, valid_bet from transaction_game) ");
		sqlBuilder.append(" tg on tg.game_code = g.game_code ");
		
		int i = 0;
		for (DatatableFilter item : req.getFilter()) {
			if (i == 0)
				sqlBuilder.append("  where");
			// filter index second
			// Column need to send (sql and, or,not,ETC.) Operators
			// sql EX. wordFilter.column = 'and username'
			sqlBuilder.append(" ").append(item.getColumn()).append(" ");
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
			} else if (item.getOp().equals("group by") || item.getOp().equals("GROUP BY")) {
				sqlBuilder.append(" ").append(item.getValue()).append(" ");
			} else {
				sqlBuilder.append(item.getOp()).append(" '").append(item.getValue()).append("' ");
			}
			i++;
		}
		sqlBuilder.append(" ) as tb ");
		GameReportRes.DataSummaryRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new Object[] {}, rowMappergetGameReportGamesTotalRes);
		return dataRes;
	}

	private RowMapper<GameReportRes.DataSummaryRes> rowMappergetGameReportGamesTotalRes = new RowMapper<GameReportRes.DataSummaryRes>() {
		@Override
		public GameReportRes.DataSummaryRes mapRow(ResultSet rs, int arg1) throws SQLException {
			GameReportRes.DataSummaryRes vo = new GameReportRes.DataSummaryRes();
			vo.setTotaltxn(rs.getInt("totaltxn"));
			vo.setPlayercount(rs.getInt("playercount"));
			if (rs.getBigDecimal("totalbet") != null)
				vo.setTotalbet(rs.getBigDecimal("totalbet"));
			else
				vo.setTotalbet(BigDecimal.ZERO);

			if (rs.getBigDecimal("totalvalidbet") != null)
				vo.setValidbet(rs.getBigDecimal("totalvalidbet"));
			else
				vo.setValidbet(BigDecimal.ZERO);

			vo.setTotalloss(BigDecimal.ZERO);
			vo.setTotalprizewon(BigDecimal.ZERO);
			vo.setTotalwin(BigDecimal.ZERO);
			
			if (rs.getBigDecimal("totalwinloss") != null)
				vo.setTotalwinloss(rs.getBigDecimal("totalwinloss"));
			else
				vo.setTotalwinloss(BigDecimal.ZERO);
			
			return vo;
		}
	};

}
