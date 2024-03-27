package coffee.backoffice.promotion.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.model.*;
import coffee.backoffice.promotion.repository.jpa.*;
import coffee.backoffice.promotion.vo.res.PromotionDatatableRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coffee.backoffice.promotion.repository.dao.PromotionDao;
import coffee.backoffice.promotion.vo.model.BonusLevelDetail;
import coffee.backoffice.promotion.vo.model.PromotionDetail;
import coffee.backoffice.promotion.vo.model.RecommendDetail;
import coffee.backoffice.promotion.vo.model.WalletDetail;
import coffee.backoffice.promotion.vo.req.PromotionReq;
import coffee.backoffice.promotion.vo.res.PromotionPlayerRes;
import coffee.backoffice.promotion.vo.res.PromotionProfileRes;
import coffee.backoffice.promotion.vo.res.PromotionRes;
import framework.constant.ProjectConstant.STATUS;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;

	@Autowired
	private PromotionDao promotionDao;

	@Autowired
	private PostSettingRepository postSettingRepository;

	@Autowired
	private AppSettingRepository appSettingRepository;

	@Autowired
	private RuleSettingRepository ruleSettingRepository;

	@Autowired
	private IssueSettingRepository issueSettingRepository;

	@Autowired
	private BonusLevelSettingRepository bonusLevelSettingRepository;

	@Autowired
	private PromotionMappingRepository promotionMappingRepository;

	@Autowired
	private PromoMappingJpa promoMappingJpa;

	@Autowired
	private PromotionRequestJpa promotionRequestJpa;

	private ModelMapper modelMapper = new ModelMapper();

	@Transactional
	public void create(PromotionReq form, boolean isClone) {
		String promo = GenerateRandomString.generate();

		Promotion promotion = new Promotion();
		promotion.setReqToEntity(form);
		promotion.setPromoCode(promo);
		promotion.setCreatedBy(UserLoginUtil.getUsername());
		if (isClone) {
			promotion.setViewStatus(PromotionConstant.Status.HIDE);
		}
		promotionRepository.save(promotion);
		
		PostSetting postSetting = new PostSetting();
		postSetting.setReqToEntity(form.getPostSetting());
		postSetting.setPromoTitle(form.getPromoTitle());
		postSetting.setPromoCode(promo);
		postSetting.setCreatedBy(UserLoginUtil.getUsername());
		postSettingRepository.save(postSetting);
		
		if(!promotion.getPromoType().equals(PromotionConstant.Type.posting)) {
			AppSetting appSetting = new AppSetting();
			appSetting.setReqToEntity(form.getAppSetting());
			appSetting.setPromoCode(promo);
			appSetting.setCreatedBy(UserLoginUtil.getUsername());
			appSettingRepository.save(appSetting);

			RuleSetting ruleSetting = new RuleSetting();
			ruleSetting.setReqToEntity(form.getRuleSetting());
			ruleSetting.setPromoCode(promo);
			ruleSetting.setCreatedBy(UserLoginUtil.getUsername());
			ruleSettingRepository.save(ruleSetting);

			List<IssueSetting> listIssueSetting = new ArrayList<IssueSetting>();
			for (WalletDetail temp : form.getRuleSetting().getWallet()) {
				IssueSetting issueSetting = new IssueSetting();
				issueSetting.setPromoCode(promo);
				issueSetting.setProviderName(temp.getProviderName());
				issueSetting.setProviderCode(temp.getProviderCode());
				listIssueSetting.add(issueSetting);
			}
			issueSettingRepository.saveAll(listIssueSetting);

			if (form.getRuleSetting().getLevel() != null) {
				List<BonusLevelSetting> listBonusLevelSetting = new ArrayList<BonusLevelSetting>();
				for (BonusLevelDetail temp : form.getRuleSetting().getLevel()) {
					BonusLevelSetting bonusLevelSetting = new BonusLevelSetting();
					bonusLevelSetting.setReqToEntity(temp);
					bonusLevelSetting.setPromoCode(promo);
					bonusLevelSetting.setCreatedBy(UserLoginUtil.getUsername());
					listBonusLevelSetting.add(bonusLevelSetting);
				}
				bonusLevelSettingRepository.saveAll(listBonusLevelSetting);
			}
		}

	}

	@Transactional
	public void clone(String promoCode) {
		String promo = GenerateRandomString.generate();
		modelMapper.typeMap(Promotion.class, Promotion.class).addMappings(mp -> {
			mp.skip(Promotion::setId);
		});
		modelMapper.typeMap(PostSetting.class, PostSetting.class).addMappings(mp -> {
			mp.skip(PostSetting::setId);
		});
		modelMapper.typeMap(AppSetting.class, AppSetting.class).addMappings(mp -> {
			mp.skip(AppSetting::setId);
		});
		modelMapper.typeMap(IssueSetting.class, IssueSetting.class).addMappings(mp -> {
			mp.skip(IssueSetting::setId);
		});
		modelMapper.typeMap(BonusLevelSetting.class, BonusLevelSetting.class).addMappings(mp -> {
			mp.skip(BonusLevelSetting::setId);
		});
		modelMapper.typeMap(RuleSetting.class, RuleSetting.class).addMappings(mp -> {
			mp.skip(RuleSetting::setId);
		});
		Promotion promotion = promotionRepository.findByPromoCode(promoCode);

		Promotion clonePromotion = modelMapper.map(promotion, Promotion.class);
		clonePromotion.setPromoCode(promo);
		clonePromotion.setPromoTitle(clonePromotion.getPromoTitle() + " CLONE");
		clonePromotion.setViewStatus(PromotionConstant.Status.HIDE);
		clonePromotion.setUpdatedBy(UserLoginUtil.getUsername());
		clonePromotion.setUpdatedDate(new Date());
		clonePromotion.setCreatedBy(UserLoginUtil.getUsername());
		clonePromotion.setCreatedDate(new Date());
		promotionRepository.save(clonePromotion);

		PostSetting postSetting = postSettingRepository.findByPromoCode(promoCode);
		PostSetting clonePostSetting = modelMapper.map(postSetting, PostSetting.class);
		clonePostSetting.setPromoTitle(clonePromotion.getPromoTitle());
		clonePostSetting.setPromoCode(promo);
		clonePostSetting.setUpdatedBy(UserLoginUtil.getUsername());
		clonePostSetting.setUpdatedDate(new Date());
		clonePostSetting.setCreatedBy(UserLoginUtil.getUsername());
		clonePostSetting.setCreatedDate(new Date());
		postSettingRepository.save(clonePostSetting);

		AppSetting appSetting = appSettingRepository.findByPromoCode(promoCode);
		AppSetting cloneAppSetting = modelMapper.map(appSetting, AppSetting.class);
		cloneAppSetting.setPromoCode(promo);
		cloneAppSetting.setUpdatedBy(UserLoginUtil.getUsername());
		cloneAppSetting.setUpdatedDate(new Date());
		cloneAppSetting.setCreatedBy(UserLoginUtil.getUsername());
		cloneAppSetting.setCreatedDate(new Date());
		appSettingRepository.save(cloneAppSetting);

		RuleSetting ruleSetting = ruleSettingRepository.findByPromoCode(promoCode);
		RuleSetting cloneRuleSetting = modelMapper.map(ruleSetting, RuleSetting.class);
		cloneRuleSetting.setPromoCode(promo);
		cloneRuleSetting.setUpdatedBy(UserLoginUtil.getUsername());
		cloneRuleSetting.setUpdatedDate(new Date());
		cloneRuleSetting.setCreatedBy(UserLoginUtil.getUsername());
		cloneRuleSetting.setCreatedDate(new Date());
		ruleSettingRepository.save(cloneRuleSetting);

		List<IssueSetting> listIssueSetting = issueSettingRepository.findAllByPromoCode(promoCode);
		List<IssueSetting> cloneListIssueSetting = modelMapper.map(listIssueSetting,
				new TypeToken<List<IssueSetting>>() {
				}.getType());
		cloneListIssueSetting.forEach(ele -> {
			ele.setPromoCode(promo);
		});
		issueSettingRepository.saveAll(cloneListIssueSetting);

		List<BonusLevelSetting> listBonusLevelSetting = bonusLevelSettingRepository.findAllByPromoCode(promoCode);
		List<BonusLevelSetting> cloneListBonusLevelSetting = modelMapper.map(listBonusLevelSetting,
				new TypeToken<List<BonusLevelSetting>>() {
				}.getType());
		cloneListBonusLevelSetting.forEach(ele -> {
			ele.setPromoCode(promo);
			ele.setUpdatedBy(UserLoginUtil.getUsername());
			ele.setUpdatedDate(new Date());
			ele.setCreatedBy(UserLoginUtil.getUsername());
			ele.setCreatedDate(new Date());
		});
		bonusLevelSettingRepository.saveAll(cloneListBonusLevelSetting);

	}

	public List<PromotionRes> getAll() {
		List<PromotionRes> res = new ArrayList<PromotionRes>();
		List<Promotion> result = promotionRepository.findAll();
		for (Promotion obj : result) {
			PromotionRes temp = new PromotionRes();
			RuleSetting rule = ruleSettingRepository.findByPromoCode(obj.getPromoCode());
			temp.setEntityToRes(obj);
			if (rule != null) {
				temp.setWallet(rule.getReceiveBonusWallet());
			}
			res.add(temp);
		}
		return res;
	}

	public Promotion getByPromoCode(String promoCode) {
		return promotionRepository.findByPromoCode(promoCode);
	}
	
	public List<Promotion> getByPromoTypeAndPromoCode(String promoType,String promoCode) {
		return promotionRepository.findByPromoTypeAndPromoCode(promoType,promoCode);
	}
	
	public List<Promotion> getByPromoTypeAndPromoCodeIn(String promoType,List<String> promoCode) {
		return promotionRepository.findByPromoTypeAndPromoCodeIn(promoType,promoCode);
	}

	public RecommendDetail recommendPromotion(BigDecimal aboveDeposit) {
		List<BonusLevelSetting> levelList = findLevelBonusByDeposit(aboveDeposit);
		RecommendDetail res = new RecommendDetail();
		List<PromotionPlayerRes> list = new ArrayList<PromotionPlayerRes>();
		Set<String> promoCode = new HashSet<String>();
		for (BonusLevelSetting level : levelList) {
			promoCode.add(level.getPromoCode());
		}

		for (String code : promoCode) {
			PostSetting postSetting = postSettingRepository.findByPromoCode(code);
			PromotionPlayerRes temp = new PromotionPlayerRes();
			temp.setPromoCode(code);
			temp.setPromoTitle(postSetting.getPromoTitle());
			temp.setPromoBanner(postSetting.getDeskBanner());
			temp.setPromoDetail(postSetting.getDeskDetail());
			list.add(temp);
		}
		res.setList(list);
		return res;
	}

	public PostSetting getPostSettingByPromoCode(String promoCode) {
		return postSettingRepository.findByPromoCode(promoCode);
	}

	public AppSetting getAppSettingByPromoCode(String promoCode) {
		return appSettingRepository.findByPromoCode(promoCode);
	}

	public List<PromotionRes> findPromotionByGroupCode(String code) {
		List<PromotionRes> res = promotionDao.findPromotionByGroupCode(code);
		return res;
	}

	public RuleSetting getRuleSettingByPromoCode(String promoCode) {
		return ruleSettingRepository.findByPromoCode(promoCode);
	}

	public List<IssueSetting> getIssueSettingByPromoCode(String promoCode) {
		return issueSettingRepository.findByPromoCode(promoCode);
	}

	public List<BonusLevelSetting> getBonusLevelSettingByPromoCode(String promoCode) {
		return bonusLevelSettingRepository.findByPromoCodeOrderByBonusLevelDesc(promoCode);
	}

	public BonusLevelSetting findLevelBonus(String promoCode, BigDecimal amount) {
		List<BonusLevelSetting> listLevel = bonusLevelSettingRepository.findByPromoCodeOrderByBonusLevelDesc(promoCode);
		for (BonusLevelSetting temp : listLevel) {
			if (temp.getDepositAmount().compareTo(amount) <= 0) {
				return temp;
			}
		}
		return null;
	}

	public List<BonusLevelSetting> findLevelBonusByDeposit(BigDecimal amount) {
		return bonusLevelSettingRepository.findByDepositAmountLessThanEqual(amount);
	}

	public List<PromotionPlayerRes> getAllToPlayer() {
		List<PromotionPlayerRes> res = new ArrayList<PromotionPlayerRes>();
		List<Promotion> result = promotionRepository.findAllByViewStatus("SHOW");
		for (Promotion obj : result) {
			PostSetting postSetting = postSettingRepository.findByPromoCode(obj.getPromoCode());
			PromotionPlayerRes temp = new PromotionPlayerRes();
			temp.setPromoCode(obj.getPromoCode());
			temp.setPromoTitle(obj.getPromoTitle());
			temp.setPromoBanner(postSetting.getDeskBanner());
			temp.setPromoDetail(postSetting.getDeskDetail());
			res.add(temp);
		}
		return res;
	}

	public PromotionDetail findPromotionDetail(String promoCode) {
		PromotionDetail res = new PromotionDetail();
		Promotion promotion = promotionRepository.findByPromoCode(promoCode);
		res.setPromotion(promotion);
		res.setPostSetting(postSettingRepository.findByPromoCode(promoCode));
		res.setAppSetting(appSettingRepository.findByPromoCode(promoCode));
		res.setRuleSetting(ruleSettingRepository.findByPromoCode(promoCode));
		res.setIssueSetting(issueSettingRepository.findAllByPromoCode(promoCode));
		res.setBonusLevelSettings(bonusLevelSettingRepository.findByPromoCodeOrderByBonusLevelAsc(promoCode));

		return res;

	}

	public PromotionPlayerRes getPromoInboxMessage(String promoCode) {
		PromotionPlayerRes res = new PromotionPlayerRes();
		PostSetting result = postSettingRepository.findByPromoCode(promoCode);

		if (result != null) {
			res.setPromoCode(promoCode);
			res.setPromoTitle(result.getPromoTitle());
			res.setPromoBanner(result.getDeskBanner());
			res.setPromoDetail(result.getDeskDetail());
		}

		return res;
	}
	
	public PromotionMapping findLastMappingByUsernameAndStatusAndDateActiveAfterOrderByDateActive(String username, String status, Date dateActive) {
		PromotionMapping res = promotionMappingRepository.findFirstByUsernameAndStatusAndDateActiveAfterOrderByDateActiveDesc(username, status,dateActive);
		return res;
	}
	
	public PromotionMapping findLastMappingByUsernameAndStatusOrderByUpdatedDate(String username, String status) {
		PromotionMapping res = promotionMappingRepository.findFirstByUsernameAndStatusOrderByUpdatedDateDesc(username, status);
		return res;
	}
	
	public List<PromotionMapping> findMappingByUsername(String username) {
		List<PromotionMapping> res = promotionMappingRepository.findByUsername(username);
		return res;
	}
	
	
	public List<PromotionMapping> findMappingByUsernameAndStatusAndDateActiveBeforeOrderByDateActive(String username, String status, Date dateActive) {
		List<PromotionMapping> res = promotionMappingRepository.findByUsernameAndStatusAndDateActiveBeforeOrderByDateActiveDesc(username, status,dateActive);
		return res;
	}

	public List<PromotionMapping> findMappingByUsernameAndStatus(String username, String status) {
		List<PromotionMapping> res = promotionMappingRepository.findByUsernameAndStatus(username, status);
		return res;
	}

	public PromotionMapping findMappingByUsernameAndStatusAndRequestId(String username, String status,
			String requestId) {
		PromotionMapping res = promotionMappingRepository.findByUsernameAndStatusAndRequestId(username, status,
				requestId);
		return res;
	}

	public void saveMapping(PromotionMapping req) {
		if (req != null) {
			promotionMappingRepository.save(req);
		}
	}

	public void editMapping(PromotionMapping req) {
		if (req != null) {
			promotionMappingRepository.save(req);
		}
	}

	@Transactional
	public void createPromotionPosting(PromotionReq form) {
		String promo = GenerateRandomString.generate();

		Promotion promotion = new Promotion();
		promotion.setReqToEntity(form);
		promotion.setPromoCode(promo);
//    	System.out.println(promotion);
		promotion.setCreatedBy(UserLoginUtil.getUsername());
		promotionRepository.save(promotion);

		PostSetting postSetting = new PostSetting();
		postSetting.setReqToEntity(form.getPostSetting());
		postSetting.setPromoTitle(form.getPromoTitle());
		postSetting.setPromoCode(promo);
//    	System.out.println(postSetting);
		postSetting.setCreatedBy(UserLoginUtil.getUsername());
		postSettingRepository.save(postSetting);
	}

	public PromotionProfileRes findPromotionByUsername(String username) {
		PromotionMapping dataPMMP = promoMappingJpa.findByUsernameAndStatus(username, STATUS.ACTIVE);
		PromotionProfileRes res = new PromotionProfileRes();
		if (dataPMMP != null) {
			res.setId(dataPMMP.getId());
			res.setPromoCode(dataPMMP.getPromoCode());
			res.setUsername(username);
			res.setStatus(dataPMMP.getStatus());
			res.setDateActive(dataPMMP.getDateActive());
			res.setRequestId(dataPMMP.getRequestId());
			Promotion dataPromo = promotionRepository.findByPromoCode(dataPMMP.getPromoCode());
			res.setPromotion(dataPromo);
			PostSetting dataPostSetting = postSettingRepository.findByPromoCode(dataPMMP.getPromoCode());
			res.setPostSetting(dataPostSetting);
		}
		return res;
	}

	@Transactional
	public void deletePromotion(Long id) {
		Promotion promotion = promotionRepository.findById(id).get();
		List<PromotionMapping> promotionMapping = promoMappingJpa.findAllByPromoCode(promotion.getPromoCode());
		List<PromotionRequest> promotionRequest = promotionRequestJpa.findAllByPromoCode(promotion.getPromoCode());
		promotionRepository.delete(promotion);
		promoMappingJpa.deleteAll(promotionMapping);
		promotionRequestJpa.deleteAll(promotionRequest);
	}

	public void toggleViewStatus(Long id) {
		Promotion promotion = promotionRepository.findById(id).get();
		if (promotion.getViewStatus().equals(PromotionConstant.Status.SHOW))
			promotion.setViewStatus(PromotionConstant.Status.HIDE);
		else
			promotion.setViewStatus(PromotionConstant.Status.SHOW);
		promotionRepository.save(promotion);
	}

	public DataTableResponse<PromotionDatatableRes> getPaginate(DatatableRequest req) {
		DataTableResponse<PromotionDatatableRes> dataTable = new DataTableResponse<>();
		DataTableResponse<PromotionDatatableRes> newData = new DataTableResponse<>();
		newData = promotionDao.paginate(req);
		List<PromotionDatatableRes> data = newData.getData();
		data.forEach(ele -> {
			ele.setDeleteAble(!promotionRequestJpa.existsByPromoCodeAndStatusRequest(ele.getPromoCode(),
					PromotionConstant.Status.PENDING));
		});
		dataTable.setRecordsTotal(newData.getRecordsTotal());
		dataTable.setDraw(newData.getDraw());
		dataTable.setData(newData.getData());
		return dataTable;
	}

	public void edit(String promoCode, PromotionReq form) {

		Promotion promotion = promotionRepository.findByPromoCode(promoCode);
		String promo = promotion.getPromoCode();
		promotion.setReqToEntity(form);
		promotion.setPromoCode(promo);
		System.out.println(promotion);
		promotion.setCreatedBy(UserLoginUtil.getUsername());
		promotionRepository.save(promotion);

		PostSetting postSetting = postSettingRepository.findByPromoCode(promo);
		postSetting.setReqToEntity(form.getPostSetting());
		postSetting.setPromoTitle(form.getPromoTitle());
		postSetting.setPromoCode(promo);
		postSetting.setCreatedBy(UserLoginUtil.getUsername());
		postSettingRepository.save(postSetting);

		AppSetting appSetting = appSettingRepository.findByPromoCode(promo);
		appSetting.setReqToEntity(form.getAppSetting());
		appSetting.setPromoCode(promo);
		appSetting.setCreatedBy(UserLoginUtil.getUsername());
		appSettingRepository.save(appSetting);

		RuleSetting ruleSetting = ruleSettingRepository.findByPromoCode(promo);
		ruleSetting.setReqToEntity(form.getRuleSetting());
		ruleSetting.setPromoCode(promo);
		ruleSetting.setCreatedBy(UserLoginUtil.getUsername());
		ruleSettingRepository.save(ruleSetting);

		List<IssueSetting> oldListIssueSetting = issueSettingRepository.findAllByPromoCode(promo);
		issueSettingRepository.deleteAll(oldListIssueSetting);
		List<IssueSetting> listIssueSetting = new ArrayList<IssueSetting>();
		for (WalletDetail temp : form.getRuleSetting().getWallet()) {
			IssueSetting issueSetting = new IssueSetting();
			issueSetting.setPromoCode(promo);
			issueSetting.setProviderName(temp.getProviderName());
			issueSetting.setProviderCode(temp.getProviderCode());
			listIssueSetting.add(issueSetting);
		}
		issueSettingRepository.saveAll(listIssueSetting);

		if (form.getRuleSetting().getLevel() != null) {
			List<BonusLevelSetting> oldBonusLevelSetting = bonusLevelSettingRepository.findAllByPromoCode(promo);
			bonusLevelSettingRepository.deleteAll(oldBonusLevelSetting);
			List<BonusLevelSetting> listBonusLevelSetting = new ArrayList<BonusLevelSetting>();
			for (BonusLevelDetail temp : form.getRuleSetting().getLevel()) {
				BonusLevelSetting bonusLevelSetting = new BonusLevelSetting();
				bonusLevelSetting.setReqToEntity(temp);
				bonusLevelSetting.setPromoCode(promo);
				bonusLevelSetting.setCreatedBy(UserLoginUtil.getUsername());
				listBonusLevelSetting.add(bonusLevelSetting);
			}
			bonusLevelSettingRepository.saveAll(listBonusLevelSetting);
		}
	}

	public PromotionMapping findByUsernameAndPromoCodeAndStatus(String username, String promoCode, String status) {
		return promotionMappingRepository.findByUsernameAndPromoCodeAndStatus(username, promoCode, status);
	}

	public PromotionMapping findByUsernameAndStatus(String username, String status) {
		return promoMappingJpa.findByUsernameAndStatus(username, status);
	}
}
