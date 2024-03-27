package coffee.backoffice.lotto.service;

import coffee.backoffice.lotto.model.LottoGroupNumber;
import coffee.backoffice.lotto.model.LottoGroupNumberChild;
import coffee.backoffice.lotto.repository.jpa.LottoGroupNumberChildRepository;
import coffee.backoffice.lotto.vo.req.LottoGroupNumberChildReq;
import coffee.backoffice.lotto.vo.res.LottoGroupNumberChildRes;
import framework.utils.UserLoginUtil;
import io.swagger.models.Model;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LottoGroupNumberChildService {

    @Autowired
    LottoGroupNumberChildRepository lottoGroupNumberChildRepository;

    private ModelMapper modelMapper =  new ModelMapper();

    public List<LottoGroupNumberChild> getOneByParentCode(String code){
        return lottoGroupNumberChildRepository.findAllByLottoGroupNumberCode(code);
    }

    public void deleteById(Long id) throws Exception{
        lottoGroupNumberChildRepository.deleteById(id);
    }

    public LottoGroupNumberChildRes updateOne(LottoGroupNumberChildReq req) throws Exception{
        LottoGroupNumberChild lottoGroupNumberChild = lottoGroupNumberChildRepository.findById(req.getId()).get();
        lottoGroupNumberChild.setReqToEntity(req);
        lottoGroupNumberChild.setUpdatedDate(new Date());
        lottoGroupNumberChild.setUpdatedBy(UserLoginUtil.getUsername());
        lottoGroupNumberChildRepository.save(lottoGroupNumberChild);
        LottoGroupNumberChildRes lottoGroupNumberChildRes = new LottoGroupNumberChildRes();
        modelMapper.map(lottoGroupNumberChild,lottoGroupNumberChildRes);
        return lottoGroupNumberChildRes;
    }

    public LottoGroupNumberChildRes insertOne(LottoGroupNumberChildReq req) throws Exception{
        LottoGroupNumberChild lottoGroupNumberChild = new LottoGroupNumberChild();
        modelMapper.map(req,lottoGroupNumberChild);
        lottoGroupNumberChildRepository.save(lottoGroupNumberChild);
        LottoGroupNumberChildRes lottoGroupNumberChildRes = new LottoGroupNumberChildRes();
        modelMapper.map(lottoGroupNumberChild,lottoGroupNumberChildRes);
        return lottoGroupNumberChildRes;
    }

}
