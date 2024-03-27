package coffee.backoffice.cashback.repository.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.cashback.vo.res.CashbackConditionRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class CashbackConditionDao {
	@Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<CashbackConditionRes> paginate(DatatableRequest req) {
        DataTableResponse<CashbackConditionRes> dataTable = new DataTableResponse<CashbackConditionRes>();
        String sqlCount = DatatableUtils.countForDatatable("game_product_type", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("game_product_type",req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<CashbackConditionRes>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , new BeanPropertyRowMapper<>(CashbackConditionRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}
