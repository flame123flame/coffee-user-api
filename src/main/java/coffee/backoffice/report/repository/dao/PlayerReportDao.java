package coffee.backoffice.report.repository.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.report.vo.res.PlayerReportRes;
import framework.model.DataTableResponse;
import framework.model.DatatableFilter;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.ConvertDateUtils;
import framework.utils.DatatableUtils;

@Repository
public class PlayerReportDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public DataTableResponse<PlayerReportRes.DataLisrRes> getPlayerReportResPaginate(DatatableRequest req) {
		DataTableResponse<PlayerReportRes.DataLisrRes> dataTable = new DataTableResponse<PlayerReportRes.DataLisrRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" c.username, c.real_name, c.group_code, c.tag_code, ");
		sqlBuilder.append(" an.register_date, ");
		sqlBuilder.append(" gl.group_name, ");
		sqlBuilder.append(" tg.totalBets,tg.validBets, tg.winLoss, ");
		sqlBuilder.append(" tldt.deposiAmt, tldt.deposit_count, ");
		sqlBuilder.append(" tlwd.withdrawAmt, tlwd.withdraw_count, ");
		sqlBuilder.append(" rb.rebate, ");
		sqlBuilder.append(" ch.cashback ");

		sqlBuilder.append(" FROM customer c ");
		sqlBuilder.append(" inner join affiliate_network an on an.username =c.username  ");
		sqlBuilder.append(
				" left join ( select username,sum(bet) as totalBets, sum(valid_bet) as validBets, sum(win_loss) as winLoss  ");
		sqlBuilder.append(
				" from transaction_game stg where stg.created_date between '" + req.getFilter().get(0).getValue()
						+ "' and '" + req.getFilter().get(0).getValue1() + "' group by username ) ");
		sqlBuilder.append(" tg on c.username = tg.username ");
		sqlBuilder.append(
				" left join ( select username,sum(add_balance) as deposiAmt,count(status) as deposit_count from transaction_list stldt where stldt.transaction_date ");
		sqlBuilder.append(
				" between '" + req.getFilter().get(0).getValue() + "' and '" + req.getFilter().get(0).getValue1()
						+ "' and stldt.transaction_type = 'DEPOSIT' and stldt.status ='SUCCESS' group by username ) ");
		sqlBuilder.append(" tldt on c.username = tldt.username ");
		sqlBuilder.append(
				" left join ( select username,sum(sub_balance) as withdrawAmt,count(status) as withdraw_count  ");
		sqlBuilder.append(" from transaction_list stlwd where stlwd.transaction_date  ");
		sqlBuilder.append(
				" between '" + req.getFilter().get(0).getValue() + "' and '" + req.getFilter().get(0).getValue1()
						+ "' and stlwd.transaction_type = 'WITHDRAW' and stlwd.status ='SUCCESS' group by username ) ");
		sqlBuilder.append(" tlwd on	c.username = tlwd.username ");
		sqlBuilder.append(" left join (select username,sum(actual_rebate) as rebate ");
		sqlBuilder
				.append(" from rebate_history srh where srh.created_date between '" + req.getFilter().get(0).getValue()
						+ "' and '" + req.getFilter().get(0).getValue1() + "' group by username ) ");
		sqlBuilder.append(" rb on c.username = rb.username ");
		sqlBuilder.append(" left join (select username,sum(actual_cashback) as cashback ");
		sqlBuilder.append(
				" from cashback_history sch where sch.created_date between '" + req.getFilter().get(0).getValue()
						+ "' and '" + req.getFilter().get(0).getValue1() + "' group by username ) ");
		sqlBuilder.append(" ch on c.username = ch.username ");
		sqlBuilder.append(
				" left join (select group_code,group_name from group_level ) gl on c.group_code = gl.group_code ");
		String sqlCount = DatatableUtils.countForDatatableReport(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTableReport(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<PlayerReportRes.DataLisrRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				rowMapperDataListResList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<PlayerReportRes.DataLisrRes> rowMapperDataListResList = new RowMapper<PlayerReportRes.DataLisrRes>() {
		@Override
		public PlayerReportRes.DataLisrRes mapRow(ResultSet rs, int arg1) throws SQLException {
			PlayerReportRes.DataLisrRes vo = new PlayerReportRes.DataLisrRes();
			vo.setUsername(rs.getString("username"));
			vo.setRealName(rs.getString("real_name"));
			vo.setGroupCode(rs.getString("group_code"));
			vo.setGroupName(rs.getString("group_name"));
			vo.setTagCode(rs.getString("tag_code"));
			vo.setDepositCount(rs.getInt("deposit_count"));
			vo.setWithdrawCount(rs.getInt("withdraw_count"));

			if (rs.getBigDecimal("deposiAmt") == null)
				vo.setDepositAmt(BigDecimal.ZERO);
			else
				vo.setDepositAmt(rs.getBigDecimal("deposiAmt"));

			if (rs.getBigDecimal("withdrawAmt") == null)
				vo.setWithdrawAmt(BigDecimal.ZERO);
			else
				vo.setWithdrawAmt(rs.getBigDecimal("withdrawAmt"));

			if (rs.getBigDecimal("totalBets") == null)
				vo.setTotalBets(BigDecimal.ZERO);
			else
				vo.setTotalBets(rs.getBigDecimal("totalBets"));

			if (rs.getBigDecimal("validBets") == null)
				vo.setValidBets(BigDecimal.ZERO);
			else
				vo.setValidBets(rs.getBigDecimal("validBets"));

			if (rs.getBigDecimal("winLoss") == null)
				vo.setWinLoss(BigDecimal.ZERO);
			else
				vo.setWinLoss(rs.getBigDecimal("winLoss"));

			if (rs.getBigDecimal("rebate") == null)
				vo.setRebate(BigDecimal.ZERO);
			else
				vo.setRebate(rs.getBigDecimal("rebate"));

			if (rs.getBigDecimal("cashback") == null)
				vo.setCashback(BigDecimal.ZERO);
			else
				vo.setCashback(rs.getBigDecimal("cashback"));
			return vo;
		}
	};

	public PlayerReportRes.DataSummaryRes getPlayerReportTotalRes(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(
				" SUM(tb.totalBets) as totalBets,SUM(tb.validBets) as validBets, SUM(tb.winLoss) as winLoss,SUM(deposiAmt) as deposiAmt ,SUM(tb.deposit_count) as deposit_count, ");
		sqlBuilder.append(
				" SUM(tb.withdrawAmt) as withdrawAmt, SUM(withdraw_count) as withdraw_count, SUM(tb.rebate) as rebate ,SUM(tb.cashback) as cashback ");
		sqlBuilder.append(" from ( ");
		sqlBuilder.append(" SELECT ");
		sqlBuilder.append(" c.username, c.real_name, c.group_code, c.tag_code, ");
		sqlBuilder.append(" an.register_date, ");
		sqlBuilder.append(" gl.group_name, ");
		sqlBuilder.append(" tg.totalBets,tg.validBets, tg.winLoss, ");
		sqlBuilder.append(" tldt.deposiAmt, tldt.deposit_count, ");
		sqlBuilder.append(" tlwd.withdrawAmt, tlwd.withdraw_count, ");
		sqlBuilder.append(" rb.rebate, ");
		sqlBuilder.append(" ch.cashback ");

		sqlBuilder.append(" FROM customer c ");
		sqlBuilder.append(" inner join affiliate_network an on an.username =c.username  ");
		sqlBuilder.append(
				" left join ( select username,sum(bet) as totalBets, sum(valid_bet) as validBets, sum(win_loss) as winLoss  ");
		sqlBuilder.append(
				" from transaction_game stg where stg.created_date between '" + req.getFilter().get(0).getValue()
						+ "' and '" + req.getFilter().get(0).getValue1() + "' group by username ) ");
		sqlBuilder.append(" tg on c.username = tg.username ");
		sqlBuilder.append(
				" left join ( select username,sum(add_balance) as deposiAmt,count(status) as deposit_count from transaction_list stldt where stldt.transaction_date ");
		sqlBuilder.append(
				" between '" + req.getFilter().get(0).getValue() + "' and '" + req.getFilter().get(0).getValue1()
						+ "' and stldt.transaction_type = 'DEPOSIT' and stldt.status ='SUCCESS' group by username ) ");
		sqlBuilder.append(" tldt on c.username = tldt.username ");
		sqlBuilder.append(
				" left join ( select username,sum(sub_balance) as withdrawAmt,count(status) as withdraw_count  ");
		sqlBuilder.append(" from transaction_list stlwd where stlwd.transaction_date  ");
		sqlBuilder.append(
				" between '" + req.getFilter().get(0).getValue() + "' and '" + req.getFilter().get(0).getValue1()
						+ "' and stlwd.transaction_type = 'WITHDRAW' and stlwd.status ='SUCCESS' group by username ) ");
		sqlBuilder.append(" tlwd on	c.username = tlwd.username ");
		sqlBuilder.append(" left join (select username,sum(actual_rebate) as rebate ");
		sqlBuilder
				.append(" from rebate_history srh where srh.created_date between '" + req.getFilter().get(0).getValue()
						+ "' and '" + req.getFilter().get(0).getValue1() + "' group by username ) ");
		sqlBuilder.append(" rb on c.username = rb.username ");
		sqlBuilder.append(" left join (select username,sum(actual_cashback) as cashback ");
		sqlBuilder.append(
				" from cashback_history sch where sch.created_date between '" + req.getFilter().get(0).getValue()
						+ "' and '" + req.getFilter().get(0).getValue1() + "' group by username ) ");
		sqlBuilder.append(" ch on c.username = ch.username ");
		sqlBuilder.append(
				" left join (select group_code,group_name from group_level ) gl on c.group_code = gl.group_code ");
		int i = 0;
		for (DatatableFilter item : req.getFilter()) {
			if (i == 1) {
				sqlBuilder.append(" where");
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

				} else {
					sqlBuilder.append(item.getOp()).append(" '").append(item.getValue()).append("' ");
				}
			} else if (i > 1) {
				sqlBuilder.append(" and ");
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

				} else {
					sqlBuilder.append(item.getOp()).append(" '").append(item.getValue()).append("' ");
				}
			}

			i++;
		}
		sqlBuilder.append(" ) as tb ");
		PlayerReportRes.DataSummaryRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new Object[] {}, rowMapperPlayerReportTotalRes);
		return dataRes;
	}

	private RowMapper<PlayerReportRes.DataSummaryRes> rowMapperPlayerReportTotalRes = new RowMapper<PlayerReportRes.DataSummaryRes>() {
		@Override
		public PlayerReportRes.DataSummaryRes mapRow(ResultSet rs, int arg1) throws SQLException {
			PlayerReportRes.DataSummaryRes vo = new PlayerReportRes.DataSummaryRes();
			if (rs.getBigDecimal("totalBets") == null)
				vo.setTotalBets(BigDecimal.ZERO);
			else
				vo.setTotalBets(rs.getBigDecimal("totalBets"));

			if (rs.getBigDecimal("validBets") == null)
				vo.setTotalValidBets(BigDecimal.ZERO);
			else
				vo.setTotalValidBets(rs.getBigDecimal("validBets"));
			
			if (rs.getBigDecimal("winLoss") == null)
				vo.setTotalWinLoss(BigDecimal.ZERO);
			else
				vo.setTotalWinLoss(rs.getBigDecimal("winLoss"));
			
			if (rs.getBigDecimal("deposiAmt") == null)
				vo.setTotalDepositAmt(BigDecimal.ZERO);
			else
				vo.setTotalDepositAmt(rs.getBigDecimal("deposiAmt"));

			if (rs.getBigDecimal("withdrawAmt") == null)
				vo.setTotalWithdrawAmt(BigDecimal.ZERO);
			else
				vo.setTotalWithdrawAmt(rs.getBigDecimal("withdrawAmt"));
			
			if (rs.getBigDecimal("rebate") == null)
				vo.setTotalRebate(BigDecimal.ZERO);
			else
				vo.setTotalRebate(rs.getBigDecimal("rebate"));

			if (rs.getBigDecimal("cashback") == null)
				vo.setTotalCashback(BigDecimal.ZERO);
			else
				vo.setTotalCashback(rs.getBigDecimal("cashback"));

			vo.setTotalDepositCount(rs.getInt("deposit_count"));
			vo.setTotalWithdrawCount(rs.getInt("withdraw_count"));
			return vo;
		}
	};
}
