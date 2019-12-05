package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/5 17:00
 */
public class JobTransViewPK  implements Serializable {

    private Integer taskId;
    @Id
    @Column(name = "task_id")
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

}