package coffee.backoffice.casino.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.model.GameGroupMappingProvider;
import coffee.backoffice.casino.repository.jpa.GameGroupMappingProviderJpa;
import coffee.backoffice.casino.vo.req.GameGroupMappingProviderReq;
import coffee.backoffice.casino.vo.res.GameGroupMapProviderRes;
import framework.utils.UserLoginUtil;

@Service
public class GameGroupMappingProviderService {
	@Autowired
	private GameGroupMappingProviderJpa gameGroupMappingProviderJpa;

	@Transactional
	public void saveMapping(GameGroupMappingProviderReq req) {
		GameGroupMappingProvider data = new GameGroupMappingProvider();
		for (String pvCode : req.getProviderCode()) {
			data = new GameGroupMappingProvider();
			data.setGameGroupCode(req.getGroupCode());
			data.setProviderCode(pvCode);
			data.setUpdatedBy(UserLoginUtil.getUsername());
			gameGroupMappingProviderJpa.save(data);

		}
	}

	@Transactional
	public void editMapping(GameGroupMappingProviderReq req) {
		List<GameGroupMappingProvider> tempData = gameGroupMappingProviderJpa.findByGameGroupCode(req.getGroupCode());
		for (GameGroupMappingProvider gmpData : tempData) {
			gameGroupMappingProviderJpa.deleteByGameGroupCode(req.getGroupCode());
		}
		GameGroupMappingProvider data = new GameGroupMappingProvider();
		for (String pvCode : req.getProviderCode()) {
			data = new GameGroupMappingProvider();
			data.setGameGroupCode(req.getGroupCode());
			data.setProviderCode(pvCode);
			data.setUpdatedBy(UserLoginUtil.getUsername());
			data.setUpdatedAt(new Date());
			gameGroupMappingProviderJpa.save(data);
		}
	}

	public GameGroupMapProviderRes getByGroupCode(String ggCode) {
		List<GameGroupMappingProvider> data = gameGroupMappingProviderJpa.findByGameGroupCode(ggCode);
		GameGroupMapProviderRes res = new GameGroupMapProviderRes();
		List<String> tempPdCode = new ArrayList<String>();
		if (data.size() > 0) {
			for (GameGroupMappingProvider tempData : data) {
				tempPdCode.add(tempData.getProviderCode());
			}
			res.setGroupCode(ggCode);
			res.setProviderCode(tempPdCode);
		}
		return res;
	}

	public List<GameGroupMappingProvider> getAllByProviderCode(String code) {
		List<GameGroupMappingProvider> data = gameGroupMappingProviderJpa.findAllByProviderCode(code);
		return data;
	}
	
	public List<String> getGameGroupCodeByProviderCode(List<String> providersCode) {
		List<String> data = gameGroupMappingProviderJpa.findGameGroupCodeByProviderCodeIn(providersCode);
		return data;
	}

    public List<GameGroupMappingProvider> getAll() {
    	return (List<GameGroupMappingProvider>) gameGroupMappingProviderJpa.findAll();
	}
}
