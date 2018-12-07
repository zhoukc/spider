package com.zhaoyouhua.spider.dao.mybatis;

import com.zhaoyouhua.spider.dao.model.BaseModel;
import com.zhaoyouhua.spider.util.ArrayUtil;
import com.zhaoyouhua.spider.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.util.*;


@Slf4j
public class BaseSqlFactory {

    private static final Map<String, String> sqlMap = new HashMap();
    private static final String ID = "id";
    private static final IdUtil idUtil = new IdUtil();

    public String insert(Object modelPo)
            throws IntrospectionException {
        String name = modelPo.getClass().getName();
        name = name + "_insert";
        String sql = (String) sqlMap.get(name);
        if (sql != null) {
            setInsertDefaultValue(modelPo);
            return sql;
        }
        sql = getInsertSql(modelPo);
        //sqlMap.put(name, sql);
        setInsertDefaultValue(modelPo);
        return sql;
    }

    public String update(Object modelPo) throws Exception {
        String name = modelPo.getClass().getName();
        name = name + "_update";
        String sql = (String) sqlMap.get(name);
        if (sql != null) {
            return sql;
        }
        sql = getUpdateSql(modelPo);
        sqlMap.put(name, sql);
        setUpdateDefaultValue(modelPo);
        return sql;
    }

    public String delete(Object modelPo) throws Exception {
        String name = modelPo.getClass().getName();
        name = name + "_delete";
        String sql = (String) sqlMap.get(name);
        if (sql != null)
            return sql;
        sql = getDeleteSql(modelPo);
        sqlMap.put(name, sql);
        return sql;
    }


    private synchronized String getInsertSql(Object modelPo) {
        Class clazz = modelPo.getClass();
        Field[] fields = getClassFields(clazz, true);
        List sqlNames = new LinkedList();
        List sqlValues = new LinkedList();
        Field[] arrayOfField1 = fields;
        int i = arrayOfField1.length;
        for (int j = 0; j < i; ++j) {
            Field field = arrayOfField1[j];
            String name = field.getName();
            if (isMiss(field)) {
                continue;
            }
            if (field.isAnnotationPresent(Key.class) || ID.equals(field.getName())) {
                Object value = null;
                try {
                    field.setAccessible(true);
                    value = field.get(modelPo);
                } catch (IllegalAccessException e) {
                    log.error("获取值失败" + e.getMessage());
                }
                Class fieldType = field.getType();
                if ((((fieldType == Long.TYPE) || (fieldType == Long.class))) && ((
                        (Objects.isNull(value)) ||
                                (((Long) value).longValue() == 0L)))) {
                    sqlNames.add("`" + name + "`");
                    sqlValues.add(String.valueOf(idUtil.getId()));
                } else {
                    sqlNames.add("`" + name + "`");
                    sqlValues.add("#{" + name + "}");
                }

            } else {
                sqlNames.add("`" + name + "`");
                sqlValues.add("#{" + name + "}");
            }
        }

        String tableName = null;
        if (clazz.isAnnotationPresent(Table.class))
            tableName = ((Table) clazz.getAnnotation(Table.class)).value();
        else {
            tableName = clazz.getSimpleName();
        }

        return "INSERT INTO " + tableName + " (" + String.join(",", sqlNames) + ") VALUES (" + String.join(",", sqlValues) + ")";
    }


    private synchronized String getUpdateSql(Object modelPo) throws Exception {
        Class clazz = modelPo.getClass();
        Field[] fields = getClassFields(clazz, true);
        List sqlValues = new LinkedList();
        List keyNames = new LinkedList();
        Field[] arrayOfField1 = fields;
        int i = arrayOfField1.length;
        for (int j = 0; j < i; ++j) {
            Field field = arrayOfField1[j];
            String name = field.getName();
            if (isMiss(field))
                continue;
            if ((field.isAnnotationPresent(Key.class)) || (Objects.equals(name, "id"))) {
                keyNames.add("`" + name + "`=#{" + name + "}");
            } else
                sqlValues.add("`" + name + "` = #{" + name + "}");
        }
        if (keyNames.size() == 0)
            throw new Exception("key是必须的");

        String tableName = null;
        if (clazz.isAnnotationPresent(Table.class))
            tableName = ((Table) clazz.getAnnotation(Table.class)).value();
        else {
            tableName = clazz.getSimpleName();
        }

        return "UPDATE " + tableName + " SET " + String.join(",", sqlValues) + " WHERE " + String.join(" AND ", keyNames);
    }

    private boolean isMiss(Field field) {
        return field.isAnnotationPresent(NotMapped.class);
    }

    private synchronized String getDeleteSql(Object modelPo) throws Exception {
        Class clazz = modelPo.getClass();
        String tableName = null;
        if (clazz.isAnnotationPresent(Table.class))
            tableName = ((Table) clazz.getAnnotation(Table.class)).value();
        else {
            tableName = clazz.getSimpleName();
        }

        Field[] fields = getClassFields(clazz, true);
        List keyNames = new LinkedList();
        Field[] arrayOfField1 = fields;
        int i = arrayOfField1.length;
        for (int j = 0; j < i; ++j) {
            Field field = arrayOfField1[j];
            String name = field.getName();
            if ((field.isAnnotationPresent(Key.class)) || (Objects.equals(name, "id")))
                keyNames.add("`" + name + "`=#{" + name + "}");

        }

        if (keyNames.size() == 0)
            throw new Exception("key是必须的");

        return "DELETE FROM " + tableName + " WHERE " + String.join(" AND ", keyNames);
    }


    private void setInsertDefaultValue(Object object) {
        if (object instanceof BaseModel) {
            BaseModel baseModel = (BaseModel) object;
            Date date = new Date();
            baseModel.setCreatedTime(date);
            baseModel.setUpdatedTime(date);
        }
    }

    private void setUpdateDefaultValue(Object object) {
        if (object instanceof BaseModel) {
            BaseModel baseModel = (BaseModel) object;
            Date date = new Date();
            baseModel.setUpdatedTime(date);
        }
    }

    private static String toUpperFirstChar(String string) {
        char[] charArray = string.toCharArray();
        int tmp7_6 = 0;
        char[] tmp7_5 = charArray;
        tmp7_5[tmp7_6] = (char) (tmp7_5[tmp7_6] + ' ');
        return String.valueOf(charArray);
    }

    private static String toLowerFirstChar(String string) {
        char[] charArray = string.toCharArray();
        int tmp7_6 = 0;
        char[] tmp7_5 = charArray;
        tmp7_5[tmp7_6] = (char) (tmp7_5[tmp7_6] - ' ');
        return String.valueOf(charArray);
    }

    private static Field[] getClassFields(Class clazz, boolean isFather) {
        if (clazz.equals(Object.class))
            return null;
        Field[] declaredFields = clazz.getDeclaredFields();
        if (isFather) {
            Field[] afterFields = getClassFields(clazz.getSuperclass(), isFather);
            if (afterFields == null)
                return declaredFields;

            return ArrayUtil.joinFields(afterFields, declaredFields);
        }
        return declaredFields;
    }
}


