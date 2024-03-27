package coffee.backoffice.casino.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.model.GameGroup;
import coffee.backoffice.casino.repository.dao.GameGroupDao;
import coffee.backoffice.casino.repository.jpa.GameGroupJpa;
import coffee.backoffice.casino.vo.req.GameGroupReq;
import coffee.backoffice.casino.vo.res.GameGroupRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.SAVE;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.UserLoginUtil;

@Service
public class GameGroupService {
	@Autowired
	private GameGroupDao gameGroupDao;

	@Autowired
	private GameGroupJpa gameGroupJpa;

	private ModelMapper modelMapper = new ModelMapper();

	@Transactional
	public String saveGroup(GameGroupReq req) {
		GameGroup temp = gameGroupJpa.findByCode(req.getCode());
		if (temp == null) {
			GameGroup data = new GameGroup();
			data.setCode(req.getCode());
			data.setNameEn(req.getNameEn());
			data.setNameTh(req.getNameTh());
			if (req.getProductCode() != null) {
				data.setProductCode(String.join(",", req.getProductCode()));
			}
			data.setUpdatedBy(UserLoginUtil.getUsername());
			gameGroupJpa.save(data);
		} else {
			return SAVE.DUPLICATE_DATA;
		}

		return SAVE.SUCCESS;
	}

	@Transactional
	public void editGroup(GameGroupReq req) {
		GameGroup data = gameGroupJpa.findByCode(req.getCode());
		if (data != null) {
			data.setNameEn(req.getNameEn());
			data.setNameTh(req.getNameTh());
			if (req.getProductCode() != null) {
				data.setProductCode(String.join(",", req.getProductCode()));
			} else if (req.getProductCode().size() < 1) {
				data.setProductCode(null);
			}
			data.setUpdatedAt(new Date());
			data.setUpdatedBy(UserLoginUtil.getUsername());
			gameGroupJpa.save(data);
		}
	}

	public GameGroupRes getGameGroupByCode(String code) {
		GameGroupRes res = gameGroupDao.findGroupByCode(code);
		return res;
	}

	public DataTableResponse<GameGroupRes> getPaginateGroup(DatatableRequest req) {
		DataTableResponse<GameGroupRes> dataTable = new DataTableResponse<>();
		DataTableResponse<GameGroupRes> newData = new DataTableResponse<>();
		newData = gameGroupDao.paginate(req);
		dataTable.setRecordsTotal(newData.getRecordsTotal());
		dataTable.setDraw(newData.getDraw());
		dataTable.setData(newData.getData());
		return dataTable;
	}

	@Transactional
	public void deleteGroup(String code) {
		gameGroupJpa.deleteByCode(code);
	}

	public List<GameGroupRes> getAll() {
		List<GameGroup> gameGroup = gameGroupJpa.findAll();
		List<GameGroupRes> gameGroupRes = modelMapper.map(gameGroup,new TypeToken<List<GameGroupRes>>(){}.getType());
		return gameGroupRes;
	}
}
