package coffee.backoffice.rebate.repository.dao;

import coffee.backoffice.rebate.vo.res.RebateStatMonthDailyRes;
import coffee.backoffice.rebate.vo.res.RebateRes;
import coffee.backoffice.rebate.vo.res.RebateStatTitleRes;
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
public class RebateDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<RebateRes> paginate(DatatableRequest req) {
        DataTableResponse<RebateRes> dataTable = new DataTableResponse<RebateRes>();
        String sqlCount = DatatableUtils.countForDatatable("rebate", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("rebate", req.getPage(), req.getLength(), req.getSort(), req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<RebateRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(), new BeanPropertyRowMapper<>(RebateRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }


    public DataTableResponse<RebateStatTitleRes> paginateRebateStatTitle(DatatableRequest req) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT");
        stringBuilder.append(" rh.rebate_title as title,");
        stringBuilder.append(" sum(rh.valid_bets) as valid_bets,");
        stringBuilder.append(" sum(rh.original_rebate) as original_rebate,");
        stringBuilder.append(" sum(rh.actual_rebate) as actual_rebate");
        stringBuilder.append(" from rebate_history rh");
        String groupBy = " group by rh.rebate_title";


        DataTableResponse<RebateStatTitleRes> dataTable = new DataTableResponse<RebateStatTitleRes>();
        String sqlCount = DatatableUtils.countForDatatableGroupBy(stringBuilder.toString(), req.getFilter(), groupBy);
        String sqlData = DatatableUtils.limitForDataTableGroupBy(stringBuilder.toString(), req.getPage(), req.getLength(), req.getSort(),
                req.getFilter(), groupBy);
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<RebateStatTitleRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(RebateStatTitleRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public DataTableResponse<RebateStatMonthDailyRes> paginateRebateStatMonth(DatatableRequest req) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT");
        stringBuilder.append(" format (rh.created_date, 'yyyy-MM')as date_time,");
        stringBuilder.append(" sum(rh.valid_bets ) as valid_bets ,");
        stringBuilder.append(" sum(rh.original_rebate ) as original_rebate ,");
        stringBuilder.append(" sum(rh.actual_rebate ) as actual_rebate ,");
        stringBuilder.append(" count(format (rh.created_date, 'yyyy-MM')) as count");
        stringBuilder.append(" from rebate_history rh");
        String groupBy = " group by format(rh.created_date, 'yyyy-MM')";

        DataTableResponse<RebateStatMonthDailyRes> dataTable = new DataTableResponse<RebateStatMonthDailyRes>();
        String sqlCount = DatatableUtils.countForDatatableGroupBy(stringBuilder.toString(), req.getFilter(), groupBy);
        String sqlData = DatatableUtils.limitForDataTableGroupBy(stringBuilder.toString(), req.getPage(), req.getLength(), req.getSort(),
                req.getFilter(), groupBy);
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<RebateStatMonthDailyRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(RebateStatMonthDailyRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public DataTableResponse<RebateStatMonthDailyRes> paginateRebateStatDaily(DatatableRequest req) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT");
        stringBuilder.append(" format (rh.created_date, 'yyyy-MM-dd')as date_time,");
        stringBuilder.append(" sum(rh.valid_bets ) as valid_bets ,");
        stringBuilder.append(" sum(rh.original_rebate ) as original_rebate ,");
        stringBuilder.append(" sum(rh.actual_rebate ) as actual_rebate ,");
        stringBuilder.append(" count(format (rh.created_date, 'yyyy-MM-dd')) as count");
        stringBuilder.append(" from rebate_history rh");
        String groupBy = " group by format(rh.created_date, 'yyyy-MM-dd')";

        DataTableResponse<RebateStatMonthDailyRes> dataTable = new DataTableResponse<RebateStatMonthDailyRes>();
        String sqlCount = DatatableUtils.countForDatatableGroupBy(stringBuilder.toString(), req.getFilter(), groupBy);
        String sqlData = DatatableUtils.limitForDataTableGroupBy(stringBuilder.toString(), req.getPage(), req.getLength(), req.getSort(),
                req.getFilter(), groupBy);
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<RebateStatMonthDailyRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(RebateStatMonthDailyRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }
}
