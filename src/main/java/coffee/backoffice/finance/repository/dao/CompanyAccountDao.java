package coffee.backoffice.finance.repository.dao;

import coffee.backoffice.finance.vo.res.CompanyAccountRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyAccountDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<CompanyAccountRes> paginate(DatatableRequest req) {
        DataTableResponse<CompanyAccountRes> dataTable = new DataTableResponse<CompanyAccountRes>();
        String sqlCount = DatatableUtils.countForDatatable("company_account", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("company_account",req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<CompanyAccountRes>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , new BeanPropertyRowMapper<>(CompanyAccountRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}
