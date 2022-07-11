package it.aruba.gamma.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEmail {

        private String id;
        private String from;
        private String to;
        private String subject;
        private String text;
        private Date sentDate;
        private String cc;
        private String bcc;
        private Boolean attachment;

        @JsonIgnore
        private MultipartFile[] multipartFiles;

}
