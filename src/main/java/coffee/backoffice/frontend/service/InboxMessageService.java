package coffee.backoffice.frontend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.vo.res.DepositListRes;
import coffee.backoffice.finance.vo.res.WithdrawConditionRes;
import coffee.backoffice.frontend.model.Message;
import coffee.backoffice.frontend.model.MessageMapping;
import coffee.backoffice.frontend.repository.dao.MessageDao;
import coffee.backoffice.frontend.repository.jpa.MessageMappingRepository;
import coffee.backoffice.frontend.repository.jpa.MessageRepository;
import coffee.backoffice.frontend.vo.req.MessageReq;
import coffee.backoffice.frontend.vo.res.InboxMessageRes;
import coffee.backoffice.frontend.vo.res.MessageRes;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.service.PromotionService;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class InboxMessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private MessageMappingRepository mappingRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
    public DataTableResponse<MessageRes> GetmessageService(DatatableRequest req) {
        DataTableResponse<MessageRes> paginateData = messageDao.MessageResPaginate(req);
        DataTableResponse<MessageRes> dataTable = new DataTableResponse<>();
        List<MessageRes> data = paginateData.getData();
        dataTable.setRecordsTotal(paginateData.getRecordsTotal());
        dataTable.setDraw(paginateData.getDraw());
        dataTable.setData(data);
        dataTable.setPage(req.getPage());
        return paginateData;
    }

	public void saveMessage(MessageReq form) {
		Message data = new Message();
		String tempGC;
		if (form.getGroupCodes() != null) {
			tempGC = String.join(",", form.getGroupCodes());
			System.out.println(tempGC);
		} else {
			tempGC = "";
		}

		data.setMessageCode(GenerateRandomString.generate());
		data.setGroupCodes(tempGC);
		data.setSubject(form.getSubject());
		data.setWebMessage(form.getWebMessage());
		data.setCreatedBy(UserLoginUtil.getUsername());
		data.setPromoCode(form.getPromoCode());
		data.setMessageType(form.getMessageType());
		messageRepository.save(data);
		saveMessageMapping(data.getMessageCode(), form.getGroupCodes(), form.getUsernames());
		for (int i = 0; i < form.getUsernames().size(); i++) {
			getMessageByUsernameSocket(form.getUsernames().get(i));
		}
	}

	public List<InboxMessageRes> getMessageByUsernameSocket(String username) {
		List<InboxMessageRes> dataRes = messageDao.listSendMessage(username);
		for (InboxMessageRes temp : dataRes) {
			if (temp.getPromoCode() != null) {
				temp.setPromotion(promotionService.getPromoInboxMessage(temp.getPromoCode()));
			}
		}
		this.simpMessagingTemplate.convertAndSend("/post/" + username, dataRes.get(0));
		return dataRes;
	}

	public void saveMessageMapping(String code, List<String> groupCodes, List<String> usernames) {
		if (groupCodes != null) {
			Set<String> tpUser = new HashSet<String>();
			for (String gc : groupCodes) {
				List<Customer> data = customerRepository.findByGroupCode(gc);
				for (Customer newData : data) {
					tpUser.add(newData.getUsername());
				}
			}
			if (usernames != null) {
				for (String un : usernames) {
					tpUser.add(un);
				}
			}
			MessageMapping mapping;
			for (String username : tpUser) {
				mapping = new MessageMapping();
				mapping.setMessageCode(code);
				mapping.setUsername(username);
				mapping.setStatus("UNREAD");
				mapping.setCreatedBy(UserLoginUtil.getUsername());
				mappingRepository.save(mapping);
			}
		} else {
			MessageMapping data;
			for (String username : usernames) {
				data = new MessageMapping();
				data.setMessageCode(code);
				data.setUsername(username);
				data.setStatus("UNREAD");
				data.setCreatedBy(UserLoginUtil.getUsername());
				mappingRepository.save(data);
			}
		}

	}

	public List<MessageRes> getAllSendMessage() {
		List<MessageRes> dataRes = messageDao.listFindSendMessage();
		return dataRes;
	}

	public MessageRes getSendMessageByCode(String code) {
		MessageRes dataRes = messageDao.findSendMessageByCode(code);
		List<String> tepmUser = new ArrayList<String>();
		List<MessageMapping> dataUsername = mappingRepository.findByMessageCode(code);

		for (MessageMapping dataMapping : dataUsername) {
			tepmUser.add(dataMapping.getUsername());
		}
		dataRes.setUsernames(tepmUser);
		return dataRes;
	}

	@Transactional
	public void deleteMessage(String code) {
		messageRepository.deleteByMessageCode(code);
		mappingRepository.deleteByMessageCode(code);
	}

}
