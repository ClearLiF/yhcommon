package pro.yinghuo.common.utils;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * @author: clearli
 * @date: 2022-06-25 15:28
 */
@SuppressWarnings({"rawtypes"})
public class ObjectUtils {

    /**
     * 判空：1. CharSquence(String, StringBuilder, StringBuffer)
     * 2. Map
     * 3. Iterable(集合)
     * Array
     * boolean
     * 空对象
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        // 包装类及日期等在这一层判断
        if (obj == null) {
            return true;
        }

        if (obj instanceof CharSequence) {
            return StrUtil.isEmpty((CharSequence) obj);
        } else if (obj instanceof Map) {
            return MapUtil.isEmpty((Map) obj);
        } else if (obj instanceof Iterable) {
            return IterUtil.isEmpty((Iterable) obj);
        } else if (obj.getClass().isArray()) {
            return ArrayUtil.isEmpty(obj);
        } else if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else if (obj instanceof Long) {
            Long longVal = (Long) obj;
            return longVal != 0L;
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
