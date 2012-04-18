/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package mx.edu.um.esu.general.social;

import javax.sql.DataSource;
import mx.edu.um.esu.general.dao.UsuarioDao;
import mx.edu.um.esu.general.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

/**
 * Simple little {@link ConnectionSignUp} command that allocates new userIds in
 * memory. Doesn't bother storing a user record in any local database, since
 * this quickstart just stores the user id in a cookie.
 *
 * @author Keith Donald
 */
public final class SimpleConnectionSignUp implements ConnectionSignUp {

    private static final Logger log = LoggerFactory.getLogger(SimpleConnectionSignUp.class);
    private JdbcTemplate jdbcTemplate;

    public SimpleConnectionSignUp(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String execute(Connection<?> connection) {
        log.debug("Ejecutando SimpleConnectionSignUp");
        String userId = null;
        try {
            userId = jdbcTemplate.queryForObject(
                    "select userid from userconnection where providerid = ? and provideruserid = ?",
                    String.class,
                    connection.getKey().getProviderId(),
                    connection.getKey().getProviderUserId());
        } catch (Exception e) {
            log.error("Tuvimos problemas en el connection signup", e);
        }

        return userId;
    }
}
