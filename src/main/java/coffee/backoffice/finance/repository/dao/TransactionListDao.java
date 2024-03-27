package coffee.backoffice.finance.repository.dao;

import coffee.backoffice.finance.model.TransactionList;
import framework.utils.CommonJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class TransactionListDao {
    @Autowired
    private CommonJdbcTemplate commonJdbcTemplate;

    public List<TransactionList> getTransactionListByUsername(Date from, Date to, String username , String type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" select * from transaction_list tl");
        stringBuilder.append(" WHERE");
        stringBuilder.append(" transaction_date >= ? and");
        stringBuilder.append(" transaction_date <= ? and");
        stringBuilder.append(" username = ? and");
        stringBuilder.append(" transaction_type = ? and");
        stringBuilder.append(" status = 'SUCCESS'");
        stringBuilder.append(" ORDER by created_date asc");

        List<Object> param = new ArrayList<>();
        param.add(from);
        param.add(to);
        param.add(username);
        param.add(type);
        return commonJdbcTemplate.executeQuery(stringBuilder.toString(), param.toArray(),
                new BeanPropertyRowMapper<>(TransactionList.class));
    }
}
