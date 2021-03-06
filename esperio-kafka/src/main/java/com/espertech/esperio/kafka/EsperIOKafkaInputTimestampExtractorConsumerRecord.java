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

package com.espertech.esperio.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EsperIOKafkaInputTimestampExtractorConsumerRecord implements EsperIOKafkaInputTimestampExtractor {
    public long extract(ConsumerRecord<Object, Object> record) {
        return record.timestamp();
    }
}
