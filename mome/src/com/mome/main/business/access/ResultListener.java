package com.mome.main.business.access;

import com.jessieray.api.model.UserInfo;

public interface ResultListener {
     void sucess(Object object);
     
     void error(String  error);
}
