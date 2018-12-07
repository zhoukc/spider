package com.zhaoyouhua.spider.util;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.GregorianCalendar;

@Component
public class IdUtil {

    private static int sequence = 0;
    private static final long startTime = new GregorianCalendar(2017, 12, 18).getTimeInMillis();
    private static final int workerIdBits = 5;
    private static final int maxWorkerId = 31;
    private static final int machineIdBits = 7;
    private static final int maxMachineId = 127;
    private static final int sequenceBits = 10;
    private static final int machineIdShift = 10;
    private static final int workerIdShift = 15;
    private static final int timestampLeftShift = 20;
    private static final int sequenceMask = 1023;
    private static long lastTimestamp = 0L;
    private int workerId;
    private int machineId;

    public synchronized long getId() {
        long timestamp = timeGenerate();
        if (lastTimestamp == timestamp) {
            sequence = sequence + 1 & 0x3FF;
            if (sequence == 0)
                timestamp = tilNextMillis(lastTimestamp);
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return (timestamp << 20 | this.workerId << 15 | this.machineId << 10 | sequence);
    }

    public void setWorkerId(int workerId) {
        if ((workerId > 31) || (workerId < 0))
            throw new IllegalArgumentException(MessageFormat.format("workerId 超出{0}", new Object[]{Integer.valueOf(31)}));

        this.workerId = workerId;
    }

    public void setMachineId(int machineId) {
        if ((machineId > 127) || (machineId < 0))
            throw new IllegalArgumentException(MessageFormat.format("machineId 超出{0}", new Object[]{Integer.valueOf(127)}));

        this.machineId = machineId;
    }

    private long timeGenerate() {
        return (System.currentTimeMillis() - startTime);
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGenerate();
        while (timestamp <= lastTimestamp)
            timestamp = timeGenerate();

        return timestamp;
    }
}
