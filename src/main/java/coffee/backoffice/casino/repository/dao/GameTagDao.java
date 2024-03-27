package coffee.backoffice.casino.repository.dao;

import coffee.backoffice.casino.vo.res.GameTagRes;
import coffee.backoffice.casino.vo.res.GamesRes;
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
public class GameTagDao {

    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<GameTagRes> getPaginateGames(DatatableRequest req) {
        DataTableResponse<GameTagRes> dataTable = new DataTableResponse<GameTagRes>();
        StringBuilder join = new StringBuilder();
        String sqlData = DatatableUtils.limitForDataTable("game_tag", req.getPage(), req.getLength(), req.getSort(),
                req.getFilter(), join.toString());
        String sqlCount = DatatableUtils.countForDatatable("game_tag", req.getFilter(), join.toString());
        System.out.println(sqlData.toString());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<GameTagRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
                new BeanPropertyRowMapper<>(GameTagRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

}
