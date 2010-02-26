package ar.com.sebasjm.dev.dynamicdto;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DynamicDTO implements InvocationHandler, Serializable, DynamicDTOControl {

    protected static final boolean throwMissingMethodException = true;

    protected String[] names = null;
    protected Object[] values = null;

    protected DynamicDTO(Map<String, Object> initData) {
        initArrays(initData);
    }

    protected void initArrays(Map<String, Object> map) {
        names = new String[map.keySet().size()];
        map.keySet().toArray(names);
        Arrays.sort(names);

        values = new Object[map.values().size()];
        map.values().toArray(values);

        for (int i = 0; i < names.length; i++) {
            values[i] = map.get(names[i]);
        }
    }

    public static <T> T getDtoInstance(Class<T> dtoInterface, Object entity) {
        if (!dtoInterface.isInterface()) {
            throw new NotAnInterfaceException(dtoInterface);
        }
        Class[] interfaces = new Class[1 + 1];
        interfaces[0] = DynamicDTOControl.class;
        interfaces[1] = dtoInterface;
        return (T) getInstance(interfaces, entity);
    }

    public static <T> T getDtoInstance(Class[] dtoInterfaces, Object entity) {
        Class[] interfaces = new Class[dtoInterfaces.length + 1];
        interfaces[0] = DynamicDTOControl.class;
        for (int i = 1; i < interfaces.length; i++) {
            if (!interfaces[i].isInterface()) {
                throw new NotAnInterfaceException(interfaces[i]);
            }
            interfaces[i] = dtoInterfaces[i];
        }
        return (T) getInstance(interfaces, entity);
    }

    protected static <T> T getInstance(Class[] interfaces, Object entity) {
        return (T) getInstance(interfaces, getDataFromEntity(interfaces, entity));
    }

    protected static <T> T getInstance(Class[] interfaces, Map<String, Object> data) {
        DynamicDTO dtoInstance = new DynamicDTO(data);

        return (T) Proxy.newProxyInstance(
                DynamicDTO.class.getClassLoader(),
                interfaces,
                dtoInstance);
    }

    /**
     * Dada una interfas y una instancia de una entidad, obtiene un mapa
     * de <NombreDeAttributo,Valor> correspondientes a la entidad y limitada
     * por los metodos getters y setters de dicha interfas.
     *
     * @param interfaces
     * @param entity
     * @return
     */
    protected static Map<String, Object> getDataFromEntity(Class[] interfaces, Object entity) {
        Map<String, Object> resultData = new HashMap<String, Object>();

        Class entityClass = entity.getClass();
        Object[] gettersDefaultParams = new Object[]{};

        for (int i = 1; i < interfaces.length; i++) {
            Method[] mtds = interfaces[i].getMethods();

            for (int j = 0; j < mtds.length; j++) {
                String methodName = mtds[j].getName();

                if (methodName.startsWith("get")) {
                    try {
                        resultData.put(
                                methodName.substring(3),
                                entityClass.getMethod(
                                    methodName,
                                    mtds[j].getParameterTypes()
                                ).invoke(entity, gettersDefaultParams)
                            );
                    } catch (Throwable e) {
                        if (throwMissingMethodException) {
                            throw new MissingMethodException(e, "get", methodName.substring(3), mtds[j].getParameterTypes(), entityClass);
                        } else {
                            e.printStackTrace();
                        }
                    }
                }

                if (methodName.startsWith("is")) {
                    try {
                        resultData.put(
                                methodName.substring(2),
                                entityClass.getMethod(
                                    methodName,
                                    mtds[j].getParameterTypes()
                                ).invoke(entity, gettersDefaultParams)
                            );
                    } catch (Exception e) {
                        if (throwMissingMethodException) {
                            throw new MissingMethodException(e, "is", methodName.substring(2), mtds[j].getParameterTypes(), entityClass);
                        } else {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return resultData;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("get")) {
            return values[Arrays.binarySearch(names, method.getName().substring(3))];
        }
        if (method.getName().startsWith("is")) {
            return values[Arrays.binarySearch(names, method.getName().substring(2))];
        }
        if (method.getName().startsWith("set")) {
            values[Arrays.binarySearch(names, method.getName().substring(3))] = args[0];
            return null;
        }

        return method.invoke(this, args);
    }

    public String[] names() {
        return names;
    }

    public Object[] values() {
        return values;
    }

    public <T> T fillInstance(T entity) {
        Class entityClass = entity.getClass();
        Class[] interfaces = this.getClass().getInterfaces();

        for (int i = 0; i < interfaces.length; i++) {
            System.out.println("interface: " + interfaces[i].getName());
            Method[] mtds = interfaces[i].getMethods();

            for (int j = 0; j < mtds.length; j++) {
                String methodName = mtds[j].getName();
                System.out.println("     method: " + methodName);

                if (methodName.startsWith("set")) {
                    try {
                        entityClass.getMethod(methodName, mtds[j].getParameterTypes()).invoke(entity, values[Arrays.binarySearch(names, methodName.substring(3))]);
                    } catch (Exception e) {
                        if (throwMissingMethodException) {
                            throw new MissingMethodException(e, "set", methodName.substring(3), mtds[j].getParameterTypes(), entityClass);
                        } else {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return entity;
    }

    /**
     * Apareo de mapas
     */
    public DynamicDTOControl addData(Class dtoInterface, Object resource) {
        Map<String, Object> newData = DynamicDTO.getDataFromEntity(new Class[]{dtoInterface}, resource);

        for (int i = 0; i < names.length; i++) {
            if (!newData.containsKey(names[i])) {
                newData.put(
                        names[i],
                        values[i]);
            }
        }

        initArrays(newData);

        return this;
    }

    public DynamicDTOControl removeData(Class dtoInterface) {
        // TODO Auto-generated method stub
        return this;
    }

    public static class NotAnInterfaceException extends RuntimeException {

        public String message = null;

        public NotAnInterfaceException(Class cls) {
            this.message = cls.getName() + " is not an interface.";
        }

        public String getMessage() {
            return message;
        }
    }

    public static class MissingMethodException extends RuntimeException {

        public MissingMethodException(Throwable cause, String prefix, String attrib, Type[] paramsType, Class dto) {
            super(getMessage(prefix, attrib, paramsType, dto),cause);
        }

        public MissingMethodException(String prefix, String attrib, Type[] paramsType, Class dto) {
            super(getMessage(prefix, attrib, paramsType, dto));
        }

        protected static String getMessage(String prefix, String attrib, Type[] paramsType, Class dto) {
            return "method '" + prefix + attrib + "(" + Arrays.deepToString(paramsType) + ")' for attribute '" + attrib + "' in '" + dto.getName() + "' could not be accessed";
        }
    }
}
