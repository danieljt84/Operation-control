select   dt.promoter,dt."date",a.description,a.situation from data_task dt 
inner join data_task_tasks dtt on dtt.data_task_id = dt.id
inner join task t on t.id = dtt.tasks_id
inner join task_activities ta  on ta.task_id  = t.id 
inner join activity a  on a.id  = ta.activities_id
where a.situation <> 'completa' and dt."date" ='2022-04-06';