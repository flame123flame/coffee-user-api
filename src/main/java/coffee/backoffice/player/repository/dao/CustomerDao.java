package coffee.backoffice.player.repository.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import coffee.backoffice.player.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.player.vo.res.BankVerifyRes;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.backoffice.rebate.vo.res.RebateRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.ConvertDateUtils;
import framework.utils.DatatableUtils;

@Repository
public class CustomerDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public DataTableResponse<CustomerRes> getCustomerPaginate(DatatableRequest req) {
		DataTableResponse<CustomerRes> dataTable = new DataTableResponse<CustomerRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(" c.*, ");
		sqlBuilder.append(" b.bank_name_en,b.bank_name_th,");
		sqlBuilder.append(" gl.group_name,gl.group_mobile_phone,gl.group_link_line, ");
		sqlBuilder.append(" tm.name as tag_name, ");
		sqlBuilder.append(" wl.bonus ,wl.balance, ");
		sqlBuilder.append(" aln.affiliate_code,aln.affiliate_code_up,aln.register_date, ");
		sqlBuilder.append(" dt.total_deposit, ");
		sqlBuilder.append(" w.total_withdraw ");
//        sqlBuilder.append("    ");
		sqlBuilder.append(" from customer c ");
		sqlBuilder.append(" left join (select * from bank) b ");
		sqlBuilder.append(" on b.bank_code = c.bank_code ");
		sqlBuilder.append(" left join (select * from group_level) gl ");
		sqlBuilder.append(" on gl.group_code = c.group_code ");
		sqlBuilder.append(" left join (select * from tag_management) tm ");
		sqlBuilder.append(" on tm.tag_code = c.tag_code ");
		sqlBuilder.append(" left join (select * from wallet where wallet_name = 'MAIN') wl ");
		sqlBuilder.append(" on wl.username = c.username  ");
		sqlBuilder.append(" left join (select * from affiliate_network ) aln ");
		sqlBuilder.append(" on aln.username = c.username ");
		sqlBuilder.append(" left join (select username,sum(amount) as total_deposit ");
		sqlBuilder.append(" from deposit where status = 'APPROVE' group by username) dt ");
		sqlBuilder.append(" on dt.username = c.username  ");
		sqlBuilder.append(" left join (select username ,sum(amount) as total_withdraw ");
		sqlBuilder.append(" from withdraw where withdraw_status='WITHDRAW_APPROVED' group by username) w ");
		sqlBuilder.append(" on w.username = c.username  ");
//        sqlBuilder.append(" where 1=1 and c.register_status =2 ");
		String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<CustomerRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(), rowMapperCustomerPgList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<CustomerRes> rowMapperCustomerPgList = new RowMapper<CustomerRes>() {
		@Override
		public CustomerRes mapRow(ResultSet rs, int arg1) throws SQLException {
			CustomerRes vo = new CustomerRes();
			vo.setId(rs.getLong("id"));
			vo.setUsername(rs.getString("username"));
			vo.setMobilePhone(rs.getString("mobile_phone"));
			vo.setRealName(rs.getString("real_name"));
			vo.setBankCode(rs.getString("bank_code"));
			vo.setBankNameEn(rs.getString("bank_name_en"));
			vo.setBankNameTh(rs.getString("bank_name_th"));
			vo.setBankAccount(rs.getString("bank_account"));
			vo.setGroupCode(rs.getString("group_code"));
			vo.setGroupName(rs.getString("group_name"));
			vo.setTagCode(rs.getString("tag_code"));
			vo.setTagName(rs.getString("tag_name"));
			vo.setCreatedBy(rs.getString("created_by"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(ConvertDateUtils.formatDateToStringEn(tempCD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setUpdatedBy(rs.getString("updated_by"));
			Date tempUD = rs.getTimestamp("updated_date");
			vo.setUpdatedDate(ConvertDateUtils.formatDateToStringEn(tempUD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setEnable(rs.getBoolean("enable"));
			Date tempLLD = rs.getTimestamp("last_login_date");
			vo.setLastLoginDate(ConvertDateUtils.formatDateToStringEn(tempLLD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setLoginStatus(rs.getBoolean("login_status"));
			vo.setAffiliateCode(rs.getString("affiliate_code"));
			vo.setAffiliateCodeUp(rs.getString("affiliate_code_up"));
			Date temcRD = rs.getTimestamp("register_date");
			vo.setRegisterDate(ConvertDateUtils.formatDateToStringEn(temcRD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setBalance(rs.getBigDecimal("balance"));
			vo.setBonus(rs.getBigDecimal("bonus"));
			if (rs.getBigDecimal("total_deposit") != null) {
				vo.setTotalDeposit(rs.getBigDecimal("total_deposit"));
			} else {
				vo.setTotalDeposit(BigDecimal.ZERO);
			}
			if (rs.getBigDecimal("total_withdraw") != null)
				vo.setWithdraw(rs.getBigDecimal("total_withdraw"));
			else
				vo.setWithdraw(BigDecimal.ZERO);

			return vo;
		}
	};

	public CustomerRes findCustomerByUsername(String username) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select c.*,b.bank_name_en,b.bank_name_th,b.bank_url,b.bank_img, ");
		sqlBuilder.append(" gl.group_name,gl.group_icon_img,gl.level, tm.name as tag_name,c.enable, ");
//		sqlBuilder.append(" wl.pending_withdrawal,wl.bonus ,wl.balance, ");
		sqlBuilder.append(" aln.affiliate_code,aln.affiliate_code_up,aln.register_date, ");
		sqlBuilder.append(" gl.group_mobile_phone, gl.group_link_line ");
		sqlBuilder.append(" from customer c ");
		sqlBuilder.append(" left join (select * from bank) b ");
		sqlBuilder.append(" on b.bank_code = c.bank_code ");
		sqlBuilder.append(" left join (select * from group_level) gl ");
		sqlBuilder.append(" on gl.group_code = c.group_code ");
		sqlBuilder.append(" left join (select * from tag_management) tm ");
		sqlBuilder.append(" on tm.tag_code = c.tag_code ");
//		sqlBuilder.append(" left join (select * from wallet) wl ");
//		sqlBuilder.append(" on wl.username = c.username  ");
		sqlBuilder.append(" left join (select * from affiliate_network) aln ");
		sqlBuilder.append(" on aln.username = c.username ");
		sqlBuilder.append(" where c.username = ? and c.enable = 1 ");
		CustomerRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), new Object[] { username },
				rowMapperCustomer);
		return dataRes;
	}

	private RowMapper<CustomerRes> rowMapperCustomer = new RowMapper<CustomerRes>() {
		@Override
		public CustomerRes mapRow(ResultSet rs, int arg1) throws SQLException {
			CustomerRes vo = new CustomerRes();
			vo.setId(rs.getLong("id"));
			vo.setUsername(rs.getString("username"));
			vo.setMobilePhone(rs.getString("mobile_phone"));
			vo.setRealName(rs.getString("real_name"));
			vo.setBankCode(rs.getString("bank_code"));
			vo.setBankNameEn(rs.getString("bank_name_en"));
			vo.setBankNameTh(rs.getString("bank_name_th"));
			vo.setBankAccount(rs.getString("bank_account"));
			vo.setBankImg(rs.getString("bank_img"));
			vo.setGroupCode(rs.getString("group_code"));
			vo.setGroupName(rs.getString("group_name"));
			vo.setLevel(rs.getString("level"));
			vo.setTagCode(rs.getString("tag_code"));
			vo.setTagName(rs.getString("tag_name"));
			vo.setCreatedBy(rs.getString("created_by"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(ConvertDateUtils.formatDateToStringEn(tempCD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setUpdatedBy(rs.getString("updated_by"));
			Date tempUD = rs.getTimestamp("updated_date");
			vo.setUpdatedDate(ConvertDateUtils.formatDateToStringEn(tempUD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setEnable(rs.getBoolean("enable"));
			Date tempLLD = rs.getTimestamp("last_login_date");
			vo.setLastLoginDate(ConvertDateUtils.formatDateToStringEn(tempLLD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setLoginStatus(rs.getBoolean("login_status"));
			vo.setAffiliateCode(rs.getString("affiliate_code"));
			vo.setAffiliateCodeUp(rs.getString("affiliate_code_up"));
			Date temcRD = rs.getTimestamp("register_date");
			vo.setRegisterDate(ConvertDateUtils.formatDateToStringEn(temcRD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setGroupMobilePhone(rs.getString("group_mobile_phone"));
			vo.setGroupLinkLine(rs.getString("group_link_line"));
			vo.setGroupImg(rs.getString("group_icon_img"));
			vo.setNickname(rs.getString("nickname"));
			vo.setEmail(rs.getString("email"));
			vo.setBirthday(rs.getDate("birthday"));
			return vo;
		}
	};

	public List<CustomerRes> listFindCustomer() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" select c.*,b.bank_name_en,b.bank_name_th,b.bank_url,b.bank_img, ");
		sqlBuilder.append(" gl.group_name, tm.name as tag_name,c.enable, ");
		sqlBuilder.append(" wl.pending_withdrawal,wl.bonus ,wl.balance, ");
		sqlBuilder.append(" aln.affiliate_code,aln.affiliate_code_up,aln.register_date, ");
		sqlBuilder.append(" gl.group_mobile_phone, dt.amount ");
		sqlBuilder.append(" from customer c ");
		sqlBuilder.append(" left join (select * from bank) b ");
		sqlBuilder.append(" on b.bank_code = c.bank_code ");
		sqlBuilder.append(" left join (select * from group_level) gl ");
		sqlBuilder.append(" on gl.group_code = c.group_code ");
		sqlBuilder.append(" left join (select * from tag_management) tm ");
		sqlBuilder.append(" on tm.tag_code = c.tag_code ");
		sqlBuilder.append(" left join (select * from wallet where wallet_name = 'MAIN') wl ");
		sqlBuilder.append(" on wl.username = c.username  ");
		sqlBuilder.append(" left join (select * from affiliate_network ) aln ");
		sqlBuilder.append(" on aln.username = c.username ");
		sqlBuilder.append(" left join (select username,sum(amount) as amount ");
		sqlBuilder.append(" from deposit where status = 'APPROVE' group by username) dt ");
		sqlBuilder.append(" on dt.username = c.username  ");
		sqlBuilder.append(" where 1=1 and c.register_status =2 ");
		List<CustomerRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				rowMapperCustomerList);
		return dataRes;
	}

	private RowMapper<CustomerRes> rowMapperCustomerList = new RowMapper<CustomerRes>() {
		@Override
		public CustomerRes mapRow(ResultSet rs, int arg1) throws SQLException {
			CustomerRes vo = new CustomerRes();
			vo.setId(rs.getLong("id"));
			vo.setUsername(rs.getString("username"));
			vo.setMobilePhone(rs.getString("mobile_phone"));
			vo.setRealName(rs.getString("real_name"));
			vo.setBankCode(rs.getString("bank_code"));
			vo.setBankNameEn(rs.getString("bank_name_en"));
			vo.setBankNameTh(rs.getString("bank_name_th"));
			vo.setBankAccount(rs.getString("bank_account"));
//			vo.setBankImg(rs.getString("bank_img"));
			vo.setGroupCode(rs.getString("group_code"));
			vo.setGroupName(rs.getString("group_name"));
			vo.setTagCode(rs.getString("tag_code"));
			vo.setTagName(rs.getString("tag_name"));
			vo.setCreatedBy(rs.getString("created_by"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(ConvertDateUtils.formatDateToStringEn(tempCD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setUpdatedBy(rs.getString("updated_by"));
			Date tempUD = rs.getTimestamp("updated_date");
			vo.setUpdatedDate(ConvertDateUtils.formatDateToStringEn(tempUD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setEnable(rs.getBoolean("enable"));
			Date tempLLD = rs.getTimestamp("last_login_date");
			vo.setLastLoginDate(ConvertDateUtils.formatDateToStringEn(tempLLD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setLoginStatus(rs.getBoolean("login_status"));
			vo.setAffiliateCode(rs.getString("affiliate_code"));
			vo.setAffiliateCodeUp(rs.getString("affiliate_code_up"));
			Date temcRD = rs.getTimestamp("register_date");
			vo.setRegisterDate(ConvertDateUtils.formatDateToStringEn(temcRD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setGroupMobilePhone(rs.getString("group_mobile_phone"));
//			vo.setGroupLinkLine(rs.getString("group_link_line"));
//			vo.setGroupImg(rs.getString("group_icon_img"));
			vo.setBalance(rs.getBigDecimal("balance"));
			vo.setBonus(rs.getBigDecimal("bonus"));
			vo.setPendingWithdrawal(rs.getBigDecimal("pending_withdrawal"));
			if (rs.getBigDecimal("amount") != null) {
				vo.setTotalDeposit(rs.getBigDecimal("amount"));
			} else {
				vo.setTotalDeposit(BigDecimal.ZERO);
			}

			return vo;
		}
	};

	public List<BankVerifyRes> listBankVerify() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(
				" SELECT c.username, c.bank_account, c.real_name, b.bank_name_th, b.bank_name_en, c.bank_status, b.bank_code ");
		sqlBuilder.append(" FROM customer c, bank b ");
		sqlBuilder.append(" WHERE c.bank_code = b.bank_code AND c.bank_status = 'PENDING' ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				new BeanPropertyRowMapper<>(BankVerifyRes.class));
	}

	public List<Customer> findAllFindByGroupCodeInAndNotInExcludedTag(List<String> vipGroupCodes,
			List<String> excludedTag) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" select * from customer c ");
		sqlBuilder.append(" where c.group_code in ");
		sqlBuilder.append(" (");

		for (int i = 0; i < vipGroupCodes.size(); i++) {
			if (i == 0) {
				sqlBuilder.append(" '");
			} else {
				sqlBuilder.append(" ,'");
			}
			sqlBuilder.append(vipGroupCodes.get(i));
			sqlBuilder.append("'");
		}

		sqlBuilder.append(" )");
		if (!excludedTag.get(0).isEmpty()) {
			sqlBuilder.append(" AND");
			sqlBuilder.append(" ( 1=2");
			for (String s : excludedTag) {
				sqlBuilder.append(" or");
				sqlBuilder.append(" c.tag_code LIKE");
				sqlBuilder.append(" '%");
				sqlBuilder.append(s);
				sqlBuilder.append("%'");
			}
			sqlBuilder.append(" )");
		}
		System.out.println(sqlBuilder.toString());
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				BeanPropertyRowMapper.newInstance(Customer.class));
	}

	public DataTableResponse<BankVerifyRes> getBankVerifyPaginate(DatatableRequest req) {
		DataTableResponse<BankVerifyRes> dataTable = new DataTableResponse<BankVerifyRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(
				" c.username, c.bank_account, c.real_name, b.bank_name_th, b.bank_name_en, c.bank_status, b.bank_code,an.register_date ");
		sqlBuilder.append(" from customer c inner join bank b on b.bank_code = c.bank_code  ");
		sqlBuilder.append(" inner join affiliate_network an on  ");
		sqlBuilder.append(" an.username = c.username ");

		String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<BankVerifyRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				new BeanPropertyRowMapper<>(BankVerifyRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	public DataTableResponse<BankVerifyRes> getBankVerifyDuplicateAcPaginate(DatatableRequest req) {
		DataTableResponse<BankVerifyRes> dataTable = new DataTableResponse<BankVerifyRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(
				" c.username, c.bank_account, c.real_name, b.bank_name_th, b.bank_name_en, c.bank_status, b.bank_code, an.register_date ");
		sqlBuilder.append(" FROM customer c ");
		sqlBuilder.append(
				" JOIN (select bank_account FROM customer cv group by bank_account  HAVING  COUNT(bank_account) > 1) dpc on dpc.bank_account=c.bank_account ");
		sqlBuilder.append(" INNER JOIN bank b on b.bank_code = c.bank_code   ");
		sqlBuilder.append(" INNER JOIN  affiliate_network an on an.username = c.username  ");

		String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<BankVerifyRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				new BeanPropertyRowMapper<>(BankVerifyRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	public DataTableResponse<BankVerifyRes> getBankVerifyDuplicateRlPaginate(DatatableRequest req) {
		DataTableResponse<BankVerifyRes> dataTable = new DataTableResponse<BankVerifyRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(
				" c.username, c.bank_account, c.real_name, b.bank_name_th, b.bank_name_en, c.bank_status, b.bank_code, an.register_date ");
		sqlBuilder.append(" FROM customer c ");
		sqlBuilder.append(
				" JOIN (select real_name FROM customer cv group by real_name  HAVING  COUNT(real_name) > 1) drl on drl.real_name = c.real_name ");
		sqlBuilder.append(" INNER JOIN bank b on b.bank_code = c.bank_code   ");
		sqlBuilder.append(" INNER JOIN  affiliate_network an on an.username = c.username  ");

		String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<BankVerifyRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				new BeanPropertyRowMapper<>(BankVerifyRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}
}
