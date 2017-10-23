/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test.utility;

import com.adi.view.models.StatusMonitor;
import com.adi.view.models.StatusMonitorScale;

/**
 *
 * @author Caique
 */
public class ConsoleProgress implements StatusMonitor {
    private long total;
    private boolean ind;
    private int unid = 64;
    private StatusMonitorScale scal; 
    
    @Override
    public void initStatus() {
        System.out.println("0 MB   1 MB    2 MB    3 MB    4 MB   5 MB     6 MB    7 MB    8 MB    9 MB   10+MB");
        System.out.println("| . | . | . | . | . | . | . | . | . | . | . | . | . | . | . | . | . | . | . | . |");
        total = 0;
        ind = true;
    }

    @Override
    public void setIndeterminate(boolean indeterm) {
        ind = indeterm;
    }

    @Override
    public void setScale(StatusMonitorScale scale) {
        scal = scale;
    }

    @Override
    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public void setActualPosition(long position) {
        if ((position == 0 || position % (unid * 1024) == 0) && (position / 1024) < (10 * 1024) ){
            System.out.print("#");
        }
    }

    @Override
    public void finalizeStatus() {
        System.out.print("\nConcluído\n");
    }

    @Override
    public void finalizeStatus(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
