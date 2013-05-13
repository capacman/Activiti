package org.activiti.editor.language.json.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.IOParameter;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class DelayedCountTaskJsonConverter extends BaseBpmnJsonConverter {
	private static final String PROPERTY_DELAYEDTASK_EXTRA = "extraproperties";
	private static final String PROPERTY_DELAYEDTASK_ACTION = "targetservice";
	private static final String PROPERTY_DELAYEDTASKDURATION_ACTION = "timerdurationdefinition";
	private static final String PROPERTY_TEASERDURATION = "teaserduration";
	private static final String PROPERTY_TEASERTEXT = "teasertext";

	public static void fillTypes(
			Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap,
			Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {

		fillJsonTypes(convertersToBpmnMap);
	}

	public static void fillJsonTypes(
			Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
		convertersToBpmnMap.put(STENCIL_TASK_DELAYEDCOUNT,
				DelayedCountTaskJsonConverter.class);
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

		callActivity.setCalledElement("delayedprocess");

		List<IOParameter> ioParameters = convertToIOParameters(
				PROPERTY_DELAYEDTASK_EXTRA, elementNode);

		IOParameter targetService = new IOParameter();
		targetService.setTarget("targetService");
		targetService.setSourceExpression("${'"
				+ getPropertyValueAsString(PROPERTY_DELAYEDTASK_ACTION,
						elementNode) + "'}");

		IOParameter duration = new IOParameter();
		duration.setTarget("duration");
		duration.setSourceExpression("${'"
				+ getPropertyValueAsString(PROPERTY_DELAYEDTASKDURATION_ACTION,
						elementNode) + "'}");

		IOParameter teaserDuration = new IOParameter();
		duration.setTarget("teaserDuration");
		if (StringUtils.isEmpty(getPropertyValueAsString(
				PROPERTY_TEASERDURATION, elementNode)))
			teaserDuration.setSourceExpression("${''}");
		else
			teaserDuration.setSourceExpression("${'"
					+ getPropertyValueAsString(PROPERTY_TEASERDURATION,
							elementNode) + "'}");
		IOParameter teaserText = new IOParameter();
		teaserText.setTarget("teaserText");
		teaserDuration.setSourceExpression("${'"
				+ getPropertyValueAsString(PROPERTY_TEASERTEXT, elementNode)
				+ "'}");

		IOParameter applicant = new IOParameter();
		applicant.setTarget("applicant");
		applicant.setSource("applicant");
		IOParameter count = new IOParameter();
		count.setSourceExpression("${'0'}");
		count.setTarget("count");
		
		ioParameters.add(count);
		ioParameters.add(applicant);
		ioParameters.add(teaserDuration);
		ioParameters.add(teaserText);
		ioParameters.add(targetService);
		ioParameters.add(duration);
		callActivity.getInParameters().addAll(ioParameters);
		return callActivity;
	}

	private List<IOParameter> convertToIOParameters(String propertyName,
			JsonNode elementNode) {
		List<IOParameter> ioParameters = new ArrayList<IOParameter>();
		JsonNode parametersNode = getProperty(propertyName, elementNode);
		if (parametersNode != null) {
			JsonNode itemsArrayNode = parametersNode
					.get(EDITOR_PROPERTIES_GENERAL_ITEMS);
			if (itemsArrayNode != null) {
				for (JsonNode itemNode : itemsArrayNode) {
					JsonNode sourceNode = itemNode
							.get(PROPERTY_IOPARAMETER_SOURCE);
					JsonNode sourceExpressionNode = itemNode
							.get(PROPERTY_IOPARAMETER_SOURCE_EXPRESSION);
					if ((sourceNode != null && StringUtils
							.isNotEmpty(sourceNode.asText()))
							|| (sourceExpressionNode != null && StringUtils
									.isNotEmpty(sourceExpressionNode.asText()))) {

						IOParameter parameter = new IOParameter();
						if (StringUtils.isNotEmpty(getValueAsString(
								PROPERTY_IOPARAMETER_SOURCE, itemNode))) {
							parameter.setSource(getValueAsString(
									PROPERTY_IOPARAMETER_SOURCE, itemNode));
						}
						if (StringUtils.isNotEmpty(getValueAsString(
								PROPERTY_IOPARAMETER_SOURCE_EXPRESSION,
								itemNode))) {
							parameter.setSourceExpression(getValueAsString(
									PROPERTY_IOPARAMETER_SOURCE_EXPRESSION,
									itemNode));
						}
						if (StringUtils.isNotEmpty(getValueAsString(
								PROPERTY_IOPARAMETER_TARGET, itemNode))) {
							parameter.setTarget(getValueAsString(
									PROPERTY_IOPARAMETER_TARGET, itemNode));
						}
						ioParameters.add(parameter);
					}
				}
			}
		}
		return ioParameters;
	}

	@Override
	protected String getStencilId(FlowElement flowElement) {
		return STENCIL_TASK_DELAYEDCOUNT;
	}
}
