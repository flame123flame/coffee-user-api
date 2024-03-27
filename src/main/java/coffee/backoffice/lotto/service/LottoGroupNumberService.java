package coffee.backoffice.lotto.service;

import coffee.backoffice.lotto.model.LottoGroupNumber;
import coffee.backoffice.lotto.model.LottoGroupNumberChild;
import coffee.backoffice.lotto.repository.jpa.LottoGroupNumberChildRepository;
import coffee.backoffice.lotto.repository.jpa.LottoGroupNumberRepository;
import coffee.backoffice.lotto.vo.req.LottoGroupNumberChildReq;
import coffee.backoffice.lotto.vo.req.LottoGroupNumberReq;
import coffee.backoffice.lotto.vo.res.LottoGroupNumberRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LottoGroupNumberService {
    @Autowired
    LottoGroupNumberRepository lottoGroupNumberRepository;
    @Autowired
    LottoGroupNumberChildRepository lottoGroupNumberChildRepository;
    @Autowired
    LottoGroupNumberChildService lottoGroupNumberChildService;
    private ModelMapper modelMapper =  new ModelMapper();


    public LottoGroupNumberRes getOne(Long id) throws Exception {
        Optional<LottoGroupNumber> data = lottoGroupNumberRepository.findById(id);
        if (!data.isPresent())
            return null;
        LottoGroupNumberRes returnData = new LottoGroupNumberRes();
        returnData.setEntityToRes(data.get());
        returnData.setLottoGroupNumberChildList(lottoGroupNumberChildRepository.findAllByLottoGroupNumberCode(data.get().getLottoNumberGroupCode()));
        returnData.setLottoGroupNumberChildCount(lottoGroupNumberChildRepository.countByLottoGroupNumberCode(data.get().getLottoNumberGroupCode()));
        return returnData;
    }

    public List<LottoGroupNumberRes> getAllByUsernameOwner(String username) throws Exception {
        List<LottoGroupNumber> data = lottoGroupNumberRepository.findAllByUsernameOwnerOrUsernameOwnerOrderByCreatedDateDesc(username,"admin");
        List<LottoGroupNumberRes> lottoGroupNumberRes = new ArrayList<>();
        for (LottoGroupNumber i : data) {
            LottoGroupNumberRes oneData = new LottoGroupNumberRes();
            oneData.setEntityToRes(i);
            oneData.setLottoGroupNumberChildCount(lottoGroupNumberChildRepository.countByLottoGroupNumberCode(i.getLottoNumberGroupCode()));
            lottoGroupNumberRes.add(oneData);
        }
        return lottoGroupNumberRes;
    }

    public List<LottoGroupNumberRes> getAll() throws Exception {
        List<LottoGroupNumber> data = lottoGroupNumberRepository.findAllByOrderByCreatedDateDesc();
        if (data.isEmpty())
            return null;
        List<LottoGroupNumberRes> returnData = new ArrayList<>();
        for (LottoGroupNumber i : data) {
            LottoGroupNumberRes oneData = new LottoGroupNumberRes();
            oneData.setEntityToRes(i);
            oneData.setLottoGroupNumberChildCount(lottoGroupNumberChildRepository.countByLottoGroupNumberCode(i.getLottoNumberGroupCode()));
            returnData.add(oneData);
        }
        return returnData;
    }

    @Transactional
    public LottoGroupNumberRes insertOne(LottoGroupNumberReq form) throws Exception {
        //  create insert
        LottoGroupNumber dataInsert = new LottoGroupNumber();
        modelMapper.map(form,dataInsert);
        dataInsert.setLottoNumberGroupCode(GenerateRandomString.generateUUID());
        LottoGroupNumber saveData = lottoGroupNumberRepository.save(dataInsert);

        if (form.getLottoGroupNumberChildList() != null && !form.getLottoGroupNumberChildList().isEmpty()) {
            for (LottoGroupNumberChildReq item:form.getLottoGroupNumberChildList()
                 ) {
                item.setLottoGroupNumberCode(saveData.getLottoNumberGroupCode());
                lottoGroupNumberChildService.insertOne(item);
            }
        }
        LottoGroupNumberRes lottoGroupNumberRes = new LottoGroupNumberRes();
        modelMapper.map(saveData,lottoGroupNumberRes);
        return lottoGroupNumberRes;
    }

    @Transactional
    public LottoGroupNumberRes updateOne(LottoGroupNumberReq form) throws Exception {
        //  create insert
        LottoGroupNumber dataInsert = lottoGroupNumberRepository.findById(form.getId()).get();
        modelMapper.map(form,dataInsert);
        LottoGroupNumber saveData = lottoGroupNumberRepository.save(dataInsert);

        if (form.getLottoGroupNumberChildList() != null && !form.getLottoGroupNumberChildList().isEmpty()) {
            for (LottoGroupNumberChildReq item:form.getLottoGroupNumberChildList()
            ) {
                item.setLottoGroupNumberCode(saveData.getLottoNumberGroupCode());
                lottoGroupNumberChildService.insertOne(item);
            }
        }
        LottoGroupNumberRes lottoGroupNumberRes = new LottoGroupNumberRes();
        modelMapper.map(saveData,lottoGroupNumberRes);
        return lottoGroupNumberRes;
    }

    @Transactional
    public LottoGroupNumberRes delete(long id) throws Exception {
        //  create insert
        Optional<LottoGroupNumber> dataDelete = lottoGroupNumberRepository.findById(id);
        if (!dataDelete.isPresent())
            return null;
        lottoGroupNumberRepository.deleteById(id);
        List<LottoGroupNumberChild> listData = lottoGroupNumberChildRepository.findAllByLottoGroupNumberCode(
                dataDelete.get().getLottoNumberGroupCode()
        );
        lottoGroupNumberChildRepository.deleteAll(listData);
        LottoGroupNumberRes returnData = new LottoGroupNumberRes();
        returnData.setEntityToRes(dataDelete.get());
        return returnData;
    }
}
