package coffee.backoffice.report.repository.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.report.vo.res.ProfitLossReportRes;
import framework.utils.CommonJdbcTemplate;

@Repository
public class ProfitLossReportDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<ProfitLossReportRes.DataListRes> getProfitLossReportList(String firstDayDate, String lastDayDate) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" tcg.date_ym, ");
		sqlBuilder.append(" an.usernamecount,cud.first_deposit, ");
		sqlBuilder.append(" tcg.totalbet,tcg.totalvalidbet,tcg.totalwinloss, ");
		sqlBuilder.append(" tclw.withdraw,tclw.withdraw_count, ");
		sqlBuilder.append(" tcld.deposit,tcld.deposit_count, ");
		sqlBuilder.append(" rbh.rebate, ");
		sqlBuilder.append(" cbh.cashback,  ");
		sqlBuilder.append(" tclb.bonus ");
		sqlBuilder.append(" from ( ");
		sqlBuilder.append(" SELECT ");
		sqlBuilder.append(" format(tg.created_date,'yyyy-MM') AS date_ym , ");
		sqlBuilder.append(" sum(bet) as totalbet,	sum(valid_bet) as totalvalidbet, ");
		sqlBuilder.append(" sum(win_loss) as totalwinloss ");
		sqlBuilder.append(" from games g ");
		sqlBuilder.append(" left join (	select created_date, username,game_code, ");
		sqlBuilder.append(" game_session_id,bet,win_loss,valid_bet	from transaction_game) ");
		sqlBuilder.append(" tg on tg.game_code = g.game_code ");
		sqlBuilder.append(" where created_date BETWEEN '" + firstDayDate + "' and '" + lastDayDate + "' ");
		sqlBuilder.append(" GROUP BY format(tg.created_date,'yyyy-MM') ) tcg ");
		sqlBuilder.append(" left join (select format(transaction_date,'yyyy-MM') AS date_ym, ");
		sqlBuilder.append(" SUM(sub_balance) as withdraw,COUNT(transaction_type) as withdraw_count ");
		sqlBuilder.append(" from transaction_list where  transaction_type = 'WITHDRAW' and status ='SUCCESS' ");
		sqlBuilder.append(" and transaction_date BETWEEN '" + firstDayDate + "' and '" + lastDayDate + "' ");
		sqlBuilder.append(" GROUP BY format(transaction_date,'yyyy-MM') ");
		sqlBuilder.append(" ) tclw on tclw.date_ym = tcg.date_ym ");
		sqlBuilder.append(" left join(select format(transaction_date,'yyyy-MM') AS date_ym, ");
		sqlBuilder.append(" SUM(add_balance) as deposit,COUNT(transaction_type) as deposit_count ");
		sqlBuilder.append(" from transaction_list ");
		sqlBuilder.append(" where  transaction_type = 'DEPOSIT' and status ='SUCCESS'AND ");
		sqlBuilder.append(" transaction_date BETWEEN '" + firstDayDate + "' and '" + lastDayDate + "' ");
		sqlBuilder.append(" GROUP BY format(transaction_date,'yyyy-MM') ");
		sqlBuilder.append(" ) tcld on tcld.date_ym = tcg.date_ym ");
		sqlBuilder.append(" left join ( ");
		sqlBuilder.append(" SELECT format(created_date,'yyyy-MM') AS date_ym,SUM(actual_rebate) as rebate ");
		sqlBuilder.append(" from rebate_history rh where ");
		sqlBuilder.append(" created_date BETWEEN '" + firstDayDate + "' and '" + lastDayDate + "' ");
		sqlBuilder.append(" GROUP BY format(created_date,'yyyy-MM')) rbh on rbh.date_ym = tcg.date_ym ");
		sqlBuilder.append(" left join (SELECT format(created_date,'yyyy-MM') AS date_ym, ");
		sqlBuilder.append(" SUM(actual_cashback) as cashback  from cashback_history rh where ");
		sqlBuilder.append(" created_date BETWEEN '" + firstDayDate + "' and '" + lastDayDate + "' ");
		sqlBuilder.append(" GROUP BY format(created_date,'yyyy-MM') ) cbh on cbh.date_ym = tcg.date_ym ");
		sqlBuilder.append(" left join (SELECT format(register_date,'yyyy-MM') AS date_ym, ");
		sqlBuilder.append(" COUNT(username) as usernamecount FROM  affiliate_network an  ");
		sqlBuilder.append(" GROUP BY format(register_date,'yyyy-MM')) an on an.date_ym = tcg.date_ym ");
		sqlBuilder.append(" left join( select sdp.date_ym,COUNT(sdp.username) as first_deposit ");
		sqlBuilder.append(" from (select s_an.date_ym, s_an.username ");
		sqlBuilder.append(" FROM ( select format(register_date, 'yyyy-MM') as date_ym, username ");
		sqlBuilder.append(" from affiliate_network ");
		sqlBuilder.append(" where register_date BETWEEN '" + firstDayDate + "' and '" + lastDayDate + "' ");
		sqlBuilder.append(" GROUP by format(register_date, 'yyyy-MM'), username ) s_an ");
		sqlBuilder.append(
				" inner join ( select format(created_date, 'yyyy-MM') as date_ym, username, COUNT(username) as count_user ");
		sqlBuilder.append(" from deposit where status = 'APPROVE' ");
		sqlBuilder.append(" and created_date BETWEEN '" + firstDayDate + "' and '" + lastDayDate + "' ");
		sqlBuilder.append(
				" GROUP by format(created_date, 'yyyy-MM'), username ) s_dp on s_dp.username = s_an.username  ");
		sqlBuilder.append(" ) sdp GROUP BY sdp.date_ym  ");
		sqlBuilder.append(" ) cud on cud.date_ym = tcg.date_ym");
		sqlBuilder.append(" left join(select format(transaction_date,'yyyy-MM') AS date_ym, ");
		sqlBuilder.append(" SUM(add_balance) as bonus from transaction_list ");
		sqlBuilder.append(" where  transaction_type = 'PROMOTION_BONUS' and status ='SUCCESS' AND  ");
		sqlBuilder.append(" transaction_date BETWEEN '" + firstDayDate + "' and '" + lastDayDate + "' ");
		sqlBuilder.append(" GROUP BY format(transaction_date,'yyyy-MM')) tclb on tclb.date_ym = tcg.date_ym ");
		List<ProfitLossReportRes.DataListRes> res = commonJdbcTemplate.executeQuery(sqlBuilder.toString(),
				params.toArray(), rowMapper);
		return res;
	}

	private RowMapper<ProfitLossReportRes.DataListRes> rowMapper = new RowMapper<ProfitLossReportRes.DataListRes>() {
		@Override
		public ProfitLossReportRes.DataListRes mapRow(ResultSet rs, int arg1) throws SQLException {
			ProfitLossReportRes.DataListRes vo = new ProfitLossReportRes.DataListRes();

			vo.setAdjustment(BigDecimal.ZERO);
			vo.setBonus(BigDecimal.ZERO);
			if(rs.getBigDecimal("bonus") != null)
				vo.setBonus(rs.getBigDecimal("bonus"));
			else
				vo.setBonus(BigDecimal.ZERO);
			if (rs.getBigDecimal("cashback") != null)
				vo.setCashback(rs.getBigDecimal("cashback"));
			else
				vo.setCashback(BigDecimal.ZERO);
			if (rs.getBigDecimal("totalwinloss") != null)
				vo.setCompanywinloss(rs.getBigDecimal("totalwinloss"));
			else
				vo.setCompanywinloss(BigDecimal.ZERO);
			if (rs.getBigDecimal("deposit") != null)
				vo.setDeposit(rs.getBigDecimal("deposit"));
			else
				vo.setDeposit(BigDecimal.ZERO);
	
			vo.setDepositbonus(BigDecimal.ZERO);
			vo.setDepositcount(rs.getInt("deposit_count"));
			vo.setFirstdepositcount(rs.getInt("first_deposit"));
			if (rs.getBigDecimal("rebate") != null)
				vo.setRebate(rs.getBigDecimal("rebate"));
			else
				vo.setRebate(BigDecimal.ZERO);
			vo.setRegistercount(rs.getInt("usernamecount"));
			vo.setSummarydate(rs.getString("date_ym"));
			if (rs.getBigDecimal("totalvalidbet") != null)
				vo.setValidbet(rs.getBigDecimal("totalvalidbet"));
			else
				vo.setValidbet(BigDecimal.ZERO);
			if (rs.getBigDecimal("withdraw") != null)
				vo.setWithdraw(rs.getBigDecimal("withdraw"));
			else
				vo.setWithdraw(BigDecimal.ZERO);
			vo.setWithdrawcount(rs.getInt("withdraw_count"));
			return vo;
		}
	};
}
