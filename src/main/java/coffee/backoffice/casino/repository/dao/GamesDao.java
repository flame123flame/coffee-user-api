package coffee.backoffice.casino.repository.dao;

import java.util.ArrayList;
import java.util.List;

import coffee.backoffice.casino.vo.res.GamesDatatableRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.vo.res.GamesRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class GamesDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<GamesRes> paginate(DatatableRequest req) {
        DataTableResponse<GamesRes> dataTable = new DataTableResponse<GamesRes>();
        StringBuilder join = new StringBuilder();
        join.append(" LEFT JOIN game_group gg ON gg.code = tb.game_group_code ");
        join.append(" LEFT JOIN game_product_type gpt ON gpt.code = gg.game_product_type_code ");
        join.append(" LEFT JOIN game_provider gp ON gp.code = gpt.game_provider_code ");
        String sqlCount = DatatableUtils.countForDatatable("games", req.getFilter(), join.toString());
        String sqlData = DatatableUtils.limitForDataTable("games", req.getPage(), req.getLength(), req.getSort(),
                req.getFilter(), join.toString());
        System.out.println(sqlData);
        System.out.println(sqlData.toString());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<GamesRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(GamesRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public DataTableResponse<GamesRes> getPaginateGames(DatatableRequest req) {
        DataTableResponse<GamesRes> dataTable = new DataTableResponse<GamesRes>();
        StringBuilder join = new StringBuilder();
        String sqlData = DatatableUtils.limitForDataTable("games", req.getPage(), req.getLength(), req.getSort(),
                req.getFilter(), join.toString());
        String sqlCount = DatatableUtils.countForDatatable("games", req.getFilter(), join.toString());
        System.out.println(sqlData.toString());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<GamesRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(GamesRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public DataTableResponse<GamesDatatableRes> getPaginateGamesList(DatatableRequest req) {
        DataTableResponse<GamesDatatableRes> dataTable = new DataTableResponse<GamesDatatableRes>();
        StringBuilder select = new StringBuilder();
        select.append("select");
        select.append(" g.id ,");
        select.append(" g.name_th ,");
        select.append(" g.created_at ,");
        select.append(" g.updated_at ,");
        select.append(" g.updated_by ,");
        select.append(" g.name_en ,");
        select.append(" g.game_product_type_code ,");
        select.append(" g.display_name ,");
        select.append(" g.status ,");
        select.append(" g.remark ,");
        select.append(" g.image1 ,");
        select.append(" g.image2 ,");
        select.append(" g.game_code ,");
        select.append(" g.enable ,");
        select.append(" g.provider_code ,");
        select.append(" g.game_group_code,");
        select.append(" gp.name_en as provider_name,");
        select.append(" gpt.name_en as product_type_name");
        select.append(" from games g");
        select.append(" left join game_provider gp on gp .code = g.provider_code");
        select.append(" left join game_product_type gpt on gpt .code = g.game_product_type_code");
        String sqlData = DatatableUtils.limitForDataTable(select.toString(), req.getPage(), req.getLength(), req.getSort(),
                req.getFilter());
        String sqlCount = DatatableUtils.countForDatatable(select.toString(), req.getFilter());
        System.out.println(sqlData.toString());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<GamesDatatableRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(GamesDatatableRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public List<GamesRes> findAllByProductType(String code) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" SELECT");
        stringBuilder.append(" g.id, g.name_th, g.created_at,g.provider_code, g.updated_at, g.name_en, g.game_product_type_code, g.display_name, g.status, g.game_code, g.enable, g.game_group_code , gp.name_en as game_provider_name_en , gpt.name_en  as game_product_type_name_en");
        stringBuilder.append(" FROM games g");
        stringBuilder.append(" inner join game_provider gp on gp.code = g.provider_code");
        stringBuilder.append(" inner join game_product_type gpt on gpt.code = g.game_product_type_code");
        stringBuilder.append(" where g.game_product_type_code = ?");
        List<Object> params = new ArrayList<>();
        params.add(code);
        List<GamesRes> data = commonJdbcTemplate.executeQuery(stringBuilder.toString(), params.toArray(),
                new BeanPropertyRowMapper<>(GamesRes.class));
        return data;
    }
}
