package coffee.backoffice.promotion.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import coffee.backoffice.player.vo.res.TagManagementRes;
import coffee.backoffice.promotion.vo.res.PromotionDatatableRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.vo.res.PromotionRes;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PromotionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public DataTableResponse<PromotionDatatableRes> paginate(DatatableRequest req) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT p.* , r.receive_bonus_wallet as receive_bonus_wallet FROM promotion p");
        stringBuilder.append(" left join rule_setting r on p.promo_code = r.promo_code");
        DataTableResponse<PromotionDatatableRes> dataTable = new DataTableResponse<PromotionDatatableRes>();
        String sqlCount = DatatableUtils.countForDatatable(stringBuilder.toString(), req.getFilter());
        String sqlData = DatatableUtils.limitForDataTable(stringBuilder.toString(), req.getPage(), req.getLength(), req.getSort(), req.getFilter());
        List<Object> params = new ArrayList<>();
        Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
        List<PromotionDatatableRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(), BeanPropertyRowMapper.newInstance(PromotionDatatableRes.class));
        dataTable.setData(data);
        dataTable.setRecordsTotal(count);
        return dataTable;
    }

    public Promotion findRegistrationPromoList(String promoCode) {
        log.info("Find List Registration Promotion");
        StringBuilder sqlBuilder = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sqlBuilder.append(" SELECT Top 1 * from promotion pmt ");
        sqlBuilder.append(" where pmt.promo_code = ? ");
        params.add(promoCode);

        sqlBuilder.append(" and pmt.promo_type = ? ");
        params.add(PromotionConstant.Type.registration);

        sqlBuilder.append(" and cast(pmt.start_date as date) <= cast(getdate() as date) ");
        sqlBuilder.append(" and (pmt.promo_period_type = ? ");
        params.add(PromotionConstant.PromoPeriodType.indefinite);

        sqlBuilder.append("  OR  cast(pmt.end_date as date) >= cast(getdate() as date)) and pmt.status = ? ");
        params.add(PromotionConstant.Status.active);

        sqlBuilder.append("order by pmt.created_date desc");
        @SuppressWarnings("deprecation")
        Promotion dataFind = jdbcTemplate.queryForObject(sqlBuilder.toString(), params.toArray(),
                BeanPropertyRowMapper.newInstance(Promotion.class));
        log.info("End Find List Registration Promotion");
        return dataFind;
    }

    public List<PromotionRes> findPromotionByGroupCode(String code) {
        StringBuilder sqlBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sqlBuilder.append(" select ast.promo_code,group_code.value,p.* from app_setting ast ");
        sqlBuilder.append(" outer apply STRING_SPLIT(ast.group_list,',') group_code ");
        sqlBuilder.append(" left join (select * from promotion ) p");
        sqlBuilder.append(" on p.promo_code = ast.promo_code ");
        sqlBuilder.append(" where group_code.value = ?");
        params.add(code);

        @SuppressWarnings("deprecation")
        List<PromotionRes> dataRes = jdbcTemplate.query(sqlBuilder.toString(), params.toArray(), rowMapperCustomerList);
        return dataRes;
    }

    private RowMapper<PromotionRes> rowMapperCustomerList = new RowMapper<PromotionRes>() {
        @Override
        public PromotionRes mapRow(ResultSet rs, int arg1) throws SQLException {
            PromotionRes vo = new PromotionRes();
            vo.setId(rs.getLong("id"));
            vo.setPromoCode(rs.getString("promo_code"));
            vo.setPromoTitle(rs.getString("promo_title"));
            vo.setPromoType(rs.getString("promo_type"));
            vo.setWallet("MAIN WALLET");
            vo.setPromoPeriodType(rs.getString("promo_period_type"));
            vo.setStartDate(rs.getDate("start_date"));
            vo.setEndDate(rs.getDate("end_date"));
            vo.setUpdatedBy(rs.getString("updated_by"));
            vo.setUpdatedDate(rs.getDate("updated_date"));
            vo.setViewStatus(rs.getString("view_status"));
            return vo;
        }
    };
}
