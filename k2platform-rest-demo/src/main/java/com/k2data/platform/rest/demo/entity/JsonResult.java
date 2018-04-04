package com.k2data.platform.rest.demo.entity;

import com.k2data.web.core.web.domain.BaseResult;

import java.util.Map;

/**
 * Created by guanxine on 18-04-04.
 */
public class JsonResult extends BaseResult {
    private Object body;

    public JsonResult() {
        super();
    }

    public JsonResult(int code, String message) {
        super(code, message);
    }

    public JsonResult(Map<String, Object> pageInfo, Object body) {
        super(pageInfo);
        this.body = body;
    }

    public JsonResult(int code, String message, Object body) {
        super(code, message);
        this.body = body;
    }

    public JsonResult(int code, String message, Map<String, Object> pageInfo, Object body) {
        super(code, message);
        setPageInfo(pageInfo);
        this.body = body;
    }


    public Object getBody() {
        return body;
    }

}
