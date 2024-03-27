package coffee.backoffice.cashback.repository.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import coffee.backoffice.cashback.vo.res.CashbackStatMonthDailyRes;
import coffee.backoffice.cashback.vo.res.CashbackStatTitleRes;
import coffee.provider.joker.vo.model.TransactionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.cashback.vo.res.CashbackRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class CashbackDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<CashbackRes> paginate(DatatableRequest req) {
        DataTableResponse<CashbackRes> dataTable = new DataTableResponse<CashbackRes>();
        String sqlCount = DatatableUtils.countForDatatable("cashback", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("cashback", req.getPage(), req.getLength(), req.getSort(),
                req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<CashbackRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(CashbackRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public DataTableResponse<CashbackStatTitleRes> paginateCashbackStatTitle(DatatableRequest req) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ch.cashback_title , sum(ABS(ch.total_loss)) as total_loss, sum(ch.actual_cashback) as actual_cashback, sum(ch.original_cashback) as original_cashback from cashback_history ch");
        String groupBy = " group by cashback_title";

        DataTableResponse<CashbackStatTitleRes> dataTable = new DataTableResponse<CashbackStatTitleRes>();
        String sqlCount = DatatableUtils.countForDatatableGroupBy(stringBuilder.toString(), req.getFilter(),groupBy);
        String sqlData = DatatableUtils.limitForDataTableGroupBy(stringBuilder.toString(), req.getPage(), req.getLength(), req.getSort(),
                req.getFilter(),groupBy);
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<CashbackStatTitleRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(CashbackStatTitleRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public DataTableResponse<CashbackStatMonthDailyRes> paginateCashbackStatMonth(DatatableRequest req) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select format (created_date, 'yyyy-MM')as date_time,");
        stringBuilder.append(" sum(total_loss) as total_loss , sum(original_cashback ) as original_cashback , sum(actual_cashback ) as actual_cashback , COUNT(format(created_date, 'yyyy-MM')) as count");
        stringBuilder.append(" from cashback_history ch");
        String groupBy = " group by format(created_date, 'yyyy-MM')";

        DataTableResponse<CashbackStatMonthDailyRes> dataTable = new DataTableResponse<CashbackStatMonthDailyRes>();
        String sqlCount = DatatableUtils.countForDatatableGroupBy(stringBuilder.toString(), req.getFilter(),groupBy);
        String sqlData = DatatableUtils.limitForDataTableGroupBy(stringBuilder.toString(), req.getPage(), req.getLength(), req.getSort(),
                req.getFilter(),groupBy);
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<CashbackStatMonthDailyRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(CashbackStatMonthDailyRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public DataTableResponse<CashbackStatMonthDailyRes> paginateCashbackStatDaily(DatatableRequest req) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select format (created_date, 'yyyy-MM-dd')as date_time,");
        stringBuilder.append(" sum(total_loss) as total_loss , sum(original_cashback ) as original_cashback , sum(actual_cashback ) as actual_cashback , COUNT(format(created_date, 'yyyy-MM-dd')) as count");
        stringBuilder.append(" from cashback_history ch");
        String groupBy = " group by format(created_date, 'yyyy-MM-dd')";

        DataTableResponse<CashbackStatMonthDailyRes> dataTable = new DataTableResponse<CashbackStatMonthDailyRes>();
        String sqlCount = DatatableUtils.countForDatatableGroupBy(stringBuilder.toString(), req.getFilter(),groupBy);
        String sqlData = DatatableUtils.limitForDataTableGroupBy(stringBuilder.toString(), req.getPage(), req.getLength(), req.getSort(),
                req.getFilter(), groupBy);
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<CashbackStatMonthDailyRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(CashbackStatMonthDailyRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

}
