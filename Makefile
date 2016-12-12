function-name=transform-geodata-clj
aws-region=eu-west-1
s3-bucket=davids-lambda-functions
# handler=hello::handler
handler=lamda_test_transform_clj.stream_handler

build: build-jar zip

build-jar: 
	lein uberjar

clean:
	rm -rf target
	rm $(function-name).zip

zip:
	zip $(function-name).zip target/lamda-test-transform-clj-0.1.0-SNAPSHOT-standalone.jar

upload-code:
	aws s3 cp $(function-name).zip s3://$(s3-bucket)

create-function:
	aws lambda create-function \
	--region $(aws-region) \
	--function-name $(function-name) \
	--zip-file fileb://./target/lamda-test-transform-clj-0.1.0-SNAPSHOT-standalone.jar \
	--role 'arn:aws:iam::593176282530:role/lambda-s3-execution-role' \
	--handler $(handler) \
	--memory 512 \
	--runtime java8

add-permission:
	aws lambda add-permission \
	--function-name $(function-name) \
	--region eu-west-1 \
	--statement-id some-unique-id \
	--action "lambda:InvokeFunction" \
	--principal s3.amazonaws.com \
	--source-arn arn:aws:s3:::davids-testbucket \
	--source-account 593176282530 \

update-function-code:
	aws lambda update-function-code \
	--function-name $(function-name) \
	--zip-file fileb://./target/lamda-test-transform-clj-0.1.0-SNAPSHOT-standalone.jar

invoke-function:
	aws lambda invoke \
	--invocation-type RequestResponse \
	--function-name $(function-name) \
	--region $(aws-region) \
	--log-type Tail \
	--payload file://resources/s3-put.json \
	outputfile.txt 

	cat outputfile.txt

delete-function:
	aws lambda delete-function \
	--function-name $(function-name)

install: build-jar zip create-function add-permission
reinstall: build-jar zip update-function-code
