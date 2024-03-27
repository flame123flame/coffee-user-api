package coffee.backoffice.finance.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.vo.res.BankRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class BankDao {
	
	@Autowired
    private CommonJdbcTemplate commonJdbcTemplate;
	
	public DataTableResponse<BankRes> paginate(DatatableRequest req) {
        DataTableResponse<BankRes> dataTable = new DataTableResponse<BankRes>();
        String sqlCount = DatatableUtils.countForDatatable("bank", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("bank",req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<BankRes>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , rowMapper);
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
	
	private RowMapper<BankRes> rowMapper = new RowMapper<BankRes>() {
        @Override
        public BankRes mapRow(ResultSet rs, int arg1) throws SQLException {
        	BankRes vo = new BankRes();
        	  vo.setBankCode(rs.getString("bank_code"));
        	  vo.setBankNameEn(rs.getString("bank_name_en"));
        	  vo.setBankNameTh(rs.getString("bank_name_th"));
        	  vo.setBankUrl(rs.getString("bank_url"));
        	  vo.setBankImg(rs.getString("bank_img"));
        	  vo.setEnable(rs.getBoolean("enable"));
        	  vo.setId(rs.getLong("id"));

            return vo;
        }
    };

}
