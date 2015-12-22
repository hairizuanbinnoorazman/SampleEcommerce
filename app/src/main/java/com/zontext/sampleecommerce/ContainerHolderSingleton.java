package com.zontext.sampleecommerce;

import android.util.Log;

import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;

import java.util.Map;

/**
 * Created by Hairizuan on 30/6/2015.
 */
public class ContainerHolderSingleton {
    private static ContainerHolder containerHolder;

    private ContainerHolderSingleton(){
    }

    public static ContainerHolder getContainerHolder(){
        return containerHolder;
    }

    public static  void setContainerHolder(ContainerHolder c){
        containerHolder = c;
    }



}
