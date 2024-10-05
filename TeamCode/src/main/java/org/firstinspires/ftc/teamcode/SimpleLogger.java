// Copyright 2024 Patrick R. Michaud

package org.firstinspires.ftc.teamcode;

import java.io.Writer;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import java.text.DecimalFormat;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;

public class SimpleLogger {
    private BufferedWriter writer = null;
    private StringBuilder line = null;
    private ElapsedTime logTime = new ElapsedTime();
    private double millisPrev = 0;
    private final String PATHFMT = "/sdcard/FIRST/java/src/Datalogs/%s.txt";
    private String sep = ", ";
    
    private OpModeNotifications opModeNotifications = new OpModeNotifications();

    public SimpleLogger() {
        OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getActivity()).registerListener(opModeNotifications);
    }
    
    public SimpleLogger(String filename) {
        OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getActivity()).registerListener(opModeNotifications);
        open(filename);
    }
    
    public SimpleLogger open() {
        return open("simplelog");
    }
    public SimpleLogger open(String filename) {
        if (writer != null) close();
        String fullpath = String.format(PATHFMT, filename);
        File tmp = new File(fullpath);
        if (!tmp.exists()) { tmp.getParentFile().mkdirs(); }

        try {
            writer = new BufferedWriter(new FileWriter(fullpath));
        } catch (IOException e) { 
            e.printStackTrace();
            throw new RuntimeException("Unable to create output file handle");
        }
        logTime = new ElapsedTime();
        return this;
    }

    public SimpleLogger flush() {
        if (line == null) return this;
        if (writer == null) { line = null; return this; }
        try {
            writer.write(line.toString());
            writer.newLine();
            line = null;
        } catch (IOException e) { }
        return this;
    }

    public void close() {
        if (writer == null) return;
        flush();
        try { writer.close(); }
        catch (IOException e) { }
        writer = null;
    }
    
    public void resetTime() {
        logTime.reset();
    }
    
    public SimpleLogger tsLine() {
        double millis = logTime.milliseconds();
        if (line == null) { line = new StringBuilder(); }
        line.insert(0, String.format("%.2f,%6.2f", millis, millis-millisPrev)+sep);
        flush();
        millisPrev = millis;
        return this;
    }
    
    public SimpleLogger headerLine() {
        String tsheader = "T,dt"+sep;
        if (line == null) add(tsheader);
        else { line.insert(0, tsheader); }
        flush();
        return this;
    }
    
    public SimpleLogger add(String s) {
        if (line == null) { line = new StringBuilder(); }
        else if (line.length() > 0) line.append(sep);
        line.append(s);
        return this;
    }
    
    public SimpleLogger add(String fmt, Object... args) {
        return add(String.format(fmt, args));
    }
    
    public SimpleLogger add(long l) {
        String s = Long.toString(l);
        return add(s);
    }
    
    public SimpleLogger add(double d) {
        return add(Double.toString(d));
    }
    
    public SimpleLogger add(boolean b) {
        return add(Boolean.toString(b));
    }
    
    public SimpleLogger add(VoltageSensor vs) {
        return add("%.2f", vs.getVoltage());
    }
    
    public double milliseconds() { return logTime.milliseconds(); }
    
    private class OpModeNotifications implements OpModeManagerNotifier.Notifications
    {
        @Override
        public void onOpModePostStop(OpMode opMode)
        {
            close();
            OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getActivity()).unregisterListener(this);
        }

        @Override
        public void onOpModePreInit(OpMode opMode) {}

        @Override
        public void onOpModePreStart(OpMode opMode) {}
    }
}