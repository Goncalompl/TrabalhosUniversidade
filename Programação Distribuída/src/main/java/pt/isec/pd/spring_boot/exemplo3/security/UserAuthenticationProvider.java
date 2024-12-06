package pt.isec.pd.spring_boot.exemplo3.security;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pt.isec.pd.spring_boot.exemplo3.helpers.DatabaseHelper;
import pt.isec.pd.spring_boot.exemplo3.models.User;


import java.util.ArrayList;
import java.util.List;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider
{
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        DatabaseHelper dbHelper = new DatabaseHelper();
        User user = dbHelper.getUserFromCredentials(username, password);
        if (user == null)
            return null;

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (username.equals("admin") && password.equals("admin"))
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        else
            authorities.add(new SimpleGrantedAuthority("USER"));

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
