package coffee.backoffice.finance.repository.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.vo.res.WithdrawConditionRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class WithdrawConditionDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public DataTableResponse<WithdrawConditionRes> WithdrawConditionResPaginate(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		DataTableResponse<WithdrawConditionRes> dataTable = new DataTableResponse<WithdrawConditionRes>();
		String sqlCount = DatatableUtils.countForDatatable("withdraw_condition", req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable("withdraw_condition", req.getPage(), req.getLength(),
				req.getSort(), req.getFilter(), sqlBuilder.toString());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<WithdrawConditionRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				BeanPropertyRowMapper.newInstance(WithdrawConditionRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}
}
