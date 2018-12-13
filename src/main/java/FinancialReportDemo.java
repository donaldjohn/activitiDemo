import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;

import java.util.List;

public class FinancialReportDemo
{
    public static void main(String[] args)
    {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        repositoryService.createDeployment().addClasspathResource("FinancialReportProcess.bpmn20.xml").deploy();

        //runtimeService.startProcessInstanceByKey("financialReport");

        TaskService taskService = processEngine.getTaskService();

        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();

        System.out.println("Task count of accountancy: " + taskList.size());
        for (Task task : taskList
        )
        {
            System.out.println("[" + task.getId() + "] [" + task.getName() + "]");
            taskService.complete(task.getId());
        }

        List<Task> taskListOfMG = taskService.createTaskQuery().taskCandidateGroup("management").list();

        System.out.println("Task count of management: " + taskListOfMG.size());
        for (Task task : taskListOfMG
        )
        {
            System.out.println("[" + task.getId() + "] [" + task.getName() + "]");
            taskService.complete(task.getId());
        }

        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> taskInfos = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup("accountancy").list();

        for (HistoricTaskInstance historicTaskInstance :
                taskInfos)
        {
            System.out.println("Task [" + historicTaskInstance.getId() + "]  [StartTime: " + historicTaskInstance.getStartTime() + "] [ EndTime : " + historicTaskInstance.getEndTime() + "] [ Duration : " + historicTaskInstance.getDurationInMillis() + "]");
        }





    }
}
