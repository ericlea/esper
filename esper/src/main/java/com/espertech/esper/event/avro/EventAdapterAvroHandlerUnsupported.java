/*
 * *************************************************************************************
 *  Copyright (C) 2006-2015 EsperTech, Inc. All rights reserved.                       *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.event.avro;

import com.espertech.esper.client.*;
import com.espertech.esper.epl.expression.core.ExprEvaluator;
import com.espertech.esper.epl.expression.core.ExprValidationException;
import com.espertech.esper.event.*;
import com.espertech.esper.core.SelectExprProcessorRepresentationFactory;
import com.espertech.esper.util.TypeWidenerCustomizer;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

public class EventAdapterAvroHandlerUnsupported implements EventAdapterAvroHandler {
    public final static EventAdapterAvroHandlerUnsupported INSTANCE = new EventAdapterAvroHandlerUnsupported();

    private EventAdapterAvroHandlerUnsupported() {
    }

    public void init(ConfigurationEngineDefaults.EventMeta.AvroSettings avroSettings) {
    }

    public AvroSchemaEventType newEventTypeFromNormalized(EventTypeMetadata metadata, String eventTypeName, int typeId, EventAdapterServiceImpl eventAdapterService, Map<String, Object> properties, Annotation[] annotations, ConfigurationEventTypeAvro optionalConfig, EventType[] superTypes, Set<EventType> deepSuperTypes, String statementName, String engineURI) {
        return null;
    }

    public TypeWidenerCustomizer getTypeWidenerCustomizer(EventType eventType) {
        return null;
    }

    public AvroSchemaEventType newEventTypeFromSchema(EventTypeMetadata metadata, String eventTypeName, int typeId, EventAdapterServiceImpl eventAdapterService, ConfigurationEventTypeAvro requiredConfig, EventType[] supertypes, Set<EventType> deepSupertypes) {
        throw getUnsupported();
    }

    public EventBean adapterForTypeAvro(Object avroGenericDataDotRecord, EventType existingType) {
        throw getUnsupported();
    }

    public AvroSchemaEventType newEventTypeFromNormalized(EventTypeMetadata metadata, String eventTypeName, int typeId, EventAdapterServiceImpl eventAdapterService, Map<String, Object> properties, Annotation[] annotations, ConfigurationEngineDefaults.EventMeta.AvroSettings avroSettings, ConfigurationEventTypeAvro optionalConfig, EventType[] superTypes, Set<EventType> deepSuperTypes, String statementName, String engineURI) {
        throw getUnsupported();
    }

    public EventBeanManufacturer getEventBeanManufacturer(AvroSchemaEventType avroSchemaEventType, EventAdapterService eventAdapterService, WriteablePropertyDescriptor[] properties) {
        throw getUnsupported();
    }

    public EventBeanFactory getEventBeanFactory(EventType type, EventAdapterService eventAdapterService) {
        throw getUnsupported();
    }

    public void validateExistingType(EventType existingType, AvroSchemaEventType proposedType) {
        throw getUnsupported();
    }

    public SelectExprProcessorRepresentationFactory getOutputFactory() {
        throw getUnsupported();
    }

    public void avroCompat(EventType existingType, Map<String, Object> selPropertyTypes) throws ExprValidationException {
        throw getUnsupported();
    }

    public Object convertEvent(EventBean theEvent, AvroSchemaEventType targetType) {
        throw getUnsupported();
    }

    public TypeWidenerCustomizer getTypeWidenerCustomizer(ConfigurationEngineDefaults.EventMeta.AvroSettings avroSettings, EventType eventType) {
        throw getUnsupported();
    }

    private UnsupportedOperationException getUnsupported() {
        throw new UnsupportedOperationException("Esper-Avro is not part of your classpath");
    }
}
