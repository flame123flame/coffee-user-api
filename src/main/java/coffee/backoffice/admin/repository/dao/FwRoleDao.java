package coffee.backoffice.admin.repository.dao;

import coffee.backoffice.admin.vo.res.FwRoleRes;
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
public class FwRoleDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;


    public DataTableResponse<FwRoleRes> paginate(DatatableRequest req) {
        DataTableResponse<FwRoleRes> dataTable = new DataTableResponse<FwRoleRes>();
        String sqlCount = DatatableUtils.countForDatatable("fw_role", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("fw_role", req.getPage(), req.getLength(),
                req.getSort(), req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<FwRoleRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                BeanPropertyRowMapper.newInstance(FwRoleRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}
