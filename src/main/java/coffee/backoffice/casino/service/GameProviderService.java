package coffee.backoffice.casino.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import framework.model.ImgUploadRes;
import framework.utils.ImgUploadUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.model.GameProvider;
import coffee.backoffice.casino.model.GameProviderNoIcon;
import coffee.backoffice.casino.repository.dao.GameProviderDao;
import coffee.backoffice.casino.repository.jpa.GameProviderJpa;
import coffee.backoffice.casino.repository.jpa.GameProviderNoIconJpa;
import coffee.backoffice.casino.vo.req.GameProviderReq;
import coffee.backoffice.casino.vo.res.GameProviderRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.EDIT;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.SAVE;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.UserLoginUtil;

@Service
public class GameProviderService {
	@Autowired
	private GameProviderDao gameProviderDao;

	@Autowired
	private GameProviderJpa gameProviderJpa;

	@Autowired
	private GameProviderNoIconJpa gameProviderNoIconJpa;

	@Autowired
	private ProductMappingProviderService mappingProviderService;
	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private ImgUploadUtils imgUploadUtils;

	public DataTableResponse<GameProviderRes> getPaginateProvider(DatatableRequest req) throws Exception {
		DataTableResponse<GameProviderRes> dataTable = new DataTableResponse<>();
		DataTableResponse<GameProviderRes> tag = new DataTableResponse<>();
		tag = gameProviderDao.paginate(req);
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(tag.getData());
		return dataTable;
	}

	@Transactional
	public void deleteByCode(String code) {
		gameProviderJpa.deleteByCode(code);
		mappingProviderService.deleteByProviderCode(code);
	}

	@Transactional
	public String saveProvider(GameProviderReq req) throws Exception {
		GameProvider tempData = gameProviderJpa.findByCode(req.getCode());
		if (tempData == null) {
			GameProvider data = new GameProvider();
			data.setCode(req.getCode());
			data.setNameEn(req.getNameEn());
			data.setNameTh(req.getNameTh());
			data.setOpenType(req.getOpenType());
			data.setCreatedAt(new Date());
			data.setCreatedBy(UserLoginUtil.getUsername());
			data.setStatusView(req.getStatus());
			gameProviderJpa.save(data);
			if (req.getIconLandscape() != null) {
				ImgUploadRes path = imgUploadUtils.uploadImg("provider-img", req.getIconLandscape(), "provider");
				data.setIconLandscape(path.getData().getSavedPath());
			}
			if (req.getIconPortrait() != null) {
				ImgUploadRes path = imgUploadUtils.uploadImg("provider-img", req.getIconPortrait(), "provider");
				data.setIconPortrait(path.getData().getSavedPath());
			}

		} else {
			return SAVE.DUPLICATE_DATA;
		}
		return SAVE.SUCCESS;
	}

	@Transactional
	public String editProvider(GameProviderReq req) throws Exception {
		GameProvider data = gameProviderJpa.findByCode(req.getCode());
		data.setNameEn(req.getNameEn());
		data.setNameTh(req.getNameTh());
		data.setOpenType(req.getOpenType());
		data.setUpdatedAt(new Date());
		data.setUpdatedBy(UserLoginUtil.getUsername());
		data.setStatusView(req.getStatus());
		if (req.getIconLandscape() != null) {
			ImgUploadRes path = imgUploadUtils.uploadImg("provider-img", req.getIconLandscape(), "provider");
			data.setIconLandscape(path.getData().getSavedPath());
		}
		if (req.getIconPortrait() != null) {
			ImgUploadRes path = imgUploadUtils.uploadImg("provider-img", req.getIconPortrait(), "provider");
			data.setIconPortrait(path.getData().getSavedPath());
		}
		gameProviderJpa.save(data);
		return EDIT.SUCCESS;
	}

	@Transactional
	public void changeStatusView(GameProviderReq req) {
		GameProvider data = gameProviderJpa.findByCode(req.getCode());
		data.setStatusView(req.getStatus());
		data.setCreatedAt(new Date());
		data.setUpdatedBy(UserLoginUtil.getUsername());
		gameProviderJpa.save(data);
	}

	public GameProvider getGameProviderByCode(String code) {
		return gameProviderJpa.findByCode(code);
	}

	public GameProviderRes getGameProviderResByCode(String code) {
		GameProvider gameProvider = gameProviderJpa.findByCode(code);
		GameProviderRes gameProviderRes = new GameProviderRes();
		modelMapper.map(gameProvider, gameProviderRes);
		return gameProviderRes;
	}

	public List<GameProvider> getAll() {
		return gameProviderJpa.findAll();
	}

	public List<GameProviderNoIcon> getDropdownProvider() {
		return gameProviderNoIconJpa.findAll();
	}
	
	public GameProviderNoIcon getGameProviderByCodeNoIcon(String code) {
		return gameProviderNoIconJpa.findByCode(code);
	}

	public GameProviderRes getProviderByCode(String code) {
		GameProvider gameProvider = gameProviderJpa.findByCode(code);
		GameProviderRes gameProviderRes = modelMapper.map(gameProvider,GameProviderRes.class);
		return gameProviderRes;
	}
}
