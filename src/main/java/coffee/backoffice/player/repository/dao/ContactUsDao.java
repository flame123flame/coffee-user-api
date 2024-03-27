package coffee.backoffice.player.repository.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.vo.res.WithdrawConditionRes;
import coffee.backoffice.player.model.ContactUs;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class ContactUsDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public DataTableResponse<ContactUs> contactUsPaginate(DatatableRequest req) {
		DataTableResponse<ContactUs> dataTable = new DataTableResponse<ContactUs>();
		String sqlCount = DatatableUtils.countForDatatable("contact_us", req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable("contact_us", req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<ContactUs> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				BeanPropertyRowMapper.newInstance(ContactUs.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}
}
