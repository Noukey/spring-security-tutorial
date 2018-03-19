package test.spring.security.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class DynamicQuery {

	private static final String[] condSufix = { "Min", "Max" };

	public static <U extends T, T> Specification<T> where(U u) {
		return new Specification<T>() {

			Root<T> root;
			CriteriaQuery<?> query;
			CriteriaBuilder cb;
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				this.root=root;
				this.query=query;
				this.cb=cb;
				Map<String, Object> conds = new HashMap<String, Object>();
				Class<?> clazz = u.getClass();
				while (clazz != Object.class) {
					Stream.of(clazz.getDeclaredFields())
							.filter(x -> x.getAnnotation(Column.class) != null || containsDefaultCond(x.getName()))
							.forEach(x -> {
						try {
							Object value = PropertyUtils.getProperty(u, x.getName());
							if (value != null) {
								// org.springframework.beans.BeanUtils.getPropertyDescriptors(u.getClass());
								System.out.println(x.getName() + ":" + value);
								conds.put(x.getName(), value);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
						}
					});
					clazz = clazz.getSuperclass();
				}
				@SuppressWarnings("rawtypes")
				List<Predicate> pres = conds.keySet().stream().map(x->getPredicate(x,conds.get(x))).collect(Collectors.toList());
				Predicate[] p = new Predicate[pres.size()];
				return cb.and(pres.toArray(p));
			}

			 @SuppressWarnings({ "rawtypes", "unchecked" })  
			private Predicate getPredicate(String x, Object object) {
				 if(!(object instanceof Comparable))return null;
				
				// TODO Auto-generated method stub
				if(!containsDefaultCond(x)){
					return cb.equal(getExpression(x), object);
				}
				if(x.endsWith("Min")){
					x=x.substring(0, x.indexOf("Min"));
					return cb.lessThanOrEqualTo(getExpression(x), (Comparable)object);
				}
				if(x.endsWith("Max")){
					x=x.substring(0, x.indexOf("Max"));
					return cb.lessThanOrEqualTo(getExpression(x), (Comparable)object);
				}
				return null;
			}

			private Path getExpression(String x) {
				System.out.println("property:"+x);
				Path expression = null;  
		        if(x.contains(".")){  
		            String[] names = StringUtils.split(x, ".");  
		            expression = root.get(names[0]);  
		            for (int i = 1; i < names.length; i++) {  
		                expression = expression.get(names[i]);  
		            }  
		        }else{  
		            expression = root.get(x);  
		        }
				return expression;
			}

			private boolean containsDefaultCond(String name) {
				// TODO Auto-generated method stub
				return Stream.of(condSufix).anyMatch(name::endsWith);
			}
		};
	}

}
