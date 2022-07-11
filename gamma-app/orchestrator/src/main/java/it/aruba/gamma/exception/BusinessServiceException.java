package it.aruba.gamma.exception;

import lombok.Getter;
import org.bouncycastle.i18n.ErrorBundle;

import javax.lang.model.type.ErrorType;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;


@Getter
public class BusinessServiceException extends RuntimeException {
    public static final int SEVERITY_BASSA = 1;
    public static final int SEVERITY_MEDIA = 2;
    public static final int SEVERITY_ALTA = 3;

    /**
     *
     */
    private static final long serialVersionUID = 2402962346495726871L;

    private ErrorMessage errorMessage;


    public BusinessServiceException(ErrorType errorType, ErrorBundle errorBundleKey) {
        this(ErrorMessage.builder()
                .target("")
                .build(), null);
    }

    public BusinessServiceException(ErrorType errorType, ErrorBundle errorBundleKey, SortedMap<String, String> errorInterpolationParams) {
        this(ErrorMessage.builder()
                .target("")
                .params(errorInterpolationParams)
                .build(), null);
    }


    public BusinessServiceException(ErrorType errorType, ErrorBundle errorBundleKey, SortedMap<String, String> errorInterpolationParams, Throwable cause) {
        this(ErrorMessage.builder()
                .target("")
                .params(errorInterpolationParams)
                .build(), cause);
    }


    public BusinessServiceException(ErrorType errorType, ErrorBundle errorBundleKey, Throwable cause) {
        this(ErrorMessage.builder()
                .target("")
                .params(null)
                .build(), cause);
    }


    public BusinessServiceException(ErrorMessage message, Throwable cause) {
        super(cause);
        this.errorMessage = message;
    }

    public BusinessServiceException(String message, String errorCode) {
        super(message);
        this.errorMessage = new ErrorMessage(message, errorCode, new HashMap<>(), 0);
    }

    public BusinessServiceException(String message, String errorCode, Map<String, String> extraParam) {
        super(message);
        this.errorMessage = new ErrorMessage(message, errorCode, extraParam, 0);
    }

}
