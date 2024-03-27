package coffee.backoffice.finance.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.model.ProductMappingProvider;
import coffee.backoffice.casino.repository.jpa.ProductMapProviderJpa;
import coffee.backoffice.casino.vo.res.GameProviderCodeRes;
import coffee.backoffice.finance.repository.dao.GameTransactionDao;
import coffee.backoffice.finance.vo.res.GameTransactionRes;
import coffee.backoffice.finance.vo.res.GamesListRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;

@Service
public class GameTransactionReportService {
	
	@Autowired
	private GameTransactionDao gameTransactionDao;
	
	@Autowired
	private ProductMapProviderJpa productMapProviderJpa;
	
	
	public DataTableResponse<GameTransactionRes> getAllPaginate(DatatableRequest req) {
		DataTableResponse<GameTransactionRes> paginateData = gameTransactionDao.paginateAllGameTranSaction(req);
		DataTableResponse<GameTransactionRes> dataTable = new DataTableResponse<>();
		List<GameTransactionRes> data = paginateData.getData();

		dataTable.setRecordsTotal(paginateData.getRecordsTotal());
		dataTable.setDraw(paginateData.getDraw());
		dataTable.setData(data);
		dataTable.setPage(req.getPage());
		return paginateData;
	}
	
	public List<GameProviderCodeRes> getByProductCode(String pdCode) {
		List<ProductMappingProvider> datafind = new ArrayList<ProductMappingProvider>();
		List<GameProviderCodeRes> dataRes = new ArrayList<GameProviderCodeRes>();
		if(pdCode.equals("ALL"))
		{
			datafind = productMapProviderJpa.findAll();
			for(ProductMappingProvider dataInfo : datafind)
			{
				dataRes.add(gameTransactionDao.findGameProviderByCode(dataInfo.getProviderCode()));
			}
		}
		else
		{
			datafind = productMapProviderJpa.findByProductCode(pdCode);	
			for(ProductMappingProvider dataInfo : datafind)
			{
				dataRes.add(gameTransactionDao.findGameProviderByCode(dataInfo.getProviderCode())) ;	
				
			}
		}
		return dataRes;
	}
	public List<GamesListRes> getGameByCode(String code,String groupCode) {
		List<GamesListRes> datafind = new ArrayList<GamesListRes>();
		datafind = gameTransactionDao.findGameByCode(code,groupCode);
		return datafind;
	}
	
	public List<GamesListRes> getGameByProviderCode(String code) {
		List<GamesListRes> datafind = new ArrayList<GamesListRes>();
		if(code.equals("ALL"))
		{
			datafind = gameTransactionDao.findGameByProviderCode("");
		}
		else
		{
			datafind = gameTransactionDao.findGameByProviderCode(code);
		}
		
		return datafind;
	}
}
