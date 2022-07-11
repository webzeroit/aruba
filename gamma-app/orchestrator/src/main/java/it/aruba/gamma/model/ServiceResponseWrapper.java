package it.aruba.gamma.model;

import it.aruba.gamma.exception.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponseWrapper<T> {

    private boolean successful;
    private Integer httpErrorCode;
    private String successMessage;
    private T result;
    private List<String> validationErrors = new ArrayList<>();
    private transient ErrorMessage errorMessage;


    public static ServiceResponseWrapper buildSuccessWrapper(Object result)
    {
        ServiceResponseWrapper wrapper = new ServiceResponseWrapper();
        wrapper.setResult(result);
        wrapper.setSuccessful(true);
        wrapper.setHttpErrorCode(HttpStatus.OK.value());
        return wrapper;
    }

}