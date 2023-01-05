/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.unomi.services.mergers;

import org.apache.unomi.api.Profile;
import org.apache.unomi.api.PropertyMergeStrategyExecutor;
import org.apache.unomi.api.PropertyType;
import org.apache.unomi.persistence.spi.PropertyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class AddPropertyMergeStrategyExecutor implements PropertyMergeStrategyExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AddPropertyMergeStrategyExecutor.class.getName());

    public boolean mergeProperty(String propertyName, PropertyType propertyType, List<Profile> profilesToMerge, Profile targetProfile) {
        logger.debug("propertyName: " + propertyName);
        logger.debug("propertyType: " + propertyType);
        logger.debug("profilesToMerge: " + profilesToMerge);
        logger.debug("targetProfile: " + targetProfile);

        Object targetPropertyValue = targetProfile.getNestedProperty(propertyName);
        if (targetPropertyValue == null)
            targetPropertyValue = targetProfile.getNestedProperty("properties." + propertyName);

        Object result = targetPropertyValue;
        Map<String, Object> properties = targetProfile.getProperties();
        logger.debug("result: " + result);

        if (result == null) {
            if (propertyType.getValueTypeId() != null) {
                if (propertyType.getValueTypeId().equals("integer")) {
                    result = new Integer(0);
                } else if (propertyType.getValueTypeId().equals("long")) {
                    result = new Long(0);
                } else if (propertyType.getValueTypeId().equals("double")) {
                    result = new Double(0.0);
                } else if (propertyType.getValueTypeId().equals("float")) {
                    result = new Float(0.0);
                } else {
                    result = new Long(0);
                }
            } else {
                result = new Long(0);
            }
        }

        for (Profile profileToMerge : profilesToMerge) {

            Object property = profileToMerge.getNestedProperty(propertyName);
            if (targetPropertyValue == null)
                property = profileToMerge.getNestedProperty("properties." + propertyName);

            logger.debug("property: " + property);
            if (property == null) {
                continue;
            }

            if (propertyType != null) {
                if (propertyType.getValueTypeId().equals("integer") || (property instanceof Integer)) {
                    result = (Integer) result + (Integer) property;
                } else if (propertyType.getValueTypeId().equals("long") || (property instanceof Long)) {
                    result = (Long) result + (Long) property;
                } else if (propertyType.getValueTypeId().equals("double") || (property instanceof Double)) {
                    result = (Double) result + (Double) property;
                } else if (propertyType.getValueTypeId().equals("float") || (property instanceof Float)) {
                    result = (Float) result + (Float) property;
                } else {
                    result = (Long) result + Long.parseLong(property.toString());
                }
            } else {
                result = (Long) result + Long.parseLong(property.toString());
            }

        }

        logger.debug("targetPropertyValue: " + targetPropertyValue);
        logger.debug("final result: " + result);

        if (targetPropertyValue == null || !targetPropertyValue.equals(result)) {
            logger.debug("properties: " + properties);
            logger.debug("final result: " + result);
            PropertyHelper.setProperty(properties, propertyName, result, "alwaysSet");
            return true;
        }
        return false;
    }
}
