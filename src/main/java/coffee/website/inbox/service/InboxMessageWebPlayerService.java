package coffee.website.inbox.service;

import coffee.backoffice.frontend.model.MessageMapping;
import coffee.backoffice.frontend.repository.dao.MessageDao;
import coffee.backoffice.frontend.repository.jpa.MessageMappingRepository;
import coffee.backoffice.frontend.vo.req.MessageReq;
import coffee.backoffice.frontend.vo.res.InboxMessageRes;
import coffee.backoffice.promotion.service.PromotionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InboxMessageWebPlayerService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessageMappingRepository mappingRepository;

    @Autowired
    private PromotionService promotionService;


    public void triggerMessage(MessageReq form) {
        MessageMapping temp = mappingRepository.findByMessageCodeAndUsername(form.getMessageCode(), form.getUsername());
        if (temp != null) {
            temp.setStatus("READ");
            mappingRepository.save(temp);
        }
    }

    public List<InboxMessageRes> getMessageByUsername(String username) {
        List<InboxMessageRes> dataRes = messageDao.listSendMessage(username);
        for (InboxMessageRes temp : dataRes) {
            if (temp.getPromoCode() != null) {
                temp.setPromotion(promotionService.getPromoInboxMessage(temp.getPromoCode()));
            }
        }
        return dataRes;
    }

    @Transactional
    public void deletInboxMessage(String code, String username) throws Exception {
        if (StringUtils.isBlank(code) && StringUtils.isNotBlank(username)) {
            mappingRepository.deleteByUsername(username);
        } else if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(username)) {
            mappingRepository.deleteByMessageCodeAndUsername(code, username);
        } else {
            throw new Exception();
        }
    }

}
