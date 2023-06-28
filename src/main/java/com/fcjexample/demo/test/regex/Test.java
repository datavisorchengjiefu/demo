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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private static final Pattern returnStatement = Pattern.compile("return (.*);");
    private static final Pattern conditionStatement = Pattern.compile(Pattern.quote("@CONDITION"));
    private static final String embeddedRuleVelocityTemplate =
            "for (String featureVal : $1) {\n"
                    + "            if (@CONDITION) {\n"
                    + "                return true;\n"
                    + "            }\n"
                    + "        }\n"
                    + "        return false;";
    private static final Pattern updateVelocityPattern = Pattern
            .compile("[\\?\\&]updateVelocity=(.*?)(&|$)");
    private static final Pattern pattern = Pattern.compile("\\?(.*?)=");

    public static void main(String[] args) throws Exception {

        convertTimeRange("");

        //        Pattern s3Pattern = Pattern.compile("s3a?://");
        Pattern s3Pattern = Pattern.compile("^s3[an]://([^/]+)/(.*)$");
        String s3String01 = "gegee g s3a://datavisor-qa-";
        String s3String02 = "dan20220222/DATASET/dataset_1/dcube_dataset.jsons3://get";
        String s3String03 = "s3a://datavisor-prod-tenant/cronferryrawdata";
        Matcher matcherS3 = s3Pattern.matcher(s3String01);
        logger.info("s3 find {}", matcherS3.find());
        matcherS3 = s3Pattern.matcher(s3String02);
        logger.info("s3 find {}", matcherS3.find());
        matcherS3 = s3Pattern.matcher(s3String03);
        logger.info("s3 find {}", matcherS3.find());

        Pattern returnStatement01 = Pattern.compile("&&(.*)&&");
        String test01 = "SELECT &&DISTINCT ON (\"userId\",\"card\")&& \"trust\",\"eventId\",\"timeAccessed\",\"userId\",\"card\" from test.ext_data_source_1676559588347_extds_data where \"timeAccessed\" between ? and ? and \"timeAccessed\" <= 1676559776682 order by \"timeAccessed\" DESC. ";
        Matcher matcher = returnStatement01.matcher(test01);
        logger.info("find {}", matcher.find());
        logger.info("find 0 is {}", matcher.group(0));
        logger.info("find 1 is {}", matcher.group(1));

        //        String script = "[{\"u\":{\"UserName\":\"CYTEST\",\"UserId\":\"111111111\",\"appExternKey\":\"dvTestExternKey1\"},\"en\":{\"d604\":\"-0.3187561,-0.26013184,9.816742\",\"d605\":\"26\"},\"p\":{\"AD\":6891438},\"ev\":{\"u\":\"chenyue\",\"n\":\"testEvent\",\"tt\":1622611602238,\"t\":1,\"tn\":0,\"cuuid\":\"CUAVUD2xX8Wj1FD1G0bD59u6qjvnb3Q_s3f1wumWTe7UZR\",\"i\":4,\"sid\":\"1__41HJ4b235BouOOAo-FDtI42en5Dz1k-nHGD-1kK6Oz9iecBq9RDLkT-GM_e8c2u2FD20NSeuqq3_4Wg2O_SDNQ\",\"stt\":1622611604664,\"s\":\"a\",\"ptt\":1210688909},\"d\":{\"d39\":\"90\",\"d8\":\"44847685632\",\"d42\":\"OPPO\",\"d44\":\"0\",\"n1\":\"10.1.11.252\",\"d78\":\"a95ad671-ba25-4d3f-8b01-fc13728d2342\",\"d15\":\"Android\",\"d16\":\"10\",\"d94\":\"1622611602437\",\"c8\":\"1\",\"d603\":\"4\",\"c9\":\"0\",\"d601\":\"1983168\",\"d51\":\"1\",\"a9\":\"1621217160891\",\"d54\":\"1\",\"d24\":\"{\\\"ringermode\\\":\\\"0\\\",\\\"call\\\":\\\"5\\\",\\\"system\\\":\\\"0\\\",\\\"ring\\\":\\\"0\\\",\\\"music\\\":\\\"10\\\",\\\"alarm\\\":\\\"12\\\"}\",\"d29\":\"DataVisor-SH\",\"c201\":\"0\",\"c203\":\"0\",\"c208\":\"0\",\"n5\":\"58.246.36.146\",\"d33\":\"87\",\"d40\":\"4\",\"d76\":\"2340*1080\",\"v1\":\"DD_z7N-1FBvBah1FyTdZzduZb757D6sz1rPEHLXbPCCBLc\",\"c10\":\"0\",\"v2\":\"DT_1ql2ayJ5j7CDrB-KgS3cHQkJF2oQR38DTlBCsIiz4Jc\",\"n4\":\"10.1.11.1\",\"c3\":\"0\",\"d30\":\"1\",\"d602\":\"0\",\"c1\":\"0\"}}]";
        //        String result = embedRuleInScript(script, "", "");
        //
        //        System.out.println(result);
        String url = "http://localhost:8080/feature?updateVelocity=tr-ue&force=02&age=03";
        String resultUrl = generatePathAndSetUpdateVelocity(url);
        logger.info("resultUrl is {}", resultUrl);

        url = "http://localhost:8080/feature?updateVelocity=tr-ue";
        resultUrl = generatePathAndSetUpdateVelocity(url);
        logger.info("resultUrl is {}", resultUrl);

        url = "http://localhost:8080/feature?test=02&updateVelocity=tr-ue&force=02&age=03";
        resultUrl = generatePathAndSetUpdateVelocity(url);
        logger.info("resultUrl is {}", resultUrl);

        url = "http://localhost:8080/feature?test=02&updateVelocity=tr-ue";
        resultUrl = generatePathAndSetUpdateVelocity(url);
        logger.info("resultUrl is {}", resultUrl);

        logger.info("==================");
        url = "http://localhost:8080/feature?test=02";
        resultUrl = generatePathAndSetUpdateVelocity(url);
        logger.info("resultUrl is {}", resultUrl);

        url = "http://localhost:8080/feature";
        resultUrl = generatePathAndSetUpdateVelocity(url);
        logger.info("resultUrl is {}", resultUrl);
    }

    public static String convertTimeRange(String timeRange) {
        String timeRangeTemp = "hahaPREVIOUS_3_WEEK";
        String regex = "PREVIOUS_(\\d+)_(WEEK|MONTH|QUARTER)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(timeRangeTemp);
        boolean matchFound = matcher.find();
        if (matchFound) {
            System.out.println("Match found");
        } else {
            System.out.println("Match not found");
        }

        logger.info("group 0 is {}. ", matcher.group(0));
        logger.info("group 1 is {}. ", matcher.group(1));
        logger.info("group 2 is {}. ", matcher.group(2));

        // Define a regular expression pattern to match the input string
        pattern = Pattern.compile("(\\d+)\\s+(days?|hours?|minutes?)");
        matcher = pattern.matcher(timeRangeTemp);

        int totalMinutes = 0;
        while (matcher.find()) {
            int amount = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            // Convert the time unit to minutes and add to total
            switch (unit) {
            case "day":
            case "days":
                totalMinutes += amount * 24 * 60;
                break;
            case "hour":
            case "hours":
                totalMinutes += amount * 60;
                break;
            case "minute":
            case "minutes":
                totalMinutes += amount;
                break;
            default:
                // Handle unsupported units
                throw new IllegalArgumentException("Unsupported time unit: " + unit);
            }
        }
        return "";
    }

    private void test() {
        Pattern pattern = Pattern.compile("w3schools", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("Visit W3Schools!");
        boolean matchFound = matcher.find();
        if (matchFound) {
            System.out.println("Match found");
        } else {
            System.out.println("Match not found");
        }

        pattern = Pattern.compile("\\?(.*)=");
        String input = "http://localhost:8080/feature/?tagName=test&force=02&age=03";
        matcher = pattern.matcher(input);
        logger.info("result 1 is {}", matcher.find());// true
        pattern = Pattern.compile("\\?updateVelocity=(.*)");
        matcher = pattern.matcher(input);
        logger.info("result 2 is {}", matcher.find());// false
        input = "http://localhost:8080/feature/?updateVelocity=true";

        logger.info("==================11111111================");
        pattern = Pattern.compile("[\\?\\&]updateVelocity=(.*?)(&|$)");
        input = "http://localhost:8080/feature/?updateVelocity=tr-ue&force=02&age=03";
        matcher = pattern.matcher(input);
        logger.info("result 3 is {}", matcher.find());
        logger.info("group is {}", matcher.group(0));
        logger.info("group is {}", matcher.group(1));
        logger.info("group is {}", matcher.group(2));

        logger.info("==================11111111================");
        input = "http://localhost:8080/feature/?updateVelocity=tru-e";
        matcher = pattern.matcher(input);
        logger.info("result 3 is {}", matcher.find());
        logger.info("group is {}", matcher.group(0));
        logger.info("group is {}", matcher.group(1));
        logger.info("group is {}", matcher.group(2));

        logger.info("==================11111111================");
        input = "http://localhost:8080/feature/?test=2&updateVelocity=tr-ue&force=02&age=03";
        matcher = pattern.matcher(input);
        logger.info("result 3 is {}", matcher.find());
        logger.info("group is {}", matcher.group(0));
        logger.info("group is {}", matcher.group(1));
        logger.info("group is {}", matcher.group(2));

        logger.info("==================11111111================");
        input = "http://localhost:8080/feature/?test=2&updateVelocity=tr-ue";
        matcher = pattern.matcher(input);
        logger.info("result 3 is {}", matcher.find());
        logger.info("group is {}", matcher.group(0));
        logger.info("group is {}", matcher.group(1));
        logger.info("group is {}", matcher.group(2));
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

    public static String generatePathAndSetUpdateVelocity(String url) {
        String resultUrl = url;// todo
        Matcher updateVelocityMatcher = updateVelocityPattern.matcher(url);
        Matcher matcher = pattern.matcher(url);
        if (updateVelocityMatcher.find()) {
            //                        resultUrl = url.replace(updateVelocityMatcher.group(1), "false");
            //            resultUrl = url.replaceAll(updateVelocityMatcher.group(1), "false");
            resultUrl = matcher.replaceAll("false");
        } else if (matcher.find()) {
            resultUrl += "&updateVelocity=false";
        } else {
            resultUrl += "?updateVelocity=false";
        }

        return resultUrl;
    }

}
