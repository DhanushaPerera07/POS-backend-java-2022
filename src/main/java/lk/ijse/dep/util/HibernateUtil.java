/*
 * MIT License
 *
 * Copyright (c) 2022 Dhanusha Perera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lk.ijse.dep.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.cfg.AvailableSettings;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

import static lk.ijse.dep.constant.CommonConstant.PERSISTENCE_UNIT_NAME;
import static lk.ijse.dep.util.DatabaseHelper.getDataSource;
import static lk.ijse.dep.util.DatabaseHelper.getMyProperties;

public class HibernateUtil {
    private static EntityManagerFactory emf = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        BasicDataSource bds = getDataSource();
        Map<String, Object> propertiesAsHashMap = new HashMap<>();
        propertiesAsHashMap.put(AvailableSettings.DATASOURCE, bds);

        System.out.println("***************************");
        for (Object elm :
                getMyProperties().keySet()) {
            String key = elm.toString().trim();
            if (!key.startsWith("javax.persistence")) {
                /* if key is not starting with javax.persistence.
                then we need to add the configuration to the properties instance. */
                propertiesAsHashMap.put(key, getMyProperties().getProperty(key));
            }
            System.out.println(elm);
        }

        EntityManagerFactory em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, propertiesAsHashMap);
        return em;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

//    private static Properties readProperties() {
//        Properties properties = new Properties();
//        try {
//            properties.load(HibernateUtil.class.getResourceAsStream("/application.properties"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return properties;
//    }
}
