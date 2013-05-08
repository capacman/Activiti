package org.activiti.editor.language.json.converter;

import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.ServiceTask;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class SalaryIntervalTaskJsonConverter extends BaseBpmnJsonConverter {
	private static final String XML_SALARYITERVALTASK_DELEGATE_CLASS = "${salaryIntervalTaskDelegate}";
	private static final String JSON_SALARYINTERVALTASK_INTERVAL = "salary_interval";
	private static final String XML_SALARYINTERVALTASK_INTERVAL = "salaryInterval";

	public static void fillTypes(
			Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap,
			Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {

		fillJsonTypes(convertersToBpmnMap);
	}

	public static void fillJsonTypes(
			Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
		convertersToBpmnMap.put(STENCIL_TASK_SALARYINTERVAL,
				SalaryIntervalTaskJsonConverter.class);
	}

	@Override
	protected void convertElementToJson(ObjectNode propertiesNode,
			FlowElement flowElement) {
		// TODO Auto-generated method stub

	}

	@Override
	protected FlowElement convertJsonToElement(JsonNode elementNode,
			JsonNode modelNode, Map<String, JsonNode> shapeMap) {
		ServiceTask task = new ServiceTask();
		task.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
		task.setImplementation(XML_SALARYITERVALTASK_DELEGATE_CLASS);
		String interval = getPropertyValueAsString(
				JSON_SALARYINTERVALTASK_INTERVAL, elementNode);
		if (StringUtils.isNotEmpty(interval)) {
			FieldExtension f1 = new FieldExtension();
			f1.setFieldName(XML_SALARYINTERVALTASK_INTERVAL);
			f1.setStringValue(interval);
			task.getFieldExtensions().add(f1);
		}
		return task;
	}

	@Override
	protected String getStencilId(FlowElement flowElement) {
		return STENCIL_TASK_SALARYINTERVAL;
	}

}
