package coffee.backoffice.casino.vo.req;

import lombok.Data;

import javax.persistence.Column;

@Data
public class GameTagMappingGameReq {

    private String gameTagCode;

    private String gameCode;
}
