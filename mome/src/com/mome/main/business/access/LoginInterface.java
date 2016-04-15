package com.mome.main.business.access;

import com.jessieray.api.model.UserInfo;

public interface LoginInterface {
     void sucess(Object object);
     
     void error(String  error);
}
