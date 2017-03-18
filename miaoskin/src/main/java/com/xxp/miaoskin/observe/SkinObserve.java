package com.xxp.miaoskin.observe;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/3/3.
 */

public class SkinObserve {
    private final List<SkinObserver> observers;

    public SkinObserve(List<SkinObserver> observers) {
        this.observers = observers;
    }

    public synchronized void notifySkinUpdate() {
        if (!observers.isEmpty()) {
            for (SkinObserver observer : observers) {
                    observer.skinUpdate(this);
            }
        }
    }

    public synchronized int getObserverSize(){
        return observers.size();
    }
    public synchronized void addObserver(SkinObserver observer){
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    public synchronized void removeObserver(SkinObserver observer){
        if(observers.contains(observer)){
            observers.remove(observer);
        }
    }

    public synchronized void cleanObserver(){
        if(!observers.isEmpty()){
            observers.clear();
        }
    }
}
