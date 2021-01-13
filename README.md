# FileContentIndexing # EnterpriseSearch # ApacheTika #MultiPartUpload
Index content of file to elastic app search by uploading file using multipart file

Steps to get started:
1. Download Docker Desktop and assign 4GB ram to it: https://www.docker.com/products/docker-desktop ( Make sure your system has atleast 8GB RAM )
2. Execute command in commmand line : docker-compose up
3. App-search will start running in http://localhost:3002
4. Create a engine and update engine name and access key in application.properites
5. Open http://localhost:8080/swagger-ui.html and try /uploadfile 
6. After file uploaded. you can search the within file content using /search endpoint

References:
Elastic appsearch documentation: https://www.elastic.co/guide/en/app-search/current/index.html
