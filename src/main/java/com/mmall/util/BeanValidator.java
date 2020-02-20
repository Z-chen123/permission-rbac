package com.mmall.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.exception.ParamException;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class BeanValidator {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static<T> Map<String,String> validate(T t, Class...groups){
        Validator validator = validatorFactory.getValidator();
        //校验结果
        Set validateResult  = validator.validate(t, groups);
        //没有异常
        if(CollectionUtils.isEmpty(validateResult)){
            return Collections.emptyMap();
        }else{
            LinkedHashMap<String, String> errors = Maps.newLinkedHashMap();
            Iterator iterator = validateResult.iterator();
            while(iterator.hasNext()){
                ConstraintViolation validation = (ConstraintViolation) iterator.next();
                errors.put(validation.getPropertyPath().toString(),validation.getMessage());
            }
             return errors;
        }
    }

    public static Map<String,String> validateList(Collection<?> collection){

        // google 中校验
        Preconditions.checkNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        Map errors;
        do{
            if(!iterator.hasNext()){
                return Collections.emptyMap();
            }
                Object object = iterator.next();
                errors = validate(object, new Class[0]);
        }while(errors.isEmpty());
        return errors;
    }

    public static Map<String,String> validateObject(Object first,Object...objects){
        if(objects!=null&&objects.length>0){
            Map<String, String> map = validateList(Lists.asList(first, objects));
            return map;
        }else{
            Map<String, String> map = validate(first, new Class[0]);
            return map;
        }
    }

    public static void check(Object param) throws ParamException{
        Map<String, String> map = validateObject(param);
        if(MapUtils.isNotEmpty(map)){
            throw new ParamException(map.toString());
        }
    }
}
