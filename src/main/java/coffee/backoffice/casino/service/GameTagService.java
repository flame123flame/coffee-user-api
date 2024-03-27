package coffee.backoffice.casino.service;

import coffee.backoffice.casino.model.GameTag;
import coffee.backoffice.casino.repository.dao.GameTagDao;
import coffee.backoffice.casino.repository.jpa.GameTagJpa;
import coffee.backoffice.casino.vo.req.GameTagReorderReq;
import coffee.backoffice.casino.vo.req.GameTagReq;
import coffee.backoffice.casino.vo.res.GameTagRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.UserLoginUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class GameTagService {
    @Autowired
    GameTagJpa gameTagJpa;
    @Autowired
    GameTagDao gameTagDao;

    private ModelMapper modelMapper = new ModelMapper();

    public void insertOne(GameTagReq param) {
        GameTag gameTag = new GameTag();
        modelMapper.map(param,gameTag);
        gameTagJpa.save(gameTag);
    }

    public void editOne(Long id, GameTagReq param) {
        GameTag gameTag = gameTagJpa.findById(id).get();
        modelMapper.map(param,gameTag);
        gameTag.setUpdatedAt(new Date());
        gameTag.setUpdatedBy(UserLoginUtil.getUsername());
        gameTagJpa.save(gameTag);
    }

    public GameTagRes getByCode(String code) {
        GameTag gameTag = gameTagJpa.findByCode(code).get();
        GameTagRes gameTagRes = modelMapper.map(gameTag,GameTagRes.class);
        return  gameTagRes;
    }

    public void deleteOne(Long id) {
        gameTagJpa.deleteById(id);
    }

    public DataTableResponse<GameTagRes> getPaginate(DatatableRequest req) {
        DataTableResponse<GameTagRes> dataTable = new DataTableResponse<>();
        DataTableResponse<GameTagRes> tag = gameTagDao.getPaginateGames(req);
        List<GameTagRes> data = tag.getData();
        dataTable.setRecordsTotal(tag.getRecordsTotal());
        dataTable.setDraw(tag.getDraw());
        dataTable.setData(data);
        return dataTable;
    }

    public List<GameTag> getAll(){
        return gameTagJpa.findAll(Sort.by("priority").descending().and(Sort.by("createdAt").descending()));
    }

    public List<GameTagRes> getAllRes(){
        List<GameTagRes> gameTagRes = modelMapper.map(gameTagJpa.findAll(),new TypeToken<List<GameTagRes>>(){}.getType());
        return gameTagRes;
    }


    @Transactional
    public void reOrder(List<GameTagReorderReq> param) {
        for (GameTagReorderReq item:param
             ) {
            GameTag gameTag = gameTagJpa.findById(item.getId()).get();
            gameTag.setPriority(item.getPriority());
            gameTag.setUpdatedBy(UserLoginUtil.getUsername());
            gameTag.setUpdatedAt(new Date());
            gameTagJpa.save(gameTag);
        }
    }

    public GameTagRes getById(Long id) {
        GameTag gameTag = gameTagJpa.findById(id).get();
        GameTagRes gameTagRes = modelMapper.map(gameTag,GameTagRes.class);
        return  gameTagRes;
    }
}
