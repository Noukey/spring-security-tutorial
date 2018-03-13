package test.spring.security.handler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		
		// TODO Auto-generated method stub
		if(authentication == null){
            throw new AccessDeniedException("permission denied");
        }

        //当前用户拥有的权限集合
        List<String> roleCodes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        //访问路径所需要的权限集合
        List<String> configRoleCodes = configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
        if(roleCodes.stream().anyMatch(x->configRoleCodes.contains(x))){
        	return;
        }
        throw new AccessDeniedException("permission denied");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
