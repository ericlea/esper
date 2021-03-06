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

package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.expression.core.ExprNodeUtility;
import com.espertech.esper.epl.expression.core.ExprValidationException;
import com.espertech.esper.epl.expression.prev.ExprPreviousNode;
import com.espertech.esper.epl.expression.prev.ExprPreviousNodePreviousType;
import com.espertech.esper.util.support.SupportExprValidationContextFactory;
import junit.framework.TestCase;
import com.espertech.esper.supportunit.epl.SupportExprNodeFactory;
import com.espertech.esper.supportunit.epl.SupportExprNode;

public class TestExprPreviousNode extends TestCase {
    private ExprPreviousNode prevNode;

    public void setUp() throws Exception
    {
        prevNode = SupportExprNodeFactory.makePreviousNode();
    }

    public void testGetType()  throws Exception
    {
        assertEquals(Double.class, prevNode.getType());
    }

    public void testValidate() throws Exception
    {
        prevNode = new ExprPreviousNode(ExprPreviousNodePreviousType.PREV);

        // No subnodes: Exception is thrown.
        tryInvalidValidate(prevNode);

        // singe child node not possible, must be 2 at least
        prevNode.addChildNode(new SupportExprNode(new Integer(4)));
        tryInvalidValidate(prevNode);
    }

    public void testEquals()  throws Exception
    {
        ExprPreviousNode node1 = new ExprPreviousNode(ExprPreviousNodePreviousType.PREV);
        assertTrue(node1.equalsNode(prevNode));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("prev(s1.intPrimitive,s1.doublePrimitive)", ExprNodeUtility.toExpressionStringMinPrecedenceSafe(prevNode));
    }

    private void tryInvalidValidate(ExprPreviousNode exprPrevNode) throws Exception
    {
        try {
            exprPrevNode.validate(SupportExprValidationContextFactory.makeEmpty());
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
