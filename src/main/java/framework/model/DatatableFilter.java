package framework.model;

import lombok.Data;

@Data
public class DatatableFilter {
    private String column;
    private String value;
    private String value1;
    private String op;
}
