package coffee.backoffice.casino.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.model.GameProductType;
import coffee.backoffice.casino.model.GameProductTypeNoIcon;
import coffee.backoffice.casino.model.GameProviderNoIcon;
import coffee.backoffice.casino.repository.dao.GameProductTypeDao;
import coffee.backoffice.casino.repository.jpa.GameProductTypeJpa;
import coffee.backoffice.casino.repository.jpa.GameProductTypeNoIconJpa;
import coffee.backoffice.casino.vo.req.GameProductTypeReq;
import coffee.backoffice.casino.vo.res.GameProductTypeRes;
import coffee.backoffice.casino.vo.res.GameProductTypeWithProviderRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.EDIT;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.SAVE;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.UserLoginUtil;

@Service
public class GameProductTypeService {
	
	@Autowired
	private GameProductTypeDao gameProductTypeDao;

	@Autowired
	private GameProductTypeJpa gameProductTypeJpa;

	@Autowired
	private ProductMappingProviderService mappingProviderService;
	
	@Autowired
	private GameProductTypeNoIconJpa gameProductTypeNoIconJpa;

	@Transactional
	public String saveProduct(GameProductTypeReq req) {
		GameProductType tempData = gameProductTypeJpa.findByCode(req.getCode());
		if (tempData == null) {
			GameProductType data = new GameProductType();
			data.setCode(req.getCode());
			data.setNameEn(req.getNameEn());
			data.setNameTh(req.getNameTh());
			data.setCreatedAt(new Date());
			data.setUpdatedBy(UserLoginUtil.getUsername());
			data.setIconUrl(req.getIcon());
			gameProductTypeJpa.save(data);
		} else {
			return SAVE.DUPLICATE_DATA;
		}
		return SAVE.SUCCESS;
	}

	@Transactional
	public String editProduct(GameProductTypeReq req) {
		GameProductType data = gameProductTypeJpa.findByCode(req.getCode());
		data.setNameEn(req.getNameEn());
		data.setNameTh(req.getNameTh());
		data.setUpdatedAt(new Date());
		data.setUpdatedBy(UserLoginUtil.getUsername());
		data.setIconUrl(req.getIcon());
		gameProductTypeJpa.save(data);
		return EDIT.SUCCESS;
	}

	public GameProductType getGameProductTypeByCode(String code) {
		return gameProductTypeJpa.findByCode(code);
	}

	public DataTableResponse<GameProductTypeRes> getPaginateProduct(DatatableRequest req) {
		DataTableResponse<GameProductTypeRes> dataTable = new DataTableResponse<>();
		DataTableResponse<GameProductTypeRes> newData = new DataTableResponse<>();
		newData = gameProductTypeDao.paginate(req);
		dataTable.setRecordsTotal(newData.getRecordsTotal());
		dataTable.setDraw(newData.getDraw());
		dataTable.setData(newData.getData());
		return dataTable;
	}

	@Transactional
	public void deleteProduct(String code) {
		gameProductTypeJpa.deleteByCode(code);
		mappingProviderService.deleteMappingProduct(code);
	}

	public List<GameProductType> getAll() {
		return gameProductTypeJpa.findAllByOrderByCreatedAtAsc();
	}

	public List<GameProductTypeWithProviderRes> getTree() {
		return gameProductTypeDao.getProductTypeWithProvider();
	}
	
	public List<GameProductTypeNoIcon> getDropdownGameProductType() {
		return gameProductTypeNoIconJpa.findAll();
	}
}
