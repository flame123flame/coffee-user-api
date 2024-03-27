package coffee.website.providers.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class GroupGameListResponse {
    private String gameGroupCode;
    private String nameTh;
    private String nameEn;
    private Long totalGame;
    private String userView;
    List<GameListResponse> gameList;
}
