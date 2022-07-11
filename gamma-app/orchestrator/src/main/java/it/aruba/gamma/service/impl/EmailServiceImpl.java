package it.aruba.gamma.service.impl;

import it.aruba.gamma.exception.BusinessServiceException;
import it.aruba.gamma.model.User;
import it.aruba.gamma.model.UserEmail;
import it.aruba.gamma.service.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public User getUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Optional<User> user = getMockUser().stream().filter(u -> u.getUsername().equals(username)).findFirst();
        if (user.isPresent())
            return user.get();
        else
            throw new BusinessServiceException("User Not Found", "ERR_NO_DATA");

    }

    @Override
    public List<UserEmail> searchEmail(Map<String,String> searchFilter) {
        List<UserEmail> emails = new ArrayList<UserEmail>();
        UserEmail oneMail = new UserEmail().builder()
                .id(UUID.randomUUID().toString())
                .from("fatturazione@aruba.it")
                .sentDate(new Date())
                .subject("Emissione fattura N.123")
                .text("In allegato la fattura")
                .to("r.lanzetta@gmail.com")
                .attachment(true)
                .build();
        emails.add(oneMail);
        return emails;
    }

    private List<User>  getMockUser() {
        /* Mock */
        List<User> users = new ArrayList<User>();

        User user1 = new User();
        user1.setUsername("raffaele");
        user1.setName("Raffaele");
        user1.setSurname("Lanzetta");
        user1.setEmails(Arrays.asList("r.lanzetta@gmail.com", "raffaele.lanzetta@pec.it"));
        users.add(user1);

        User user2 = new User();
        user2.setUsername("michele");
        user2.setName("Michele");
        user2.setSurname("Lanzetta");
        user2.setEmails(Arrays.asList("m.lanzetta@gmail.com"));
        users.add(user2);

        return users;
    }
}
