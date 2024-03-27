package coffee.backoffice.affiliate.repository.dao;

import coffee.backoffice.affiliate.model.Affiliate;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AffiliateDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public List<Affiliate> findAll() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append("   SELECT * FROM affilate where 1=1 ORDER BY created_date DESC");

		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),new BeanPropertyRowMapper<>(Affiliate.class));
	}
    
    public DataTableResponse<Affiliate> paginate(DatatableRequest req) {
    	StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM affiliate  WHERE affiliate_code IN (SELECT affiliate_code FROM affiliate_network an JOIN customer c ON an.username = c.username) ");
		
        DataTableResponse<Affiliate> dataTable = new DataTableResponse<Affiliate>();
        String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(),req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<Affiliate>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , rowMapper);
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
    
    private RowMapper<Affiliate> rowMapper = new RowMapper<Affiliate>() {
        @Override
        public Affiliate mapRow(ResultSet rs, int arg1) throws SQLException {
        	Affiliate vo = new Affiliate();
        	  vo.setAffiliateGroupCode(rs.getString("affiliate_group_code"));
        	  vo.setAffiliateCode(rs.getString("affiliate_code"));
        	  vo.setDetail(rs.getString("detail"));
        	  vo.setBanner(rs.getString("banner"));
        	  vo.setTotalIncome(rs.getBigDecimal("total_income"));
        	  vo.setIncomeOne(rs.getBigDecimal("income_one"));
        	  vo.setIncomeTwo(rs.getBigDecimal("income_two"));
        	  vo.setWithdraw(rs.getBigDecimal("withdraw"));
        	  vo.setId(rs.getLong("id"));
        	  vo.setClickCount(rs.getInt("click_count"));

            return vo;
        }
    };
}
