package com.cn.sundeinfo.sys.core.permission;

import java.util.HashMap;
import java.util.Map;

public class PermissionHelper {


    public static int getDefault(){
        return FunctionType.NONE.type();
    }

    public static int getFull(){
        return FunctionType.VISIBLE.type()+
               FunctionType.READ.type()+
               FunctionType.MODIFY.type()+
               FunctionType.NEW.type()+
               FunctionType.UPDATE.type()+
               FunctionType.DELETE.type()+
               FunctionType.LINK.type()+
               FunctionType.DOWNLOAD.type();
    }

    public static Map<String,Boolean> getDefaultPermissionMap(){
        HashMap<String,Boolean> permissionMap = new HashMap<>();
        permissionMap.put(FunctionType.VISIBLE.value(),false);
        permissionMap.put(FunctionType.READ.value(),false);
        permissionMap.put(FunctionType.MODIFY.value(),false);
        permissionMap.put(FunctionType.NEW.value(),false);
        permissionMap.put(FunctionType.UPDATE.value(),false);
        permissionMap.put(FunctionType.DELETE.value(),false);
        permissionMap.put(FunctionType.LINK.value(),false);
        permissionMap.put(FunctionType.DOWNLOAD.value(),false);
        return permissionMap;
    }

    public static Map<String,Boolean> getVisiblePermissionMap(){
        HashMap<String,Boolean> permissionMap = new HashMap<>();
        permissionMap.put(FunctionType.VISIBLE.value(),true);
        permissionMap.put(FunctionType.READ.value(),false);
        permissionMap.put(FunctionType.MODIFY.value(),false);
        permissionMap.put(FunctionType.NEW.value(),false);
        permissionMap.put(FunctionType.UPDATE.value(),false);
        permissionMap.put(FunctionType.DELETE.value(),false);
        permissionMap.put(FunctionType.LINK.value(),false);
        permissionMap.put(FunctionType.DOWNLOAD.value(),false);
        return permissionMap;
    }

    public static Map<String,Boolean> getFullPermissionMap(){
        HashMap<String,Boolean> permissionMap = new HashMap<>();
        permissionMap.put(FunctionType.VISIBLE.value(),true);
        permissionMap.put(FunctionType.READ.value(),true);
        permissionMap.put(FunctionType.MODIFY.value(),true);
        permissionMap.put(FunctionType.NEW.value(),true);
        permissionMap.put(FunctionType.UPDATE.value(),true);
        permissionMap.put(FunctionType.DELETE.value(),true);
        permissionMap.put(FunctionType.LINK.value(),true);
        permissionMap.put(FunctionType.DOWNLOAD.value(),true);
        return permissionMap;
    }

    public static Map<String,Boolean> getPermissionMapByFunction(int functionSum){
        HashMap<String,Boolean> permissionMap = new HashMap<>();
        permissionMap.put(FunctionType.VISIBLE.value(),(FunctionType.VISIBLE.type() & functionSum) > 0);
        permissionMap.put(FunctionType.READ.value(),(FunctionType.READ.type() & functionSum) > 0);
        permissionMap.put(FunctionType.MODIFY.value(),(FunctionType.MODIFY.type() & functionSum) > 0);
        permissionMap.put(FunctionType.NEW.value(),(FunctionType.NEW.type() & functionSum) > 0);
        permissionMap.put(FunctionType.UPDATE.value(),(FunctionType.UPDATE.type() & functionSum) > 0);
        permissionMap.put(FunctionType.DELETE.value(),(FunctionType.DELETE.type() & functionSum) > 0);
        permissionMap.put(FunctionType.LINK.value(),(FunctionType.LINK.type() & functionSum) > 0);
        permissionMap.put(FunctionType.DOWNLOAD.value(),(FunctionType.DOWNLOAD.type() & functionSum) > 0);
        return permissionMap;
    }

    public static int getPermissionByMap(Map<String,Boolean> permissionMap){
        int permission = 0;
        if (permissionMap.containsKey(FunctionType.VISIBLE.value()))
            permission = permission + (permissionMap.get(FunctionType.VISIBLE.value())? FunctionType.VISIBLE.type():0);
        if (permissionMap.containsKey(FunctionType.READ.value()))
            permission = permission + (permissionMap.get(FunctionType.READ.value())? FunctionType.READ.type():0);
        if (permissionMap.containsKey(FunctionType.MODIFY.value()))
            permission = permission + (permissionMap.get(FunctionType.MODIFY.value())? FunctionType.MODIFY.type():0);
        if (permissionMap.containsKey(FunctionType.NEW.value()))
            permission = permission + (permissionMap.get(FunctionType.NEW.value())? FunctionType.NEW.type():0);
        if (permissionMap.containsKey(FunctionType.UPDATE.value()))
            permission = permission + (permissionMap.get(FunctionType.UPDATE.value())? FunctionType.UPDATE.type():0);
        if (permissionMap.containsKey(FunctionType.DELETE.value()))
            permission = permission + (permissionMap.get(FunctionType.DELETE.value())? FunctionType.DELETE.type():0);
        if (permissionMap.containsKey(FunctionType.LINK.value()))
            permission = permission + (permissionMap.get(FunctionType.LINK.value())? FunctionType.LINK.type():0);
        if (permissionMap.containsKey(FunctionType.DOWNLOAD.value()))
            permission = permission + (permissionMap.get(FunctionType.DOWNLOAD.value())? FunctionType.DOWNLOAD.type():0);
        return permission;
    }





}
