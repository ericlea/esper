/**************************************************************************************
 * Copyright (C) 2006-2015 EsperTech Inc. All rights reserved.                        *
 * http://www.espertech.com/esper                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.metrics.instrumentation.InstrumentationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Index for filter parameter constants to match using the equals (=) operator.
 * The implementation is based on a regular HashMap.
 */
public final class FilterParamIndexNotEquals extends FilterParamIndexNotEqualsBase
{
    public FilterParamIndexNotEquals(FilterSpecLookupable lookupable, ReadWriteLock readWriteLock) {
        super(lookupable, readWriteLock, FilterOperator.NOT_EQUAL);
    }

    public final void matchEvent(EventBean theEvent, Collection<FilterHandle> matches)
    {
        Object attributeValue = lookupable.getGetter().get(theEvent);
        if (InstrumentationHelper.ENABLED) { InstrumentationHelper.get().qFilterReverseIndex(this, attributeValue);}

        if (attributeValue == null) {   // null cannot match any other value, not even null (use "is" or "is not", i.e. null != null returns null)
            if (InstrumentationHelper.ENABLED) { InstrumentationHelper.get().aFilterReverseIndex(false);}
            return;
        }

        // Look up in hashtable
        constantsMapRWLock.readLock().lock();
        try {
            for(Map.Entry<Object, EventEvaluator> entry : constantsMap.entrySet())
            {
                if (entry.getKey() == null)
                {
                    continue;   // null-value cannot match, not even null (use "is" or "is not", i.e. null != null returns null)
                }

                if (!entry.getKey().equals(attributeValue))
                {
                    entry.getValue().matchEvent(theEvent, matches);
                }
            }
        }
        finally {
            constantsMapRWLock.readLock().unlock();
        }

        if (InstrumentationHelper.ENABLED) { InstrumentationHelper.get().aFilterReverseIndex(null);}
    }

    private static final Logger log = LoggerFactory.getLogger(FilterParamIndexNotEquals.class);
}
