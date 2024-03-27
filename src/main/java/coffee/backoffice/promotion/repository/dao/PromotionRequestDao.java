package coffee.backoffice.promotion.repository.dao;

import coffee.backoffice.promotion.vo.res.PromotionRequestRes;
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
public class PromotionRequestDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<PromotionRequestRes> paginate(DatatableRequest req) {
        DataTableResponse<PromotionRequestRes> dataTable = new DataTableResponse<PromotionRequestRes>();
        String sqlCount = DatatableUtils.countForDatatable("promotion", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("promotion",req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<PromotionRequestRes>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , new BeanPropertyRowMapper<>(PromotionRequestRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}
