package coffee.backoffice.affiliate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.affiliate.model.AffiliateChannel;
import coffee.backoffice.affiliate.model.AffiliateGroup;
import coffee.backoffice.affiliate.repository.dao.AffiliateChannelDao;
import coffee.backoffice.affiliate.repository.dao.AffiliateGroupDao;
import coffee.backoffice.affiliate.repository.jpa.AffiliateChannelRepository;
import coffee.backoffice.affiliate.repository.jpa.AffiliateGroupRepository;
import coffee.backoffice.affiliate.vo.req.AffiliateChannelReq;
import coffee.backoffice.affiliate.vo.req.AffiliateGroupReq;
import coffee.backoffice.affiliate.vo.res.AffiliateChannelRes;
import coffee.backoffice.affiliate.vo.res.AffiliateGroupRes;
import framework.utils.ConvertDateUtils;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class AffiliateGroupService {

	@Autowired
	private AffiliateGroupRepository groupRepository;

	@Autowired
	private AffiliateGroupDao groupDao;

	@Autowired
	private AffiliateChannelRepository channelRepository;

	@Autowired
	private AffiliateChannelDao channelDao;

	public void saveAffiliateGroup(AffiliateGroupReq form) {
		AffiliateGroup data = new AffiliateGroup();
		data.setAffiliateGroupCode(GenerateRandomString.generate());
		data.setGroupName(form.getGroupName());
		data.setDescription(form.getDescription());
		data.setWithdrawCondition(form.getWithdrawCondition());
		data.setMinTotalBets(form.getMinTotalBets());
		data.setMinAffiliateCount(form.getMinAffiliateCount());
		data.setMinTotalIncome(form.getMinTotalIncome());
		data.setCreatedBy(UserLoginUtil.getUsername());
		groupRepository.save(data);

		AffiliateChannel dataSet;
		for (AffiliateChannelReq dataAc : form.getChannelList()) {
			dataSet = new AffiliateChannel();
			dataSet.setAffiliateChannelCode(GenerateRandomString.generate());
			dataSet.setAffiliateGroupCode(data.getAffiliateGroupCode());
			dataSet.setChannelName(dataAc.getChannelName());
			dataSet.setProductTypeCode(dataAc.getProductType());
			dataSet.setShareRateOne(dataAc.getShareRateOne());
			dataSet.setShareRateTwo(dataAc.getShareRateTwo());
			dataSet.setRemark(dataAc.getRemark());
			dataSet.setCreatedBy(UserLoginUtil.getUsername());

			dataSet.setProviderCode(dataAc.getProvider());
			dataSet.setGameGroupCode(dataAc.getGameGroup());
			channelRepository.save(dataSet);
		}
	}

	public void editAffiliateGroup(AffiliateGroupReq form) {
		AffiliateGroup data = groupRepository.findByAffiliateGroupCode(form.getAffiliateGroupCode());
		data.setGroupName(form.getGroupName());
		data.setDescription(form.getDescription());
		data.setWithdrawCondition(form.getWithdrawCondition());
		data.setMinTotalBets(form.getMinTotalBets());
		data.setMinAffiliateCount(form.getMinAffiliateCount());
		data.setMinTotalIncome(form.getMinTotalIncome());
		data.setUpdatedBy(UserLoginUtil.getUsername());
		data.setUpdatedDate(new Date());
		groupRepository.save(data);

		AffiliateChannel dataSet;
		for (AffiliateChannelReq dataAc : form.getChannelList()) {
			dataSet = channelRepository.findByAffiliateChannelCode(dataAc.getAffiliateChannelCode());
			if (dataSet == null) {
				dataSet = new AffiliateChannel();
				dataSet.setAffiliateChannelCode(GenerateRandomString.generate());
				dataSet.setAffiliateGroupCode(data.getAffiliateGroupCode());
				dataSet.setChannelName(dataAc.getChannelName());
				dataSet.setProductTypeCode(dataAc.getProductType());
				dataSet.setShareRateOne(dataAc.getShareRateOne());
				dataSet.setShareRateTwo(dataAc.getShareRateTwo());
				dataSet.setRemark(dataAc.getRemark());
				dataSet.setCreatedBy(UserLoginUtil.getUsername());

				dataSet.setProviderCode(dataAc.getProvider());
				dataSet.setGameGroupCode(dataAc.getGameGroup());
			} else {
				dataSet.setAffiliateGroupCode(data.getAffiliateGroupCode());
				dataSet.setChannelName(dataAc.getChannelName());
				dataSet.setProductTypeCode(dataAc.getProductType());
				dataSet.setShareRateOne(dataAc.getShareRateOne());
				dataSet.setShareRateTwo(dataAc.getShareRateTwo());
				dataSet.setRemark(dataAc.getRemark());
				dataSet.setUpdatedBy(UserLoginUtil.getUsername());
				dataSet.setUpdatedDate(new Date());

				dataSet.setProviderCode(dataAc.getProvider());
				dataSet.setGameGroupCode(dataAc.getGameGroup());
			}
			channelRepository.save(dataSet);
		}
	}

	public AffiliateGroupRes getAffiliateGroupByCode(String code) {
		AffiliateGroup dataAg = groupRepository.findByAffiliateGroupCodeAndEnable(code, true);
		AffiliateGroupRes dataRes = new AffiliateGroupRes();
		dataRes.setId(dataAg.getId());
		dataRes.setAffiliateGroupCode(dataAg.getAffiliateGroupCode());
		dataRes.setGroupName(dataAg.getGroupName());
		dataRes.setDescription(dataAg.getDescription());
		dataRes.setWithdrawCondition(dataAg.getWithdrawCondition());
		dataRes.setMinTotalBets(dataAg.getMinTotalBets());
		dataRes.setMinAffiliateCount(dataAg.getMinAffiliateCount());
		dataRes.setMinTotalIncome(dataAg.getMinTotalIncome());
		dataRes.setCreatedBy(dataAg.getCreatedBy());
		dataRes.setCreatedDate(
				ConvertDateUtils.formatDateToStringEn(dataAg.getCreatedDate(), ConvertDateUtils.YYYY_MM_DD_HHMMSS));
		dataRes.setUpdatedDate(
				ConvertDateUtils.formatDateToStringEn(dataAg.getUpdatedDate(), ConvertDateUtils.YYYY_MM_DD_HHMMSS));
		dataRes.setUpdatedBy(dataAg.getUpdatedBy());
		dataRes.setEnable(dataAg.getEnable());

		List<AffiliateChannel> dataAc = channelRepository.findAllByAffiliateGroupCodeAndEnable(code, true);
		List<AffiliateChannelRes> channelList = new ArrayList<AffiliateChannelRes>();
		AffiliateChannelRes channelSet;
		for (AffiliateChannel dataGet : dataAc) {
			channelSet = new AffiliateChannelRes();
			channelSet.setId(dataGet.getId());
			channelSet.setAffiliateChannelCode(dataGet.getAffiliateChannelCode());
			channelSet.setAffiliateGroupCode(dataGet.getAffiliateGroupCode());
			channelSet.setChannelName(dataGet.getChannelName());
			channelSet.setProductTypeCode(dataGet.getProductTypeCode());
			channelSet.setShareRateOne(dataGet.getShareRateOne());
			channelSet.setShareRateTwo(dataGet.getShareRateTwo());
			channelSet.setProviderCode(dataGet.getProviderCode());
			channelSet.setGameGroupCode(dataGet.getGameGroupCode());
			channelSet.setRemark(dataGet.getRemark());
			channelSet.setCreatedDate(
					ConvertDateUtils.formatDateToStringEn(dataAg.getCreatedDate(), ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			channelSet.setCreatedBy(dataAg.getCreatedBy());
			channelSet.setUpdatedDate(
					ConvertDateUtils.formatDateToStringEn(dataAg.getUpdatedDate(), ConvertDateUtils.YYYY_MM_DD_HHMMSS));
			channelSet.setUpdatedBy(dataAg.getUpdatedBy());
			channelSet.setEnable(dataAg.getEnable());
			channelList.add(channelSet);

		}
		dataRes.setChannelList(channelList);
		return dataRes;
	}

	public List<AffiliateGroupRes> getAffiliateGroupList() {
		List<AffiliateGroupRes> dataRes = new ArrayList<AffiliateGroupRes>();
		List<AffiliateChannelRes> listCh;

		List<AffiliateGroupRes> dataAgFind = groupDao.listFindAffiliateGroup();
		for (AffiliateGroupRes getAG : dataAgFind) {
			listCh = new ArrayList<AffiliateChannelRes>();
			List<AffiliateChannelRes> dataAcFind = channelDao
					.listFindAffiliateChannelByCode(getAG.getAffiliateGroupCode());
			for (AffiliateChannelRes getAc : dataAcFind) {
				listCh.add(getAc);
			}
			getAG.setChannelList(listCh);
			dataRes.add(getAG);
		}
		return dataRes;
	}

	@Transactional
	public void updateEnableAffiliateGroupByCode(String code) {
		AffiliateGroup data = groupRepository.findByAffiliateGroupCode(code);
		data.setEnable(false);
		groupRepository.save(data);
		List<AffiliateChannel> dataAc = channelRepository.findAllByAffiliateGroupCodeAndEnable(code, true);
		AffiliateChannel dataSet;
		for (AffiliateChannel getAc : dataAc) {
			dataSet = channelRepository.findByAffiliateChannelCode(getAc.getAffiliateChannelCode());
			if (dataSet != null) {
				dataSet.setEnable(false);
			} else {

			}
			channelRepository.save(dataSet);
		}
	}
}
