package coffee.backoffice.casino.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.vo.res.GameGroupRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class GameGroupDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public DataTableResponse<GameGroupRes> paginate(DatatableRequest req) {
		DataTableResponse<GameGroupRes> dataTable = new DataTableResponse<GameGroupRes>();
		String sqlData = DatatableUtils.limitForDataTable("game_group", req.getPage(), req.getLength(), req.getSort(),
				req.getFilter());
		String sqlCount = DatatableUtils.countForDatatable("game_group", req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<GameGroupRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(), rowMapperGameGroupList);
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	private RowMapper<GameGroupRes> rowMapperGameGroupList = new RowMapper<GameGroupRes>() {
		@Override
		public GameGroupRes mapRow(ResultSet rs, int arg1) throws SQLException {
			GameGroupRes vo = new GameGroupRes();
			vo.setId(rs.getLong("id"));
			vo.setCode(rs.getString("code"));
			vo.setNameEn(rs.getString("name_en"));
			vo.setNameTh(rs.getString("name_th"));
			Date tempCD = rs.getTimestamp("created_at");
			vo.setCreatedAt(tempCD);
			vo.setUpdatedBy(rs.getString("updated_by"));
			Date tempUD = rs.getTimestamp("updated_At");
			vo.setUpdatedAt(tempUD);
			List<String> temp = new ArrayList<String>();
			if (StringUtils.isNotBlank(rs.getString("product_code"))) {
				temp = Arrays.asList(rs.getString("product_code").split(","));
			} else {
				temp = null;
			}
			vo.setProductCode(temp);
			return vo;
		}
	};

	public GameGroupRes findGroupByCode(String code) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select * from game_group where code = ?");
		GameGroupRes res = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), new Object[] { code },
				rowMapperGroup);
		return res;
	}

	private RowMapper<GameGroupRes> rowMapperGroup = new RowMapper<GameGroupRes>() {
		@Override
		public GameGroupRes mapRow(ResultSet rs, int arg1) throws SQLException {
			GameGroupRes vo = new GameGroupRes();
			vo.setId(rs.getLong("id"));
			vo.setCode(rs.getString("code"));
			vo.setNameEn(rs.getString("name_en"));
			vo.setNameTh(rs.getString("name_th"));
			Date tempCD = rs.getTimestamp("created_at");
			vo.setCreatedAt(tempCD);
			vo.setUpdatedBy(rs.getString("updated_by"));
			Date tempUD = rs.getTimestamp("updated_At");
			vo.setUpdatedAt(tempUD);
			List<String> temp = new ArrayList<String>();
			if (StringUtils.isNotBlank(rs.getString("product_code"))) {
				temp = Arrays.asList(rs.getString("product_code").split(","));
			} else {
				temp = null;
			}
			vo.setProductCode(temp);
			return vo;
		}
	};
}
