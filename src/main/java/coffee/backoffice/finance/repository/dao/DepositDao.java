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

import coffee.backoffice.finance.model.Deposit;
import coffee.backoffice.finance.vo.res.DepositListRes;
import coffee.backoffice.report.vo.res.PlayerReportRes;
import framework.model.DataTableResponse;
import framework.model.DatatableFilter;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class DepositDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Deposit> findAll() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append("   SELECT * FROM deposit where 1=1 ORDER BY created_date DESC");

		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				new BeanPropertyRowMapper<>(Deposit.class));
	}

	public DataTableResponse<Deposit> paginate(DatatableRequest req) {
		DataTableResponse<Deposit> dataTable = new DataTableResponse<Deposit>();
		String sqlCount = DatatableUtils.countForDatatable("deposit", req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable("deposit", req.getPage(), req.getLength(), req.getSort(),
				req.getFilter());

		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<Deposit> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(), rowMapper);
		System.out.println("sqlData = " + sqlData);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<Deposit> rowMapper = new RowMapper<Deposit>() {
		@Override
		public Deposit mapRow(ResultSet rs, int arg1) throws SQLException {
			Deposit vo = new Deposit();
			vo.setDepositOrder(rs.getString("deposit_order"));
			vo.setAmount(rs.getBigDecimal("amount"));
			vo.setAuditor(rs.getString("auditor"));

			vo.setAuditorDate(rs.getTimestamp("auditor_date"));
			vo.setCompanyAccountCode(rs.getString("company_account_code"));
			vo.setDepositOrder(rs.getString("deposit_order"));
			vo.setDepositRemark(rs.getString("deposit_remark"));
			vo.setId(rs.getLong("id"));
			vo.setStatus(rs.getString("status"));
			vo.setDepositType(rs.getString("deposit_type"));
			vo.setUsername(rs.getString("username"));

			return vo;
		}
	};

	public DataTableResponse<DepositListRes.DataLisrRes> getDepositResPaginate(DatatableRequest req) {
		DataTableResponse<DepositListRes.DataLisrRes> dataTable = new DataTableResponse<DepositListRes.DataLisrRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" d.*, ");
		sqlBuilder.append(" ca.bank,bank_account,ca.account_name,ca.bank_code,ca.display_name, ");
		sqlBuilder.append(" c.real_name ");
		sqlBuilder.append(" from deposit d  ");
		sqlBuilder.append(
				" inner join (select company_account_code,bank,bank_account,account_name,bank_code,display_name from company_account ) ");
		sqlBuilder.append(" ca on ca.company_account_code = d.company_account_code ");
		sqlBuilder.append(" inner join (select username,real_name from customer) ");
		sqlBuilder.append(" c on c.username = d.username ");

		String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<DepositListRes.DataLisrRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				rowMapperDataListResList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<DepositListRes.DataLisrRes> rowMapperDataListResList = new RowMapper<DepositListRes.DataLisrRes>() {
		@Override
		public DepositListRes.DataLisrRes mapRow(ResultSet rs, int arg1) throws SQLException {
			DepositListRes.DataLisrRes vo = new DepositListRes.DataLisrRes();
			vo.setAuditor(rs.getString("auditor"));
			Date tempAD = rs.getTimestamp("auditor_date");
			vo.setAuditorDate(tempAD);
			vo.setCompanyAccountName(rs.getString("account_name"));
			vo.setCompanyAccountNumber(rs.getString("bank_account"));
			vo.setCompanyBankCode(rs.getString("bank_code"));
			vo.setCompanyBankName(rs.getString("bank"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(tempCD);
			vo.setDeposit(rs.getBigDecimal("amount"));

			if (rs.getBigDecimal("before_balance") != null)
				vo.setBeforeBalance(rs.getBigDecimal("before_balance"));
			else
				vo.setBeforeBalance(BigDecimal.ZERO);

			if (rs.getBigDecimal("after_balance") != null)
				vo.setAfterBalance(rs.getBigDecimal("after_balance"));
			else
				vo.setAfterBalance(BigDecimal.ZERO);

			vo.setDepositDate(null);
			vo.setDepositOrder(rs.getString("deposit_order"));
			vo.setDepositRemark(rs.getString("deposit_remark"));
			vo.setDepositType(rs.getString("deposit_type"));
			vo.setRealname(rs.getString("real_name"));
			vo.setStatus(rs.getString("status"));
			vo.setUsername(rs.getString("username"));

			return vo;
		}
	};

	public DepositListRes.DataSummaryRes getDepositTotalRes(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" SUM(d.amount) as totalDeposit ");
		sqlBuilder.append(" from deposit d ");
		sqlBuilder.append(
				" inner join (select company_account_code,bank,bank_account,account_name,bank_code,display_name from company_account ) ");
		sqlBuilder.append(" ca on ca.company_account_code = d.company_account_code ");
		sqlBuilder.append(" inner join (select username,real_name from customer) ");
		sqlBuilder.append(" c on c.username = d.username ");

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
		DepositListRes.DataSummaryRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(),
				new Object[] {}, rowMapperDepositTotalRes);
		return dataRes;
	}

	private RowMapper<DepositListRes.DataSummaryRes> rowMapperDepositTotalRes = new RowMapper<DepositListRes.DataSummaryRes>() {
		@Override
		public DepositListRes.DataSummaryRes mapRow(ResultSet rs, int arg1) throws SQLException {
			DepositListRes.DataSummaryRes vo = new DepositListRes.DataSummaryRes();
			if (rs.getBigDecimal("totalDeposit") != null)
				vo.setTotalDeposit(rs.getBigDecimal("totalDeposit"));
			else
				vo.setTotalDeposit(BigDecimal.ZERO);
			return vo;
		}
	};
}
