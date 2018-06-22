package io.github.forezp.netty.rpc.core.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-22
 **/
public class ExceptionUtil {

    public static String toExceptionString(Exception exception) {
        if (exception == null) {
            return null;
        }

        return detailMessage( exception );
    }

    private static String detailMessage(Exception exception) {
        String result;
        ByteArrayOutputStream baos = null;
        PrintStream ps = null;
        try {
            baos = new ByteArrayOutputStream();
            ps = new PrintStream( baos );
            exception.printStackTrace( ps );
            result = baos.toString().trim();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

}
