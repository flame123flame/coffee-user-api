package coffee.backoffice.rebate.repository.dao;

import coffee.backoffice.rebate.vo.res.RebateConditionRes;
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
public class RebateConditionDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<RebateConditionRes> paginate(DatatableRequest req) {
        DataTableResponse<RebateConditionRes> dataTable = new DataTableResponse<RebateConditionRes>();
        String sqlCount = DatatableUtils.countForDatatable("game_product_type", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("game_product_type",req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<RebateConditionRes>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , new BeanPropertyRowMapper<>(RebateConditionRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}
