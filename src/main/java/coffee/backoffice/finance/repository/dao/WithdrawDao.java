package coffee.backoffice.finance.repository.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.vo.res.DepositListRes;
import coffee.backoffice.finance.vo.res.WithdrawListRes;
import coffee.backoffice.finance.vo.res.WithdrawRes;
import framework.model.DataTableResponse;
import framework.model.DatatableFilter;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class WithdrawDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public DataTableResponse<WithdrawRes> WithdrawResponsePaginate(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		DataTableResponse<WithdrawRes> dataTable = new DataTableResponse<WithdrawRes>();
		String sqlData = DatatableUtils.limitForDataTable("withdraw", req.getPage(), req.getLength(), req.getSort(),
				req.getFilter(), sqlBuilder.toString());
		String sqlCount = DatatableUtils.countForDatatable("withdraw", req.getFilter(), sqlBuilder.toString());
		System.out.println(sqlData);
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<WithdrawRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				BeanPropertyRowMapper.newInstance(WithdrawRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	// <============================== Withdraw ================================>
	public DataTableResponse<WithdrawListRes.DataLisrRes> getWithdrawResPaginate(DatatableRequest req) {
		DataTableResponse<WithdrawListRes.DataLisrRes> dataTable = new DataTableResponse<WithdrawListRes.DataLisrRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" w.id,w.created_date,w.order_withdraw,w.username,w.to_bank_account, ");
		sqlBuilder.append(" w.to_account_name,w.to_bank_name,ub.bank_name_en,ub.bank_name_th, ");
		sqlBuilder.append(
				" w.amount,w.before_balance,w.after_balance ,cb.bank_name_en as company_bank_name,w.account_name, ");
		sqlBuilder.append(" w.bank_name,w.bank_account,w.withdraw_date,w.withdraw_status, ");
		sqlBuilder.append(" w.created_by,w.updated_by ,w.updated_date, ");
		sqlBuilder.append(" w.admin_remark,w.user_remark ");

		sqlBuilder.append(" FROM withdraw w   ");
		sqlBuilder.append(" left join ( select bank_code,bank_name_en,bank_name_th from bank) ");
		sqlBuilder.append(" ub  on ub.bank_code = w.to_bank_name ");
		sqlBuilder.append(" left join ( select bank_code,bank_name_en,bank_name_th from bank) ");
		sqlBuilder.append(" cb  on cb.bank_code = w.account_name ");


		String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<WithdrawListRes.DataLisrRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				rowMapperDataListResList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<WithdrawListRes.DataLisrRes> rowMapperDataListResList = new RowMapper<WithdrawListRes.DataLisrRes>() {
		@Override
		public WithdrawListRes.DataLisrRes mapRow(ResultSet rs, int arg1) throws SQLException {
			WithdrawListRes.DataLisrRes vo = new WithdrawListRes.DataLisrRes();
			vo.setId(rs.getLong("id"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(tempCD);
			vo.setOrderWithdraw(rs.getString("order_withdraw"));
			vo.setUsername(rs.getString("username"));
			vo.setRealName(rs.getString("to_account_name"));
			vo.setBankAccount(rs.getString("to_bank_account"));
			vo.setBankCode(rs.getString("to_bank_name"));
			vo.setBankName(rs.getString("bank_name_en"));

			if (rs.getBigDecimal("amount") != null)
				vo.setAmount(rs.getBigDecimal("amount"));
			else
				vo.setAmount(BigDecimal.ZERO);

			if (rs.getBigDecimal("before_balance") != null)
				vo.setBeforeBalance(rs.getBigDecimal("before_balance"));
			else
				vo.setBeforeBalance(BigDecimal.ZERO);

			if (rs.getBigDecimal("after_balance") != null)
				vo.setAfterBalance(rs.getBigDecimal("after_balance"));
			else
				vo.setAfterBalance(BigDecimal.ZERO);

			vo.setCompanyBankName(rs.getString("company_bank_name"));
			vo.setCompanyBankCode(rs.getString("account_name"));
			vo.setCompanyAccountName(rs.getString("bank_name"));
			vo.setCompanyAccountNumber(rs.getString("bank_account"));
			Date tempWD = rs.getTimestamp("withdraw_date");
			vo.setWithdrawDate(tempWD);
			vo.setWithdrawStatus(rs.getString("withdraw_status"));
			vo.setCreatedBy(rs.getString("created_by"));
			vo.setUpdatedBy(rs.getString("updated_by"));
			Date tempUD = rs.getTimestamp("updated_date");
			vo.setUpdatedDate(tempUD);
			vo.setAdminRemark(rs.getString("admin_remark"));
			vo.setUserRemark(rs.getString("user_remark"));
			return vo;
		}
	};

	public WithdrawListRes.DataSummaryRes getWithdrawTotalRes(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" SUM(w.amount) as totalWithdraw ");
		sqlBuilder.append(" FROM withdraw w ");
		sqlBuilder.append(" left join ( select bank_code,bank_name_en,bank_name_th from bank) ");
		sqlBuilder.append(" ub  on ub.bank_code = w.to_bank_name ");
		sqlBuilder.append(" left join ( select bank_code,bank_name_en,bank_name_th from bank) ");
		sqlBuilder.append(" cb  on cb.bank_code = w.account_name ");
		sqlBuilder.append(" ");

		int i = 0;
		for (DatatableFilter item : req.getFilter()) {
			if (i == 0)
				sqlBuilder.append(" where");
			if (i > 0)
				sqlBuilder.append(" and ");
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
		WithdrawListRes.DataSummaryRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new Object[] {}, rowMapperWithdrawTotalRes);
		return dataRes;
	}

	private RowMapper<WithdrawListRes.DataSummaryRes> rowMapperWithdrawTotalRes = new RowMapper<WithdrawListRes.DataSummaryRes>() {
		@Override
		public WithdrawListRes.DataSummaryRes mapRow(ResultSet rs, int arg1) throws SQLException {
			WithdrawListRes.DataSummaryRes vo = new WithdrawListRes.DataSummaryRes();
			if (rs.getBigDecimal("totalWithdraw") != null)
				vo.setTotalWithdraw(rs.getBigDecimal("totalWithdraw"));
			else
				vo.setTotalWithdraw(BigDecimal.ZERO);
			return vo;
		}
	};
}
