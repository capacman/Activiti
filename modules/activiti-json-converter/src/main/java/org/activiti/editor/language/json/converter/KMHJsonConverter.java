package org.activiti.editor.language.json.converter;

import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.ServiceTask;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class KMHJsonConverter extends BaseBpmnJsonConverter {
	private static final String XML_KMHTASK_DELEGATE_CLASS = "${kmhTaskDelegate}";

	public static void fillTypes(
			Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap,
			Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {

		fillJsonTypes(convertersToBpmnMap);
	}

	public static void fillJsonTypes(
			Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
		convertersToBpmnMap.put(STENCIL_TASK_KMH, KMHJsonConverter.class);
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
		task.setImplementation(XML_KMHTASK_DELEGATE_CLASS);
		return task;
	}

	@Override
	protected String getStencilId(FlowElement flowElement) {
		return STENCIL_TASK_KMH;
	}

}
