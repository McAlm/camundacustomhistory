package com.camunda.consulting.engineplugin;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordering.DEFAULT_ORDER + 1)
public class CustomHistoryLevelPlugin implements ProcessEnginePlugin {
	
	@Override
	public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
		List<HistoryLevel> customHistoryLevels = processEngineConfiguration.getCustomHistoryLevels();
		if (customHistoryLevels == null) {
			customHistoryLevels = new ArrayList<HistoryLevel>();
		}
		customHistoryLevels.add(new MyCustomHistoryLevel());

	}

	@Override
	public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
	}

	@Override
	public void postProcessEngineBuild(ProcessEngine processEngine) {

	}
}
