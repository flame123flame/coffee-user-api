package coffee.backoffice.finance.repository.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.BankBot;
import framework.utils.CommonJdbcTemplate;

@Repository
public class BankBotDao {
	
	@Qualifier("bankBotCommonJdbcTemplate")
	@Autowired
    private CommonJdbcTemplate bankBotCommonJdbcTemplate;

    public List<BankBot> getAll() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM Tx  ");
        List<BankBot> res = bankBotCommonJdbcTemplate.executeQuery(sqlBuilder.toString(), BeanPropertyRowMapper.newInstance(BankBot.class));
        return res;
    }
	
	public List<BankBot> getTypeAfterDate(String type,Date after) {
        StringBuilder sqlBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sqlBuilder.append(" SELECT * FROM Tx  WHERE TxType LIKE ");
        sqlBuilder.append(" '%"+type+"%' ");
        sqlBuilder.append(" AND TxDateTime > ? ");
        params.add(after);
        List<BankBot> res = bankBotCommonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(BankBot.class));
        return res;
    }
	
}
