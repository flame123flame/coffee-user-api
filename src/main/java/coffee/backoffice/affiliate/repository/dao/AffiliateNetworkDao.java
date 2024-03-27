package coffee.backoffice.affiliate.repository.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.affiliate.model.AffiliateNetwork;
import framework.utils.CommonJdbcTemplate;

@Repository
public class AffiliateNetworkDao {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<AffiliateNetwork> findAffiliateNetworkTwoByCode(String code) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM affiliate_network ");
		sqlBuilder.append(" WHERE affiliate_code_up IN ");
		sqlBuilder.append(" ( ");
		sqlBuilder.append(" SELECT affiliate_code FROM affiliate_network WHERE affiliate_code_up = ? ");
		params.add(code);
		sqlBuilder.append(" ) ");
		List<AffiliateNetwork> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new BeanPropertyRowMapper<>(AffiliateNetwork.class));
		return dataRes;
	}
}
