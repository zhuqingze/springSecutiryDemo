package com.test.securityjwt.demo.util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Date;



public class EntityUtils {

	public static <T> void setCreatAndUpdatInfo(T entity) {
		setCreateInfo(entity);
		setUpdatedInfo(entity);
	}
	
	/**
	 * 快速将bean的crtUser、crtHost、crtTime附上相关值
	 * 
	 * @param entity 实体bean
	 * @author dk
	 */
	public static <T> void setCreateInfo(T entity){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String hostIp = "";
		String name = "";
		String id = "";
		String pkID = "";
		if(request!=null) {
		    if(!StringUtils.isEmpty(request.getHeader(CommonConstants.WEB_USERHOST))){
                hostIp = String.valueOf(request.getHeader(CommonConstants.WEB_USERHOST));
            }
            if(!StringUtils.isEmpty(request.getHeader(CommonConstants.WEB_USERNAME))){
                name = String.valueOf(request.getHeader(CommonConstants.WEB_USERNAME));
                //TODO:存入数据库有乱码
				try {
					name = URLDecoder.decode(name,"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
            if(!StringUtils.isEmpty(request.getHeader(CommonConstants.WEB_USERID))){
                id = String.valueOf(request.getHeader(CommonConstants.WEB_USERID));
            }
		}
		pkID = IDUtil.generateShortUuid();
		// 默认属性
		String[] fields = {"id","crtName","crtUser","crtHost","crtTime"};
		Field field = ReflectionUtils.getAccessibleField(entity, "crtTime");
		// 默认值
		Object [] value = null;
		if(field!=null&&field.getType().equals(Date.class)){
			value = new Object []{pkID,name,id,hostIp,new Date()};
		}
		// 填充默认属性值
		setDefaultValues(entity, fields, value);
	}

	/**
	 * 快速将bean的updUser、updHost、updTime附上相关值
	 * 
	 * @param entity 实体bean
	 * @author dk
	 */
	public static <T> void setUpdatedInfo(T entity){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String hostIp = "";
		String name = "";
		String id = "";
		if(request!=null) {
			if(!StringUtils.isEmpty(request.getHeader(CommonConstants.WEB_USERHOST))){
				hostIp = String.valueOf(request.getHeader(CommonConstants.WEB_USERHOST));
			}
			if(!StringUtils.isEmpty(request.getHeader(CommonConstants.WEB_USERNAME))){
				name = String.valueOf(request.getHeader(CommonConstants.WEB_USERNAME));
				try {
					name = URLDecoder.decode(name,"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if(!StringUtils.isEmpty(request.getHeader(CommonConstants.WEB_USERID))){
				id = String.valueOf(request.getHeader(CommonConstants.WEB_USERID));
			}
		}
		// 默认属性
		String[] fields = {"updName","updUser","updHost","updTime"};
		Field field = ReflectionUtils.getAccessibleField(entity, "updTime");
		Object [] value = null;
		if(field!=null&&field.getType().equals(Date.class)){
			value = new Object []{name,id,hostIp,new Date()};
		}
		// 填充默认属性值
		setDefaultValues(entity, fields, value);
	}
	/**
	 * 依据对象的属性数组和值数组对对象的属性进行赋值
	 * 
	 * @param entity 对象
	 * @param fields 属性数组
	 * @param value 值数组
	 * @author dk
	 */
	private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
		for(int i=0;i<fields.length;i++){
			String field = fields[i];
			if(ReflectionUtils.hasField(entity, field)){
				ReflectionUtils.invokeSetter(entity, field, value[i]);
			}
		}
	}
}
