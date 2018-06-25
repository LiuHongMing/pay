package com.github.tiger.common.dao;

/**
 * 数据访问层：基础支撑接口
 */
public interface BaseDAO<PK, T> {

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    int removeById(PK id);

    /**
     * 保存记录
     *
     * @param record
     * @return
     */
    int save(T record);

    /**
     * 根据id获取记录
     *
     * @param id
     * @return
     */
    T getById(PK id);

    /**
     * 更新记录
     *
     * @param recode
     * @return
     */
    int update(T recode);

    /**
     * 根据id更新记录
     *
     * @param record
     * @return
     */
    int updateById(T record);

}
