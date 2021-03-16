package com.cloume.maps.commons.utils;

import com.cloume.commons.beanutils.IConverter;

import java.util.Map;

/**
 * @author yxiao
 * @date
 * @description
 */
public class UpperCaseConverterUtils implements IConverter {
    @Override
    public Map.Entry<String, Object> convert(String s, Object o) {
        return pair(s.toUpperCase(), o);
    }
}
