package pro.yinghuo.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import pro.yinghuo.common.response.CommonCode;
import pro.yinghuo.common.response.QueryResponseResult;
import pro.yinghuo.common.response.QueryResult;
import pro.yinghuo.common.response.ResultCode;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Stream utils.
 *
 * @author : CLEAR Li
 * @version : V1.0
 * @className : StreamUtils
 * @packageName : com.yinghuo.framework.utils
 * @description : 流处理类
 * @date : 2020-10-24 13:19
 */
@Slf4j
public class StreamUtils {

    /**
     * 将pojo类转换为vo
     *
     * @param <T>  the type parameter
     * @param <V>  the type parameter
     * @param list 源数据list
     * @param cls  目标数据class
     * @return java.util.List<V> pojo to vo
     * @description
     * @author ClearLi
     * @date 2020 /10/24 13:30
     */
    public static <T, V> List<V> getPojoToVo(List<T> list, Class<V> cls) {
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(t -> {
            V v = null;
            try {
                v = cls.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                log.error("初始化vo对象出错");
            }
            //值拷贝
            BeanUtils.copyProperties(t, v);
            return v;
        }).collect(Collectors.toList());
    }

    /**
     * 将pojo类转换为vo
     *
     * @param <T>       the type parameter
     * @param <V>       the type parameter
     * @param list      源数据list
     * @param cls       目标数据class
     * @param functions the functions
     * @return java.util.List<V> modify pojo to vo
     * @description
     * @author ClearLi
     * @date 2020 /10/24 13:30
     */
    public static <T, V> List<V> getModifyPojoToVo(List<T> list, Class<V> cls, Function<V, V>... functions) {
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(t -> {
            V v = null;
            try {
                v = cls.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                log.error("初始化vo对象出错");
            }
            //值拷贝
            //apply:函数式接口方法，输入T，输出R。
            //compose:compose接收一个Function参数，先执行传入的逻辑，再执行当前的逻辑。
            //andThen:andThen接收一个Function参数，先执行当前的逻辑，再执行传入的逻辑。
            //identity:方便方法的连缀，返回当前对象。
            BeanUtils.copyProperties(t, v);
            for (Function<V, V> function : functions) {
                function.apply(v);
            }
            return v;
        }).collect(Collectors.toList());
    }

    /**
     * 将pojo类转换为vo
     *
     * @param <T>   the type parameter
     * @param <V>   the type parameter
     * @param list  源数据list
     * @param cls   目标数据class
     * @param total the total
     * @return java.util.List<V> pojo to vo to response
     * @description
     * @author ClearLi
     * @date 2020 /10/24 13:30
     */
    public static <T, V> QueryResponseResult<V> getPojoToVoToResponse(List<T> list, Class<V> cls, long total) {
        List<V> listResult = getPojoToVo(list, cls);
        QueryResult<V> result = new QueryResult<>();
        result.setList(listResult);
        result.setTotal(total);
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);
    }

    /**
     * 将pojo类转换为vo
     *
     * @param <T>       the type parameter
     * @param <V>       the type parameter
     * @param list      源数据list
     * @param cls       目标数据class
     * @param total     the total
     * @param functions the functions
     * @return java.util.List<V> modify pojo to vo to response
     * @description
     * @author ClearLi
     * @date 2020 /10/24 13:30
     */
    @SafeVarargs
    public static <T, V> QueryResponseResult<V> getModifyPojoToVoToResponse(List<T> list, Class<V> cls, long total, Function<V, V>... functions) {
        List<V> listResult = getModifyPojoToVo(list, cls, functions);
        QueryResult<V> result = new QueryResult<>();
        result.setList(listResult);
        result.setTotal(total);
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);
    }

    /**
     * Modify convert query response result.
     *
     * @param <T>      the type parameter
     * @param <V>      the type parameter
     * @param list     the list
     * @param cls      the cls
     * @param total    the total
     * @param function the function
     * @return the query response result
     */
    public static <T, V> QueryResponseResult<V> modifyConvert(List<T> list, Class<V> cls, long total, Function<T, V> function) {
        List<V> listResult = modifyConvert(list, cls, function);
        QueryResult<V> result = new QueryResult<>();
        result.setList(listResult);
        result.setTotal(total);
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);
    }


    /**
     * 将pojo类转换为vo
     *
     * @param <T>      the type parameter
     * @param <V>      the type parameter
     * @param list     源数据list
     * @param cls      目标数据class
     * @param function the function
     * @return java.util.List<V> list
     * @description
     * @author soundtao
     * @date 2020 /10/24 13:30
     */
    public static <T, V> List<V> modifyConvert(List<T> list, Class<V> cls, Function<T, V> function) {
        if (cn.hutool.core.collection.CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(t -> {
            V v = null;
            try {
                v = cls.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                log.error("初始化vo对象出错");
            }
            //值拷贝
            //apply:函数式接口方法，输入T，输出R。
            //compose:compose接收一个Function参数，先执行传入的逻辑，再执行当前的逻辑。
            //andThen:andThen接收一个Function参数，先执行当前的逻辑，再执行传入的逻辑。
            //identity:方便方法的连缀，返回当前对象。
            v = function.apply(t);
            return v;
        }).collect(Collectors.toList());
    }


    /**
     * 封装返回对象
     *
     * @param <T>   the type parameter
     * @param list  list集合
     * @param total 总数
     * @return com.yinghuo.framework.response.QueryResponseResult<T> simple response
     * @description
     * @author ClearLi
     * @date 2021 /3/16 18:11
     */
    public static <T> QueryResponseResult<T> getSimpleResponse(List<T> list, long total) {
        QueryResult<T> result = new QueryResult<>();
        if (CollectionUtil.isEmpty(list)) {
            list = Lists.newArrayList();
        }
        result.setList(list);
        result.setTotal(total);
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);
    }

    /**
     * 将pojo类转换为vo
     *
     * @param <T> the type parameter
     * @param <V> the type parameter
     * @param obj 源数据
     * @param cls 目标数据class
     * @return java.util.List<V> v
     * @description
     * @author soundtao
     * @date 2020 /10/24 13:30
     */
    public static <T, V> V simpleConvert(T obj, Class<V> cls) {
        V v = null;
        try {
            v = cls.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            log.error("初始化vo对象出错");
        }
        //值拷贝
        BeanUtils.copyProperties(obj, v);
        return v;
    }

    /**
     * 封装返回对象
     *
     * @param <T> the type parameter
     * @param t   单例对象
     * @return com.yinghuo.framework.response.QueryResponseResult<T> singleton
     * @description
     * @author ClearLi
     * @date 2021 /3/16 18:11
     */
    public static <T> QueryResponseResult<T> getSingleton(T t ) {
        QueryResult<T> result = new QueryResult<>();
        result.setList(Lists.newArrayList(t));
        result.setTotal(1);
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);
    }

    /**
     * 封装返回对象
     *
     * @param <T>        the type parameter
     * @param t          单例对象
     * @param resultCode the result code
     * @return com.yinghuo.framework.response.QueryResponseResult<T> singleton
     * @description
     * @author ClearLi
     * @date 2021 /3/16 18:11
     */
    public static <T> QueryResponseResult<T> getSingleton(T t , ResultCode resultCode) {
        QueryResult<T> result = new QueryResult<>();
        result.setList(Lists.newArrayList(t));
        result.setTotal(1);
        return new QueryResponseResult<>(resultCode, result);
    }

    /**
     * 封装返回对象
     *
     * @param <T> the type parameter
     * @return com.yinghuo.framework.response.QueryResponseResult<T> empty
     * @description
     * @author ClearLi
     * @date 2021 /3/16 18:11
     */
    public static <T> QueryResponseResult<T> getEmpty() {
        QueryResult<T> result = new QueryResult<>();
        result.setList(Lists.newArrayList());
        result.setTotal(0);
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);
    }


    /**
     * 封装返回对象
     *
     * @param <T> the type parameter
     * @param <V> the type parameter
     * @param t   单例对象
     * @param cls the cls
     * @return com.yinghuo.framework.response.QueryResponseResult<T> singleton
     * @description
     * @author ClearLi
     * @date 2021 /3/16 18:11
     */
    public static <T, V> QueryResponseResult<V> getSingleton(T t , Class<V> cls) {
        V v = null;
        try {
            v = cls.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error("初始化vo对象出错");
        }
        //值拷贝
        if (t==null){
            QueryResult<V> result = new QueryResult<>();
            result.setList(Lists.newArrayList());
            result.setTotal(1);
            return new QueryResponseResult<>(CommonCode.SUCCESS, result);
        } else {
            BeanUtils.copyProperties(t, v);
            QueryResult<V> result = new QueryResult<>();
            result.setList(Lists.newArrayList(v));
            result.setTotal(1);
            return new QueryResponseResult<>(CommonCode.SUCCESS, result);
        }

    }

    /**
     * One convert modify.
     *
     * @param source     the source
     * @param dateFormat the date format
     * @param target     the target
     */
    public static void oneConvertModify(Object source, String dateFormat, Object target) {
        if (dateFormat == null) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        Class<?> aClass = source.getClass();
        PropertyDescriptor[] apds = BeanUtils.getPropertyDescriptors(aClass);
        Class<?> bClass = target.getClass();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bClass);
        int n = pds.length;
        for (int i = 0; i < n; ++i) {
            PropertyDescriptor targetPd = pds[i];
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    for (int j = 0; j < apds.length; j++) {
                        PropertyDescriptor apd = apds[j];
                        if (apd.getReadMethod().getName().equals(readMethod.getName())) {
                            // 将对象2的属性get方法转换成对象1的get方法（获得的value的类型就是对象1的该属性的类型）
                            readMethod = apd.getReadMethod();
                            break;
                        }
                    }
                    if (readMethod != null) {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = null;
                        try {
                            value = readMethod.invoke(source);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                        if (value == null) {
                            continue;
                        }
                        // 此处对要赋的值类型进行判断和处理
                        if (value.getClass().equals(Long.class)) {
                            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                            value = sdf.format(value);

                        }
//                        } else if (value.getClass().equals(BigDecimal.class)) {
//                            BigDecimal b = (BigDecimal) value;
//                            value = b.doubleValue();
//                        }
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        try {
                            writeMethod.invoke(target, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

}
