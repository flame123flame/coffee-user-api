package coffee.backoffice.casino.repository.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.GameProvider;
import coffee.backoffice.casino.vo.res.GameProviderRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class GameProviderDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<GameProviderRes> paginate(DatatableRequest req) {
        DataTableResponse<GameProviderRes> dataTable = new DataTableResponse<GameProviderRes>();
        StringBuilder select = new StringBuilder();
        select.append("select ");
        select.append(" gp.id ,");
        select.append(" gp.name_th ,");
        select.append(" gp.created_at ,");
        select.append(" gp.updated_at ,");
        select.append(" gp.updated_by ,");
        select.append(" gp.name_en ,");
        select.append(" gp.code ,");
        select.append(" gp.status_view ,");
        select.append(" gp.created_by ,");
        select.append(" gp.open_type ");
        select.append(" from game_provider gp");
        String sqlData = DatatableUtils.limitForDataTable(select.toString(), req.getPage(), req.getLength(), req.getSort(), req.getFilter());
        String sqlCount = DatatableUtils.countForDatatable(select.toString(), req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<GameProviderRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(), new BeanPropertyRowMapper<>(GameProviderRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public DataTableResponse<GameProviderRes> paginateNotJoin(DatatableRequest req) {
        DataTableResponse<GameProviderRes> dataTable = new DataTableResponse<GameProviderRes>();
        String sqlCount = DatatableUtils.countForDatatable("game_provider", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("game_provider", req.getPage(), req.getLength(), req.getSort(), req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<GameProviderRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(), new BeanPropertyRowMapper<>(GameProviderRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public List<GameProvider> findGameProviderByProductTypeCode(String productTypeCode) {
        StringBuilder sqlBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sqlBuilder.append(" SELECT gp.* from game_product_type gpt ");
        sqlBuilder.append(" join game_provider gp on ");
        sqlBuilder.append(" gpt.game_provider_code = gp.code ");
        sqlBuilder.append(" WHERE gpt.code = ? ");
        params.add(productTypeCode);
        List<GameProvider> res = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(GameProvider.class));
        return res;
    }
}
