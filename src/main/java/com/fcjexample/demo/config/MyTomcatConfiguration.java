/*************************************************************************
 *
 * Copyright (c) 2021, DATAVISOR, INC.
 * All rights reserved.
 * __________________
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of DataVisor, Inc.
 * The intellectual and technical concepts contained
 * herein are proprietary to DataVisor, Inc. and
 * may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from DataVisor, Inc.
 */

package com.fcjexample.demo.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MyTomcatConfiguration {

    private static final int httpPort = 9009;
    private static final int sslPort = 9010;

    //    @Bean
    public TomcatServletWebServerFactory servletContainerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                //设置安全性约束
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("NONE");
                //设置约束条件
                SecurityCollection collection = new SecurityCollection();
                //拦截所有请求
                collection.addPattern("/hello/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        //设置将分配给通过此连接器接收到的请求的方案
        connector.setScheme("http");

        //true： http使用http, https使用https;
        //false： http重定向到https;
        connector.setSecure(false);

        //设置监听请求的端口号，这个端口不能其他已经在使用的端口重复，否则会报错
        connector.setPort(httpPort);

        //重定向端口号(非SSL到SSL)
        connector.setRedirectPort(sslPort);

        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> sslConnectorCustomizer() {
        return (tomcat) -> tomcat.addAdditionalTomcatConnectors(createSslConnector());
    }

    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        try {
            //            URL keystore = ResourceUtils.getURL("keystore");
            //            URL truststore = ResourceUtils.getURL("truststore");
            //            connector.setScheme("https");
            //            connector.setSecure(true);
            //            connector.setPort(9444);
            //            protocol.setSSLEnabled(true);
            //            protocol.setKeystoreFile(keystore.toString());
            //            protocol.setKeystorePass("changeit");
            //            protocol.setTruststoreFile(truststore.toString());
            //            protocol.setTruststorePass("changeit");
            //            protocol.setKeyAlias("apitester");

            //定制化keep alive timeout，设置30秒内没有请求则服务端自动断开keep alive链接
            //            protocol.setKeepAliveTimeout(30000);
            //当客户端发送超过10000个请求则自动断开keep alive链接
            //            protocol.setMaxKeepAliveRequests(10000);
            protocol.setMaxConnections(2);
            connector.setPort(8088);
            return connector;
        } catch (Exception ex) {
            throw new IllegalStateException("Fail to create ssl connector", ex);
        }
    }

}


