package coffee.backoffice.player.repository.dao;

import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.DatatableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.player.vo.res.TagManagementRes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import framework.utils.CommonJdbcTemplate;


@Repository
public class TagManagementDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<TagManagementRes> paginate(DatatableRequest req) {
        DataTableResponse<TagManagementRes> dataTable = new DataTableResponse<TagManagementRes>();
        String sqlCount = DatatableUtils.countForDatatable("tag_management", req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable("tag_management",req.getPage(), req.getLength(), req.getSort(),req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray() ,Integer.class);
        List<TagManagementRes>  data = commonJdbcTemplate.executeQuery(sqlData, params.toArray() , rowMapper);
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    private RowMapper<TagManagementRes> rowMapper = new RowMapper<TagManagementRes>() {
        @Override
        public TagManagementRes mapRow(ResultSet rs, int arg1) throws SQLException {
            TagManagementRes vo = new TagManagementRes();
            vo.setCreatedAt(rs.getString("created_at"));
            vo.setDescription(rs.getString("description"));
            vo.setId(rs.getLong("id"));
            vo.setName(rs.getString("name"));
            vo.setRemark(rs.getString("remark"));
            vo.setTotalPlayers(rs.getLong("total_players"));
            vo.setUpdatedAt(rs.getString("updated_at"));
            vo.setUpdatedBy(rs.getString("updated_by"));
            return vo;
        }
    };
}
