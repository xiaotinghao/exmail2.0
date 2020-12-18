package com.xs.common.constants;

/**
 * 数据库操作分类枚举类
 *
 * @author 18871430207@163.com
 */
public enum DbOptTypeEnum {
    /**
     * 0 ,查询
     */
    QUERY(0, "form"),
    /**
     * 1 ,新增
     */
    INSERT(1, "insert"),
    /**
     * 2 ,更新
     */
    UPDATE(2, "update"),
    /**
     * 3 ,删除
     */
    DELETE(3, "delete");

    private Integer code;

    private String name;

    DbOptTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据name查询code
     *
     * @param name 操作（form、insert、update、delete）
     * @return code 编号（0、1、2、3）
     */
    public static Integer getCodeByName(String name) {
        for (DbOptTypeEnum typeEnum : DbOptTypeEnum.values()) {
            if (typeEnum.getName().equals(name)) {
                return typeEnum.getCode();
            }
        }
        return null;
    }

}
