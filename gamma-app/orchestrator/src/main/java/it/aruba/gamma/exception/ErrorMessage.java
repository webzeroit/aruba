package it.aruba.gamma.exception;

import lombok.*;

import java.io.Serializable;
import java.util.Map;


@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class ErrorMessage implements Serializable {

    private static final long serialVersionUID = -1293322726381604017L;

    private String target;

    private String code;

    private Map<String, String> params;

    private long id;

}

