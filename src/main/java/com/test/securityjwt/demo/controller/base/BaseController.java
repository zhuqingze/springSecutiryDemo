package com.test.securityjwt.demo.controller.base;



import com.test.securityjwt.demo.biz.base.BaseBiz;
import com.test.securityjwt.demo.res.base.ListRestResponse;
import com.test.securityjwt.demo.res.base.ObjectRestResponse;
import com.test.securityjwt.demo.res.base.TableResultResponse;
import com.test.securityjwt.demo.util.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
public class BaseController<Biz extends BaseBiz, Entity> {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected Biz baseBiz;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Entity> add(@RequestBody Entity entity) {
        baseBiz.insertSelective(entity);
        return new ObjectRestResponse<Entity>().rel(true);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Entity> get(@PathVariable String id) {
        ObjectRestResponse<Entity> entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = baseBiz.selectById(id);
        entityObjectRestResponse.data((Entity) o).rel(true);
        return entityObjectRestResponse;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<Entity> update(@RequestBody @Valid Entity entity) {
        baseBiz.updateSelectiveById(entity);
        return new ObjectRestResponse<Entity>().rel(true);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<Entity> remove(@PathVariable String id) {
        Object deletingObject = baseBiz.selectById(id);
        baseBiz.deleteById(id);
        return new ObjectRestResponse<Entity>().rel(true).data(deletingObject);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<Entity>> all() {
        List list = baseBiz.selectListAll();
        return new ListRestResponse("",list.size(),list);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<Entity> page(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        return baseBiz.selectByQuery(query);
    }
}
