package coffee.backoffice.cashback.repository.dao;

import coffee.backoffice.cashback.model.CashbackBatchWaiting;
import coffee.backoffice.rebate.model.RebateBatchWaiting;
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
public class CashbackBatchWaitingDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<CashbackBatchWaiting> paginate(DatatableRequest req) {
        DataTableResponse<CashbackBatchWaiting> dataTable = new DataTableResponse<CashbackBatchWaiting>();
        String sqlCount = DatatableUtils.countForDatatable("cashback_batch_waiting", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("cashback_batch_waiting",req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<CashbackBatchWaiting>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , new BeanPropertyRowMapper<>(CashbackBatchWaiting.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}
