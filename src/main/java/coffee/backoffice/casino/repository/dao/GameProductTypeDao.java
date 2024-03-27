package coffee.backoffice.casino.repository.dao;

import coffee.backoffice.casino.model.GameProductType;
import coffee.backoffice.casino.vo.res.GameProductTypeRes;
import coffee.backoffice.casino.vo.res.GameProductTypeWithProviderRes;
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
public class GameProductTypeDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<GameProductTypeRes> paginate(DatatableRequest req) {
        DataTableResponse<GameProductTypeRes> dataTable = new DataTableResponse<GameProductTypeRes>();
        String sqlData = DatatableUtils.limitForDataTable("game_product_type",req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        String sqlCount = DatatableUtils.countForDatatable("game_product_type", req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<GameProductTypeRes>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , new BeanPropertyRowMapper<>(GameProductTypeRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }


    public List<GameProductType> findDistinctNameEn() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select DISTINCT name_en  from game_product_type gpt ");
        List<Object> params = new ArrayList<>();
        List<GameProductType> data = commonJdbcTemplate.executeQuery(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(GameProductType.class));
        return data;
    }

    public List<GameProductType> findDistinctCodeByNameEn(String name) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select DISTINCT game_provider_code , code  from game_product_type gpt where name_en = ");
        sql.append(" '");
        sql.append(name);
        sql.append("' ");
        String finalSql = sql.toString().replace("%20"," ");
        List<Object> params = new ArrayList<>();
        List<GameProductType> data = commonJdbcTemplate.executeQuery(finalSql, params.toArray(), new BeanPropertyRowMapper<>(GameProductType.class));
        return data;
    }


    public List<GameProductTypeWithProviderRes> getProductTypeWithProvider() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sqlBuilder.append(" SELECT gpt.code as gptCode , gpt.name_en as gptNameEn , gp.code as gpCode , gp.name_en as gpNameEn from game_product_type_mapping_provider pmp");
        sqlBuilder.append(" inner join game_provider gp on gp.code = pmp.provider_code");
        sqlBuilder.append(" inner join game_product_type gpt on gpt.code = pmp.product_code");
        List<GameProductTypeWithProviderRes> res = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(GameProductTypeWithProviderRes.class));
        return res;
    }
}
