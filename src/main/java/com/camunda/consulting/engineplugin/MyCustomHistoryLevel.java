package com.camunda.consulting.engineplugin;

import org.camunda.bpm.engine.impl.history.HistoryLevelAudit;
import org.camunda.bpm.engine.impl.history.event.HistoryEventType;
import org.camunda.bpm.engine.impl.history.event.HistoryEventTypes;

public class MyCustomHistoryLevel extends HistoryLevelAudit {

	@Override
	public int getId() {
		return 5;
	}

	@Override
	public String getName() {
		return "CUSTOMLEVEL";
	}

	@Override
	public boolean isHistoryEventProduced(HistoryEventType eventType, Object entity) {

		//additionally create UserOperationLog events
		if (eventType == HistoryEventTypes.USER_OPERATION_LOG) {
			return true;
		}
		return super.isHistoryEventProduced(eventType, entity);
	}
}
