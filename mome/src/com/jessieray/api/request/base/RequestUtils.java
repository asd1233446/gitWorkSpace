package com.jessieray.api.request.base;

import java.util.Collection;

import org.springframework.util.StringUtils;

public class RequestUtils
{
//  private static final String EMPTY = "";
//  private static final String COLLECTION_STR_DELIM = ",";

  public static String object2String(Object object)
  {
    if (object == null) {
      return "";
    }

    if ((object instanceof Collection)) {
      return StringUtils.collectionToDelimitedString((Collection<?>)object, ",");
    }
    return object.toString();
  }
}