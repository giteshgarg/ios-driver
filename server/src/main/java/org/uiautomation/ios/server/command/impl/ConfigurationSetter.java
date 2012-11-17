package org.uiautomation.ios.server.command.impl;

import java.util.Iterator;

import org.json.JSONObject;
import org.uiautomation.ios.UIAModels.configuration.WorkingMode;
import org.uiautomation.ios.communication.WebDriverLikeCommand;
import org.uiautomation.ios.communication.WebDriverLikeRequest;
import org.uiautomation.ios.communication.WebDriverLikeResponse;
import org.uiautomation.ios.server.IOSDriver;
import org.uiautomation.ios.server.ServerSideSession;
import org.uiautomation.ios.server.command.BaseCommandHandler;

public class ConfigurationSetter extends BaseCommandHandler {

  public ConfigurationSetter(IOSDriver driver, WebDriverLikeRequest request) {
    super(driver, request);
  }

  @Override
  public WebDriverLikeResponse handle() throws Exception {
    ServerSideSession session = getSession();
    WorkingMode mode = WorkingMode.valueOf((String) getRequest().getVariableValue(":subdriver"));
    WebDriverLikeCommand command = WebDriverLikeCommand.valueOf((String) getRequest().getVariableValue(":command"));
    JSONObject payload = getRequest().getPayload();

    Iterator<String> iter = payload.keys();
    while (iter.hasNext()) {
      String key = iter.next();
      Object value = payload.opt(key);
      getSession().getConf(mode).get(command).set(key, value);
      System.out.println(getSession().getConf(mode).get(command).get(key));
    }

    return new WebDriverLikeResponse(getSession().getSessionId(), 0, new JSONObject());

  }

}