package xyz.chengzi.halma.utils;

/**
 * TODO
 *
 * @author xiaohongxin
 * @version 1.0.0
 * @date 2020/5/5 22:06
 */
public class ArrayUtils {

    public static void print(Object[][] objects) {
        for (Object[] object : objects) {
            for (Object o : object) {
                System.out.print(o);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    public static void print(Object[][] objects,Object nullFlag, Object noNullFlag) {
        for (Object[] object : objects) {
            for (Object o : object) {
                System.out.print(o == null ? nullFlag : noNullFlag);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

}
