package com.camunda.consulting.engineplugin;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.ActivityTypes;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricExternalTaskLogEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricIncidentEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.event.HistoryEventTypes;
import org.camunda.bpm.engine.impl.history.event.UserOperationLogEntryEventEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomHistoryEventHandler {

	@Autowired
	private RuntimeService runtime;

	private static final Logger log = LoggerFactory.getLogger(CustomHistoryEventHandler.class);

	@EventListener
	public void handleEvent(HistoryEvent historyEvent) {

		if (historyEvent instanceof UserOperationLogEntryEventEntity) {
			log.info("received user operation log event: "
					+ ((UserOperationLogEntryEventEntity) historyEvent).toString());

			UserOperationLogEntryEventEntity e = (UserOperationLogEntryEventEntity) historyEvent;
			if (e.getOperationType().equalsIgnoreCase("ModifyProcessInstance")) {
				// 1. persist info in external database
				// external dbEventId = createEventInExternalDatabase(e);
				// 2. create a processVariable with reference to that newly created database
				// entry
				// create ProcessVariable dynamically
				// unt
				// String variableName = e.getOperationType() + "." + e.getId();
				// String variableValue = dbEventId;
			}
		}

		if (historyEvent instanceof HistoricActivityInstanceEventEntity) {
			HistoricActivityInstanceEventEntity activityInstanceEventEntity = (HistoricActivityInstanceEventEntity) historyEvent;

			if (historyEvent.getEventType().equals(HistoryEventTypes.ACTIVITY_INSTANCE_START.getEventName())
					|| historyEvent.getEventType().equals(HistoryEventTypes.ACTIVITY_INSTANCE_END.getEventName())) {

				List<String> relevantActivityTypes = new ArrayList<>();
				relevantActivityTypes.add(ActivityTypes.TASK_USER_TASK);
				relevantActivityTypes.add(ActivityTypes.INTERMEDIATE_EVENT_MESSAGE);
				relevantActivityTypes.add(ActivityTypes.INTERMEDIATE_EVENT_TIMER);

				if (relevantActivityTypes.contains(activityInstanceEventEntity.getActivityType())) {
					log.info("Received <" + historyEvent.getEventType() + "> event for <"
							+ activityInstanceEventEntity.getActivityType() + "> with activityId <"
							+ activityInstanceEventEntity.getActivityId() + ">");
				}
			}
		} else if (historyEvent instanceof HistoricExternalTaskLogEntity) {
			HistoricExternalTaskLogEntity externalTaskLogEvent = (HistoricExternalTaskLogEntity) historyEvent;

			log.info("Received <" + historyEvent.getEventType() + "> event for external task with activityId <"
					+ externalTaskLogEvent.getActivityId() + ">");
		} else if (historyEvent instanceof HistoricIncidentEventEntity) {

			// incident occured....
		}
	}
}
