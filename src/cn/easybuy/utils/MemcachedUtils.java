package cn.easybuy.utils;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import java.util.ArrayList;
import java.util.List;

//memcached保存数据
public class MemcachedUtils{

    static MemCachedClient client = null;

    static String[] connectUrls = new String[]{"127.0.0.1:11211"};

    static {
        String[] attr = connectUrls;
        client = new MemCachedClient();
        //获取连接池的单态方法
        SockIOPool pool = SockIOPool.getInstance();
        //设置每个Memached服务地址
        pool.setServers(attr);
        //设置每个Memached服务权重
        pool.setWeights(new Integer[]{3});
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(200);
        pool.setMaxIdle(1000 * 30 * 30);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketConnectTO(30);
        pool.initialize();
    }

    public static void add(String key, Object object) {
        client.set(key, object);
    }

    public static void del(String key) {
        client.delete(key);
    }

    public static Object get(String key) {
        return client.get(key);
    }

}
