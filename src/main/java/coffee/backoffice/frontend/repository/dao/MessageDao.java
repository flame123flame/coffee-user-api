package coffee.backoffice.frontend.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.vo.res.WithdrawConditionRes;
import coffee.backoffice.frontend.vo.res.InboxMessageRes;
import coffee.backoffice.frontend.vo.res.MessageRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.CommonJdbcTemplate;
import framework.utils.ConvertDateUtils;
import framework.utils.DatatableUtils;

@Repository
public class MessageDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	
	
	public DataTableResponse<MessageRes> MessageResPaginate(DatatableRequest req) {
		StringBuilder sqlBuilder = new StringBuilder();
		DataTableResponse<MessageRes> dataTable = new DataTableResponse<MessageRes>();
		String sqlCount = DatatableUtils.countForDatatable("message", req.getFilter());
		String sqlData = DatatableUtils.limitForDataTable("message", req.getPage(), req.getLength(),
				req.getSort(), req.getFilter(), sqlBuilder.toString());
		List<Object> params = new ArrayList<>();
		Integer count = commonJdbcTemplate.executeQueryForObject(sqlCount, params.toArray(), Integer.class);
		List<MessageRes> data = commonJdbcTemplate.executeQuery(sqlData, params.toArray(),
				BeanPropertyRowMapper.newInstance(MessageRes.class));
		dataTable.setData(data);
		dataTable.setRecordsTotal(count);
		return dataTable;
	}

	public MessageRes findSendMessageByCode(String username) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select * from message sm ");
		sqlBuilder.append(" where message_code = ? ");
		MessageRes dataRes = commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), new Object[] { username },
				rowMapperSendMessage);
		return dataRes;
	}

	private RowMapper<MessageRes> rowMapperSendMessage = new RowMapper<MessageRes>() {
		@Override
		public MessageRes mapRow(ResultSet rs, int arg1) throws SQLException {
			MessageRes vo = new MessageRes();
			vo.setId(rs.getLong("id"));
			vo.setMessageCode(rs.getString("message_code"));
			vo.setGroupCodes(new ArrayList<String>(Arrays.asList(rs.getString("group_codes").split(","))));
			vo.setSubject(rs.getString("subject"));
			vo.setWebMessage(rs.getString("web_message"));
			vo.setCreatedBy(rs.getString("created_by"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(ConvertDateUtils.formatDateToStringEn(tempCD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setPromoCode(rs.getString("promo_code"));
			vo.setMessageType(rs.getString("message_type"));
			return vo;
		}
	};

//
	public List<MessageRes> listFindSendMessage() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" select * from message m ");
		sqlBuilder.append(" order by created_date desc ");
		List<MessageRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				rowMapperInboxMessageList);
		return dataRes;
	}

	private RowMapper<MessageRes> rowMapperInboxMessageList = new RowMapper<MessageRes>() {
		@Override
		public MessageRes mapRow(ResultSet rs, int arg1) throws SQLException {
			MessageRes vo = new MessageRes();
			vo.setId(rs.getLong("id"));
			vo.setMessageCode(rs.getString("message_code"));
			vo.setSubject(rs.getString("subject"));
			vo.setWebMessage(rs.getString("web_message"));
			vo.setCreatedBy(rs.getString("created_by"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(ConvertDateUtils.formatDateToStringEn(tempCD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setPromoCode(rs.getString("promo_code"));
			vo.setMessageType(rs.getString("message_type"));
			return vo;
		}
	};

	public List<InboxMessageRes> listSendMessage(String username) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" select ms.*,msm.status from message ms ");
		sqlBuilder.append(" left join (select * from message_mapping ) msm ");
		sqlBuilder.append(" on msm.message_code = ms.message_code ");
		sqlBuilder.append(" where msm.username = ? ");
		params.add(username);
		sqlBuilder.append(" order by ms.created_date desc ");
		List<InboxMessageRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				rowMapperSendMessageList);
		return dataRes;
	}

	private RowMapper<InboxMessageRes> rowMapperSendMessageList = new RowMapper<InboxMessageRes>() {
		@Override
		public InboxMessageRes mapRow(ResultSet rs, int arg1) throws SQLException {
			InboxMessageRes vo = new InboxMessageRes();
			vo.setId(rs.getLong("id"));
			vo.setMessageCode(rs.getString("message_code"));
			vo.setGroupCode(rs.getString("group_codes"));
			vo.setSubject(rs.getString("subject"));
			vo.setWebMessage(rs.getString("web_message"));
			vo.setCreatedBy(rs.getString("created_by"));
			Date tempCD = rs.getTimestamp("created_date");
			vo.setCreatedDate(ConvertDateUtils.formatDateToStringEn(tempCD, ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			vo.setPromoCode(rs.getString("promo_code"));
			vo.setMessageType(rs.getString("message_type"));
			vo.setStatus(rs.getString("status"));
			return vo;
		}
	};
}
