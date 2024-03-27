package coffee.backoffice.finance.repository.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.vo.res.GameProviderCodeRes;
import coffee.backoffice.finance.vo.res.GameTransactionRes;
import coffee.backoffice.finance.vo.res.GamesListRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.DatatableUtils;

@Repository
public class GameTransactionDao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	public DataTableResponse<GameTransactionRes> paginateAllGameTranSaction(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select tb.id as id, ");
		sqlBuilder.append("tb.game_code as gameCode, ");
		sqlBuilder.append("tb.bet as bet, ");
		sqlBuilder.append("tb.bet_result as bet_result , ");
		sqlBuilder.append("tb.game_session_id as gameSessionId, ");
		sqlBuilder.append("tb.balance as balance , ");
		sqlBuilder.append("tb.username as username , ");
		sqlBuilder.append("tb.created_date as createdDate, ");
		sqlBuilder.append("tb.game_provider as game_provider, ");
		sqlBuilder.append("tb.win_loss as winLoss, ");
		sqlBuilder.append("tb.game_result as gameResult, ");
		sqlBuilder.append("g.display_name as displayName ");
		sqlBuilder.append("from transaction_game tb ");
		sqlBuilder.append("INNER JOIN games g on tb.game_code = g.game_code");
		DataTableResponse<GameTransactionRes> dataTable = new DataTableResponse<GameTransactionRes>();
		String sqlCount = DatatableUtils.countForDatatable(sqlBuilder.toString(), req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable(sqlBuilder.toString(), req.getPage(), req.getLength(),
				req.getSort(), req.getFilter());
		System.out.println(sqlData);
		System.out.println(sqlCount);
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<GameTransactionRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				BeanPropertyRowMapper.newInstance(GameTransactionRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}
	
	 public GameProviderCodeRes findGameProviderByCode(String code) {
	        StringBuilder sqlBuilder = new StringBuilder();
	        List<Object> params = new ArrayList<>();
	        sqlBuilder.append("SELECT gp.name_th as nameTh,gp.name_en nameEn,gp.code as code FROM game_provider gp where gp.code =? ");
	        params.add(code);
	        GameProviderCodeRes res = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(GameProviderCodeRes.class));
	        return res;
	 }
	 
	 public List<GamesListRes> findGameByCode(String code,String groupCode) {
	        StringBuilder sqlBuilder = new StringBuilder();
	        List<Object> params = new ArrayList<>();
	        sqlBuilder.append("SELECT g.name_th as nameTh,g.name_en as nameEn, g.game_product_type_code as gameProductTypeCode,g.display_name as displayName,");
	        sqlBuilder.append("g.game_code as gameCode,g.provider_code as providerCode,");
	        sqlBuilder.append("g.game_group_code as gameGroupCode ");
	        sqlBuilder.append("FROM games g where g.provider_code = ? and g.game_group_code = ? ");
	        params.add(code);
	        params.add(groupCode);
	        List<GamesListRes> res = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(GamesListRes.class));
	        return res;
	 }
	 
	 public List<GamesListRes> findGameByProviderCode(String code) {
	        StringBuilder sqlBuilder = new StringBuilder();
	        List<Object> params = new ArrayList<>();
	        sqlBuilder.append("SELECT g.name_th as nameTh,g.name_en as nameEn, g.game_product_type_code as gameProductTypeCode,g.display_name as displayName,");
	        sqlBuilder.append("g.game_code as gameCode,g.provider_code as providerCode,");
	        sqlBuilder.append("g.game_group_code as gameGroupCode ");
	        sqlBuilder.append("FROM games g where g.provider_code LIKE ? ORDER BY g.display_name asc");
	        params.add("%"+code.trim()+"%");
	        List<GamesListRes> res = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(GamesListRes.class));
	        return res;
	 }
	 
}
