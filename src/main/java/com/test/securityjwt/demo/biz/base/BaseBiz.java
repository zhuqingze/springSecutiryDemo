package com.test.securityjwt.demo.biz.base;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.test.securityjwt.demo.res.base.TableResultResponse;
import com.test.securityjwt.demo.util.EntityUtils;
import com.test.securityjwt.demo.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseBiz<M extends Mapper<T>, T> {
    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    public T selectById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    public List<T> selectListAll() {
        return mapper.selectAll();
    }

    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }

    public void insert(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
        mapper.insert(entity);
    }

    public void insertSelective(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
        mapper.insertSelective(entity);
    }

    public void delete(T entity) {
        mapper.delete(entity);
    }

    public void deleteById(String id) {
        mapper.deleteByPrimaryKey(id);
    }

    public void updateById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKey(entity);
    }

    public void updateSelectiveById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKeySelective(entity);
    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public TableResultResponse<T> selectByQuery(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        if(this.isContantsCrtTime(clazz)){
            example.setOrderByClause("CRT_TIME DESC");
        }
        Page<Object> result = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<T> list = mapper.selectByExample(example);
        return new TableResultResponse<T>(result.getPageSize(), result.getPageNum() ,result.getPages(), result.getTotal(), list);
    }

    /**
     * ?????????????????????
     * @param query
     * @return
     */
    public TableResultResponse<T> selectByQueryEq(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                criteria.andEqualTo(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<T> list = mapper.selectByExample(example);
        return new TableResultResponse<T>(result.getPageSize(), result.getPageNum() ,result.getPages(), result.getTotal(), list);
    }

    /**
     * ????????????????????????
     * @param query
     * @return
     */
    public TableResultResponse<T> selectByQueryNotEq(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                criteria.andNotEqualTo(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<T> list = mapper.selectByExample(example);
        return new TableResultResponse<T>(result.getPageSize(), result.getPageNum() ,result.getPages(), result.getTotal(), list);
    }

    /**
     * ???????????????????????????
     * ??????????????????
     * @param query
     * @return
     */
    public TableResultResponse<T> selectByQueryM(Query query,String type) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                List<String> valueList = new ArrayList<>();
                valueList = (List<String>) entry.getValue();
                if (type.equals("log")){
                    criteria.andNotEqualTo(entry.getKey(),  valueList.get(0) ).andNotEqualTo(entry.getKey(),  valueList.get(1));
                }
                else if (type.equals("Security")){
                    criteria.orEqualTo(entry.getKey(),  valueList.get(0) ).orEqualTo(entry.getKey(),  valueList.get(1) );
                }
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<T> list = mapper.selectByExample(example);
        return new TableResultResponse<T>(result.getPageSize(), result.getPageNum() ,result.getPages(), result.getTotal(), list);
    }

    protected abstract String getPageName();

    private boolean isContantsCrtTime(Class<T> t){
        try {
            Method crtTime = t.getMethod("getCrtTime");
            if(crtTime != null){
                return true;
            }
            return false;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

}
