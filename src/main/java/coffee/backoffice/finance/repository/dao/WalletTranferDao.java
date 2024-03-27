package coffee.backoffice.finance.repository.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.WalletTransfer;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class WalletTranferDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	
	public DataTableResponse<WalletTransfer> paginateAllWalletTranfer(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select tb.* from wallet_transfer tb");
		DataTableResponse<WalletTransfer> dataTable = new DataTableResponse<WalletTransfer>();
		String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		System.out.println(sqlData);
		System.out.println(sqlCount);
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<WalletTransfer> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				BeanPropertyRowMapper.newInstance(WalletTransfer.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}
}
