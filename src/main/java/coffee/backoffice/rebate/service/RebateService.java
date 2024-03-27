package coffee.backoffice.rebate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import coffee.backoffice.cashback.vo.res.CashbackStatMonthDailyRes;
import coffee.backoffice.cashback.vo.res.CashbackStatTitleRes;
import coffee.backoffice.casino.model.GameProvider;
import coffee.backoffice.casino.model.GameProviderNoIcon;
import coffee.backoffice.casino.repository.jpa.GameProviderNoIconJpa;
import coffee.backoffice.casino.service.GamesService;
import coffee.backoffice.casino.vo.res.GameProviderRes;
import coffee.backoffice.rebate.vo.res.RebateStatMonthDailyRes;
import coffee.backoffice.rebate.vo.res.RebateStatTitleRes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.service.GameProductTypeService;
import coffee.backoffice.casino.service.GameProviderService;
import coffee.backoffice.casino.vo.res.GameProductTypeRes;
import coffee.backoffice.player.service.GroupLevelService;
import coffee.backoffice.player.service.TagManagementService;
import coffee.backoffice.rebate.model.Rebate;
import coffee.backoffice.rebate.model.RebateCondition;
import coffee.backoffice.rebate.repository.dao.RebateDao;
import coffee.backoffice.rebate.repository.jpa.RebateConditionJpa;
import coffee.backoffice.rebate.repository.jpa.RebateJpa;
import coffee.backoffice.rebate.vo.req.RebateConditionReq;
import coffee.backoffice.rebate.vo.req.RebateReq;
import coffee.backoffice.rebate.vo.res.RebateConditionRes;
import coffee.backoffice.rebate.vo.res.RebateRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class RebateService {
	@Autowired
	private RebateDao rebateDao;

	@Autowired
	private RebateJpa rebateJpa;

	@Autowired
	private RebateConditionService rebateConditionService;
	@Autowired
	private GameProductTypeService gameProductTypeService;
	@Autowired
	private RebateConditionJpa rebateConditionJpa;
	@Autowired
	private GroupLevelService groupLevelService;
	@Autowired
	private TagManagementService tagManagementService;
	@Autowired
	private GameProviderService gameProviderService;
	@Autowired
	private GameProviderNoIconJpa gameProviderNoIconJpa;
	@Autowired
	private GamesService gamesService;

	private ModelMapper modelMapper = new ModelMapper();

	public RebateRes getOne(String code) throws Exception {
		Rebate data = rebateJpa.findByCode(code);
		RebateRes returnData = new RebateRes();
		returnData.setEntityToRes(data);
		List<RebateCondition> rebateConditions = rebateConditionJpa.findAllByRebateCode(returnData.getCode());
		List<RebateConditionRes> resConditions = new ArrayList<>();
		RebateConditionRes resCondition;
		for (RebateCondition dataRC : rebateConditions) {
			resCondition = new RebateConditionRes();
			resCondition.setEntityToRes(dataRC);
			GameProviderNoIcon gameProvider = gameProviderNoIconJpa.findByCode(resCondition.getGameProviderCode());
			GameProviderRes gameProviderRes = new GameProviderRes();
			modelMapper.map(gameProvider, gameProviderRes);
			resCondition.setGameProviderRes(gameProviderRes);
//			resCondition.setGameGroupRes(gamesService.getByCode(resCondition.getGameCode()));
			resConditions.add(resCondition);
		}
		returnData.setRebateConditionResList(resConditions);
		return returnData;
	}

	public List<RebateRes> getAll() throws Exception {
		List<Rebate> data = rebateJpa.findAll();
		if (data.isEmpty())
			return null;
		List<RebateRes> returnData = new ArrayList<>();
		RebateRes oneData;
		for (Rebate i : data) {
			oneData = new RebateRes();
			oneData.setId(i.getId());
			oneData.setTitle(i.getTitle());
			oneData.setDescription(i.getDescription());
			oneData.setStartDate(i.getStartDate());
			oneData.setEndDate(i.getEndDate());
			oneData.setPeriodStatus(i.getPeriodStatus());
			oneData.setRebateType(i.getRebateType());
			oneData.setIsAutoRebate(i.getIsAutoRebate());
			oneData.setStatus(i.getStatus());
			oneData.setVipGroupCode(new ArrayList<String>(Arrays.asList(i.getVipGroupCode().split(","))));
			oneData.setProductTypeCode(i.getProductTypeCode());
			oneData.setMaxGroupRebate(i.getMaxGroupRebate());
			oneData.setCreatedDate(i.getCreatedDate());
			oneData.setUpdatedDate(i.getUpdatedDate());
			oneData.setUpdatedBy(i.getUpdatedBy());
			oneData.setCode(i.getCode());
			oneData.setTagCode(new ArrayList<String>(Arrays.asList(i.getTagCode().split(","))));
			oneData.setGamesCodeExclude(i.getGamesCodeExclude());
			oneData.setRebateConditionMultiplier(i.getRebateConditionMultiplier());
			returnData.add(oneData);
		}
		return returnData;
	}

	public DataTableResponse<RebateRes> getPaginateModel(DatatableRequest req) throws Exception {
		DataTableResponse<RebateRes> dataTable = new DataTableResponse<>();
		DataTableResponse<RebateRes> tag = new DataTableResponse<>();
		tag = rebateDao.paginate(req);
		List<RebateRes> data = tag.getData();
		for (RebateRes item : data) {
			GameProductTypeRes res = new GameProductTypeRes();
			System.out.println(item.getProductTypeCode());
//			res = gameProductTypeService.getByCode(item.getProductTypeCode());

			item.setGameProductTypeRes(res);
		}
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
	}


	public DataTableResponse<RebateStatTitleRes> getPaginateModelCashbackStatTitle(DatatableRequest req) throws Exception {
		DataTableResponse<RebateStatTitleRes> dataTable = new DataTableResponse<>();
		DataTableResponse<RebateStatTitleRes> tag = new DataTableResponse<>();
		tag = rebateDao.paginateRebateStatTitle(req);
		List<RebateStatTitleRes> data = tag.getData();
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
	}

	public DataTableResponse<RebateStatMonthDailyRes> getPaginateModelCashbackStatMonth(DatatableRequest req) throws Exception {
		DataTableResponse<RebateStatMonthDailyRes> dataTable = new DataTableResponse<>();
		DataTableResponse<RebateStatMonthDailyRes> tag = new DataTableResponse<>();
		tag = rebateDao.paginateRebateStatMonth(req);
		List<RebateStatMonthDailyRes> data = tag.getData();
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
	}

	public DataTableResponse<RebateStatMonthDailyRes> getPaginateModelCashbackStatDaily(DatatableRequest req) throws Exception {
		DataTableResponse<RebateStatMonthDailyRes> dataTable = new DataTableResponse<>();
		DataTableResponse<RebateStatMonthDailyRes> tag = new DataTableResponse<>();
		tag = rebateDao.paginateRebateStatDaily(req);
		List<RebateStatMonthDailyRes> data = tag.getData();
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
	}

	public void insertOne(RebateReq form) throws Exception {
		// create insert
		Rebate dataInsert = new Rebate();
		dataInsert.setReqToEntity(form);

		if (!form.getRebateConditionList().isEmpty()) {
			List<RebateConditionReq> rebateCondition = form.getRebateConditionList();
			for (RebateConditionReq item : rebateCondition) {
				// create insert
				RebateCondition newCondition = new RebateCondition();
				newCondition.setReqToEntity(item);
				newCondition.setRebateCode(dataInsert.getCode());
				rebateConditionJpa.save(newCondition);
			}
		}
		rebateJpa.save(dataInsert);
	}

	public void updateOne(RebateReq form) throws Exception {

		// create insert
		Rebate dataUpdate = rebateJpa.findById(form.getId()).get();
		dataUpdate.setReqToEntity(form);
		dataUpdate.setUpdatedBy(UserLoginUtil.getUsername());
		dataUpdate.setUpdatedDate(new Date());

		if (!form.getRebateConditionList().isEmpty()) {
			List<RebateConditionReq> rebateCondition = form.getRebateConditionList();
			for (RebateConditionReq item : rebateCondition) {
				// create insert
				RebateCondition newCondition = rebateConditionJpa.findById(item.getId()).get();
				newCondition.setReqToEntity(item);
				newCondition.setUpdatedDate(new Date());
				newCondition.setUpdatedBy(UserLoginUtil.getUsername());
				rebateConditionJpa.save(newCondition);
			}
		}
		rebateJpa.save(dataUpdate);
	}

	@Transactional
	public void delete(String code) throws Exception {

		rebateJpa.deleteByCode(code);

		List<RebateCondition> reCondition = rebateConditionJpa.findAllByRebateCode(code);
		if (reCondition != null) {
			for (RebateCondition data : reCondition) {
				rebateConditionJpa.deleteByRebateCode(code);
			}
		}
	}

	public BigDecimal getCommissionPercentByGroupCodeAndProviderCode(String groupCode, String providerCode) {
		List<Rebate> rebates = rebateJpa.findALLByVipGroupCodeContaining(groupCode);
		BigDecimal maxCommissionRate = BigDecimal.valueOf(0);
		for (Rebate item : rebates) {
			List<RebateCondition> rebateConditions = rebateConditionJpa
					.findAllByRebateCodeAndGameProviderCode(item.getCode(), providerCode);
			if (!rebateConditions.isEmpty()) {
				for (RebateCondition it : rebateConditions) {
					if (maxCommissionRate.compareTo(it.getRebatePercent()) == -1) {
						maxCommissionRate = it.getRebatePercent();
					}
				}
			}
		}
		return maxCommissionRate;
	}
}