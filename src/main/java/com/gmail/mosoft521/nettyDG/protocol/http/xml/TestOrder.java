package com.gmail.mosoft521.nettyDG.protocol.http.xml;

import com.gmail.mosoft521.nettyDG.protocol.http.xml.pojo.Order;
import com.gmail.mosoft521.nettyDG.protocol.http.xml.pojo.OrderFactory;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class TestOrder {

    private final static String CHARSET_NAME = "UTF-8";
    private IBindingFactory factory = null;
    private StringWriter writer = null;
    private StringReader reader = null;

    public static void main(String[] args) throws JiBXException, IOException {
        TestOrder test = new TestOrder();
        Order order = OrderFactory.create(123);
        String body = test.encode2Xml(order);
        Order order2 = test.decode2Order(body);
        System.out.println(order2);
    }

    private String encode2Xml(Order order) throws JiBXException, IOException {
        factory = BindingDirectory.getFactory(Order.class);
        writer = new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(order, CHARSET_NAME, null, writer);
        String xmlStr = writer.toString();
        writer.close();
        System.out.println(xmlStr.toString());
        return xmlStr;
    }

    private Order decode2Order(String xmlBody) throws JiBXException {
        reader = new StringReader(xmlBody);
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        Order order = (Order) uctx.unmarshalDocument(reader);
        return order;
    }
}