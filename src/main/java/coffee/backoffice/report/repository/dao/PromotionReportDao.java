package coffee.backoffice.report.repository.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.report.vo.res.PromotionReportDatatableRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class PromotionReportDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public DataTableResponse<PromotionReportDatatableRes> paginate(DatatableRequest req) {
		DataTableResponse<PromotionReportDatatableRes> dataTable = new DataTableResponse<PromotionReportDatatableRes>();
		StringBuilder select = new StringBuilder();
		select.append(
				"SELECT p.*,ISNULL(reject.count,0) as reject_count, ISNULL(approve.count,0) as approve_count,ISNULL(all_count.count,0) as all_count from promotion p");
		select.append(" left join (");
		select.append(" SELECT COUNT(pr.promo_code) as count , pr.promo_code FROM promotion_request pr");
		select.append(" where pr.status_request ='REJECT' ");
		select.append(" AND pr.created_by BETWEEN '"+req.getFilter().get(0).getValue()+"' and '"+req.getFilter().get(0).getValue1()+"' ");
		select.append(" GROUP by pr.promo_code");
		select.append(" ) as reject on reject.promo_code = p.promo_code");
		select.append(" left join (");
		select.append(" SELECT COUNT(pr.promo_code) as count , pr.promo_code FROM promotion_request pr");
		select.append(" where pr.status_request ='APPROVE'");
		select.append(" AND pr.created_by BETWEEN '"+req.getFilter().get(0).getValue()+"' and '"+req.getFilter().get(0).getValue1()+"' ");
		select.append(" GROUP by pr.promo_code");
		select.append(" ) as approve on approve.promo_code = p.promo_code");
		select.append(" left join (");
		select.append(" SELECT COUNT(pr.promo_code) as count , pr.promo_code FROM promotion_request pr");
		select.append(" GROUP by pr.promo_code");
		select.append(" ) as all_count on all_count.promo_code = p.promo_code");
		String sqlCount = DatatableUtils.countForDatatableReport(select.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTableReport(select.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<PromotionReportDatatableRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				new BeanPropertyRowMapper<>(PromotionReportDatatableRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}
}
