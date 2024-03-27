package coffee.backoffice.finance.repository.dao;

import coffee.backoffice.finance.vo.res.ManualAdjustmentRes;
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
public class ManualAdjustmentDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<ManualAdjustmentRes> paginate(DatatableRequest req) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * from manual_adjustment mj");
        DataTableResponse<ManualAdjustmentRes> dataTable = new DataTableResponse<ManualAdjustmentRes>();
        String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
                req.getSort(), req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<ManualAdjustmentRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                BeanPropertyRowMapper.newInstance(ManualAdjustmentRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}
