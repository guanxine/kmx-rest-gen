package com.k2data.platform.rest.demo.common;;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.k2data.web.core.utils.LogUtils;
import com.k2data.web.core.web.domain.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by guanxine on 18-04-04.
 */
public class BaseExceptionMapper implements ExceptionMapper {

    private static final String UNRECOGNIZED_PROPERTY_EXCEPTION = "Error: Property %s of %s can not be recognized.";
    private static final String INVALID_FORMAT_EXCEPTION = "Error: Invalid %s '%s'. %s must be %s.";
    private static final String BAD_JSON_PAYLOAD_EXCEPTION = "Error: Input is not a json format.";
    private static final String BAD_JSON_TYPE_PAYLOAD_EXCEPTION = "Error: Invalid type %s.";
    private static final String BAD_PARAM_TYPE_PAYLOAD_EXCEPTION = "Error: Invalid param type, %s";

    private static Logger logger = LoggerFactory.getLogger(BaseExceptionMapper.class);

    @Override
    public Response toResponse(Throwable e) {
        BaseResult result = new BaseResult(1, e.getMessage());
        int errorCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        if (e instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException ex = (UnrecognizedPropertyException) e;
            List<JsonMappingException.Reference> refs = ex.getPath();
            JsonMappingException.Reference reference = refs.get(refs.size() - 1);
            String className = "";
            if (reference != null) {
                Class<?> t = reference.getFrom().getClass();
                className = t.getSimpleName();
            }
            errorCode = Response.Status.BAD_REQUEST.getStatusCode();
            result.setMessage(String.format(UNRECOGNIZED_PROPERTY_EXCEPTION, ex.getPropertyName(), className));
        }
        else if (e instanceof WebApplicationException) {
            WebApplicationException ex = (WebApplicationException) e;
            if (e instanceof NotFoundException) { // param exception
                Throwable cause = e.getCause();
                if (cause instanceof NumberFormatException) {
                    NumberFormatException numberFormatException = (NumberFormatException) cause;
                    errorCode = Response.Status.BAD_REQUEST.getStatusCode();
                    result.setMessage(String.format(BAD_PARAM_TYPE_PAYLOAD_EXCEPTION, numberFormatException.getMessage()));
                }
            }
            else {
                errorCode = ex.getResponse().getStatus();
                result.setMessage(ex.getLocalizedMessage());
            }
        }
        else if (e instanceof JsonMappingException) {
            if (e instanceof InvalidFormatException) {
                InvalidFormatException ex = (InvalidFormatException) e;
                List<JsonMappingException.Reference> jsonPaths = ex.getPath();
                String fieldName = jsonPaths.get(jsonPaths.size() - 1).getFieldName();
                String usage = ex.getTargetType().getName();

                Class clazz = null;

                try {
                    clazz = Class.forName(usage);
                }
                catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                if (clazz != null && clazz.isEnum()) {
                    errorCode = Response.Status.BAD_REQUEST.getStatusCode();
                    result.setMessage("Error: " + e.getCause().getMessage());
                }
                else {
                    errorCode = Response.Status.BAD_REQUEST.getStatusCode();
                    result.setMessage(String.format(INVALID_FORMAT_EXCEPTION, fieldName, ex.getValue(), fieldName, usage));
                }

            }
            else {
                errorCode = Response.Status.BAD_REQUEST.getStatusCode();
                JsonMappingException ex = (JsonMappingException) e;
                List<JsonMappingException.Reference> jsonPaths = ex.getPath();
                if (jsonPaths.size() > 0) {
                    JsonMappingException.Reference reference = jsonPaths.get(jsonPaths.size() - 1);
                    String fieldName = reference.getFieldName();
                    Class tempClass = reference.getFrom().getClass();

                    List<Field> fieldList = new ArrayList<>();
                    while (tempClass != null) {
                        fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                        tempClass = tempClass.getSuperclass();
                    }

                    Class fieldClazz = getFieldClass(fieldList, fieldName);
                    if (fieldClazz != null && fieldClazz.isEnum()) {
                        errorCode = Response.Status.BAD_REQUEST.getStatusCode();
                        result.setMessage("Error: " + e.getCause().getMessage());
                    }
                    else {
                        result.setMessage(String.format(BAD_JSON_TYPE_PAYLOAD_EXCEPTION, fieldName));
                    }
                }
                else {
                    result.setMessage(e.getMessage());
                }
            }
        }
        else if (e instanceof JsonParseException) {
            errorCode = Response.Status.BAD_REQUEST.getStatusCode();
            result.setMessage(BAD_JSON_PAYLOAD_EXCEPTION);
        }
        else {
            result.setMessage(e.getMessage());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String message;
        try {
            message = objectMapper.writeValueAsString(result);
        }
        catch (JsonProcessingException exception) {
            message = e.getMessage();
        }
        if (errorCode == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            LogUtils.logStackTrace(logger, e);
        }
        return Response.status(errorCode).entity(message).build();
    }

    private Class getFieldClass(List<Field> fieldList, String fieldName) {
        for (Field f : fieldList) {
            if (fieldName.equals(f.getName())) {
                return f.getType();
            }
        }
        return null;
    }
}
