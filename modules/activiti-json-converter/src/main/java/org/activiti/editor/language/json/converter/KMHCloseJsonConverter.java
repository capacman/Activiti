package org.activiti.editor.language.json.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.IOParameter;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class KMHCloseJsonConverter extends BaseBpmnJsonConverter {
	private static final String PROPERTY_TERMINATE_ACTION = "terminate";

	public static void fillTypes(
			Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap,
			Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {

		fillJsonTypes(convertersToBpmnMap);
	}

	public static void fillJsonTypes(
			Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
		convertersToBpmnMap.put(STENCIL_TASK_KMHCLOSE,
				KMHCloseJsonConverter.class);
	}

	@Override
	protected void convertElementToJson(ObjectNode propertiesNode,
			FlowElement flowElement) {
		// TODO Auto-generated method stub

	}

	@Override
	protected FlowElement convertJsonToElement(JsonNode elementNode,
			JsonNode modelNode, Map<String, JsonNode> shapeMap) {
		CallActivity callActivity = new CallActivity();
		callActivity.setCalledElement("kmhcloseprocess");
		List<IOParameter> parameters = new ArrayList<IOParameter>();

		IOParameter applicant = new IOParameter();
		applicant.setSource("applicant");
		applicant.setTarget("applicant");

		IOParameter terminate = new IOParameter();
		terminate.setTarget("terminate");
		terminate.setSourceExpression("${"
				+ getPropertyValueAsString(PROPERTY_TERMINATE_ACTION,
						elementNode) + "}");

		parameters.add(applicant);
		parameters.add(terminate);
		callActivity.getInParameters().addAll(parameters);
		return callActivity;
	}

	@Override
	protected String getStencilId(FlowElement flowElement) {
		return STENCIL_TASK_KMHCLOSE;
	}

}
