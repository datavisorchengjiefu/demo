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

package com.fcjexample.demo.test.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    private static final Pattern returnStatement = Pattern.compile("return (.*);");
    private static final Pattern conditionStatement = Pattern.compile(Pattern.quote("@CONDITION"));
    private static final String embeddedRuleVelocityTemplate =
            "for (String featureVal : $1) {\n"
                    + "            if (@CONDITION) {\n"
                    + "                return true;\n"
                    + "            }\n"
                    + "        }\n"
                    + "        return false;";

    public static void main(String[] args) throws Exception {

        String script = "[{\"u\":{\"UserName\":\"CYTEST\",\"UserId\":\"111111111\",\"appExternKey\":\"dvTestExternKey1\"},\"en\":{\"d604\":\"-0.3187561,-0.26013184,9.816742\",\"d605\":\"26\"},\"p\":{\"AD\":6891438},\"ev\":{\"u\":\"chenyue\",\"n\":\"testEvent\",\"tt\":1622611602238,\"t\":1,\"tn\":0,\"cuuid\":\"CUAVUD2xX8Wj1FD1G0bD59u6qjvnb3Q_s3f1wumWTe7UZR\",\"i\":4,\"sid\":\"1__41HJ4b235BouOOAo-FDtI42en5Dz1k-nHGD-1kK6Oz9iecBq9RDLkT-GM_e8c2u2FD20NSeuqq3_4Wg2O_SDNQ\",\"stt\":1622611604664,\"s\":\"a\",\"ptt\":1210688909},\"d\":{\"d39\":\"90\",\"d8\":\"44847685632\",\"d42\":\"OPPO\",\"d44\":\"0\",\"n1\":\"10.1.11.252\",\"d78\":\"a95ad671-ba25-4d3f-8b01-fc13728d2342\",\"d15\":\"Android\",\"d16\":\"10\",\"d94\":\"1622611602437\",\"c8\":\"1\",\"d603\":\"4\",\"c9\":\"0\",\"d601\":\"1983168\",\"d51\":\"1\",\"a9\":\"1621217160891\",\"d54\":\"1\",\"d24\":\"{\\\"ringermode\\\":\\\"0\\\",\\\"call\\\":\\\"5\\\",\\\"system\\\":\\\"0\\\",\\\"ring\\\":\\\"0\\\",\\\"music\\\":\\\"10\\\",\\\"alarm\\\":\\\"12\\\"}\",\"d29\":\"DataVisor-SH\",\"c201\":\"0\",\"c203\":\"0\",\"c208\":\"0\",\"n5\":\"58.246.36.146\",\"d33\":\"87\",\"d40\":\"4\",\"d76\":\"2340*1080\",\"v1\":\"DD_z7N-1FBvBah1FyTdZzduZb757D6sz1rPEHLXbPCCBLc\",\"c10\":\"0\",\"v2\":\"DT_1ql2ayJ5j7CDrB-KgS3cHQkJF2oQR38DTlBCsIiz4Jc\",\"n4\":\"10.1.11.1\",\"c3\":\"0\",\"d30\":\"1\",\"d602\":\"0\",\"c1\":\"0\"}}]";
        String result = embedRuleInScript(script, "", "");

        System.out.println(result);

    }

    private static String embedRuleInScript(String script, String op, String value)
            throws Exception {
        String embeddedScript = null;

        Matcher m = returnStatement.matcher(script);
        if (!m.find()) {
            throw new RuntimeException("No return statement found in velocity feature script");
        }
        embeddedScript = m.replaceAll(embeddedRuleVelocityTemplate);

        String condition = "8condition88";
        m = conditionStatement.matcher(embeddedScript);
        embeddedScript = m.replaceAll(Matcher.quoteReplacement(condition));

        return embeddedScript;
    }

}
