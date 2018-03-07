package test.spring.security.handler;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.mapred.gethistory_jsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import test.spring.security.domain.Authority;
import test.spring.security.repository.AuthorityRepository;

@Service
public class UserFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private Map<String, Collection<ConfigAttribute>> matchMap = null;

	@Autowired
	private AuthorityRepository authorityRepository;

	public synchronized Map<String, Collection<ConfigAttribute>> getMatchMap() {
		if (matchMap == null) {
			matchMap = authorityRepository.findAll().stream().filter(x->x.getUrl()!=null)
					.collect(Collectors.groupingBy(Authority::getUrl, Collectors.mapping(
							x -> new SecurityConfig(x.getAuthority()), Collectors.toCollection(ArrayList::new))));
		}
		return matchMap;
	}

	public synchronized void refreshMatchMap() {
		matchMap = null;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
		if (isMatcherAllowedRequest(httpRequest))
			return null;
		Map<String, Collection<ConfigAttribute>> map = getMatchMap();
		return map.keySet().stream().filter(x->new AntPathRequestMatcher(x).matches(httpRequest)).flatMap(x->map.get(x).stream()).collect(Collectors.toCollection(ArrayList::new));
//		Optional<String> key = map.keySet().stream().filter(x->new AntPathRequestMatcher(x).matches(httpRequest)).findFirst();
//		return map.get(key.orElse(null));
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	public boolean isMatcherAllowedRequest(HttpServletRequest reqest) {
		return allowedRequest().stream().map(AntPathRequestMatcher::new).filter(r -> r.matches(reqest))
				.toArray().length > 0;

	}

	/**
	 * @return 定义允许请求的列表
	 */
	private List<String> allowedRequest() {
		return Arrays.asList("/login", "/css/**", "/fonts/**", "/js/**", "/scss/**", "/img/**");
	}

	/**
	 * @return 默认拒绝访问配置
	 */
	private List<ConfigAttribute> deniedRequest() {
		return Collections.singletonList(new SecurityConfig("ROLE_DENIED"));
	}
}
