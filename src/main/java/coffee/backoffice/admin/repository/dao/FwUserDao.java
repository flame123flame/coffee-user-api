package coffee.backoffice.admin.repository.dao;

import coffee.backoffice.admin.vo.res.FwUserRes;
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
public class FwUserDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<FwUserRes> paginate(DatatableRequest req) {
        DataTableResponse<FwUserRes> dataTable = new DataTableResponse<FwUserRes>();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT");
        stringBuilder.append(" DISTINCT fu.username , fu.fw_users_id , fu.password , fu.last_login_time , fu.last_login_ip , fu.is_disable , fu.is_active , fu.created_by , fu.created_date , fu.updated_by , fu.updated_date , fu.fw_user_role_code");
        stringBuilder.append(" from");
        stringBuilder.append(" fw_user fu");
        stringBuilder.append(" left join fw_user_role_mapping furm on");
        stringBuilder.append(" furm .username = fu.username");
        String sqlCount = DatatableUtils.countForDatatable(stringBuilder.toString(), req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable(stringBuilder.toString(), req.getPage(), req.getLength(),
                req.getSort(), req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<FwUserRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                BeanPropertyRowMapper.newInstance(FwUserRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}