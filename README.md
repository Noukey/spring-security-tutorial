# spring-security-tutorial
spring-security完整示例，实现数据库动态管理权限

相关知识：
- spring-security提供的UserDetails、GrantedAuthority、UserDetailsService接口;
```java
  @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);  //指定一个自定义实现的UserDetailsService接口
	}
```
  
  
- FilterInvocationSecurityMetadataSource接口，通过AntPathRequestMatcher匹配路径所需权限
 ```java
  @Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
		if (isMatcherAllowedRequest(httpRequest))
			return null;
		Map<String, Collection<ConfigAttribute>> map = getMatchMap();
		return map.keySet().stream().filter(x->new AntPathRequestMatcher(x).matches(httpRequest)).flatMap(x->map.get(x).stream()).collect(Collectors.toCollection(ArrayList::new));
	}
 ```
  
  
- AccessDecisionManager接口，用户权限和所需权限的决策策略;
```java
  @Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		
		// TODO Auto-generated method stub
		if(authentication == null){
            throw new AccessDeniedException("permission denied");
        }

        //当前用户拥有的角色集合
        List<String> roleCodes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        //访问路径所需要的角色集合
        List<String> configRoleCodes = configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
        if(roleCodes.stream().anyMatch(x->configRoleCodes.contains(x))){
        	return;
        }
        throw new AccessDeniedException("permission denied");
	}

```
  
------------------------------   
配置一个FilterSecurityInterceptor过滤器并加载     
`http.addFilterBefore(createFilterInterceptor(), FilterSecurityInterceptor.class);`
```java
  protected FilterSecurityInterceptor createFilterInterceptor(){
		FilterSecurityInterceptor filterInerceptor=new FilterSecurityInterceptor();
		filterInerceptor.setAccessDecisionManager(accessDecisionManager);
		filterInerceptor.setSecurityMetadataSource(securityMetadataSource);
		return filterInerceptor;
	}
```

-----------------------------------  
参考：
- http://blog.csdn.net/u012373815/article/details/54633046
- https://www.gitbook.com/book/waylau/spring-security-tutorial/details
