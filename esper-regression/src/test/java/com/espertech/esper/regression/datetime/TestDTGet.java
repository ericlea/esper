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

package com.espertech.esper.regression.datetime;

import com.espertech.esper.client.*;
import com.espertech.esper.client.scopetest.EPAssertionUtil;
import com.espertech.esper.client.scopetest.SupportUpdateListener;
import com.espertech.esper.metrics.instrumentation.InstrumentationHelper;
import com.espertech.esper.supportregression.bean.SupportDateTime;
import com.espertech.esper.supportregression.bean.SupportTimeStartEndA;
import com.espertech.esper.supportregression.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.supportregression.client.SupportConfigFactory;
import junit.framework.TestCase;

public class TestDTGet extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportDateTime", SupportDateTime.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        if (InstrumentationHelper.ENABLED) { InstrumentationHelper.startTest(epService, this.getClass(), getName());}
        listener = new SupportUpdateListener();
    }

    public void tearDown() {
        if (InstrumentationHelper.ENABLED) { InstrumentationHelper.endTest();}
        listener = null;
    }

    public void testInput() {

        String[] fields = "val0,val1,val2,val3,val4".split(",");
        String epl = "select " +
                "utildate.get('month') as val0," +
                "msecdate.get('month') as val1," +
                "caldate.get('month') as val2, " +
                "localdate.get('month') as val3, " +
                "zoneddate.get('month') as val4 " +
                " from SupportDateTime";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmt.getEventType(), fields, new Class[]{Integer.class, Integer.class, Integer.class, Integer.class, Integer.class});

        String startTime = "2002-05-30T09:00:00.000";
        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        EPAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{4, 4, 4, 5, 5});

        // try event as input
        ConfigurationEventTypeLegacy configBean = new ConfigurationEventTypeLegacy();
        configBean.setStartTimestampPropertyName("msecdateStart");
        configBean.setEndTimestampPropertyName("msecdateEnd");
        epService.getEPAdministrator().getConfiguration().addEventType("SupportTimeStartEndA", SupportTimeStartEndA.class.getName(), configBean);

        stmt.destroy();
        epl = "select abc.get('month') as val0 from SupportTimeStartEndA as abc";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        
        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("A0", startTime, 0));
        EPAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "val0".split(","), new Object[]{4});

        // test "get" method on object is preferred
        epService.getEPAdministrator().getConfiguration().addEventType(MyEvent.class);
        epService.getEPAdministrator().createEPL("select e.get() as c0, e.get('abc') as c1 from MyEvent as e").addListener(listener);
        epService.getEPRuntime().sendEvent(new MyEvent());
        EPAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "c0,c1".split(","), new Object[]{1, 2});
    }

    public void testFields() {

        String[] fields = "val0,val1,val2,val3,val4,val5,val6,val7".split(",");
        String eplFragment = "select " +
                "utildate.get('msec') as val0," +
                "utildate.get('sec') as val1," +
                "utildate.get('minutes') as val2," +
                "utildate.get('hour') as val3," +
                "utildate.get('day') as val4," +
                "utildate.get('month') as val5," +
                "utildate.get('year') as val6," +
                "utildate.get('week') as val7" +
                " from SupportDateTime";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class});

        String startTime = "2002-05-30T09:01:02.003";
        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        EPAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{3, 2, 1, 9, 30, 4, 2002, 22});
    }

    public static class MyEvent {
        public int get() {
            return 1;
        }

        public int get(String abc) {
            return 2;
        }
    }
}
