# Create Or Update EventTransformer

## Api
### Create Api
````
@PostMapping("/{tenant}/event_transformer/create")
````
RequestBody
````
{
   "topic":"dedge.testxxx.tenantName",
   "script":"xxxxxx"
}
````

### Update Api
````
    @RequestMapping("/{tenant}/event_transformer/update/{id}")
````
RequestBody
````
{
   "topic":"dedge.testxxx.tenantName",
   "script":"xxxxxx"
}
````

## FPC
EventTransformerApplication

# Update Api(Insert data to Cassandra table)

````
@PostMapping("/{tenant}/transformer/event/accumulate/external")
````

````
POST /api-1.0-SNAPSHOT/tenantName/transformer/event/accumulate/external HTTP/1.1
Host: fpHost
Content-Type: application/json

{
    "topic": "dedge.testxxx.tenantName",
    "inputObject": "[{\"u\":{\"UserName\":\"CYTEST\",\"UserId\":\"111111111\",\"appExternKey\":\"dvTestExternKey1\"},\"en\":{\"d604\":\"-0.3187561,-0.26013184,9.816742\",\"d605\":\"26\"},\"p\":{\"AD\":6891438},\"ev\":{\"u\":\"chenyue\",\"n\":\"testEvent\",\"tt\":1622611602238,\"t\":1,\"tn\":0,\"cuuid\":\"CUAVUD2xX8Wj1FD1G0bD59u6qjvnb3Q_s3f1wumWTe7UZR\",\"i\":4,\"sid\":\"1__41HJ4b235BouOOAo-FDtI42en5Dz1k-nHGD-1kK6Oz9iecBq9RDLkT-GM_e8c2u2FD20NSeuqq3_4Wg2O_SDNQ\",\"stt\":1622611604664,\"s\":\"a\",\"ptt\":1210688909},\"d\":{\"d39\":\"90\",\"d8\":\"44847685632\",\"d42\":\"OPPO\",\"d44\":\"0\",\"n1\":\"10.1.11.252\",\"d78\":\"a95ad671-ba25-4d3f-8b01-fc13728d2342\",\"d15\":\"Android\",\"d16\":\"10\",\"d94\":\"1622611602437\",\"c8\":\"1\",\"d603\":\"4\",\"c9\":\"0\",\"d601\":\"1983168\",\"d51\":\"1\",\"a9\":\"1621217160891\",\"d54\":\"1\",\"d24\":\"{\\\"ringermode\\\":\\\"0\\\",\\\"call\\\":\\\"5\\\",\\\"system\\\":\\\"0\\\",\\\"ring\\\":\\\"0\\\",\\\"music\\\":\\\"10\\\",\\\"alarm\\\":\\\"12\\\"}\",\"d29\":\"DataVisor-SH\",\"c201\":\"0\",\"c203\":\"0\",\"c208\":\"0\",\"n5\":\"58.246.36.146\",\"d33\":\"87\",\"d40\":\"4\",\"d76\":\"2340*1080\",\"v1\":\"DD_z7N-1FBvBah1FyTdZzduZb757D6sz1rPEHLXbPCCBLc\",\"c10\":\"0\",\"v2\":\"DT_1ql2ayJ5j7CDrB-KgS3cHQkJF2oQR38DTlBCsIiz4Jc\",\"n4\":\"10.1.11.1\",\"c3\":\"0\",\"d30\":\"1\",\"d602\":\"0\",\"c1\":\"0\"}}]"
}
````

