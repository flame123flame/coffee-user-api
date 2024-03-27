package coffee.backoffice.player.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.player.model.FailedLogin;
import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.vo.res.FailedLoginRes;
import coffee.backoffice.player.vo.res.GroupLevelRes;
import coffee.backoffice.player.vo.res.NewRegistrantRes;
import coffee.backoffice.player.vo.res.TagManagementRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;
import framework.utils.GenerateRandomString;

@Repository
public class FailedLoginDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public DataTableResponse<FailedLoginRes> paginate(DatatableRequest req) {
		DataTableResponse<FailedLoginRes> dataTable = new DataTableResponse<FailedLoginRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		String sqlCount = DatatableUtils.countForDatatable("failed_login", req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable("failed_login", req.getPage(), req.getLength(), req.getSort(),
				req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<FailedLoginRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				BeanPropertyRowMapper.newInstance(FailedLoginRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	public List<FailedLogin> findTop1ByUsernameOrderByCreatedDateDesc(String username) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT top 1 fl.*");
		sqlBuilder.append(" FROM failed_login fl");
		sqlBuilder.append(" WHERE 1=1");
		if (username != null) {
			sqlBuilder.append(" AND fl.username LIKE ?");
			params.add("%" + username + "%");
		}
		sqlBuilder.append("  ORDER BY fl.created_date DESC");

		List<FailedLogin> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				BeanPropertyRowMapper.newInstance(FailedLogin.class));
		return dataRes;
	}

}
