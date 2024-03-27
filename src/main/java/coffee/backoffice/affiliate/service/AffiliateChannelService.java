package coffee.backoffice.affiliate.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.affiliate.model.AffiliateChannel;
import coffee.backoffice.affiliate.repository.dao.AffiliateChannelDao;
import coffee.backoffice.affiliate.repository.jpa.AffiliateChannelRepository;
import coffee.backoffice.affiliate.vo.res.AffiliateChannelRes;
import coffee.backoffice.affiliate.vo.res.AffiliateGroupRes;

@Service
public class AffiliateChannelService {

	@Autowired
	private AffiliateChannelRepository channelRepository;

	@Autowired
	private AffiliateChannelDao channelDao;
	
	public List<AffiliateChannel> getAffiliateChannelByGroup(String code) {
		List<AffiliateChannel> dataList = channelRepository.findAllByAffiliateGroupCodeAndEnable(code,true);
		return dataList;
	}

	@Transactional
	public List<AffiliateChannel> updateEnableAffiliateChannel(String code) {
		AffiliateChannel data = channelRepository.findByAffiliateChannelCode(code);
		data.setEnable(false);
		channelRepository.save(data);
//		channelRepository.delete(data);

		return getAffiliateChannelByGroup(data.getAffiliateGroupCode());
	}
}
