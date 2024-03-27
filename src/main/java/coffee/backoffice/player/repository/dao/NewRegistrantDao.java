package coffee.backoffice.player.repository.dao;

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


import coffee.backoffice.player.model.NewRegistrant;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.backoffice.player.vo.res.NewRegistrantRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.ConvertDateUtils;
import framework.utils.DatatableUtils;

@Repository
public class NewRegistrantDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
		
	
	public List<NewRegistrantRes> getCountToDay() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append("  SELECT COUNT(an.register_date) AS count_to_day, ");
		sqlBuilder.append("  (SELECT COUNT(DISTINCT d.username) ");
		sqlBuilder.append("  FROM deposit d ");
		sqlBuilder.append("  inner JOIN affiliate_network an ");
		sqlBuilder.append("  ON an.username = d.username ");
		sqlBuilder.append("  WHERE d.status = 'APPROVE' AND d.amount > 0  ");
		sqlBuilder.append("  AND CAST(an.register_date AS Date) = CAST( GETDATE() AS Date )) AS count_to_day_deposit ");
		sqlBuilder.append("  FROM affiliate_network an  ");
		sqlBuilder.append("  WHERE CAST(an.register_date AS Date) = CAST( GETDATE() AS Date ) ");
		
		List<NewRegistrantRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(NewRegistrantRes.class));
		return dataRes;
}
	

	public List<NewRegistrantRes> getCountToWeek() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append("  SET DATEFIRST 1 ");
		sqlBuilder.append("  SELECT COUNT(an.register_date) AS count_to_week, ");
		sqlBuilder.append("  (SELECT COUNT(DISTINCT d.username) ");
		sqlBuilder.append("  FROM deposit d ");
		sqlBuilder.append("  inner JOIN affiliate_network an  ");
		sqlBuilder.append("  ON an.username = d.username ");
		sqlBuilder.append("  WHERE d.status = 'APPROVE' AND d.amount > 0 ");
		sqlBuilder.append("  AND DATEPART(week, an.register_date ) = DATEPART(week, GETDATE()) ) AS count_to_week_deposit ");
		sqlBuilder.append("  FROM affiliate_network an ");
		sqlBuilder.append("  WHERE DATEPART(week, an.register_date ) = DATEPART(week, GETDATE()) ");
		
		List<NewRegistrantRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(NewRegistrantRes.class));
		return dataRes;
}
	
	public List<NewRegistrantRes> getCountLastweek() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append("  SELECT COUNT(an.register_date) AS count_to_lastweek,  ");
		sqlBuilder.append("  (SELECT COUNT(DISTINCT d.username) ");
		sqlBuilder.append("  FROM deposit d ");
		sqlBuilder.append("  inner JOIN affiliate_network an ");
		sqlBuilder.append("  ON an.username = d.username  ");
		sqlBuilder.append("  WHERE d.status = 'APPROVE' AND d.amount > 0 ");
		sqlBuilder.append("  AND an.register_date >= DATEADD(week, DATEDIFF(week,0,GETDATE()), -7) ");
		sqlBuilder.append("  and an.register_date < DATEADD(week, DATEDIFF(week,0,GETDATE()),0) ) AS count_to_lastweek_deposit ");
		sqlBuilder.append("  FROM affiliate_network an ");
		sqlBuilder.append("  WHERE an.register_date >= DATEADD(week, DATEDIFF(week,0,GETDATE()), -7) ");
		sqlBuilder.append("  and an.register_date < DATEADD(week, DATEDIFF(week,0,GETDATE()),0) ");
		
		List<NewRegistrantRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(NewRegistrantRes.class));
		return dataRes;
}
	
	public  DataTableResponse<NewRegistrantRes> getPaginate(DatatableRequest req) {
		DataTableResponse<NewRegistrantRes> dataTable = new DataTableResponse<NewRegistrantRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT c.* ,wl.balance, ");
		sqlBuilder.append(" aln.affiliate_code,aln.register_date,  dt.amount ");
		sqlBuilder.append(" from customer c ");
		sqlBuilder.append(" left join (select * from wallet where wallet_name = 'MAIN') wl ");
		sqlBuilder.append(" on wl.username = c.username ");
		sqlBuilder.append(" left join (select * from affiliate_network ) aln  ");
		sqlBuilder.append(" on aln.username = c.username ");
		sqlBuilder.append(" left join (select username,sum(amount) as amount ");
		sqlBuilder.append(" from deposit where status = 'APPROVE' group by username) dt  ");
		sqlBuilder.append(" on dt.username = c.username ");
		sqlBuilder.append(" left join affiliate_network an ");
		sqlBuilder.append(" on c.username = an.username ");
//		sqlBuilder.append(" where 1=1 and c.register_status =2 ");

		String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<NewRegistrantRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				rowMapperCustomerList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
}
	private RowMapper<NewRegistrantRes> rowMapperCustomerList = new RowMapper<NewRegistrantRes>() {
        @Override
        public NewRegistrantRes mapRow(ResultSet rs, int arg1) throws SQLException {
        	NewRegistrantRes vo = new NewRegistrantRes();
            vo.setId(rs.getLong("id"));
            vo.setUsername(rs.getString("username"));
            vo.setMobilePhone(rs.getString("mobile_phone"));
            vo.setRealName(rs.getString("real_name"));
            vo.setBankAccount(rs.getString("bank_account"));
            vo.setGroupCode(rs.getString("group_code"));
            vo.setTagCode(rs.getString("tag_code"));
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
            Date temcRD = rs.getTimestamp("register_date");
            vo.setRegisterDate(ConvertDateUtils.formatDateToStringEn(temcRD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
            vo.setBalance(rs.getBigDecimal("balance"));
            if (rs.getBigDecimal("amount") != null) {
                vo.setTotalDeposit(rs.getBigDecimal("amount"));
            } else {
                vo.setTotalDeposit(BigDecimal.ZERO);
            }

            return vo;
        }
    };
	
}
