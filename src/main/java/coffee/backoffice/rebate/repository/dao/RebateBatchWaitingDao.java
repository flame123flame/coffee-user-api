package coffee.backoffice.rebate.repository.dao;

import coffee.backoffice.rebate.model.RebateBatchWaiting;
import coffee.backoffice.rebate.vo.res.RebateRes;
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
public class RebateBatchWaitingDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<RebateBatchWaiting> paginate(DatatableRequest req) {
        DataTableResponse<RebateBatchWaiting> dataTable = new DataTableResponse<RebateBatchWaiting>();
        String sqlCount = DatatableUtils.countForDatatable("rebate_batch_waiting", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("rebate_batch_waiting",req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<RebateBatchWaiting>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , new BeanPropertyRowMapper<>(RebateBatchWaiting.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}
