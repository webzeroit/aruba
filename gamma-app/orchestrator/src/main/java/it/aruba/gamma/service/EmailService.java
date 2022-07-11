package it.aruba.gamma.service;

import it.aruba.gamma.model.User;
import it.aruba.gamma.model.UserEmail;

import java.util.List;
import java.util.Map;

public interface EmailService {

    User getUserEmail();

    List<UserEmail> searchEmail(Map<String, String> searchFilter);
}
