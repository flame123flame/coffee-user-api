package coffee.backoffice.casino.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.model.ProductMappingProvider;
import coffee.backoffice.casino.repository.jpa.ProductMapProviderJpa;
import coffee.backoffice.casino.vo.req.ProductMapProviderReq;
import coffee.backoffice.casino.vo.res.ProductMapProviderRes;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class ProductMappingProviderService {

    @Autowired
    private ProductMapProviderJpa productMapProviderJpa;

    @Transactional
    public void saveMapping(ProductMapProviderReq req) {
        ProductMappingProvider data = new ProductMappingProvider();
        for (String pvCode : req.getProviderCode()) {
            data = new ProductMappingProvider();
            data.setCode(GenerateRandomString.generateUUID());
            data.setProductCode(req.getProductCode());
            data.setProviderCode(pvCode);
            data.setUpdatedBy(UserLoginUtil.getUsername());
            productMapProviderJpa.save(data);
        }
    }

    @Transactional
    public void editMapping(ProductMapProviderReq req) {
        List<ProductMappingProvider> tempData = productMapProviderJpa.findByProductCode(req.getProductCode());
        for (ProductMappingProvider pmpData : tempData) {
            productMapProviderJpa.deleteByCode(pmpData.getCode());
        }
        ProductMappingProvider data = new ProductMappingProvider();
        for (String pvCode : req.getProviderCode()) {
            data = new ProductMappingProvider();
            data.setCode(GenerateRandomString.generateUUID());
            data.setProductCode(req.getProductCode());
            data.setProviderCode(pvCode);
            data.setUpdatedBy(UserLoginUtil.getUsername());
            data.setUpdatedAt(new Date());
            productMapProviderJpa.save(data);
        }
    }

    public ProductMapProviderRes getByProductCode(String pdCode) {
        List<ProductMappingProvider> data = productMapProviderJpa.findByProductCode(pdCode);
        ProductMapProviderRes res = new ProductMapProviderRes();
        List<String> tempPdCode = new ArrayList<String>();
        if (data.size() > 0) {
            for (ProductMappingProvider tempData : data) {
                tempPdCode.add(tempData.getProviderCode());
            }
            res.setProductCode(pdCode);
            res.setProviderCode(tempPdCode);
        }
        return res;
    }

//    public List<String> getProviderByProduct(String pdCode) {
//        List<ProductMappingProvider> data = productMapProviderJpa.findByProductCode(pdCode);
//        List<String> tempPdCode = new ArrayList<String>();
//        for (ProductMappingProvider tempData : data) {
//            tempPdCode.add(tempData.getProviderCode());
//        }
//        return tempPdCode;
//    }

    public List<String> getProductByProvider(String pvCode) {
        List<ProductMappingProvider> data = productMapProviderJpa.findByProviderCode(pvCode);
        List<String> tempPdCode = new ArrayList<String>();
        for (ProductMappingProvider tempData : data) {
            tempPdCode.add(tempData.getProductCode());
        }
        return tempPdCode;
    }
    
    public List<String> getListProviderByProduct(String productCode) {
    	return productMapProviderJpa.findProviderCodeByProductCode(productCode);
    }

    public List<ProductMappingProvider> getByProduct(String pdCode) {
        return productMapProviderJpa.findByProductCode(pdCode);
    }

    @Transactional
    public void deleteMappingProduct(String code) {
        productMapProviderJpa.deleteByProductCode(code);
    }

    @Transactional
    public void deleteByProviderCode(String code) {
        productMapProviderJpa.deleteByProviderCode(code);
    }

    public List<ProductMappingProvider> getAll() {
        return productMapProviderJpa.findAll();
    }
}
