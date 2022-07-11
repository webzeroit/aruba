package it.aruba.gamma.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

        private String username;
        private String name;
        private String surname;
        private List<String> emails;

}
