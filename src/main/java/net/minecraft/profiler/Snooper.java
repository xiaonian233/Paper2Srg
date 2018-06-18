package net.minecraft.profiler;

import com.google.common.collect.Maps;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.HttpUtil;

public class Snooper {

    private final Map<String, Object> snooperStats = Maps.newHashMap();
    private final Map<String, Object> clientStats = Maps.newHashMap();
    private final String uniqueID = UUID.randomUUID().toString();
    private final URL serverUrl;
    private final ISnooperInfo playerStatsCollector;
    private final Timer threadTrigger = new Timer("Snooper Timer", true);
    private final Object syncLock = new Object();
    private final long minecraftStartTimeMilis;
    private boolean isRunning;
    private int selfCounter;

    public Snooper(String s, ISnooperInfo imojangstatistics, long i) {
        try {
            this.serverUrl = new URL("http://snoop.minecraft.net/" + s + "?version=" + 2);
        } catch (MalformedURLException malformedurlexception) {
            throw new IllegalArgumentException();
        }

        this.playerStatsCollector = imojangstatistics;
        this.minecraftStartTimeMilis = i;
    }

    public void startSnooper() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.addOSData();
            this.threadTrigger.schedule(new TimerTask() {
                public void run() {
                    if (Snooper.this.playerStatsCollector.isSnooperEnabled()) {
                        HashMap hashmap;

                        synchronized (Snooper.this.syncLock) {
                            hashmap = Maps.newHashMap(Snooper.this.clientStats);
                            if (Snooper.this.selfCounter == 0) {
                                hashmap.putAll(Snooper.this.snooperStats);
                            }

                            hashmap.put("snooper_count", Integer.valueOf(Snooper.308(Snooper.this)));
                            hashmap.put("snooper_token", Snooper.this.uniqueID);
                        }

                        MinecraftServer minecraftserver = Snooper.this.playerStatsCollector instanceof MinecraftServer ? (MinecraftServer) Snooper.this.playerStatsCollector : null;

                        HttpUtil.postMap(Snooper.this.serverUrl, (Map) hashmap, true, minecraftserver == null ? null : minecraftserver.getServerProxy());
                    }
                }
            }, 0L, 900000L);
        }
    }

    private void addOSData() {
        this.addJvmArgsToSnooper();
        this.addClientStat("snooper_token", this.uniqueID);
        this.addStatToSnooper("snooper_token", this.uniqueID);
        this.addStatToSnooper("os_name", System.getProperty("os.name"));
        this.addStatToSnooper("os_version", System.getProperty("os.version"));
        this.addStatToSnooper("os_architecture", System.getProperty("os.arch"));
        this.addStatToSnooper("java_version", System.getProperty("java.version"));
        this.addClientStat("version", "1.12.2");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }

    private void addJvmArgsToSnooper() {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        List list = runtimemxbean.getInputArguments();
        int i = 0;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();

            if (s.startsWith("-X")) {
                this.addClientStat("jvm_arg[" + i++ + "]", s);
            }
        }

        this.addClientStat("jvm_args", Integer.valueOf(i));
    }

    public void addMemoryStatsToSnooper() {
        this.addStatToSnooper("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        this.addStatToSnooper("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        this.addStatToSnooper("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
        this.addStatToSnooper("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }

    public void addClientStat(String s, Object object) {
        Object object1 = this.syncLock;

        synchronized (this.syncLock) {
            this.clientStats.put(s, object);
        }
    }

    public void addStatToSnooper(String s, Object object) {
        Object object1 = this.syncLock;

        synchronized (this.syncLock) {
            this.snooperStats.put(s, object);
        }
    }

    public boolean isSnooperRunning() {
        return this.isRunning;
    }

    public void stopSnooper() {
        this.threadTrigger.cancel();
    }

    public long getMinecraftStartTimeMillis() {
        return this.minecraftStartTimeMilis;
    }

    static int 308(Snooper mojangstatisticsgenerator) {
        return mojangstatisticsgenerator.selfCounter++;
    }
}