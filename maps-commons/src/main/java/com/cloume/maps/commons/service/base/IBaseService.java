package com.cloume.maps.commons.service.base;

import com.cloume.commons.beanutils.IConverter;
import com.cloume.commons.beanutils.Updater;
import com.cloume.maps.commons.model.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.*;

public interface IBaseService<T> {

    /**
     * 创建新的T
     * @return
     */
    default T create() {
        return (T) new Object();
    }

    /**
     * 返回与Service对应的Repository
     * @return
     */
    JpaRepository<T, String> getRepository();

    /**
     * 更新
     * @param t
     * @return
     */
    default T wrap(T t, Map<String, Object> map) {
        return wrap(t, map, null);
    }

    /**
     * 更新
     * @param t
     * @param map
     * @param converter
     * @return
     */
    default T wrap(T t, Map<String, Object> map, IConverter converter) {
        if (map == null) {
            return null;
        }
        return Updater.wrap(t).update(map, converter);
    }

    default T find(String id) {
        Optional optional = getRepository().findById(id);
        if (optional == null || !optional.isPresent()) {
            return null;
        }
        return (T) optional.get();
    }

    /**
     * 保存
     * @param map
     * @return
     */
    default T save(Map<String, Object> map) {
        T t = this.wrap(this.create(), map);
        if (t == null) {
            return null;
        }

        return getRepository().save(t);
    }

    /**
     * 保存
     * @param t
     */
    default T save(T t) {
        if (t == null) {
            return null;
        }

        return getRepository().save(t);
    }

    /**
     * 批量保存
     * @param tList
     * @return
     */
    default Iterable<T> save(List<T> tList) {
        if (tList == null || tList.isEmpty()) {
            return null;
        }

        return getRepository().saveAll(tList);
    }

    /**
     * 更新
     * @param t
     * @param map
     * @return
     */
    default T update(T t, Map<String, Object> map) {
        if (t == null) return t;
        return getRepository().save(this.wrap(t, map));
    }

    /**
     * 更新对象
     * @param origin
     * @param target
     * @return
     */
    default T update(T origin, T target) {
        if (origin == null) {
            return null;
        }
        List<Field> fields = new ArrayList<>();
        Class tempClass = origin.getClass();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object valueTarget = field.get(target);
//                if(valueTarget != null || StringUtils.isNotEmpty(valueTarget.toString())){
                if (valueTarget != null && ((valueTarget.getClass().equals(String.class) && !valueTarget.equals("")) || (!valueTarget.getClass().equals(String.class)))) {
                    field.set(origin, valueTarget);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        getRepository().save(origin);
        return origin;
    }

    /**
     * 删除
     * @param id
     */
    default T delete(String id) {
        return delete(getRepository().findById(id).get());
    }

    /**
     * 删除
     * @param t
     */
    default T delete(T t) {
        if (t == null) {
            return null;
        }
        if (t instanceof BaseEntity) {
            ((BaseEntity) t).setRemoved(true);
            return getRepository().save(t);
        } else {
            getRepository().delete(t);
            return t;
        }
    }
}