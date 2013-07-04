package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestMyProcess {

	private String filename = "/home/capacman/Data/programlama/bpmn/activiti-5.12.1/workspace/activiti/src/main/resources/MyProcess.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		repositoryService
				.createDeployment()
				.addInputStream("myProcess.bpmn20.xml",
						new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		List<ProcessInstance> instances = runtimeService
				.createProcessInstanceQuery().active().list();
		for (ProcessInstance processInstance : instances) {
			runtimeService.deleteProcessInstance(processInstance.getId(),
					"test");
		}

		System.out.println("total active process "
				+ runtimeService.createProcessInstanceQuery().active().count());
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByMessage("myMessage");
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		Thread.sleep(2000);
		while (true) {
			runtimeService.messageEventReceived(
					"myMessage2",
					runtimeService.createExecutionQuery()
							.processInstanceId(processInstance.getId())
							.messageEventSubscriptionName("myMessage2")
							.singleResult().getId());
			Thread.sleep(1000);
		}
	}
}