package coffee.backoffice.finance.service;


import coffee.backoffice.casino.repository.jpa.ProductMapProviderJpa;
import coffee.backoffice.finance.repository.dao.GameTransactionDao;
import coffee.backoffice.finance.repository.dao.ManualAdjustmentDao;
import coffee.backoffice.finance.repository.jpa.ManualAdjustmentJpa;
import coffee.backoffice.finance.vo.res.GameTransactionRes;
import coffee.backoffice.finance.vo.res.ManualAdjustmentRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManualAdjustmentService {

    @Autowired
    private ManualAdjustmentJpa manualAdjustmentJpa;

    @Autowired
    private ManualAdjustmentDao manualAdjustmentDao;

    public DataTableResponse<ManualAdjustmentRes> getAllPaginate(DatatableRequest req) {
        DataTableResponse<ManualAdjustmentRes> paginateData = manualAdjustmentDao.paginate(req);
        DataTableResponse<ManualAdjustmentRes> dataTable = new DataTableResponse<>();
        List<ManualAdjustmentRes> data = paginateData.getData();

        dataTable.setRecordsTotal(paginateData.getRecordsTotal());
        dataTable.setDraw(paginateData.getDraw());
        dataTable.setData(data);
        dataTable.setPage(req.getPage());
        return paginateData;
    }
}
