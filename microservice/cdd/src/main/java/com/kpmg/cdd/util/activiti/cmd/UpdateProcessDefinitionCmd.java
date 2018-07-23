/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kpmg.cdd.util.activiti.cmd;


import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Joram Barrez
 */
public class UpdateProcessDefinitionCmd implements Command<Void> {

 private ProcessDefinitionEntity processDefinitionEntity;
 private JdbcTemplate jdbcTemplate;

  public UpdateProcessDefinitionCmd(JdbcTemplate jdbcTemplate,ProcessDefinitionEntity processDefinitionEntity) {
      this.jdbcTemplate=jdbcTemplate;
    this.processDefinitionEntity=processDefinitionEntity;
  }

  public Void execute(CommandContext commandContext) {
    jdbcTemplate.update("update ACT_RE_PROCDEF set DESCRIPTION_=? where ID_=?",
                    processDefinitionEntity.getDescription(), processDefinitionEntity.getId());
    return null;
  }
}
