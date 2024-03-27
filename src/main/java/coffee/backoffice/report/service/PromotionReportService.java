package coffee.backoffice.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.report.repository.dao.PromotionReportDao;
import coffee.backoffice.report.vo.res.PromotionReportDatatableRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;

@Service
public class PromotionReportService {

    @Autowired
    PromotionReportDao promotionReportDao;

    public DataTableResponse<PromotionReportDatatableRes> getPaginate(DatatableRequest req){
        DataTableResponse<PromotionReportDatatableRes> dataTable = new DataTableResponse<>();
        DataTableResponse<PromotionReportDatatableRes> tag = promotionReportDao.paginate(req);
        List<PromotionReportDatatableRes> data = tag.getData();
        dataTable.setRecordsTotal(tag.getRecordsTotal());
        dataTable.setDraw(tag.getDraw());
        dataTable.setData(data);
        return dataTable;
    }
}
