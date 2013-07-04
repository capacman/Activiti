package com.globalmaksimum.bpmn.activiti;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class SimpleProcessTest {
	@Test
	public void startBookOrder() {
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();

		RuntimeService runtimeService = processEngine.getRuntimeService();
		IdentityService identityService = processEngine.getIdentityService();
		TaskService taskService = processEngine.getTaskService();
		RepositoryService repositoryService = processEngine
				.getRepositoryService();

		repositoryService.createDeployment()
				.addClasspathResource("bookorder.simple.bpmn20.xml").deploy();

		Map<String, Object> variableMap = new HashMap<String, Object>();

		variableMap.put("isbn", "123456");
		identityService.setAuthenticatedUserId("kermit");

		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("simplebookorder", variableMap);
		assertNotNull(processInstance.getId());
		assertNotNull(processInstance.getId());
		List<Task> taskList = taskService.createTaskQuery()
				.taskCandidateUser("kermit").list();
		assertEquals(1, taskList.size());
		System.out.println("found task " + taskList.get(0).getName());
		taskService.complete(taskList.get(0).getId());

	}
}
